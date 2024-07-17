package net.zepalesque.redux.loot.functions;

import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxLootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registry.LOOT_FUNCTION_TYPE.key(), Redux.MODID);
    public static final RegistryObject<LootItemFunctionType> SWET_GEL_COUNT = LOOT_FUNCTION_TYPES.register("swet_gel_count", () -> new LootItemFunctionType(new SwetSizeFunction.Serializer()));
}
