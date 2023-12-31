package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record RootedShelfConfiguration(BlockStateProvider shelfBlock, BlockStateProvider rootBlock, FloatProvider radius, UniformInt yRange, HolderSet<Block> validBlocks) implements FeatureConfiguration {
    public static final Codec<RootedShelfConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("shelf_block").forGetter(RootedShelfConfiguration::shelfBlock),
            BlockStateProvider.CODEC.fieldOf("root_block").forGetter(RootedShelfConfiguration::rootBlock),
            FloatProvider.CODEC.fieldOf("radius").forGetter(RootedShelfConfiguration::radius),
            UniformInt.CODEC.fieldOf("y_range").forGetter(RootedShelfConfiguration::yRange),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_blocks").forGetter(RootedShelfConfiguration::validBlocks)
    ).apply(instance, RootedShelfConfiguration::new));
}
