package net.zepalesque.redux.data.resource.builders;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.zepalesque.redux.Redux;

public class ReduxNoiseBuilders {
	protected static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
		return ResourceKey.create(Registries.NOISE, Redux.loc(name));
	}
}
