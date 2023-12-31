package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CloudLayerConfig(BlockStateProvider block, int baseHeight, int upperHeight, double scaleXZ) implements FeatureConfiguration {
    public static final Codec<CloudLayerConfig> CODEC = RecordCodecBuilder.create(
            (builder) -> builder.group(
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(CloudLayerConfig::block),
                    Codec.INT.fieldOf("base_height").forGetter(CloudLayerConfig::baseHeight),
                    Codec.INT.fieldOf("upper_height").forGetter(CloudLayerConfig::upperHeight),
                    Codec.DOUBLE.fieldOf("xz_scale").forGetter(CloudLayerConfig::scaleXZ)
            ).apply(builder, CloudLayerConfig::new));

}
