package net.zepalesque.redux.data.resource;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.zepalesque.redux.Redux;

public class ReduxStructures {
    public static final ResourceKey<Structure> LOBOTIUM_DUNGEON = createKey("lobotium_dungeon");
    public static final ResourceKey<Structure> ANCIENT_RUIN = createKey("ancient_ruin");


    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Redux.MODID, name));
    }
}