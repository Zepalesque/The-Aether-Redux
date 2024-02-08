package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.*;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ReduxRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
            .add(Registries.DAMAGE_TYPE, ReduxDamageTypes::bootstrap)
            .add(Registries.DENSITY_FUNCTION, ReduxDensityFunctions::bootstrap)
            .add(Registries.CONFIGURED_CARVER, ReduxConfiguredCarvers::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ReduxConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ReduxPlacedFeatures::bootstrap)
            .add(Registries.BIOME, ReduxBiomes::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ReduxBiomeModifiers::bootstrap);

    public ReduxRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Redux.MODID, Aether.MODID));
    }

    public static HolderLookup.Provider createLookup() {
        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
    }

    public static HolderLookup.Provider patchLookup(HolderLookup.Provider lookup) {
        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), lookup);
    }

}
