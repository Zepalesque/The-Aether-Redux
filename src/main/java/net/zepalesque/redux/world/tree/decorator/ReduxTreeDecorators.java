package net.zepalesque.redux.world.tree.decorator;

import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Redux.MODID);
    public static final RegistryObject<TreeDecoratorType<GenesisTrunkDecorator>> GENESIS_TRUNK_DECORATOR = TREE_DECORATORS.register("genesis_trunk_decorator", () -> new TreeDecoratorType<>(GenesisTrunkDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<EnchantedVineDecorator>> ENCHANTED_VINE_DECORATOR = TREE_DECORATORS.register("enchanted_vine", () -> new TreeDecoratorType<>(EnchantedVineDecorator.CODEC));

    public static final RegistryObject<TreeDecoratorType<PatchTreeDecorator>> PATCH_TREE_DECORATOR = TREE_DECORATORS.register("patch_tree_decorator", () -> new TreeDecoratorType<>(PatchTreeDecorator.CODEC));


}
