package net.zepalesque.redux.data;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.PackOutput;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.api.condition.Not;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.condition.DataLootCondition;
import net.zepalesque.redux.loot.modifiers.DungeonLootModifier;
import net.zepalesque.redux.loot.modifiers.AddDrops;
import net.zepalesque.redux.loot.modifiers.RawOreModifier;
import net.zepalesque.redux.loot.modifiers.RemoveDrops;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.List;

public class ReduxLootModifierData extends GlobalLootModifierProvider {
    public ReduxLootModifierData(PackOutput output) {
        super(output, Redux.MODID);
    }
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));

    @Override
    protected void start() {

        this.add("cockatrice_feather", new AddDrops(new ItemStack(ReduxItems.COCKATRICE_FEATHER.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.COCKATRICE.get())).build(),
                        LootItemRandomChanceCondition.randomChance(0.05F).build()
                }));

        this.add("raw_gravitite", new RawOreModifier(AetherBlocks.GRAVITITE_ORE.get().asItem(), new ItemStack(ReduxItems.RAW_GRAVITITE.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).build(),
                        ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build()
                },
                new LootItemCondition[] {
                        DataLootCondition.conditionOf(Conditions.RAW_ORES).build(),
                        HAS_SILK_TOUCH.invert().build(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetherBlocks.GRAVITITE_ORE.get()).build()}));

        // TODO: Integrate directly into loot table
        this.add("raw_veridium", new RawOreModifier(ReduxBlocks.VERIDIUM_ORE.get().asItem(), new ItemStack(ReduxItems.RAW_VERIDIUM.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).build(),
                        ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build()
                },
                new LootItemCondition[] {
                        DataLootCondition.conditionOf(Conditions.RAW_ORES).build(),
                        HAS_SILK_TOUCH.invert().build(),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ReduxBlocks.VERIDIUM_ORE.get()).build()}));

        this.add("sentry_chip", new AddDrops(new ItemStack(ReduxItems.SENTRY_CHIP.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(0, 1.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(ReduxTags.EntityTypes.SENTRIES)).build(),
                        LootItemRandomChanceCondition.randomChance(0.25F).build()
                }));


        this.add("swet_sugar_no_genesis", new AddDrops(new ItemStack(Items.SUGAR),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherTags.Entities.SWETS))
                        .build(),
                        DataLootCondition.conditionOf(new Not(Conditions.GENESIS)).build()

                }));

        this.add("remove_blue_swet_cloud", new RemoveDrops(AetherBlocks.BLUE_AERCLOUD.get().asItem(),
                new LootItemCondition[]{LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.BLUE_SWET.get()))
                        .build()
        }));
        this.add("remove_golden_swet_glowstone", new RemoveDrops(Blocks.GLOWSTONE.asItem(),
                new LootItemCondition[]{LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.GOLDEN_SWET.get()))
                        .build()
        }));

        this.doSwetBallIncreases();

        this.add("vampire_amulet", new DungeonLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_REWARD).build(),
                        LootItemRandomChanceCondition.randomChance(0.35F).build()
                },
                List.of(
                        WeightedEntry.wrap(new ItemStack(ReduxItems.VAMPIRE_AMULET.get()), 1)
                ),
                ConstantInt.of(1)
        ));

        this.add("airbound_cape", new DungeonLootModifier(
                new LootItemCondition[]
                        {
                                LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_REWARD).build(),
                                LootItemRandomChanceCondition.randomChance(0.75F).build()
                        },
                List.of(
                        WeightedEntry.wrap(new ItemStack(ReduxItems.AIRBOUND_CAPE.get()), 1)
                ),
                ConstantInt.of(1)
        ));

        this.add("grand_medal", new AddDrops(new ItemStack(ReduxItems.GRAND_VICTORY_MEDAL.get()),
                new LootItemFunction[]{
                        SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).build(),
                },
                new LootItemCondition[] {
                        LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.VALKYRIE_QUEEN.get())).build(),
                        LootItemRandomChanceCondition.randomChance(0.65F).build()
                }));

        this.add("solar_emblem", new DungeonLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(AetherLoot.GOLD_DUNGEON_REWARD).build(),
                        LootItemRandomChanceCondition.randomChance(0.35F).build()
                },
                List.of(
                        WeightedEntry.wrap(new ItemStack(ReduxItems.SOLAR_EMBLEM.get()), 1)
                ),
                ConstantInt.of(1)
        ));

        this.add("subzero_crossbow", new DungeonLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(AetherLoot.GOLD_DUNGEON_REWARD).build(),
                        LootItemRandomChanceCondition.randomChance(0.85F).build()
                },
                List.of(
                        WeightedEntry.wrap(new ItemStack(ReduxItems.SUBZERO_CROSSBOW.get()), 1)
                ),
                ConstantInt.of(1)
        ));

        this.add("vanilla_gummy_swet", new DungeonLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(AetherLoot.BRONZE_DUNGEON_REWARD).build()
                },
                List.of(
                        WeightedEntry.wrap(new ItemStack(ReduxItems.VANILLA_GUMMY_SWET.get()), 1)
                ),
                UniformInt.of(1, 3)
        ));

    }

    private void doSwetBallIncreases() {


        this.add("blue_swet_ball_increase", new AddDrops(new ItemStack(AetherItems.SWET_BALL.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.BLUE_SWET.get()))
                        .build()

                }));
        this.add("golden_swet_ball_add", new AddDrops(new ItemStack(ReduxItems.GOLDEN_SWET_BALL.get()),
                new LootItemFunction[] {
                        SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)).build(),
                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
                new LootItemCondition[] {LootItemEntityPropertyCondition
                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.GOLDEN_SWET.get()))
                        .build(),
                        DataLootCondition.conditionOf(new Not<>(Conditions.GENESIS)).build()
                }));
//        this.add("genesis_golden_swet_ball_increase", new GenesisAddDropsModifier(new ItemStack(GenesisItems.GOLDEN_SWET_BALL.get()),
//                new LootItemFunction[] {
//                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
//                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
//                new LootItemCondition[] {LootItemEntityPropertyCondition
//                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(AetherEntityTypes.GOLDEN_SWET.get()))
//                        .build(),
//                        DataLootCondition.conditionOf(Conditions.GENESIS).build()
//                }));
//        this.add("genesis_dark_swet_ball_increase", new GenesisAddDropsModifier(new ItemStack(GenesisItems.DARK_SWET_BALL.get()),
//                new LootItemFunction[] {
//                        SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)).build(),
//                        LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)).build() },
//                new LootItemCondition[] {LootItemEntityPropertyCondition
//                        .hasProperties(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().of(GenesisEntityTypes.DARK_SWET.get()))
//                        .build(),
//                        DataLootCondition.conditionOf(Conditions.GENESIS).build()
//                }));
    }
}
