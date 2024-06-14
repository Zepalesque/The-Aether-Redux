package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.zepalesque.redux.data.gen.loot.ReduxBlockLoot;
import net.zepalesque.redux.loot.ReduxLoot;

import java.util.List;

public class ReduxLootData {

    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, ReduxLoot.IMMUTABLE_LOOT_TABLES, List.of(
                new LootTableProvider.SubProviderEntry(ReduxBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }
}
