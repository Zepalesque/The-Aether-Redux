package net.zepalesque.redux.world.tree.root;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.util.function.OctoFunction;
import net.zepalesque.redux.world.tree.trunk.BlightwillowTrunkPlacer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class BlightwillowRootPlacer extends RootPlacer {
   public static final int ROOT_WIDTH_LIMIT = 8;
   public static final int ROOT_LENGTH_LIMIT = 15;
   public static final Codec<BlightwillowRootPlacer> CODEC = RecordCodecBuilder.create((builder) -> {
      return rootPlacerParts(builder).and(BlightwillowRootConfig.CODEC.fieldOf("root_config").forGetter((rootplacer) -> {
         return rootplacer.rootConfig;
      })).apply(builder, BlightwillowRootPlacer::new);
   });
   private final BlightwillowRootConfig rootConfig;

   public BlightwillowRootPlacer(IntProvider trunkOffset, BlockStateProvider roots, Optional<AboveRootPlacement> abovePlacement, BlightwillowRootConfig rootPlacement) {
      super(trunkOffset, roots, abovePlacement);
      this.rootConfig = rootPlacement;
   }

   public boolean placeRoots(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> biconsumer, RandomSource pRandom, BlockPos groundOrigin, BlockPos trunkOrigin, TreeConfiguration pTreeConfig) {
//      List<BlockPos> list = Lists.newArrayList();
      Map<BlockPos, Direction> rootLocations = this.potentialRootPositions(trunkOrigin);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = groundOrigin.mutable();
      boolean success = false;
      boolean negZSuccess = false;
      boolean posZSuccess = false;
      boolean posXSuccess = false;
      boolean negXSuccess = false;
      int rootCount = 0;
      for (Map.Entry<BlockPos, Direction> entry : rootLocations.entrySet()) {
         RootType rootType = RootType.getFromIndex(pRandom.nextInt(2));

         if (canPlaceRoot(pLevel, entry.getKey(), entry.getValue(), rootType, pRandom)) {
            rootGenerate(rootType, entry.getKey(), pRandom, entry.getValue(), this.rootConfig, biconsumer, this.rootProvider, pLevel);
            success = true;
            if (entry.getValue() == Direction.NORTH) {
               negZSuccess = true;
            }
            if (entry.getValue() == Direction.SOUTH) {
               posZSuccess = true;
            }
            if (entry.getValue() == Direction.EAST) {
               posXSuccess = true;
            }
            if (entry.getValue() == Direction.WEST) {
               negXSuccess = true;
            }
            rootCount++;
         }
      }
      if (success) {
         if (rootCount < 3) {
            if (pTreeConfig.trunkPlacer instanceof BlightwillowTrunkPlacer btp) {
               BlockStateProvider wood = btp.getWoodProvider();
               BlockStateProvider log = pTreeConfig.trunkProvider;
               BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
               for (int x = -1; x <= 1; x++) {
                  for (int z = -1; z <= 1; z++) {
                     for (int y = 0; y <= trunkOrigin.getY() - groundOrigin.getY(); y++) {
                        if (groundOrigin.getY() > trunkOrigin.getY()) {
                           break;
                        }
                        mutable.setWithOffset(groundOrigin, x, y, z);
                        if (x == 0 || z == 0) {
                           biconsumer.accept(mutable.immutable(), log.getState(pRandom, mutable.immutable()));
                        }
                     }
                  }
               }
            }
         }
         for (int xTemp = 0; xTemp <= 1; xTemp++) {
            for (int zTemp = 0; zTemp <= 1; zTemp++) {
               int xReal = xTemp == 0 ? -1 : xTemp;
               int zReal = zTemp == 0 ? -1 : zTemp;
               if (pRandom.nextBoolean()) {
                  BlockPos extraRootPos = trunkOrigin.offset(xReal, 0, zReal);
                  boolean posXposZ = xReal == 1 && zReal == 1 && posXSuccess && posZSuccess;
                  boolean negXnegZ = xReal == -1 && zReal == -1 && negXSuccess && negZSuccess;
                  boolean posXnegZ = xReal == 1 && zReal == -1 && posXSuccess && negZSuccess;
                  boolean negXposZ = xReal == -1 && zReal == 1 && negXSuccess && posZSuccess;
                  boolean shouldTryPlacement = posXposZ || negXnegZ || posXnegZ || negXposZ;
                  if (shouldTryPlacement) {
                     if (rootCount >= 3 || pRandom.nextBoolean()) {
                        biconsumer.accept(extraRootPos, this.rootProvider.getState(pRandom, extraRootPos));
                        if (pRandom.nextInt(2) == 0) {
                           biconsumer.accept(extraRootPos.above(), this.rootProvider.getState(pRandom, extraRootPos));
                        }
                     }
                     if (rootCount < 3) {
                        biconsumer.accept(extraRootPos.below(), this.rootProvider.getState(pRandom, extraRootPos));

                     }
                  }
               }
            }
         }
      }
      return success;
   }

   protected Map<BlockPos, Direction> potentialRootPositions(BlockPos trunkOrigin) {


      ImmutableMap.Builder<BlockPos, Direction> builder = new ImmutableMap.Builder<BlockPos, Direction>();

      for (Direction d : Direction.Plane.HORIZONTAL)
      {
         BlockPos start = trunkOrigin.relative(d, 2);
         builder.put(start, d);
      }
      return builder.build();

   }

   protected boolean canPlaceRoot(LevelSimulatedReader pLevel, BlockPos pPos, Direction d, RootType rootType, RandomSource r) {
      return (super.canPlaceRoot(pLevel, pPos)
              || pLevel.isStateAtPosition(pPos, (p_225858_) -> p_225858_.is(this.rootConfig.canGrowThrough())))
              && rootType.generator.apply(pPos, r, d, this.rootConfig, (blockPos, blockState) -> {}, new BlockPos.MutableBlockPos(), this.rootProvider, pLevel);
   }

   protected static @Nullable boolean rootGenerate(RootType type, BlockPos rootStartOrigin, RandomSource rand, Direction d, BlightwillowRootConfig config, BiConsumer<BlockPos, BlockState> blockSetter, BlockStateProvider rootProvider, LevelSimulatedReader lvl) {
      BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
      return type.generator.apply(rootStartOrigin, rand, d, config, blockSetter, mutable, rootProvider, lvl);
   }


   protected RootPlacerType<?> type() {
      return ReduxRootPlacers.BLIGHTWILLOW_ROOT_PLACER.get();
   }

   public enum RootType {
      S(0, (origin, rand, direction, cfg, placer, mutable, rootProvider, level) -> {

         int firstHalfLength = Mth.floor(cfg.maxRootLength() / 2) + 1;
         for (int i = 0; i < firstHalfLength; i++)
         {
            setWithOffset(mutable, origin, direction, i);
            BlockPos transformed = mutable.immutable();
            if (level.isStateAtPosition(transformed, blockState -> !cfg.canGrowThrough().contains(blockState.getBlockHolder()) && !blockState.isAir())) {
               return false;
            }
            placer.accept(transformed, rootProvider.getState(rand, transformed));
         }
         BlockPos lower = origin.below();
         for (int i = firstHalfLength - 1; i <= cfg.maxRootLength() - 1; i++) {
            setWithOffset(mutable, lower, direction, i);
            BlockPos transformed = mutable.immutable();
            if (level.isStateAtPosition(transformed, blockState -> !cfg.canGrowThrough().contains(blockState.getBlockHolder()) && !blockState.isAir())) {
               return false;
            }
            placer.accept(transformed, rootProvider.getState(rand, transformed));
         }

         return true;
      }),

      L(1,(origin, rand, direction, cfg, placer, mutable, rootProvider, level) -> {
         if (level.isStateAtPosition(origin, blockState -> !cfg.canGrowThrough().contains(blockState.getBlockHolder()) && !blockState.isAir())) {
            return false;
         }
         placer.accept(origin, rootProvider.getState(rand, origin));
         BlockPos lower = origin.below();
         for (int i = 0; i < cfg.maxRootLength(); i++) {
            setWithOffset(mutable, lower, direction, i);
            BlockPos transformed = mutable.immutable();
            if (level.isStateAtPosition(transformed, blockState -> !cfg.canGrowThrough().contains(blockState.getBlockHolder()) && !blockState.isAir())) {
               return false;
            }
            placer.accept(transformed, rootProvider.getState(rand, transformed));
         }
         return true;
      }),

      MINI_L(0,(origin, rand, direction, cfg, placer, mutable, rootProvider, level) -> {
         if (level.isStateAtPosition(origin, blockState -> !cfg.canGrowThrough().contains(blockState.getBlockHolder()) && !blockState.isAir())) {
            return false;
         }
         placer.accept(origin, rootProvider.getState(rand, origin));

         BlockPos lower = origin.below();
         int length = Mth.floor(cfg.maxRootLength() / 2) + 1;
         for (int i = 0; i < length; i++) {
            setWithOffset(mutable, lower, direction, i);
            BlockPos transformed = mutable.immutable();
            if (level.isStateAtPosition(transformed, blockState -> !cfg.canGrowThrough().contains(blockState.getBlockHolder()) && !blockState.isAir())) {
               return false;
            }
            placer.accept(transformed, rootProvider.getState(rand, transformed));
         }

         return true;
      });


      private final int index;
      private final RootFunction generator;
      RootType(int index, RootFunction consumer)
      {
         this.index = index;
         this.generator = consumer;
      }

      public RootFunction getGenerator() {
         return generator;
      }

      public static RootType getFromIndex(int index)
      {
         return index == 0 ? S : index == 1 ? L : index == 2 ? MINI_L : null;
      }


      public int getIndex() {
         return index;
      }
   }

   interface RootFunction extends OctoFunction<BlockPos, RandomSource, Direction, BlightwillowRootConfig, BiConsumer<BlockPos, BlockState>, BlockPos.MutableBlockPos, BlockStateProvider, LevelSimulatedReader, Boolean> {}

   private static BlockPos.MutableBlockPos setWithOffset(BlockPos.MutableBlockPos mutable, Vec3i origin, Direction direction, int amount) {
      return mutable.set(origin.getX() + (direction.getStepX() * amount), origin.getY() + (direction.getStepY() * amount), origin.getZ() + (direction.getStepZ() * amount));
   }
}