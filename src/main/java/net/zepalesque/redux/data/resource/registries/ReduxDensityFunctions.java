package net.zepalesque.redux.data.resource.registries;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.zepalesque.redux.data.resource.builders.ReduxDensityBuilders;
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;

public class ReduxDensityFunctions extends ReduxDensityBuilders {

    public static final ResourceKey<DensityFunction> CLOUDBED_NOISE = copyKey(ReduxNoises.CLOUDBED_NOISE);
    public static final ResourceKey<DensityFunction> CLOUDBED_Y_OFFSET = copyKey(ReduxNoises.CLOUDBED_Y_OFFSET);

    public static final ResourceKey<DensityFunction> REDUX_3D_NOISE = createKey("base_3d_noise_redux");
    public static final ResourceKey<DensityFunction> REDUX_FINAL_DENSITY = createKey("redux_final_density");

    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        context.register(CLOUDBED_NOISE,
                DensityFunctions.mul(
                        new PerlinNoiseFunction(
                                noises.getOrThrow(ReduxNoises.CLOUDBED_NOISE),
                                0.01D, 0.0D, 42),
                        DensityFunctions.constant(1.5D)
                ));

        context.register(CLOUDBED_Y_OFFSET,
                DensityFunctions.mul(
                        new PerlinNoiseFunction(
                                noises.getOrThrow(ReduxNoises.CLOUDBED_Y_OFFSET),
                                0.005D, 0.0D, 95),
                        DensityFunctions.constant(1.5D)
                ));

        context.register(REDUX_3D_NOISE, BlendedNoise.createUnseeded(
                0.25, // xz scale
                0.375, // y scale
                80.0, // xz factor
                80.0, // y factor
                8.0 // smear scale multiplier, capped at 8
        ));

        context.register(REDUX_FINAL_DENSITY,
                createFinal(getFunction(functions, REDUX_3D_NOISE))
        );

    }

}