package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class JellyshroomConfig implements FeatureConfiguration {
   public static final Codec<JellyshroomConfig> CODEC = RecordCodecBuilder.create((mushroom) ->
           mushroom.group(BlockStateProvider.CODEC.fieldOf("jelly_block").forGetter((config) -> config.jelly),
                   BlockStateProvider.CODEC.fieldOf("stem_block").forGetter((config) -> config.stem),
                   IntProvider.CODEC.fieldOf("height").forGetter((config) -> config.height))
                   .apply(mushroom, JellyshroomConfig::new));
   public final BlockStateProvider jelly;
   public final BlockStateProvider stem;
   public final IntProvider height;


   public JellyshroomConfig(BlockStateProvider jelly, BlockStateProvider stem, IntProvider height) {
      this.jelly = jelly;
      this.stem = stem;
      this.height = height;
   }
}