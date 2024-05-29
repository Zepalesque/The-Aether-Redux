package net.zepalesque.redux.world.biome.tint;

import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.biometint.BiomeTint;

public class ReduxBiomeTints {


    public static final DeferredRegister<BiomeTint> TINTS = DeferredRegister.create(Zenith.Keys.BIOME_TINT, Redux.MODID);

    public static final DeferredHolder<BiomeTint, BiomeTint> AETHER_GRASS = TINTS.register("aether_grass", () -> new BiomeTint(Redux.loc("aether_grass"), 0xADF9C4));

}
