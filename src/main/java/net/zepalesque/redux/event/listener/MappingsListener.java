package net.zepalesque.redux.event.listener;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.event.hook.MappingsHooks;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class MappingsListener {

    // TODO: Convert to, funnily enough, some HashMaps
    @SubscribeEvent
    public static void remapItems(MissingMappingsEvent event)
    {
        for (MissingMappingsEvent.Mapping<Item> map : event.getMappings(ForgeRegistries.ITEMS.getRegistryKey(), Redux.MODID)) {
            ResourceLocation path = map.getKey();
            if (MappingsHooks.ITEMS.containsKey(path)) {
                map.remap(MappingsHooks.ITEMS.get(path).get().asItem());
            }
        }
        for (MissingMappingsEvent.Mapping<Block> map : event.getMappings(ForgeRegistries.BLOCKS.getRegistryKey(), Redux.MODID)) {
            ResourceLocation path = map.getKey();
            if (MappingsHooks.BLOCKS.containsKey(path)) {
                map.remap(MappingsHooks.BLOCKS.get(path).get());
            }
        }
        for (MissingMappingsEvent.Mapping<EntityType<?>> map : event.getMappings(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), Redux.MODID)) {
            ResourceLocation path = map.getKey();
            if (MappingsHooks.ENTITIES.containsKey(path)) {
                map.remap(MappingsHooks.ENTITIES.get(path).get());
            }
        }
        for (MissingMappingsEvent.Mapping<Biome> map : event.getMappings(ForgeRegistries.BIOMES.getRegistryKey(), Redux.MODID)) {
            IForgeRegistry<Biome> registry = map.getRegistry();
            ResourceLocation path = map.getKey();
            if (MappingsHooks.BIOMES.containsKey(path)) {
                ResourceLocation loc = MappingsHooks.BIOMES.get(path).location();
                @Nullable Biome biome = registry.getValue(loc);
                if (biome != null) {
                    map.remap(biome);
                }
            }
        }

    }

}
