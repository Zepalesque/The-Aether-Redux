package net.zepalesque.redux.blockhandlers;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;

public class WoodHandlers {
    public static final WoodHandler FIELDSPROOT = register(WoodHandler.handler("fieldsproot", null, true, WoodHandler.cherrySoundBlockSet(), "trees", "log", "wood", SoundType.CHERRY_WOOD, SoundType.CHERRY_WOOD, false, MapColor.NETHER, MapColor.COLOR_ORANGE, false, true));
    public static final WoodHandler BLIGHTWILLOW = register(WoodHandler.handler("blightwillow", null, true, WoodHandler.bambooSoundBlockSet(), "trees", "log", "wood", SoundType.BAMBOO_WOOD, SoundType.BAMBOO_WOOD, true, MapColor.TERRACOTTA_CYAN, MapColor.COLOR_GREEN, true, false));
    public static final WoodHandler CLOUDCAP = register(WoodHandler.fungus("cloudcap", true, MapColor.WOOL, MapColor.TERRACOTTA_PURPLE, false));
    public static final WoodHandler JELLYSHROOM = register(WoodHandler.noStrippingFungus("jellyshroom", false, MapColor.COLOR_GRAY, MapColor.COLOR_GRAY, false));
    public static final WoodHandler CRYSTAL = register(WoodHandler.tree("crystal", false, MapColor.TERRACOTTA_CYAN, MapColor.COLOR_LIGHT_BLUE, false));
    public static final WoodHandler GLACIA = register(WoodHandler.tree("glacia", false, MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_LIGHT_GRAY, true));

    private static <T extends WoodHandler> T register(T handler) {
        Redux.WOOD_HANDLERS.add(handler);
        return handler;
    }

    public static void init() {
    }
}
