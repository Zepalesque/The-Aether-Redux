package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.Redux;

import java.util.List;

public class ReduxPlacements {

    public static final ResourceKey<PlacedFeature> CLOUDBED = copyKey(ReduxFeatureConfig.CLOUDBED);
    public static final ResourceKey<PlacedFeature> SENTRITE_ORE = copyKey(ReduxFeatureConfig.SENTRITE_ORE);

    public static final ResourceKey<PlacedFeature> BONEMEAL_OVERRIDE = AetherPlacedFeatures.AETHER_GRASS_BONEMEAL;


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configs = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CLOUDBED, configs.getOrThrow(ReduxFeatureConfig.CLOUDBED));
        register(context, SENTRITE_ORE, configs.getOrThrow(ReduxFeatureConfig.SENTRITE_ORE),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(128))),
                BiomeFilter.biome()
        );

        // Overrides
        register(context, BONEMEAL_OVERRIDE, configs.getOrThrow(ReduxFeatureConfig.GRASS_BONEMEAL), PlacementUtils.isEmpty());
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Redux.MODID, name));
    }

    private static ResourceKey<PlacedFeature> copyKey(ResourceKey<ConfiguredFeature<?, ?>> configFeat) {
        return createKey(configFeat.location().getPath());
    }

    private static ResourceKey<PlacedFeature> aetherKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Aether.MODID, name));
    }

    private static String name(DeferredHolder<?, ?> reg) {
        return reg.getId().getPath();
    }


}
