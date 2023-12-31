package net.zepalesque.redux.world.tree.trunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.zepalesque.redux.util.function.HexFunction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BlightwillowTrunkPlacer extends TrunkPlacer {
   public static final Codec<BlightwillowTrunkPlacer> CODEC = RecordCodecBuilder.create((builder) -> {
      return trunkPlacerParts(builder).and(BlockStateProvider.CODEC.fieldOf("wood_provider").forGetter(t -> t.woodProvider))
              .and(IntProvider.CODEC.fieldOf("large_branch_base_height").forGetter(t -> t.largeHeight))
              .and(IntProvider.CODEC.fieldOf("small_branch_base_height").forGetter(t -> t.smallHeight)).apply(builder, BlightwillowTrunkPlacer::new);
   });

   public BlockStateProvider getWoodProvider() {
      return woodProvider;
   }

   public IntProvider getLargeHeight() {
      return largeHeight;
   }

   public IntProvider getSmallHeight() {
      return smallHeight;
   }

   private final BlockStateProvider woodProvider;
   private final IntProvider largeHeight;
   private final IntProvider smallHeight;
   public BlightwillowTrunkPlacer(int baseHeight, int heightA, int heightB, BlockStateProvider woodProvider, IntProvider largeHeight, IntProvider smallHeight) {
      super(baseHeight, heightA, heightB);
      this.woodProvider = woodProvider;

      this.largeHeight = largeHeight;
      this.smallHeight = smallHeight;
   }

   protected TrunkPlacerType<?> type() {
      return ReduxTrunkPlacers.BLIGHTWILLOW_TRUNK_PLACER.get();
   }

   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, RandomSource rand, int height, BlockPos origin, TreeConfiguration cfg) {
      setDirtAt(level, consumer, rand, origin.below(), cfg);


      BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
      final int[] middleHeight = {Integer.MAX_VALUE};
      Direction.Axis largeAxis = rand.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
      List<FoliagePlacer.FoliageAttachment> list =
   Direction.Plane.HORIZONTAL.stream().map(direction -> {
         int size = direction.getAxis() == largeAxis ? this.largeHeight.sample(rand) : this.smallHeight.sample(rand);
         if (size < middleHeight[0]) { middleHeight[0] = size - 1; }
         return new Branch(direction, direction.getAxis() == largeAxis, origin.relative(direction), size);
      }).map(branch -> branch.generate(this, level, consumer, rand, cfg, mutable)).toList();

      int trueHeight = middleHeight[0] == Integer.MAX_VALUE ? 5 : middleHeight[0];
      for(int i = 0; i < trueHeight - 1; ++i) {
         HexFunction<BlightwillowTrunkPlacer, LevelSimulatedReader, BiConsumer<BlockPos, BlockState>, RandomSource, BlockPos, TreeConfiguration, Boolean>
                 func = i == 0 || i == height - 1 ? BlightwillowTrunkPlacer::placeWood : BlightwillowTrunkPlacer::placeLog;
         func.apply(this, level, consumer, rand, origin.above(i), cfg);
      }
      return list;
   }

   public boolean placeLog(@NotNull LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, TreeConfiguration pConfig)
   {
      return super.placeLog(pLevel, pBlockSetter, pRandom, pPos, pConfig);
   }

   public boolean placeWood(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, TreeConfiguration pConfig) {
      return this.placeWood(pLevel, pBlockSetter, pRandom, pPos, pConfig, Function.identity());
   }

   public boolean placeWood(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, TreeConfiguration pConfig, Function<BlockState, BlockState> pPropertySetter) {
      if (this.validTreePos(pLevel, pPos)) {
         pBlockSetter.accept(pPos, pPropertySetter.apply(this.woodProvider.getState(pRandom, pPos)));
         return true;
      } else {
         return false;
      }



   }



   static class Branch {


      private final Direction direction;
      private final boolean isLarge;
      private final BlockPos start;
      private final int firstColumnSize;

      public Branch(Direction direction, boolean isLarge, BlockPos start, int firstColumnSize)
      {

         this.direction = direction;
         this.isLarge = isLarge;
         this.start = start;
         this.firstColumnSize = firstColumnSize;
      }

       /** Returns a {@link FoliagePlacer.FoliageAttachment} with a radius of 0, 1, or 2.
       *  0 denotes a small foliage piece,
       *  1 denotes a larger one facing Direction.Axis.X
       *  2 denotes a larger one facing Direction.Axis.Z
       */
      public FoliagePlacer.FoliageAttachment generate(BlightwillowTrunkPlacer placer, LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, RandomSource rand, TreeConfiguration config, BlockPos.MutableBlockPos mutable)
      {
         int secondColumnSize = Math.max(firstColumnSize - 1 - rand.nextInt(2), 2);
         FoliagePlacer.FoliageAttachment attachment = null;
         for (int i = 0; i < this.firstColumnSize; i++)
         {
            HexFunction<BlightwillowTrunkPlacer, LevelSimulatedReader, BiConsumer<BlockPos, BlockState>, RandomSource, BlockPos, TreeConfiguration, Boolean>
                    func = i == 0 || i == this.firstColumnSize - 1 ? BlightwillowTrunkPlacer::placeWood : BlightwillowTrunkPlacer::placeLog;
            mutable.setWithOffset(this.start, 0, i, 0);
            func.apply(placer, level, consumer, rand, mutable.immutable(), config);
         }
         for (int i = this.firstColumnSize - 1; i < this.firstColumnSize + secondColumnSize - 1; i++)
         {
            HexFunction<BlightwillowTrunkPlacer, LevelSimulatedReader, BiConsumer<BlockPos, BlockState>, RandomSource, BlockPos, TreeConfiguration, Boolean>
                    func = i == this.firstColumnSize - 1 || (i == this.firstColumnSize + secondColumnSize - 2 && this.isLarge) ? BlightwillowTrunkPlacer::placeWood : BlightwillowTrunkPlacer::placeLog;
            mutable.setWithOffset(this.start, 0, i, 0).move(this.direction);
            func.apply(placer, level, consumer, rand, mutable.immutable(), config);
            if (!this.isLarge && i == this.firstColumnSize + secondColumnSize - 2)
            {
               attachment = new FoliagePlacer.FoliageAttachment(mutable.immutable().above(), 0, false);

            }
         }
         if (this.isLarge)
         {
            int thirdColumnSize = rand.nextInt(1) + 2;
            for (int i = this.firstColumnSize + secondColumnSize - 1; i < this.firstColumnSize + secondColumnSize - 1 + thirdColumnSize; i++) {
               HexFunction<BlightwillowTrunkPlacer, LevelSimulatedReader, BiConsumer<BlockPos, BlockState>, RandomSource, BlockPos, TreeConfiguration, Boolean>
                       func = i == this.firstColumnSize + secondColumnSize - 1 ? BlightwillowTrunkPlacer::placeWood : BlightwillowTrunkPlacer::placeLog;
               mutable.setWithOffset(this.start, 0, i, 0).move(this.direction, 2);
               func.apply(placer, level, consumer, rand, mutable.immutable(), config);
               if (i == this.firstColumnSize + secondColumnSize - 1 + thirdColumnSize - 1)
               {
                  attachment = new FoliagePlacer.FoliageAttachment(mutable.immutable().above(), this.direction.getAxis() == Direction.Axis.X ? 1 : 2, false);
               }
            }
         }
         return attachment;
      }
   }


}