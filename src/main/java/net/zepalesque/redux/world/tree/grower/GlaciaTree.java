package net.zepalesque.redux.world.tree.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.zepalesque.redux.data.resource.ReduxConfiguredFeatures;

import javax.annotation.Nullable;

public class GlaciaTree extends AbstractTreeGrower {
    public GlaciaTree() {
    }

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
        return ReduxConfiguredFeatures.SMALL_GLACIA_TREE;
    }
}
