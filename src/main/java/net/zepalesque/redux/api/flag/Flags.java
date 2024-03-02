package net.zepalesque.redux.api.flag;

import net.zepalesque.redux.config.ReduxConfig;

public class Flags {
    public static final DataFlag<?> DEEP = new ModLoaded("deep_aether");
    public static final DataFlag<?> GENESIS = new ModLoaded("aether_genesis");
    public static final DataFlag<?> LOST = new ModLoaded("lost_aether_content");
    public static final DataFlag<?> ANCIENT = new ModLoaded("ancient_aether");
    public static final DataFlag<?> GUMMY_NERF = new ReduxConfigFlag(ReduxConfig.COMMON.nerf_gummy_swets);
    public static final DataFlag<?> VANILLA_SWETS = new ReduxConfigFlag(ReduxConfig.COMMON.vanilla_swets);
    public static final DataFlag<?> MOSSY_ORE = new ReduxConfigFlag(ReduxConfig.COMMON.mossy_holystone_ores);
    public static final DataFlag<?> RAW_ORES = new ReduxConfigFlag(ReduxConfig.COMMON.raw_ores);
    public static final DataFlag<?> ENCHGRASS = new ReduxConfigFlag(ReduxConfig.COMMON.enchanted_gilded_grass);
    public static final DataFlag<?> WATER = new ReduxConfigFlag(ReduxConfig.COMMON.better_water_color);
    public static final DataFlag<?> VINES = new ReduxConfigFlag(ReduxConfig.COMMON.enchanted_vines);
    public static final DataFlag<?> ENDERMEN = new ReduxConfigFlag(ReduxConfig.COMMON.enderman_spawns);
    public static final DataFlag<?> ALT_GILDED = new ReduxConfigFlag(ReduxConfig.COMMON.alternate_gilded_trees);
}