package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.data.generators.loot.AetherAdvancementLoot;
import com.aetherteam.aether.data.generators.loot.AetherBlockLoot;
import com.aetherteam.aether.data.generators.loot.AetherChestLoot;
import com.aetherteam.aether.data.generators.loot.AetherEntityLoot;
import com.aetherteam.aether.data.generators.loot.AetherSelectorLoot;
import com.aetherteam.aether.data.generators.loot.AetherStrippingLoot;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.loot.AetherLootContexts;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.zepalesque.redux.data.gen.loot.ReduxBlockLoot;
import net.zepalesque.redux.loot.ReduxLoot;

import java.util.List;

public class ReduxLootGen {

    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, ReduxLoot.IMMUTABLE_LOOT_TABLES, List.of(
                new LootTableProvider.SubProviderEntry(ReduxBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }
}
