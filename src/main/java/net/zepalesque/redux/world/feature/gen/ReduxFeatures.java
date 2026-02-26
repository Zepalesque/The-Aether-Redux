package net.zepalesque.redux.world.feature.gen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES =
		Redux.reg(BuiltInRegistries.FEATURE);

	public static final DeferredHolder<Feature<?>, Feature<LakesFeature.Config>> LAKES = 
		FEATURES.register(
			"lakes",
			() -> new LakesFeature(LakesFeature.Config.CODEC)
		);

	public static final DeferredHolder<Feature<?>, Feature<CloudbedFeature.Config>> CLOUDBED = 
		FEATURES.register(
			"cloudbed",
			() -> new CloudbedFeature(CloudbedFeature.Config.CODEC)
		);
	
	public static final DeferredHolder<Feature<?>, Feature<DebugNoiseFeature.Config>> DEBUG_NOISE =
		FEATURES.register(
			"debug_noise",
			() -> new DebugNoiseFeature(DebugNoiseFeature.Config.CODEC)
		);

	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> CRYSTAL_ISLAND = 
		FEATURES.register("crystal_island", ReduxCrystalIslandFeature::new);
}
