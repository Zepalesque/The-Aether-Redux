package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.util.level.WorldgenUtil;

public class CandyCaneFeature extends Feature<CandyCaneFeature.Config> {
   public CandyCaneFeature(Codec<Config> pCodec) {
      super(pCodec);
   }

   public boolean place(FeaturePlaceContext<Config> pContext) {
      WorldGenLevel worldgenlevel = pContext.level();
      BlockPos blockpos = pContext.origin();
      RandomSource randomsource = pContext.random();
      Config config = pContext.config();
      int height = config.height.sample(randomsource);
      Direction caneFacing = Direction.Plane.HORIZONTAL.getRandomDirection(randomsource);
      BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
      boolean canPlace = this.placeCane(worldgenlevel, randomsource, blockpos, config, height, mutable, caneFacing, true);
      if (canPlace) {
         this.placeCane(worldgenlevel, randomsource, blockpos, config, height, mutable, caneFacing, false);
         return true;
      }
      return false;
   }

   private static boolean canPlaceHere(LevelAccessor lvl, BlockPos pos)
   {
      BlockState b = lvl.getBlockState(pos);
      return b.isAir() || b.canBeReplaced();
   }

   protected boolean placeCane(LevelAccessor lvl, RandomSource rand, BlockPos pos, Config config, int height, BlockPos.MutableBlockPos mutable, Direction d, boolean isSimulated) {
      boolean success = true;
      for (int i = 0; i < height; ++i) {
         mutable.setWithOffset(pos, 0, i, 0);
         BlockStateProvider provider = i == height - 1 ? config.peppermintProvider : config.caneProvider;
         if (canPlaceHere(lvl, mutable)) {
            if (!isSimulated) {
               this.setBlock(lvl, mutable, provider.getState(rand, mutable));
            }
         } else {
            success = false;
         }
      }
      BlockPos airAboveTop = pos.above(height);
      for (int i = 1; i <= 2; i++) {
         WorldgenUtil.setWithOffset(mutable, airAboveTop, d, i);
         if (canPlaceHere(lvl, mutable)) {
            if (!isSimulated) {

               BlockState b = config.peppermintProvider.getState(rand, mutable);
               BlockState b2;
               if (b.hasProperty(BlockStateProperties.AXIS)) {
                  b2 = b.setValue(BlockStateProperties.AXIS, d.getAxis());
               } else {
                  b2 = b;
               }
               this.setBlock(lvl, mutable, b2);
            }
         } else {
            success = false;
         }
      }
      boolean doubleEnd = rand.nextBoolean();
      BlockPos startOfEnd = pos.above(height - 1).relative(d, 3);
      if (canPlaceHere(lvl, startOfEnd)) {
         if (!isSimulated) {
            this.setBlock(lvl, startOfEnd, config.peppermintProvider.getState(rand, startOfEnd));
         }
      } else { success = false; }
      if (doubleEnd)
      {
         mutable.setWithOffset(startOfEnd, 0, -1, 0);
         if (canPlaceHere(lvl, mutable)) {
            if (!isSimulated) {

               this.setBlock(lvl, mutable, config.peppermintProvider.getState(rand, mutable));
            }
         } else { success = false; }
      }
      return success;
   }

   public static class Config implements FeatureConfiguration {
      public static final Codec<Config> CODEC = RecordCodecBuilder.create((mushroom) ->
              mushroom.group(BlockStateProvider.CODEC.fieldOf("cane_provider").forGetter((config) -> config.caneProvider),
                              BlockStateProvider.CODEC.fieldOf("peppermint_provider").forGetter((config) -> config.peppermintProvider),
                              IntProvider.CODEC.fieldOf("height").forGetter((config) -> config.height))
                      .apply(mushroom, Config::new));
      public final BlockStateProvider caneProvider;
      public final BlockStateProvider peppermintProvider;
      public final IntProvider height;

      public Config(BlockStateProvider cane, BlockStateProvider peppermint, IntProvider height) {
         this.caneProvider = cane;
         this.peppermintProvider = peppermint;
         this.height = height;
      }
   }
}