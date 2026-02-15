package net.zepalesque.redux.world.tree.roots;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxRootPlacers {
	public static final DeferredRegister<RootPlacerType<?>>
		ROOT_PLACERS = Redux.reg(BuiltInRegistries.ROOT_PLACER_TYPE);

	public static final DeferredHolder<RootPlacerType<?>, RootPlacerType<BlightwillowRootsPlacer>>
		BLIGHTWILLOW_ROOTS = ROOT_PLACERS.register(
			"blightwillow_roots",
			() -> new RootPlacerType<>(BlightwillowRootsPlacer.CODEC)
		);
}
