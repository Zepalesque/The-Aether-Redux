package net.zepalesque.redux.data.prov.loot;

import com.aetherteam.aether.data.providers.AetherBlockLootSubProvider;
import com.aetherteam.aether.loot.functions.DoubleDrops;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.zepalesque.redux.block.state.ReduxStates;

import java.util.Set;
import java.util.function.Function;

public abstract class ReduxBlockLootProvider extends AetherBlockLootSubProvider {

    public ReduxBlockLootProvider(Set<Item> items, FeatureFlagSet flags) {
        super(items, flags);
    }

    protected LootTable.Builder naturalDropBase(Block block, ItemLike other) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionDecay(other, LootItem.lootTableItem(other)))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.NATURAL_GEN, true))
                                ).apply(DoubleDrops.builder())
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionDecay(other, LootItem.lootTableItem(block)))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.NATURAL_GEN, false))
                                ).apply(DoubleDrops.builder())
                );
    }

    public void naturalDrop(Block block, ItemLike other) {
        this.add(block, naturalDropBase(block, other));
    }

    @Override
    public void dropSelf(Block block) {
        super.dropSelf(block);
    }

    @Override
    public void dropOther(Block block, ItemLike item) {
        super.dropOther(block, item);
    }

    @Override
    public void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
    }

    @Override
    public void add(Block block, Function<Block, LootTable.Builder> factory) {
        super.add(block, factory);
    }

    @Override
    public LootTable.Builder createDoorTable(Block pDoorBlock) {
        return super.createDoorTable(pDoorBlock);
    }
}
