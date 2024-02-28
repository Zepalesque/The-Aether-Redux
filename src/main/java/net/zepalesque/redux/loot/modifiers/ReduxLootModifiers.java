package net.zepalesque.redux.loot.modifiers;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.zepalesque.redux.Redux;

public class ReduxLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Redux.MODID);
    public static final RegistryObject<Codec<DungeonModifier>> ADD_DUNGEON_LOOT = LOOT_MODIFIERS.register("add_dungeon_loot", () -> DungeonModifier.CODEC);
    public static final RegistryObject<Codec<AddDropsModifier>> ADD_ENTITY_DROPS = LOOT_MODIFIERS.register("add_drops", () -> AddDropsModifier.CODEC);
    public static final RegistryObject<Codec<RemoveDropsModifier>> REMOVE_ENTITY_DROPS = LOOT_MODIFIERS.register("remove_drops", () -> RemoveDropsModifier.CODEC);
    public static final RegistryObject<Codec<RawOreModifier>> RAW_ORE = LOOT_MODIFIERS.register("raw_ore", () -> RawOreModifier.CODEC);
}
