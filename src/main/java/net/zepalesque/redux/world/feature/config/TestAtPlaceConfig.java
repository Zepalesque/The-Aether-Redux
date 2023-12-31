package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record TestAtPlaceConfig(BlockPredicate blockPredicate, int tries, int xzSpread, int ySpread, Holder<PlacedFeature> feature) implements FeatureConfiguration {
   public static final Codec<TestAtPlaceConfig> CODEC = RecordCodecBuilder.create(
           builder -> builder.group(
                   BlockPredicate.CODEC.fieldOf("predicate").forGetter(TestAtPlaceConfig::blockPredicate),
                   ExtraCodecs.POSITIVE_INT.fieldOf("tries").forGetter(TestAtPlaceConfig::tries),
                   ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").forGetter(TestAtPlaceConfig::xzSpread),
                   ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").forGetter(TestAtPlaceConfig::ySpread),
                   PlacedFeature.CODEC.fieldOf("feature").forGetter(TestAtPlaceConfig::feature)).apply(builder, TestAtPlaceConfig::new));

}