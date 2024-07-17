package net.zepalesque.redux.world.tree.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.zepalesque.redux.data.resource.ReduxFeatureConfig;

import javax.annotation.Nullable;

public class CrystalTree extends ReduxTree {

    public final boolean fruit;
    public CrystalTree(boolean hasFruit) {
        this.fruit = hasFruit;
    }

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getKey(RandomSource random, boolean largeHive) {
        return this.fruit ? ReduxFeatureConfig.CRYSTAL_RARE_FRUIT_TREE : ReduxFeatureConfig.CRYSTAL_LEAF_TREE;
    }
}
