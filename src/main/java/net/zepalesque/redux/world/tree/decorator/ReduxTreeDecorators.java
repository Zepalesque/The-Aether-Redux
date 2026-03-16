package net.zepalesque.redux.world.tree.decorator;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>>
        TREE_DECORATORS = Redux.reg(BuiltInRegistries.TREE_DECORATOR_TYPE);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<VineDecorator>>
        GOLDEN_VINES = TREE_DECORATORS.register(
            "golden_vines",
            () -> new TreeDecoratorType<>(
                VineDecorator.CODEC
            ));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<BranchLeavesDecorator>>
        BRANCH_LEAVES = TREE_DECORATORS.register(
            "branch_leaves",
            () -> new TreeDecoratorType<>(
                BranchLeavesDecorator.CODEC
            ));
}
