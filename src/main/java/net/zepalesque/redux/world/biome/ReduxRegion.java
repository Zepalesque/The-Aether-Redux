package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.ReduxBiomes;
import teamrazor.aeroblender.aether.AetherRegionType;
import terrablender.api.Region;

import java.util.function.Consumer;

public class ReduxRegion extends Region {

    public ReduxRegion(ResourceLocation name, int weight) {
        super(name, AetherRegionType.THE_AETHER, weight);
    }

    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        Climate.Parameter fullRange = range(-1.0F, 1.0F);
        this.addBiome(mapper, new Climate.ParameterPoint(range(-1.0, -0.8), fullRange, fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_MEADOW);
        if (ReduxConfig.COMMON.enable_snowy_biomes.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(-0.8D, -0.4D), range(-1.0D, -0.6D), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.GLACIAL_TAIGA);
            this.addBiome(mapper, new Climate.ParameterPoint(range(-0.4D, 0D), range(-0.6D, 0.0D), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.FROSTED_TUNDRA);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(-0.8, 0.0), range(-1.0, 0.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_MEADOW);
        }
        this.addBiome(mapper, new Climate.ParameterPoint(range(-0.8, 0.0), range(0.0, 1.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_FOREST);
        if (ReduxConfig.COMMON.enable_the_blight.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(-0.2D, 0.6D), range(-1.0D, 0.0D), fullRange, fullRange, range(0.85D, 1.25D), fullRange, 0L), ReduxBiomes.THE_BLIGHT);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.0, 0.4), range(-1.0, 0.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_GROVE);
        }
        if (ReduxConfig.COMMON.enable_gilded_groves.get()) {
            boolean flag = ReduxConfig.COMMON.enable_skyroot_shrublands.get();
            this.addBiome(mapper, new Climate.ParameterPoint(range(-0.1D, 0.4D), range(flag ? 0.6D : 0.4D, 0.9D), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.GILDED_GROVES);
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.0D, 0.5D), range(flag ? 0.3D : 0.0D, flag ? 0.6D : 0.4D), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.GILDED_GRASSLANDS);
        }
        if (ReduxConfig.COMMON.enable_skyroot_shrublands.get()) {
            boolean flag1 = ReduxConfig.COMMON.enable_gilded_groves.get();
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.1D, 0.6D), range(0.0D, flag1 ? 0.3D : 0.8D), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.SKYROOT_SHRUBLANDS);
        }
        if (!ReduxConfig.COMMON.enable_skyroot_shrublands.get() && !ReduxConfig.COMMON.enable_gilded_groves.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.0, 0.4), range(0.0, 0.8), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_FOREST);
        }
        if (ReduxConfig.COMMON.enable_cloudcap_jungle.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(-0.2D, 0.6D), range(0.5D, 1.0D), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.CLOUDCAPS);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.0, 0.4), range(0.8, 1.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_GROVE);
        }
        this.addBiome(mapper, new Climate.ParameterPoint(range(0.4, 0.93), range(-1.0, -0.1), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_GROVE);
        if (ReduxConfig.COMMON.enable_highfields.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.4D, 0.93D), range(-0.1D, 1.1D), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.HIGHFIELDS);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.4, 0.93), range(-0.1, 1.1), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_FOREST);
        }
        this.addBiome(mapper, new Climate.ParameterPoint(range(0.93, 0.94), range(-1.0, -0.6), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_MEADOW);
        this.addBiome(mapper, new Climate.ParameterPoint(range(0.93, 0.94), range(-0.6, -0.3), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_GROVE);
        this.addBiome(mapper, new Climate.ParameterPoint(range(0.93, 0.94), range(-0.3, 1.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_FOREST);
        this.addBiome(mapper, new Climate.ParameterPoint(range(0.94, 1.0), range(-1.0, -0.1), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_MEADOW);
        this.addBiome(mapper, new Climate.ParameterPoint(range(0.94, 1.0), range(-0.1, 0.8), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_WOODLAND);

        this.addBiome(mapper, new Climate.ParameterPoint(range(0.93, 0.94), range(0.8, 1.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_FOREST);
    }

    private static Climate.Parameter range(float min, float max)
    {
        return Climate.Parameter.span(Math.min(min, max), Math.max(min, max));
    }
    private static Climate.Parameter range(Double min, Double max)
    {
        return Climate.Parameter.span(Math.min(min.floatValue(), max.floatValue()), Math.max(min.floatValue(), max.floatValue()));
    }
}
