package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class HugeAetherMushroomFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<HugeAetherMushroomFeatureConfiguration> CODEC = RecordCodecBuilder.create((mushroom) ->
           mushroom.group(BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter((config) -> config.capProvider),
                   BlockStateProvider.CODEC.fieldOf("spore_provider").forGetter((config) -> config.sporeProvider),
                   BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter((config) -> config.stemProvider),
                   Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter((config) -> config.foliageRadius))
                   .apply(mushroom, HugeAetherMushroomFeatureConfiguration::new));
   public final BlockStateProvider capProvider;
   public final BlockStateProvider sporeProvider;
   public final BlockStateProvider stemProvider;
   public final int foliageRadius;

   public HugeAetherMushroomFeatureConfiguration(BlockStateProvider cap, BlockStateProvider spore, BlockStateProvider stem, int radius) {
      this.capProvider = cap;
      this.sporeProvider = spore;
      this.stemProvider = stem;
      this.foliageRadius = radius;
   }
}