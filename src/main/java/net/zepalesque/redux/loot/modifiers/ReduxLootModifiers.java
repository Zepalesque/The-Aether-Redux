package net.zepalesque.redux.loot.modifiers;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Redux.MODID);
    public static final RegistryObject<Codec<DungeonLootModifier>> ADD_DUNGEON_LOOT = LOOT_MODIFIERS.register("add_dungeon_loot", () -> DungeonLootModifier.CODEC);
    public static final RegistryObject<Codec<AddDrops>> ADD_ENTITY_DROPS = LOOT_MODIFIERS.register("add_drops", () -> AddDrops.CODEC);
    public static final RegistryObject<Codec<RemoveDrops>> REMOVE_ENTITY_DROPS = LOOT_MODIFIERS.register("remove_drops", () -> RemoveDrops.CODEC);
    public static final RegistryObject<Codec<RawOreModifier>> RAW_ORE = LOOT_MODIFIERS.register("raw_ore", () -> RawOreModifier.CODEC);
    public static final RegistryObject<Codec<EmptyLootModifier>> EMPTY = LOOT_MODIFIERS.register("empty", () -> EmptyLootModifier.CODEC);
}
