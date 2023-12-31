package net.zepalesque.redux.data;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import com.aetherteam.aether_genesis.item.GenesisItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.api.condition.NotCondition;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.condition.DataLootCondition;
import net.zepalesque.redux.loot.modifiers.GenesisAddDungeonLootModifier;
import net.zepalesque.redux.loot.modifiers.GenesisAddEntityDropsModifier;
import net.zepalesque.redux.loot.modifiers.RemoveEntityDropsModifier;

import java.util.List;

public class ReduxLootModifierData extends GlobalLootModifierProvider {
    public ReduxLootModifierData(PackOutput output) {
        super(output, Redux.MODID);
    }

    @Override
    protected void start() {

        this.add("cockatrice_feather", new GenesisAddEntityDropsModifier(new ItemStack(ReduxItems.COCKATRICE_FEATHER.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.COCKATRICE.get())).build(),
                        LootItemRandomChanceCondition.randomChance(0.02F).build()
                }));

        this.add("swet_sugar_no_genesis", new GenesisAddEntityDropsModifier(new ItemStack(Items.SUGAR),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherTags.Entities.SWETS))
                        .build(),
                        DataLootCondition.conditionOf(new NotCondition(Conditions.GENESIS)).build()

                }));

        this.add("remove_blue_swet_cloud", new RemoveEntityDropsModifier(AetherBlocks.BLUE_AERCLOUD.get().asItem(),
                new LootItemCondition[]{LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.BLUE_SWET.get()))
                        .build()
        }));
        this.add("remove_golden_swet_glowstone", new RemoveEntityDropsModifier(Blocks.GLOWSTONE.asItem(),
                new LootItemCondition[]{LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.GOLDEN_SWET.get()))
                        .build()
        }));

        this.doSwetBallIncreases();

        this.add("enchanted_ring", new GenesisAddDungeonLootModifier(
                new LootItemCondition[] { LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_LOOT).build(), LootItemRandomChanceCondition.randomChance(0.35F).build() }, List.of(WeightedEntry.wrap(new ItemStack(ReduxItems.ENCHANTED_RING.get()), 1)),
                BiasedToBottomInt.of(0, 1)));

        this.add("vampire_amulet", new GenesisAddDungeonLootModifier(
                new LootItemCondition[] { LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_REWARD).build(), LootItemRandomChanceCondition.randomChance(0.35F).build() }, List.of(WeightedEntry.wrap(new ItemStack(ReduxItems.VAMPIRE_AMULET.get()), 1)),
                ConstantInt.of(1)));

        this.add("sentry_ring", new GenesisAddDungeonLootModifier(
                new LootItemCondition[] { LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_REWARD).build() }, List.of(WeightedEntry.wrap(new ItemStack(ReduxItems.AIRBOUND_CAPE.get()), 1)),
                UniformInt.of(0, 1)));

        this.add("valkyrie_ring", new GenesisAddDungeonLootModifier(
                new LootItemCondition[] { LootTableIdCondition.builder(AetherLoot.SILVER_DUNGEON_REWARD).build() }, List.of(WeightedEntry.wrap(new ItemStack(ReduxItems.VALKYRIE_RING.get()), 1)),
                UniformInt.of(0, 1)));

        this.add("phoenix_ring", new GenesisAddDungeonLootModifier(
                new LootItemCondition[] { LootTableIdCondition.builder(AetherLoot.GOLD_DUNGEON_REWARD).build() }, List.of(WeightedEntry.wrap(new ItemStack(ReduxItems.PHOENIX_EMBLEM.get()), 1)),
                UniformInt.of(0, 1)));

        this.add("subzero_crossbow", new GenesisAddDungeonLootModifier(
                new LootItemCondition[] { LootTableIdCondition.builder(AetherLoot.GOLD_DUNGEON_REWARD).build(), LootItemRandomChanceCondition.randomChance(0.65F).build() }, List.of(WeightedEntry.wrap(new ItemStack(ReduxItems.SUBZERO_CROSSBOW.get()), 1)),
                UniformInt.of(0, 1)));

        this.add("vanilla_gummy_swet", new GenesisAddDungeonLootModifier(
                new LootItemCondition[] { LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_REWARD).build() }, List.of(WeightedEntry.wrap(new ItemStack(ReduxItems.VANILLA_GUMMY_SWET.get()), 1)),
                UniformInt.of(1, 3)));

    }

    private void doSwetBallIncreases() {


        this.add("blue_swet_ball_increase", new GenesisAddEntityDropsModifier(new ItemStack(AetherItems.SWET_BALL.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.BLUE_SWET.get()))
                        .build()

                }));
        this.add("golden_swet_ball_add", new GenesisAddEntityDropsModifier(new ItemStack(ReduxItems.GOLDEN_SWET_BALL.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.GOLDEN_SWET.get()))
                        .build(),
                        DataLootCondition.conditionOf(new NotCondition<>(Conditions.GENESIS)).build()
                }));
        this.add("genesis_golden_swet_ball_increase", new GenesisAddEntityDropsModifier(new ItemStack(GenesisItems.GOLDEN_SWET_BALL.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.GOLDEN_SWET.get()))
                        .build(),
                        DataLootCondition.conditionOf(Conditions.GENESIS).build()
                }));
        this.add("genesis_dark_swet_ball_increase", new GenesisAddEntityDropsModifier(new ItemStack(GenesisItems.DARK_SWET_BALL.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(GenesisEntityTypes.DARK_SWET.get()))
                        .build(),
                        DataLootCondition.conditionOf(Conditions.GENESIS).build()
                }));

    }
}
