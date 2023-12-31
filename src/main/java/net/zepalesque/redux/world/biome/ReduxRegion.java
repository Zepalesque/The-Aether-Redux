package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.common.ForgeConfigSpec;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.ReduxBiomes;
import org.jetbrains.annotations.Nullable;
import teamrazor.aeroblender.aether.AetherRegionType;
import terrablender.api.Region;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReduxRegion extends Region {

    public static void resetBiomeParameters() {
        resetConfig(ReduxConfig.COMMON.blight_temp_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.blight_temp_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.blight_humid_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.blight_humid_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.blight_depth_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.blight_depth_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.grove_temp_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.grove_temp_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.grove_humid_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.grove_humid_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.frost_temp_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.frost_temp_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.frost_humid_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.frost_humid_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.high_temp_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.high_temp_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.high_humid_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.high_humid_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.cap_temp_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.cap_temp_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.cap_humid_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.cap_humid_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.cap_depth_min, ReduxConfig.COMMON.auto_reset_biome_values, true);
        resetConfig(ReduxConfig.COMMON.cap_depth_max, ReduxConfig.COMMON.auto_reset_biome_values, true);
    }

    public static <T> void resetConfig(ForgeConfigSpec.ConfigValue<T> config, @Nullable ForgeConfigSpec.ConfigValue<Boolean> override, boolean ifOverrideIsThisThenReset) {
        if (override == null || override.get() == ifOverrideIsThisThenReset) {
            if (!config.get().equals(config.getDefault())) {
                config.set(config.getDefault());
            }
        }
    }

    public static <T> void resetConfig(ForgeConfigSpec.ConfigValue<T> config)
    {
        resetConfig(config, null, false);
    }

    public static class Params {
        public static final double blight_temp_min = -0.2D;
        public static final double blight_temp_max = 0.6D;

        public static final double blight_humid_min = -1.0D;
        public static final double blight_humid_max = 0.0D;

        public static final double blight_depth_min = 0.85D;
        public static final double blight_depth_max = 1.25D;



        public static final double grove_temp_min = 0.0D;
        public static final double grove_temp_max = 0.4D;

        public static final double grove_humid_min = 0.0D;
        public static final double grove_humid_max = 0.8D;



        public static final double frost_temp_min = -0.8D;
        public static final double frost_temp_max = 0.0D;

        public static final double frost_humid_min = -1.0D;
        public static final double frost_humid_max = 0.0D;



        public static final double highfields_temp_min = 0.4D;
        public static final double highfields_temp_max = 0.93D;

        public static final double highfields_humid_min = -0.1D;
        public static final double highfields_humid_max = 1.1D;



        public static final double cloudcap_temp_min = -0.2D;
        public static final double cloudcap_temp_max = 0.6D;

        public static final double cloudcap_humid_min = 0.8D;
        public static final double cloudcap_humid_max = 1.0D;

        public static final double cloudcap_depth_min = 0.25D;
        public static final double cloudcap_depth_max = 1.25D;

    }

    public ReduxRegion(ResourceLocation name, int weight) {
        super(name, AetherRegionType.THE_AETHER, weight);
    }

    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        Climate.Parameter fullRange = range(-1.0F, 1.0F);
        this.addBiome(mapper, new Climate.ParameterPoint(range(-1.0, -0.8), fullRange, fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_MEADOW);
        if (ReduxConfig.COMMON.enable_frosted_forests.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(ReduxConfig.COMMON.frost_temp_min, ReduxConfig.COMMON.frost_temp_max), range(ReduxConfig.COMMON.frost_humid_min, ReduxConfig.COMMON.frost_humid_max), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.FROSTED_FORESTS);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(-0.8, 0.0), range(-1.0, 0.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_MEADOW);
        }
        this.addBiome(mapper, new Climate.ParameterPoint(range(-0.8, 0.0), range(0.0, 1.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_FOREST);
        if (ReduxConfig.COMMON.enable_the_blight.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(ReduxConfig.COMMON.blight_temp_min, ReduxConfig.COMMON.blight_temp_max), range(ReduxConfig.COMMON.blight_humid_min, ReduxConfig.COMMON.blight_humid_max), fullRange, fullRange, range(ReduxConfig.COMMON.blight_depth_min, ReduxConfig.COMMON.blight_depth_max), fullRange, 0L), ReduxBiomes.THE_BLIGHT);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.0, 0.4), range(-1.0, 0.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_GROVE);
        }
        if (ReduxConfig.COMMON.enable_gilded_groves.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(ReduxConfig.COMMON.grove_temp_min, ReduxConfig.COMMON.grove_temp_max), range(ReduxConfig.COMMON.grove_humid_min, ReduxConfig.COMMON.grove_humid_max), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.GILDED_GROVES);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.0, 0.4), range(0.0, 0.8), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_FOREST);
        }
        if (ReduxConfig.COMMON.enable_cloudcap_jungle.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(ReduxConfig.COMMON.cap_temp_min, ReduxConfig.COMMON.cap_temp_max), range(ReduxConfig.COMMON.cap_humid_min, ReduxConfig.COMMON.cap_humid_max), fullRange, fullRange, range(ReduxConfig.COMMON.cap_depth_min, ReduxConfig.COMMON.cap_depth_max), fullRange, 0L), ReduxBiomes.CLOUDCAP_JUNGLE);
        } else {
            this.addBiome(mapper, new Climate.ParameterPoint(range(0.0, 0.4), range(0.8, 1.0), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_GROVE);
        }
        this.addBiome(mapper, new Climate.ParameterPoint(range(0.4, 0.93), range(-1.0, -0.1), fullRange, fullRange, fullRange, fullRange, 0L), AetherBiomes.SKYROOT_GROVE);
        if (ReduxConfig.COMMON.enable_highfields.get()) {
            this.addBiome(mapper, new Climate.ParameterPoint(range(ReduxConfig.COMMON.high_temp_min, ReduxConfig.COMMON.high_temp_max), range(ReduxConfig.COMMON.high_humid_min, ReduxConfig.COMMON.high_humid_max), fullRange, fullRange, fullRange, fullRange, 0L), ReduxBiomes.HIGHFIELDS);
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
    private static Climate.Parameter range(Supplier<Double> min, Supplier<Double> max)
    {
        return Climate.Parameter.span(Math.min(min.get().floatValue(), max.get().floatValue()), Math.max(min.get().floatValue(), max.get().floatValue()));
    }



}
