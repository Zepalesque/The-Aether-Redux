package net.zepalesque.redux.blockset;

import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class ReduxBlockSetBuilders {

    public static ItemLike lazySetItem(Supplier<? extends ItemLike> supplier) {
        if (supplier instanceof ItemLike il) return il;
        else return () -> supplier.get().asItem();
    }
}
