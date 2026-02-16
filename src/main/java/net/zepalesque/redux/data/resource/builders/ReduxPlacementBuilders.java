package net.zepalesque.redux.data.resource.builders;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.Redux;
import net.zepalesque.unity.data.resource.builders.base.BasePlacementBuilders;

public class ReduxPlacementBuilders extends BasePlacementBuilders {

    protected static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Redux.loc(name));
    }

    protected static ResourceKey<PlacedFeature> copyKey(ResourceKey<?> key) {
        return createKey(key.location().getPath());
    }

    protected static ResourceKey<PlacedFeature> copyKey(ResourceKey<?> key, String format) {
        return createKey(format.formatted(key.location().getPath()));
    }

//    protected static ResourceKey<PlacedFeature> aetherKey(String name) {
//        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Aether.MODID, name));
//    }
}
