package net.zepalesque.redux.event.hook;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;

public class TagHooks {
    public static <T> Collection<TagKey<T>> collectLoreFilters(Registry<T> registry) {
        ArrayList<TagKey<T>> list = new ArrayList<>();
        registry.getTags().map(Pair::getFirst).forEach(tag ->
        {
            String path = tag.location().getPath();
                if (path.matches("^lore_filter/.*")) {
            list.add(tag);
                }
        });
        return list;
    }
}
