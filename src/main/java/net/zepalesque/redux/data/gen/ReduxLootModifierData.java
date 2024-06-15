package net.zepalesque.redux.data.gen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.prov.ReduxLootModifierProvider;
import net.zepalesque.redux.data.resource.registries.ReduxConditions;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.modifer.RawOreModifier;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.loot.condition.ConditionLootModule;

import java.util.concurrent.CompletableFuture;

public class ReduxLootModifierData extends ReduxLootModifierProvider {
    public ReduxLootModifierData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Redux.MODID, lookup);
    }

    @Override
    protected void withHolderLookups(HolderLookup.Provider provider) {
        this.add("raw_veridium", new RawOreModifier(ReduxBlocks.VERIDIUM_ORE.get().asItem(), new ItemStack(ReduxItems.RAW_VERIDIUM.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).build(),
                        ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build()
                },
                new LootItemCondition[] {
                        // It appears that LootModifierManager is being a bit buggy, so just use a direct inline holder instead of the key
                        new ConditionLootModule(Holder.direct(new ConfigCondition("redux_server", ReduxConfig.SERVER.raw_ores))),
                        HAS_SILK_TOUCH.invert().build(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ReduxBlocks.VERIDIUM_ORE.get()).build()}));

    }
}
