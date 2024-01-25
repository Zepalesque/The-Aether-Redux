package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record PatchRockConfig(BlockStateProvider provider, int tries, int xzSpread, int ySpread, Holder<PlacedFeature> feature) implements FeatureConfiguration {
   public static final Codec<PatchRockConfig> CODEC = RecordCodecBuilder.create(
           builder -> builder.group(
                   BlockStateProvider.CODEC.fieldOf("rock_state").forGetter(PatchRockConfig::provider),
                   ExtraCodecs.POSITIVE_INT.fieldOf("patch_tries").forGetter(PatchRockConfig::tries),
                   ExtraCodecs.NON_NEGATIVE_INT.fieldOf("patch_xz_spread").forGetter(PatchRockConfig::xzSpread),
                   ExtraCodecs.NON_NEGATIVE_INT.fieldOf("patch_y_spread").forGetter(PatchRockConfig::ySpread),
                   PlacedFeature.CODEC.fieldOf("patch_feature").forGetter(PatchRockConfig::feature)).apply(builder, PatchRockConfig::new));

}