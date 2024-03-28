package net.zepalesque.redux.data.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.ReduxLoot;

import java.util.function.BiConsumer;

// TODO: Peppermint Barkling
public class ReduxStrippingLootData implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(ReduxLoot.STRIP_BLIGHTWILLOW, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ReduxItems.BLIGHTED_SPORES.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        builder.accept(ReduxLoot.STRIP_PEPPERMINT, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ReduxBlocks.PEPPERMINT_BARKLING.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.1F))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
}