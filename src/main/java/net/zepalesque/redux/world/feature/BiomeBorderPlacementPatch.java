package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class BiomeBorderPlacementPatch extends Feature<RandomPatchConfiguration> {
   public BiomeBorderPlacementPatch(Codec<RandomPatchConfiguration> p_66605_) {
      super(p_66605_);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param pContext A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<RandomPatchConfiguration> pContext) {
      RandomPatchConfiguration randompatchconfiguration = pContext.config();
      RandomSource randomsource = pContext.random();
      BlockPos blockpos = pContext.origin();
      WorldGenLevel worldgenlevel = pContext.level();
      int i = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int j = randompatchconfiguration.xzSpread() + 1;
      int k = randompatchconfiguration.ySpread() + 1;

      for(int l = 0; l < randompatchconfiguration.tries(); ++l) {

         blockpos$mutableblockpos.setWithOffset(blockpos, randomsource.nextInt(j) - randomsource.nextInt(j), randomsource.nextInt(k) - randomsource.nextInt(k), randomsource.nextInt(j) - randomsource.nextInt(j));
         Holder<Biome> biomeBelow = worldgenlevel.getBiome(blockpos$mutableblockpos.below());
         Holder<Biome> originBiome = worldgenlevel.getBiome(blockpos);
         if (biomeBelow == originBiome && randompatchconfiguration.feature().value().place(worldgenlevel, pContext.chunkGenerator(), randomsource, blockpos$mutableblockpos)) {
               ++i;
         }
      }

      return i > 0;
   }
}