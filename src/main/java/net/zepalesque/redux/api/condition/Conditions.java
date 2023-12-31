package net.zepalesque.redux.api.condition;

import net.zepalesque.redux.config.ReduxConfig;

public class Conditions {
    public static final AbstractCondition<?> DEEP = new ModLoadedCondition("deep_aether");
    public static final AbstractCondition<?> GENESIS = new ModLoadedCondition("aether_genesis");
    public static final AbstractCondition<?> LOST = new ModLoadedCondition("lost_aether_content");
    public static final AbstractCondition<?> ANCIENT = new ModLoadedCondition("ancient_aether");
    public static final AbstractCondition<?> GUMMY_NERF = ReduxConfigCondition.of(ReduxConfig.COMMON.nerf_gummy_swets);
    public static final AbstractCondition<?> MOSSY_ORE = ReduxConfigCondition.of(ReduxConfig.COMMON.mossy_holystone_ores);
}