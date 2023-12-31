package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.zepalesque.redux.world.feature.config.TestAtPlaceConfig;

public class TestBlockAtPlacementPatch extends Feature<TestAtPlaceConfig> {
   public TestBlockAtPlacementPatch(Codec<TestAtPlaceConfig> p_66605_) {
      super(p_66605_);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param pContext A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<TestAtPlaceConfig> pContext) {
      TestAtPlaceConfig config = pContext.config();
      RandomSource randomsource = pContext.random();
      BlockPos blockpos = pContext.origin();
      WorldGenLevel worldgenlevel = pContext.level();
      int i = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int j = config.xzSpread() + 1;
      int k = config.ySpread() + 1;

      for(int l = 0; l < config.tries(); ++l) {

         blockpos$mutableblockpos.setWithOffset(blockpos, randomsource.nextInt(j) - randomsource.nextInt(j), randomsource.nextInt(k) - randomsource.nextInt(k), randomsource.nextInt(j) - randomsource.nextInt(j));
         Holder<Biome> biomeBelow = worldgenlevel.getBiome(blockpos$mutableblockpos.below());
         Holder<Biome> originBiome = worldgenlevel.getBiome(blockpos);
         if (config.blockPredicate().test(worldgenlevel, blockpos$mutableblockpos) && biomeBelow == originBiome && config.feature().value().place(worldgenlevel, pContext.chunkGenerator(), randomsource, blockpos$mutableblockpos)) {
               ++i;
         }
      }
      return i > 0;
   }
}