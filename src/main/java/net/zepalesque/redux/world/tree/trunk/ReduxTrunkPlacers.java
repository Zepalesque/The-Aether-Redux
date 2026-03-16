package net.zepalesque.redux.world.tree.trunk;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxTrunkPlacers {
	public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = 
		Redux.reg(BuiltInRegistries.TRUNK_PLACER_TYPE);

	public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<OffsetTrunkPlacer>>
		OFFSET_TRUNK = TRUNK_PLACERS.register(
			"offset_trunk",
			() -> new TrunkPlacerType<>(OffsetTrunkPlacer.CODEC)
		);

	public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<HookedTrunkPlacer>>
		HOOKED_TRUNK = TRUNK_PLACERS.register(
			"hooked_trunk",
			() -> new TrunkPlacerType<>(HookedTrunkPlacer.CODEC)
		);
}
