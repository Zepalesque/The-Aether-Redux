package net.zepalesque.redux.world.tree.root;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxRootPlacers {
    public static final DeferredRegister<RootPlacerType<?>> ROOT_PLACERS = DeferredRegister.create(Registry.ROOT_PLACER_TYPE_REGISTRY, Redux.MODID);
    public static final RegistryObject<RootPlacerType<BlightwillowRootsPlacer>> BLIGHTWILLOW_ROOTS = ROOT_PLACERS.register("blightwillow_roots", () -> new RootPlacerType<>(BlightwillowRootsPlacer.CODEC));

}
