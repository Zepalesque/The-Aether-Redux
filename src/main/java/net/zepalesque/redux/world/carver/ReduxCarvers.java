package net.zepalesque.redux.world.carver;

import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxCarvers {

    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, Redux.MODID);

    public static RegistryObject<WorldCarver<?>> AETHER_CAVE = CARVERS.register("aether_cave", () -> new AetherCaveWorldCarver(CaveCarverConfiguration.CODEC));
}