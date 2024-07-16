package net.zepalesque.redux.world.tree.trunk;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxTrunkPlacers {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Redux.MODID);

    public static final RegistryObject<TrunkPlacerType<GenesisHookedTrunkPlacer>> GENESIS_HOOKED_TRUNK_PLACER = TRUNK_PLACERS.register("genesis_hooked_trunk_placer", () -> new TrunkPlacerType<>(GenesisHookedTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<GenesisSkinnyHookedTrunkPlacer>> GENESIS_SKINNY_HOOKED_TRUNK_PLACER = TRUNK_PLACERS.register("genesis_skinny_hooked_trunk_placer", () -> new TrunkPlacerType<>(GenesisSkinnyHookedTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<IntProviderTrunkPlacer>> INT_PROVIDER = TRUNK_PLACERS.register("int_provider_trunk", () -> new TrunkPlacerType<>(IntProviderTrunkPlacer.CODEC));

}