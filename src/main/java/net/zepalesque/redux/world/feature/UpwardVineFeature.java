package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.util.level.WorldgenUtil;

public class UpwardVineFeature extends Feature<UpwardVineFeature.UpwardVinesConfig> {
   public UpwardVineFeature(Codec<UpwardVinesConfig> codec) {
      super(codec);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param context A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<UpwardVinesConfig> context) {
      WorldGenLevel worldgenlevel = context.level();
      BlockPos blockpos = context.origin().below();
      if (isInvalidPlacementLocation(worldgenlevel, blockpos)) {
         return false;
      } else {
         RandomSource randomsource = context.random();
         UpwardVinesConfig twistingvinesconfig = context.config();
         int i = twistingvinesconfig.spreadWidth();
         int j = twistingvinesconfig.spreadHeight();
         int k = twistingvinesconfig.maxHeight();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int l = 0; l < i * i; ++l) {
            blockpos$mutableblockpos.set(blockpos).move(Mth.nextInt(randomsource, -i, i), Mth.nextInt(randomsource, -j, j), Mth.nextInt(randomsource, -i, i));
            if (findFirstAirBlockAboveGround(worldgenlevel, blockpos$mutableblockpos) && !isInvalidPlacementLocation(worldgenlevel, blockpos$mutableblockpos)) {
               int i1 = Mth.nextInt(randomsource, 1, k);
               if (randomsource.nextInt(6) == 0) {
                  i1 *= 2;
               }

               if (randomsource.nextInt(5) == 0) {
                  i1 = 1;
               }

               int j1 = 17;
               int k1 = 25;
               placeVinesColumn(worldgenlevel, randomsource, blockpos$mutableblockpos, i1, 17, 25, context.config());
            }
         }

         return true;
      }
   }

   private static boolean findFirstAirBlockAboveGround(LevelAccessor level, BlockPos.MutableBlockPos pos) {
      do {
         pos.move(0, -1, 0);
         if (level.isOutsideBuildHeight(pos)) {
            return false;
         }
      } while(level.getBlockState(pos).isAir());

      pos.move(0, 1, 0);
      return true;
   }

   public static void placeVinesColumn(LevelAccessor level, RandomSource random, BlockPos.MutableBlockPos pos, int length, int minAge, int maxAge, UpwardVinesConfig config) {
      for(int i = 1; i <= length; ++i) {
         if (level.isEmptyBlock(pos)) {
            if (i == length || !level.isEmptyBlock(pos.above())) {
               level.setBlock(pos, WorldgenUtil.trySetValue(config.head.getState(random, pos), GrowingPlantHeadBlock.AGE, Mth.nextInt(random, minAge, maxAge)), 2);
               break;
            }

            level.setBlock(pos, config.vine.getState(random, pos), 2);
         }

         pos.move(Direction.UP);
      }

   }

   private static boolean isInvalidPlacementLocation(LevelAccessor level, BlockPos pos) {
      if (!level.isStateAtPosition(pos, state -> !state.canBeReplaced() && !state.isAir())) {
         return true;
      } else {
         BlockState blockstate = level.getBlockState(pos.below());
         return !blockstate.is(BlockTags.DIRT);
      }
   }

   public record UpwardVinesConfig(BlockStateProvider vine, BlockStateProvider head, int spreadWidth, int spreadHeight, int maxHeight) implements FeatureConfiguration {
      public static final Codec<UpwardVinesConfig> CODEC = RecordCodecBuilder.create((p_191375_) -> p_191375_.group(
              BlockStateProvider.CODEC.fieldOf("vine_plant").forGetter(UpwardVinesConfig::vine),
              BlockStateProvider.CODEC.fieldOf("vine_head").forGetter(UpwardVinesConfig::head),
              ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter(UpwardVinesConfig::spreadWidth),
              ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter(UpwardVinesConfig::spreadHeight),
              ExtraCodecs.POSITIVE_INT.fieldOf("max_height").forGetter(UpwardVinesConfig::maxHeight))
              .apply(p_191375_, UpwardVinesConfig::new));
   }
}