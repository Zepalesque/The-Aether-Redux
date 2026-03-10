package net.zepalesque.redux.blockset.wood;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockset.wood.type.LogWallWoodSet;
import net.zepalesque.redux.blockset.wood.type.RegularBookshelfSet;
import net.zepalesque.zenith.api.blockset.type.AbstractWoodSet;

public class ReduxWoodSets {
	public static final LogWallWoodSet CRYSTAL = register(new RegularBookshelfSet("crystal", MapColor.COLOR_LIGHT_BLUE, MapColor.TERRACOTTA_CYAN, SoundType.WOOD));
	public static final LogWallWoodSet BLIGHTWILLOW = register(new RegularBookshelfSet("blightwillow", MapColor.COLOR_GREEN, MapColor.COLOR_GRAY, SoundType.CHERRY_WOOD));
	public static final LogWallWoodSet MOONFIR = register(new RegularBookshelfSet("moonfir", MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_LIGHT_GRAY, SoundType.WOOD));

	public static <T extends AbstractWoodSet> T register(T set) {
		Redux.BLOCK_SETS.add(set);
		return set;
	}
}
