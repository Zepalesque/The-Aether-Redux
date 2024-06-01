package net.zepalesque.redux.world.feature.gen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.world.feature.gen.TestAtBlockFeature;
import net.zepalesque.zenith.world.feature.gen.TestBelowBlockFeature;
import net.zepalesque.zenith.world.feature.gen.config.PredicateStateConfig;

public class ReduxFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Redux.MODID);

    public static DeferredHolder<Feature<?>, Feature<CloudbedFeature.Config>> CLOUDBED = FEATURES.register("cloudbed", () -> new CloudbedFeature(CloudbedFeature.Config.CODEC));
}
