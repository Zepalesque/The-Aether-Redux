package net.zepalesque.redux.data.prov.loot;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.functions.DoubleDrops;
import com.aetherteam.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.zepalesque.redux.block.backport.MossyCarpetBlock;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.unity.data.prov.loot.UnityBlockLootProvider;

// Many of these are just public overrides with no differences, as this is used by the BlockSets
public abstract class ReduxBlockLootProvider extends UnityBlockLootProvider {

	public ReduxBlockLootProvider(
		Set<Item> items,
		FeatureFlagSet flags,
		HolderLookup.Provider registries
	) {
		super(items, flags, registries);
	}

	protected LootTable.Builder naturalDropBase(Block block, ItemLike other) {
		return LootTable.lootTable()
			.withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1.0F))
					.add(this.applyExplosionDecay(other, LootItem.lootTableItem(other)))
					.when(
						LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
							StatePropertiesPredicate.Builder.properties().hasProperty(
								ReduxStates.NATURAL_GEN,
								true
							)
						)
					)
					.apply(DoubleDrops.builder())
			)
			.withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1.0F))
					.add(this.applyExplosionDecay(other, LootItem.lootTableItem(block)))
					.when(
						LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
							StatePropertiesPredicate.Builder.properties().hasProperty(
								ReduxStates.NATURAL_GEN,
								false
							)
						)
					)
					.apply(DoubleDrops.builder())
			);
	}

	public void naturalDrop(Block block, ItemLike other) {
		this.add(block, this.naturalDropBase(block, other));
	}

	@Override
	public void dropSelf(Block block) {
		super.dropSelf(block);
	}

	public static LootTable.Builder createOnlyWhenDispatchTable(
		Block block,
		LootItemCondition.Builder conditionBuilder
	) {
		return LootTable.lootTable().withPool(
			LootPool.lootPool()
				.setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(block).when(conditionBuilder))
		);
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

	public void mossyCarpet(Block carpet) {
		this.add(carpet, block ->
			createOnlyWhenDispatchTable(
				block,
				LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
					StatePropertiesPredicate.Builder.properties().hasProperty(MossyCarpetBlock.BASE, true)
				)
			)
		);
	}

	public void leaves(
		DeferredBlock<? extends LeavesBlock> leaves,
		DeferredBlock<? extends SaplingBlock> sapling
	) {
		this.add(leaves.get(), l -> this.droppingWithChancesAndSkyrootSticks(
			l,
			sapling.get(),
			BlockLootAccessor.aether$getNormalLeavesSaplingChances()
		));
	}

	@Override
	public LootTable.Builder createDoorTable(Block pDoorBlock) {
		return super.createDoorTable(pDoorBlock);
	}

	@Override
	public LootTable.Builder createSingleItemTableWithSilkTouch(Block pBlock, ItemLike pItem) {
		return super.createSingleItemTableWithSilkTouch(pBlock, pItem);
	}

	@Override
	public LootTable.Builder createSingleItemTableWithSilkTouch(
		Block pBlock,
		ItemLike pItem,
		NumberProvider pCount
	) {
		return super.createSingleItemTableWithSilkTouch(pBlock, pItem, pCount);
	}

	@Override
	public void dropPottedContents(Block pFlowerPot) {
		super.dropPottedContents(pFlowerPot);
	}

	@Override
	public LootTable.Builder createSlabItemTable(Block pBlock) {
		return super.createSlabItemTable(pBlock);
	}

	public LootTable.Builder infectedBlightwillow(
		Block block,
		Block sapling,
		Item item,
		float... chances
	) {
		return this.createForgeSilkTouchOrShearsDispatchTable(
			block,
			this.applyExplosionCondition(block, LootItem.lootTableItem(sapling)).when(
				BonusLevelTableCondition.bonusLevelFlatChance(
					this.registries.holderOrThrow(Enchantments.FORTUNE),
					chances
				)
			)
		)
			.withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1))
					.when(HAS_SHEARS.or(this.hasSilkTouch()).invert())
					.add(
						this.applyExplosionDecay(
							block,
							LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(
								SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))
							)
						).when(
							BonusLevelTableCondition.bonusLevelFlatChance(
								this.registries.holderOrThrow(Enchantments.FORTUNE),
								0.02F,
								0.022222223F,
								0.025F,
								0.033333335F,
								0.1F
							)
						)
					)
			)
			.apply(DoubleDrops.builder())
			.withPool(
				this.applyExplosionDecay(
					item,
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(
							LootItem.lootTableItem(item)
								.when(
									MatchTool.toolMatches(
										ItemPredicate.Builder.item().of(ReduxTags.Items.WILLOW_SPORE_HARVESTERS)
									)
								)
								.when(HAS_SHEARS.or(this.hasSilkTouch()).invert())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
								.apply(
									ApplyBonusCount.addOreBonusCount(
										this.registries.holderOrThrow(Enchantments.FORTUNE)
									)
								)
						)
				)
			)
			.apply(DoubleDrops.builder());
	}
}
