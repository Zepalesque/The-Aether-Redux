package net.zepalesque.redux.world.tree.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ReduxSuppliedTree extends ReduxTree {
    private final Function<RandomSource, ResourceKey<ConfiguredFeature<?, ?>>> key;

    public ReduxSuppliedTree(Function<RandomSource, ResourceKey<ConfiguredFeature<?, ?>>> function) {
        this.key = function;
    }
    public ReduxSuppliedTree(ResourceKey<ConfiguredFeature<?, ?>> feature) {
        this.key = randomSource -> feature;
    }
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getKey(RandomSource random, boolean hasFlowers) {
        return this.key.apply(random);
    }
}
