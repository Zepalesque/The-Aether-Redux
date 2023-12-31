package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record TestBelowPlaceConfig(BlockPredicate belowPredicate, int tries, int xzSpread, int ySpread, Holder<PlacedFeature> feature) implements FeatureConfiguration {
   public static final Codec<TestBelowPlaceConfig> CODEC = RecordCodecBuilder.create(
           builder -> builder.group(
                   BlockPredicate.CODEC.fieldOf("block_below").forGetter(TestBelowPlaceConfig::belowPredicate),
                   ExtraCodecs.POSITIVE_INT.fieldOf("tries").forGetter(TestBelowPlaceConfig::tries),
                   ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").forGetter(TestBelowPlaceConfig::xzSpread),
                   ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").forGetter(TestBelowPlaceConfig::ySpread),
                   PlacedFeature.CODEC.fieldOf("feature").forGetter(TestBelowPlaceConfig::feature)).apply(builder, TestBelowPlaceConfig::new));

}