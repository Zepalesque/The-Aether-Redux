package net.zepalesque.redux.world.tree.grower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public abstract class ReduxTree extends AbstractTreeGrower {



    protected abstract ResourceKey<ConfiguredFeature<?, ?>> getKey(RandomSource random, boolean irrelevantSorryLol);

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(ServerLevel level, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random, boolean hasFlowers) {
        return level.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY).getOrCreateHolderOrThrow(this.getKey(random, false));
    }

    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
        return null;
    }
}