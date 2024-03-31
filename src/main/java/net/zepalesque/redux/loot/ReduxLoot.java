package net.zepalesque.redux.loot;

import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ReduxLoot {
    private static final Set<ResourceLocation> LOOT_TABLES = new HashSet();
    public static final Set<ResourceLocation> IMMUTABLE_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);

    public static final ResourceLocation STRIP_BLIGHTWILLOW = register("stripping/strip_blightwillow");
    public static final ResourceLocation STRIP_PEPPERMINT = register("stripping/strip_peppermint");
    public static final ResourceLocation LOBOTIUM_DUNGEON = register("chests/dungeon/lobotium/lobotium_dungeon");

    private static ResourceLocation register(String id) {
        return register(new ResourceLocation(Redux.MODID, id));
    }

    private static ResourceLocation register(ResourceLocation id) {
        if (LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException("" + id + " is already a registered built-in loot table");
        }
    }
}
