package net.zepalesque.redux.loot.modifiers;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.zepalesque.redux.Redux;

public class ReduxLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Redux.MODID);
    public static final RegistryObject<Codec<GenesisAddDungeonLootModifier>> ADD_DUNGEON_LOOT = GLOBAL_LOOT_MODIFIERS.register("genesis_add_dungeon_loot", () -> GenesisAddDungeonLootModifier.CODEC);
    public static final RegistryObject<Codec<AddOatsModifier>> ADD_OATS = GLOBAL_LOOT_MODIFIERS.register("add_oats", () -> AddOatsModifier.CODEC);
    public static final RegistryObject<Codec<GenesisAddDropsModifier>> ADD_ENTITY_DROPS = GLOBAL_LOOT_MODIFIERS.register("genesis_add_drops", () -> GenesisAddDropsModifier.CODEC);
    public static final RegistryObject<Codec<RemoveDropsModifier>> REMOVE_ENTITY_DROPS = GLOBAL_LOOT_MODIFIERS.register("remove_drops", () -> RemoveDropsModifier.CODEC);
}
