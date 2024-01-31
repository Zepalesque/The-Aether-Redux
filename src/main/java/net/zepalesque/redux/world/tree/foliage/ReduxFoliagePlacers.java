package net.zepalesque.redux.world.tree.foliage;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxFoliagePlacers {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, Redux.MODID);
    public static final RegistryObject<FoliagePlacerType<GlaciaFoliagePlacer>> GLACIA = FOLIAGE_PLACERS.register("glacia", () -> new FoliagePlacerType<>(GlaciaFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<GenesisHookedFoliagePlacer>> GENESIS_HOOKED = FOLIAGE_PLACERS.register("genesis_hooked", () -> new FoliagePlacerType<>(GenesisHookedFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<GenesisPineFoliagePlacer>> GENESIS_PINE = FOLIAGE_PLACERS.register("genesis_pine", () -> new FoliagePlacerType<>(GenesisPineFoliagePlacer.CODEC));

    public static final RegistryObject<FoliagePlacerType<LegacyBlightwillowFoliagePlacer>> LEGACY_BLIGHTWILLOW = FOLIAGE_PLACERS.register("legacy_blightwillow", () -> new FoliagePlacerType<>(LegacyBlightwillowFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<BlightwillowFoliagePlacer>> BLIGHTWILLOW = FOLIAGE_PLACERS.register("blightwillow", () -> new FoliagePlacerType<>(BlightwillowFoliagePlacer.CODEC));
}
