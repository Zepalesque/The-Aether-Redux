package net.zepalesque.redux.world.tree.foliage;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxFoliagePlacers {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, Redux.MODID);
    public static final RegistryObject<FoliagePlacerType<GlaciaFoliagePlacer>> GLACIA_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("glacia_foliage_placer", () -> new FoliagePlacerType(GlaciaFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<GenesisHookedFoliagePlacer>> GENESIS_HOOKED_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("genesis_hooked_foliage_placer", () -> new FoliagePlacerType(GenesisHookedFoliagePlacer.CODEC));

    public static final RegistryObject<FoliagePlacerType<BlightwillowFoliagePlacer>> BLIGHTWILLOW_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("blightwillow_foliage_placer", () -> new FoliagePlacerType(BlightwillowFoliagePlacer.CODEC));
}
