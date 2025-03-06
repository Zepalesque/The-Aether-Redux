package net.zepalesque.redux.data.resource.builders;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.builders.AetherNoiseBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.registries.ReduxDensityFunctions;
import net.zepalesque.redux.mixin.mixins.common.accessor.AetherNoiseBuildersMixin;

import java.util.List;
import java.util.Optional;

public class ReduxDensityBuilders {

    public static DensityFunction createFinal(DensityFunction density) {
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));
        density = slide(density, 0, 144, 88, 0, -0.2, 8, 40, -0.1);
        density = DensityFunctions.add(density, DensityFunctions.constant(-0.05));
        density = DensityFunctions.blendDensity(density);
        density = DensityFunctions.interpolated(density);
        density = density.squeeze();
        return density;
    }

    protected static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

    /**
     * [CODE COPY] - {@link AetherNoiseBuilders#slide(DensityFunction, int, int, int, int, double, int, int, double)}.
     */
    private static DensityFunction slide(DensityFunction density, int minY, int maxY, int fromYTop, int toYTop, double offset1, int fromYBottom, int toYBottom, double offset2) {
        DensityFunction topSlide = DensityFunctions.yClampedGradient(minY + maxY - fromYTop, minY + maxY - toYTop, 1, 0);
        density = DensityFunctions.lerp(topSlide, offset1, density);
        DensityFunction bottomSlide = DensityFunctions.yClampedGradient(minY + fromYBottom, minY + toYBottom, 0, 1);
        return DensityFunctions.lerp(bottomSlide, offset2, density);
    }


    public static NoiseGeneratorSettings reduxSkylandsNoiseSettings(HolderGetter<DensityFunction> functions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        BlockState holystone = AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        return new NoiseGeneratorSettings(
                new NoiseSettings(0, 144, 2, 1), // noiseSettings
                holystone, // defaultBlock
                Blocks.WATER.defaultBlockState(), // defaultFluid
                AetherNoiseBuildersMixin.callCreateNoiseRouter(functions, noise, getFunction(functions, ReduxDensityFunctions.REDUX_FINAL_DENSITY)), // noiseRouter
                AetherNoiseBuilders.aetherSurfaceRules(), // surfaceRule
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // useLegacyRandomSource
        );
    }

    protected static ResourceKey<DensityFunction> copyKey(ResourceKey<NormalNoise.NoiseParameters> noise) {
        return createKey(noise.location().getPath());
    }

    protected static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Redux.loc(name));
    }

    public static DensityFunction get(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

    public static DensityFunction get(RegistryAccess access, ResourceKey<DensityFunction> key) {
        Optional<HolderLookup.RegistryLookup<DensityFunction>> optional = access.lookup(Registries.DENSITY_FUNCTION);
        if (optional.isPresent()) {
            HolderLookup.RegistryLookup<DensityFunction> lookup = optional.get();
            return lookup.getOrThrow(key).value();

        } else throw new NullPointerException("Optional value is not present!");
    }

    public static DensityFunction get(Level level, ResourceKey<DensityFunction> key) {
        return level.registryAccess().registryOrThrow(Registries.DENSITY_FUNCTION).getOrThrow(key);
    }
}
