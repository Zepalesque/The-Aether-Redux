package net.zepalesque.redux.data.prov;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.registries.ReduxConditions;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.modifer.RawOreModifier;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.loot.condition.ConditionLootModule;

import java.util.concurrent.CompletableFuture;

public abstract class ReduxLootModifierProvider extends GlobalLootModifierProvider {
    protected final CompletableFuture<HolderLookup.Provider> lookup;
    public ReduxLootModifierProvider(PackOutput output, String modid, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, modid);
        this.lookup = lookup;
    }
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));

    @Override
    protected void start() { }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(this.doHolderLookupModifiers(), super.run(cache));
    }

    protected CompletableFuture<HolderLookup.Provider> doHolderLookupModifiers() {
        return this.lookup.thenApply(provider -> {
            this.withHolderLookups(provider);
            return provider;
        });
    }

    protected abstract void withHolderLookups(HolderLookup.Provider provider);
}
