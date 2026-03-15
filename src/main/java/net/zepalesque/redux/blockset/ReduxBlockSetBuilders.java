package net.zepalesque.redux.blockset;

import java.util.function.Supplier;
import net.minecraft.world.level.ItemLike;

public class ReduxBlockSetBuilders {
	public static ItemLike lazySetItem(Supplier<? extends ItemLike> supplier) {
		if (supplier instanceof ItemLike il) return il;
		else return () -> supplier.get().asItem();
	}
}
