package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ConfiguredPatchBoulder extends Feature<ConfiguredPatchBoulder.Config> {
   public ConfiguredPatchBoulder(Codec<Config> p_65248_) {
      super(p_65248_);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param context A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<Config> context) {
      BlockPos blockpos = context.origin();
      WorldGenLevel worldgenlevel = context.level();
      RandomSource randomsource = context.random();

      BlockStateProvider prov;
      for(prov = context.config().provider(); blockpos.getY() > worldgenlevel.getMinBuildHeight() + 3; blockpos = blockpos.below()) {
         if (!worldgenlevel.isEmptyBlock(blockpos.below())) {
            BlockState blockstate = worldgenlevel.getBlockState(blockpos.below());
            if (isDirt(blockstate) || isStone(blockstate)) {
               break;
            }
         }
      }

      if (blockpos.getY() <= worldgenlevel.getMinBuildHeight() + 3) {
         return false;
      } else {
         for(int l = 0; l < 3; ++l) {
            int i = randomsource.nextInt(2);
            int j = randomsource.nextInt(2);
            int k = randomsource.nextInt(2);
            float f = (float)(i + j + k) * 0.333F + 0.5F;

            for(BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-i, -j, -k), blockpos.offset(i, j, k))) {
               if (blockpos1.distSqr(blockpos) <= (double)(f * f)) {
                  worldgenlevel.setBlock(blockpos1, prov.getState(randomsource, blockpos1), 3);
               }
            }

            blockpos = blockpos.offset(-1 + randomsource.nextInt(2), -randomsource.nextInt(2), -1 + randomsource.nextInt(2));
         }
         placePatch(context);
         return true;
      }
   }

   public boolean placePatch(FeaturePlaceContext<Config> p_160210_) {
      Config randompatchconfiguration = p_160210_.config();
      RandomSource randomsource = p_160210_.random();
      BlockPos blockpos = p_160210_.origin();
      WorldGenLevel worldgenlevel = p_160210_.level();
      int i = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      int j = randompatchconfiguration.xzSpread() + 1;
      int k = randompatchconfiguration.ySpread() + 1;

      for(int l = 0; l < randompatchconfiguration.tries(); ++l) {
         blockpos$mutableblockpos.setWithOffset(blockpos, randomsource.nextInt(j) - randomsource.nextInt(j), randomsource.nextInt(k) - randomsource.nextInt(k), randomsource.nextInt(j) - randomsource.nextInt(j));
         if (randompatchconfiguration.feature().value().place(worldgenlevel, p_160210_.chunkGenerator(), randomsource, blockpos$mutableblockpos)) {
            ++i;
         }
      }

      return i > 0;
   }

   public record Config(BlockStateProvider provider, int tries, int xzSpread, int ySpread, Holder<PlacedFeature> feature) implements FeatureConfiguration {
      public static final Codec<Config> CODEC = RecordCodecBuilder.create(
              builder -> builder.group(
                      BlockStateProvider.CODEC.fieldOf("rock_state").forGetter(Config::provider),
                      ExtraCodecs.POSITIVE_INT.fieldOf("patch_tries").forGetter(Config::tries),
                      ExtraCodecs.NON_NEGATIVE_INT.fieldOf("patch_xz_spread").forGetter(Config::xzSpread),
                      ExtraCodecs.NON_NEGATIVE_INT.fieldOf("patch_y_spread").forGetter(Config::ySpread),
                      PlacedFeature.CODEC.fieldOf("patch_feature").forGetter(Config::feature)).apply(builder, Config::new));

   }
}