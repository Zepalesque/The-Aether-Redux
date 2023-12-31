package net.zepalesque.redux.capability.player;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.util.INBTSerializable;
import org.w3c.dom.css.Counter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoreBookModule implements INBTSerializable {


    private Map<EntityType<?>, Integer> entities = new HashMap<>();



    public void tick(EntityType<?> entityType) {
        if (entities.containsKey(entityType)) {
            int i = entities.get(entityType) + 1;
            entities.remove(entityType);
            entities.put(entityType, i);
        }
        else {
            entities.put(entityType, 1);
        }
    }



    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<EntityType<?>, Integer> entry : entities.entrySet()) {
            tag.putInt(BuiltInRegistries.ENTITY_TYPE.getKey(entry.getKey()).toString(), entry.getValue());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        Map<EntityType<?>, Integer> entities = new HashMap<>();
        if (nbt instanceof CompoundTag tag) {
            for (String id : tag.getAllKeys()) {
                entities.put(BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(id)), tag.getInt(id));
            }
        }
        this.entities = entities;
    }
}
