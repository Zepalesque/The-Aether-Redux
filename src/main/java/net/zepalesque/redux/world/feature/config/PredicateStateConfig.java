package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.world.feature.TestBelowBlockFeature;

public record PredicateStateConfig(BlockStateProvider toPlace,
                                   BlockPredicate predicate) implements FeatureConfiguration {
    public static final Codec<PredicateStateConfig> CODEC =
            RecordCodecBuilder.create((config) ->
                    config.group(
                            BlockStateProvider.CODEC.fieldOf("to_place").forGetter(PredicateStateConfig::toPlace),
                            BlockPredicate.CODEC.fieldOf("predicate").forGetter(PredicateStateConfig::predicate)
                    ).apply(config, PredicateStateConfig::new));
}
