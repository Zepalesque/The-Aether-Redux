package net.zepalesque.redux.event.hook;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.zepalesque.redux.Redux;

import java.util.function.Supplier;

public class MappingsHooks {

    public static <T> void remapped(String unmapped, Supplier<? extends T> newValue, MissingMappingsEvent.Mapping<T> map)
    {
        remapped(Redux.locate(unmapped), newValue.get(), map);
    }
    public static void itemLike(String unmapped, Supplier<? extends ItemLike> newValue, MissingMappingsEvent.Mapping<Item> map)
    {
        remapped(Redux.locate(unmapped), newValue.get().asItem(), map);
    }
    public static <T> void remapped(String unmapped, T newValue, MissingMappingsEvent.Mapping<T> map)
    {
        remapped(Redux.locate(unmapped), newValue, map);
    }
    public static <T> void remapped(ResourceLocation unmappedLoc, Supplier<? extends T> newValue, MissingMappingsEvent.Mapping<T> map)
    {
        remapped(unmappedLoc, newValue.get(), map);
    }
    public static <T> void remapped(ResourceLocation unmappedLoc, T newValue, MissingMappingsEvent.Mapping<T> map)
    {
        if (map.getKey().equals(unmappedLoc)) {
            map.remap(newValue);
        }
    }

}
