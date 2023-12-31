package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SurfaceRuleLakeConfig(BlockStateProvider fluid) implements FeatureConfiguration {
    public static final Codec<SurfaceRuleLakeConfig> CODEC = RecordCodecBuilder.create((p_190962_) -> p_190962_.group(BlockStateProvider.CODEC.fieldOf("fluid").forGetter(SurfaceRuleLakeConfig::fluid)).apply(p_190962_, SurfaceRuleLakeConfig::new));
}
