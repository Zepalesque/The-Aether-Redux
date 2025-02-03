package net.zepalesque.redux.world.tree.roots;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.tree.trunk.BlightwillowTrunkPlacer;

public class ReduxRootPlacers {
    public static final DeferredRegister<RootPlacerType<?>> ROOT_PLACERS = DeferredRegister.create(BuiltInRegistries.ROOT_PLACER_TYPE, Redux.MODID);

    public static final DeferredHolder<RootPlacerType<?>, RootPlacerType<BlightwillowRootsPlacer>> BLIGHTWILLOW_ROOTS = ROOT_PLACERS.register("blightwillow_roots", () -> new RootPlacerType<>(BlightwillowRootsPlacer.CODEC));

}
