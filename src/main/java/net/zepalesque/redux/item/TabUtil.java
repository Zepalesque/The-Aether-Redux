package net.zepalesque.redux.item;

import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

public class TabUtil {

    public static void putAfter(Supplier<? extends ItemLike> itemBefore, Supplier<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().putAfter(new ItemStack(itemBefore.get()), new ItemStack(insertedItem.get()), TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    public static void putBefore(Supplier<? extends ItemLike> itemAfter, Supplier<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().putBefore(new ItemStack(itemAfter.get()), new ItemStack(insertedItem.get()), TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    public static void remove(Supplier<? extends ItemLike> toRemove, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().remove(new ItemStack(toRemove.get()));
    }

    public static void add(Supplier<? extends ItemLike> toAdd, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().put(new ItemStack(toAdd.get()), TabVisibility.PARENT_AND_SEARCH_TABS);
    }
}
