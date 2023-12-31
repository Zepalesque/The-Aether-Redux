package net.zepalesque.redux.data.loot;

import com.aetherteam.aether.loot.AetherLootContexts;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.zepalesque.redux.loot.ReduxLoot;

import java.util.List;

public class ReduxLootData {
    public ReduxLootData() {
    }

    public static LootTableProvider loot(PackOutput output) {
        return new LootTableProvider(output, ReduxLoot.IMMUTABLE_LOOT_TABLES, List.of(
                new LootTableProvider.SubProviderEntry(ReduxBlockLootData::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ReduxEntityLootData::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(ReduxStrippingLootData::new, AetherLootContexts.STRIPPING)
        ));
    }
}