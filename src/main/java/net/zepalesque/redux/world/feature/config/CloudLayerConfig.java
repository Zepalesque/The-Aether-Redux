package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CloudLayerConfig(BlockStateProvider block, BlockPredicate predicate, int yLevel, double scaleXZ) implements FeatureConfiguration {
    public static final Codec<CloudLayerConfig> CODEC = RecordCodecBuilder.create(
            (builder) -> builder.group(
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(CloudLayerConfig::block),
                    BlockPredicate.CODEC.fieldOf("predicate").forGetter(CloudLayerConfig::predicate),
                    Codec.INT.fieldOf("base_height").forGetter(CloudLayerConfig::yLevel),
                    Codec.DOUBLE.fieldOf("xz_scale").forGetter(CloudLayerConfig::scaleXZ)
            ).apply(builder, CloudLayerConfig::new));

}
