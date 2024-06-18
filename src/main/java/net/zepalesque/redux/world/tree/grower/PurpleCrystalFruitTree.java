package net.zepalesque.redux.world.tree.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.ReduxFeatureConfig;

import javax.annotation.Nullable;

public class PurpleCrystalFruitTree extends AbstractTreeGrower {

    public PurpleCrystalFruitTree() {
    }

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean largeHive) {
        return Redux.aetherGenesisCompat() ? ReduxFeatureConfig.PURPLE_CRYSTAL_RARE_FRUIT_TREE : ReduxFeatureConfig.CRYSTAL_RARE_FRUIT_TREE;
    }
}
