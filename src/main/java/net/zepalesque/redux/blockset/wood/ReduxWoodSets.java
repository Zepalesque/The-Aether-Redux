package net.zepalesque.redux.blockset.wood;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.api.blockset.AbstractWoodSet;

public class ReduxWoodSets {

    public static final LogWallWoodSet CRYSTAL = register(new RegularBookshelfWoodSet("crystal", MapColor.COLOR_LIGHT_BLUE, MapColor.TERRACOTTA_CYAN, SoundType.WOOD));

    public static <T extends BookshelfSet<?>> T register(T set) {
        Redux.WOOD_SETS.add(set);
        return set;
    }

    public static void init() {}
}
