package net.zepalesque.redux.world.tree.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.ReduxConfiguredFeatures;

import javax.annotation.Nullable;

public class GildedSkyrootTree extends ReduxTree {
    public GildedSkyrootTree() {
    }

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getKey(RandomSource random, boolean largeHive) {
        return ReduxConfig.COMMON.alternate_gilded_trees.get() ? ReduxConfiguredFeatures.SMALL_GILDED_OAK_TREE : random.nextBoolean() ? ReduxConfiguredFeatures.FANCY_GILDED_OAK_TREE : ReduxConfiguredFeatures.GILDED_OAK_TREE;
    }
}
