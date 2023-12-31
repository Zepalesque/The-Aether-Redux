package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class MegaCloudcapFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<MegaCloudcapFeatureConfiguration> CODEC = RecordCodecBuilder.create((mushroom) ->
           mushroom.group(BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter((config) -> config.capProvider),
                   BlockStateProvider.CODEC.fieldOf("spore_provider").forGetter((config) -> config.sporeProvider),
                   BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter((config) -> config.stemProvider),
                   BlockStateProvider.CODEC.fieldOf("hyphae_provider").forGetter((config) -> config.hyphaeProvider))
                   .apply(mushroom, MegaCloudcapFeatureConfiguration::new));
   public final BlockStateProvider capProvider;
   public final BlockStateProvider sporeProvider;
   public final BlockStateProvider stemProvider;
   public final BlockStateProvider hyphaeProvider;

   public MegaCloudcapFeatureConfiguration(BlockStateProvider cap, BlockStateProvider spore, BlockStateProvider stem, BlockStateProvider hyphae) {
      this.capProvider = cap;
      this.sporeProvider = spore;
      this.stemProvider = stem;
      this.hyphaeProvider = hyphae;
   }
}