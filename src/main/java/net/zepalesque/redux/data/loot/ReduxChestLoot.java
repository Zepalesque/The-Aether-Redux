package net.zepalesque.redux.data.loot;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.ReduxLoot;

import java.util.function.BiConsumer;

public class ReduxChestLoot implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(ReduxLoot.LOBOTIUM_DUNGEON, LootTable.lootTable(
                // THE item
                ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ReduxItems.FIREINTHEHOLE.get()).setWeight(1))
                // Funny Items
                ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 5.0F))
                        .add(LootItem.lootTableItem(ReduxBlocks.HOLEFIRE_SPIKE.get()).setWeight(7))
                        .add(LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(2))
                        .add(LootItem.lootTableItem(ReduxItems.MOUSE_EAR_SOUP.get()).setWeight(6))
                        .add(LootItem.lootTableItem(ReduxBlocks.FLAWLESS_BLOOM.get()).setWeight(5))
                // Random Stuff
                ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 10.0F))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).setWeight(3))
                        .add(LootItem.lootTableItem(ReduxBlocks.SPLITFERN.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ReduxItems.WYNDSPROUT_SEEDS.get()).setWeight(4))
                        .add(LootItem.lootTableItem(ReduxItems.LEMON_POPROCKS.get()).setWeight(3))
                        .add(LootItem.lootTableItem(ReduxItems.OATMEAL.get()).setWeight(3))
                        .add(LootItem.lootTableItem(ReduxItems.BLUEBERRY_BAGEL.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.CANDY_CANE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(ReduxBlocks.RAINBOW_AERCLOUD.get()).setWeight(1))
                // Cobwebs
                ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(Blocks.COBWEB).setWeight(1))
                // Valuables
                ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(10))
                        .add(LootItem.lootTableItem(ReduxItems.GRAVITITE_INGOT.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ReduxItems.VERIDIUM_INGOT.get()).setWeight(8))
                        .add(LootItem.lootTableItem(ReduxItems.VERIDIUM_SWORD.get()).setWeight(3))
                        .add(LootItem.lootTableItem(ReduxItems.VERIDIUM_PICKAXE.get()).setWeight(4))
                        .add(LootItem.lootTableItem(ReduxItems.VERIDIUM_DART_SHOOTER.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(6))
                )
        );
    }
}