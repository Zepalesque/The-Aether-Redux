package net.zepalesque.redux.blockhandlers;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MaterialColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;

public class WoodHandlers {
    public static final WoodHandler FIELDSPROOT = register(WoodHandler.handler("fieldsproot", null, true, "trees", "log", "wood", SoundType.WOOD, SoundType.WOOD, false, MaterialColor.NETHER, MaterialColor.COLOR_ORANGE, false, true));
    public static final WoodHandler BLIGHTWILLOW = register(WoodHandler.handler("blightwillow", null, true,"trees", "log", "wood", SoundType.WOOD, SoundType.WOOD, true, MaterialColor.TERRACOTTA_CYAN, MaterialColor.COLOR_GREEN, true, false));
    public static final WoodHandler CLOUDCAP = register(WoodHandler.fungus("cloudcap", true, MaterialColor.WOOL, MaterialColor.TERRACOTTA_PURPLE, false));
    public static final WoodHandler JELLYSHROOM = register(WoodHandler.noStrippingFungus("jellyshroom", false, MaterialColor.COLOR_GRAY, MaterialColor.COLOR_GRAY, false));
    public static final WoodHandler CRYSTAL = register(WoodHandler.tree("crystal", false, MaterialColor.TERRACOTTA_CYAN, MaterialColor.COLOR_LIGHT_BLUE, false));
    public static final WoodHandler GLACIA = register(WoodHandler.tree("glacia", false, MaterialColor.TERRACOTTA_BLACK, MaterialColor.TERRACOTTA_LIGHT_GRAY, true));

    private static <T extends WoodHandler> T register(T handler) {
        Redux.WOOD_HANDLERS.add(handler);
        return handler;
    }

    public static void init() {
        // Manually load class
    }
}
