package net.zepalesque.redux.data.resource;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.zepalesque.redux.Redux;

public class ReduxConfiguredCarvers {
	public static final ResourceKey<ConfiguredWorldCarver<?>> AETHER_CAVES = createKey("aether_caves");

	private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
		return ResourceKey.create(BuiltinRegistries.CONFIGURED_CARVER.key(), new ResourceLocation(Redux.MODID, name));
	}
}