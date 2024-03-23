package net.zepalesque.redux.world.tree.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.zepalesque.redux.data.resource.ReduxConfiguredFeatures;

import javax.annotation.Nullable;

public class CrystalTree extends AbstractTreeGrower {

    public final boolean fruit;
    public CrystalTree(boolean hasFruit) {
        this.fruit = hasFruit;
    }

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
        return this.fruit ? ReduxConfiguredFeatures.CRYSTAL_RARE_FRUIT_TREE : ReduxConfiguredFeatures.CRYSTAL_LEAF_TREE;
    }
}
