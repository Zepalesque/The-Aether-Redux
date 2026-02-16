package net.zepalesque.redux.data.resource.registries;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.zepalesque.redux.data.resource.builders.ReduxNoiseBuilders;

public class ReduxNoises extends ReduxNoiseBuilders {
    public static final ResourceKey<NoiseParameters> CLOUDBED_NOISE = createKey("cloudbed_noise");
    public static final ResourceKey<NoiseParameters> CLOUDBED_Y_OFFSET = createKey("cloudbed_y_offset");

    public static final ResourceKey<NoiseParameters> LAKES_NOISE = createKey("lakes_noise");
    public static final ResourceKey<NoiseParameters> LAKES_Y_OFFSET = createKey("lakes_y_offset");

    public static void bootstrap(BootstrapContext<NoiseParameters> context) {
        context.register(CLOUDBED_NOISE, new NormalNoise.NoiseParameters(0, 1, 1, 1, 1, 1));
        context.register(CLOUDBED_Y_OFFSET, new NormalNoise.NoiseParameters(0, 1, 1));

        context.register(LAKES_NOISE, new NormalNoise.NoiseParameters(0, 1, 1, 1, 1, 1));
        context.register(LAKES_Y_OFFSET, new NormalNoise.NoiseParameters(0, 1, 1));
    }
}
