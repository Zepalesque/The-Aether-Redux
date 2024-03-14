package net.zepalesque.redux.capability.player;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.INBTSerializable;
import net.zepalesque.redux.Redux;

import java.util.*;

public class LoreBookModule implements INBTSerializable {


    private Map<EntityType<?>, Integer> entities = new HashMap<>();


    // TODO: Lore book overrides
    private Collection<Item> unlockedItems = new ArrayList<>();

    private Map<ResourceLocation, Integer> biomes = new HashMap<>();

    public void incrementEntity(EntityType<?> entityType) {
        if (entities.containsKey(entityType)) {
            int i = entities.get(entityType) + 1;
            entities.put(entityType, i);
        }
        else {
            entities.put(entityType, 1);
        }
    }
    public void unlock(Item item) {
        if (!unlockedItems.contains(item)) {
            unlockedItems.add(item);
        }
    }

    public void incrementBiome(ResourceLocation biome) {
        if (biomes.containsKey(biome)) {
            int i = biomes.get(biome) + 1;
            biomes.put(biome, i);
        }
        else {
            biomes.put(biome, 1);
        }
    }




    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag mobs = new CompoundTag();
        for (Map.Entry<EntityType<?>, Integer> entry : entities.entrySet()) {
            mobs.putInt(Registry.ENTITY_TYPE.getKey(entry.getKey()).toString(), entry.getValue());
        }
        tag.put("entity", mobs);

        CompoundTag b = new CompoundTag();
        for (Map.Entry<ResourceLocation, Integer> entry : biomes.entrySet()) {
            b.putInt(entry.getKey().toString(), entry.getValue());
        }
        tag.put("biome", b);

        List<String> s = unlockedItems.stream().map(Registry.ITEM::getKey).map(ResourceLocation::toString).toList();
        String combined = String.join(",", s);
        tag.putString("item", combined);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        Map<EntityType<?>, Integer> entities = new HashMap<>();
        Map<ResourceLocation, Integer> biomes = new HashMap<>();
        Collection<Item> unlockedItems = new ArrayList<>();
        if (nbt instanceof CompoundTag tag) {
            Tag mobs = tag.get("entity");
            if (mobs instanceof CompoundTag mobsTag) {
                for (String id : mobsTag.getAllKeys()) {
                    ResourceLocation loc = new ResourceLocation(id);
                    if (Registry.ENTITY_TYPE.containsKey(loc)) {
                        entities.put(Registry.ENTITY_TYPE.get(loc), mobsTag.getInt(id));
                    } else {
                        Redux.LOGGER.warn("Could not find entity_type with id {} for Book of Lore! Skipping...", id);
                    }
                }
            }

            Tag b = tag.get("biome");
            if (b instanceof CompoundTag biomeTag) {
                for (String id : biomeTag.getAllKeys()) {
                    ResourceLocation loc = new ResourceLocation(id);
                    biomes.put(loc, biomeTag.getInt(id));
                }
            }

            String items = tag.getString("item");
            if (!items.isEmpty()) {
                String[] array = items.split(",");
                for (String id : array) {
                    ResourceLocation loc = new ResourceLocation(id);
                    if (Registry.ITEM.containsKey(loc)) {
                        unlockedItems.add(Registry.ITEM.get(loc));
                    } else {
                        Redux.LOGGER.warn("Could not find item with id {} for Book of Lore! Skipping...", id);
                    }
                }
            }
        }
        this.entities = entities;
        this.unlockedItems = unlockedItems;
        this.biomes = biomes;
    }
}
