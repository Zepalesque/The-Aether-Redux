package net.zepalesque.redux.api.condition;

import net.zepalesque.redux.config.ReduxConfig;

public class Conditions {
    public static final AbstractCondition<?> DEEP = new ModLoaded("deep_aether");
    public static final AbstractCondition<?> GENESIS = new ModLoaded("aether_genesis");
    public static final AbstractCondition<?> LOST = new ModLoaded("lost_aether_content");
    public static final AbstractCondition<?> ANCIENT = new ModLoaded("ancient_aether");
    public static final AbstractCondition<?> GUMMY_NERF = ReduxConfigCondition.of(ReduxConfig.COMMON.nerf_gummy_swets);
    public static final AbstractCondition<?> MOSSY_ORE = ReduxConfigCondition.of(ReduxConfig.COMMON.mossy_holystone_ores);
    public static final AbstractCondition<?> RAW_GRAVITITE = ReduxConfigCondition.of(ReduxConfig.COMMON.raw_gravitite);
    public static final AbstractCondition<?> ENCHGRASS = ReduxConfigCondition.of(ReduxConfig.COMMON.enchanted_gilded_grass);
    public static final AbstractCondition<?> WATER = ReduxConfigCondition.of(ReduxConfig.COMMON.better_water_color);
    public static final AbstractCondition<?> VINES = ReduxConfigCondition.of(ReduxConfig.COMMON.enchanted_vines);
    public static final AbstractCondition<?> ALT_GILDED = ReduxConfigCondition.of(ReduxConfig.COMMON.alternate_gilded_trees);
    public static final AbstractCondition<?> OVERRIDE_AA = ReduxConfigCondition.of(ReduxConfig.COMMON.override_aa_cloud_compat);
    public static final AbstractCondition<?> CLOUD_LAYER = new Or<>(new Not<>(Conditions.ANCIENT), Conditions.OVERRIDE_AA);
}