package net.zepalesque.redux.api.condition;

import net.zepalesque.redux.config.ReduxConfig;

public class Conditions {
    public static final AbstractCondition<?> DEEP = new ModLoaded("deep_aether");
    public static final AbstractCondition<?> GENESIS = new ModLoaded("aether_genesis");
    public static final AbstractCondition<?> LOST = new ModLoaded("lost_aether_content");
    public static final AbstractCondition<?> ANCIENT = new ModLoaded("ancient_aether");
    public static final AbstractCondition<?> GUMMY_NERF = new ReduxConfigCondition(ReduxConfig.COMMON.nerf_gummy_swets);
    public static final AbstractCondition<?> VANILLA_SWETS = new ReduxConfigCondition(ReduxConfig.COMMON.vanilla_swets);
    public static final AbstractCondition<?> MOSSY_ORE = new ReduxConfigCondition(ReduxConfig.COMMON.mossy_holystone_ores);
    public static final AbstractCondition<?> RAW_ORES = new ReduxConfigCondition(ReduxConfig.COMMON.raw_ores);
    public static final AbstractCondition<?> ENCHGRASS = new ReduxConfigCondition(ReduxConfig.COMMON.enchanted_gilded_grass);
    public static final AbstractCondition<?> WATER = new ReduxConfigCondition(ReduxConfig.COMMON.better_water_color);
    public static final AbstractCondition<?> VINES = new ReduxConfigCondition(ReduxConfig.COMMON.enchanted_vines);
    public static final AbstractCondition<?> ENDERMEN = new ReduxConfigCondition(ReduxConfig.COMMON.enderman_spawns);
    public static final AbstractCondition<?> ALT_GILDED = new ReduxConfigCondition(ReduxConfig.COMMON.alternate_gilded_trees);
    public static final AbstractCondition<?> CLASSIC_SKYFIELDS = new ReduxConfigCondition(ReduxConfig.COMMON.classic_skyfields);
    public static final AbstractCondition<?> ZEPHYR_MOMENTO = new ReduxConfigCondition(ReduxConfig.COMMON.zephyr_meme_tweaks);
}