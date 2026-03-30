package net.zepalesque.redux.data.gen.loot;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.crop.WyndoatsBlock;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.item.ReduxItems;

public class ReduxBlockLoot extends ReduxBlockLootProvider {
	private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(AetherBlocks.TREASURE_CHEST.get())
		.map(ItemLike::asItem)
		.collect(Collectors.toSet());

	public ReduxBlockLoot(HolderLookup.Provider registries) {
		super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	protected void generate() {
		Redux.BLOCK_SETS.forEach(set -> set.lootData(this));

		this.leaves(ReduxBlocks.SILVEROOT_LEAVES, ReduxFlowerSets.SILVEROOT_SAPLING.flower());
		this.add(ReduxBlocks.SILVEROOT_LEAF_PILE.get(), shears());

		this.leaves(ReduxBlocks.STORMFIR_LEAVES, ReduxFlowerSets.STORMFIR_SAPLING.flower());
		this.add(ReduxBlocks.STORMFIR_LEAF_PILE.get(), shears());

		this.leaves(ReduxBlocks.BLIGHTWILLOW_LEAVES, ReduxFlowerSets.BLIGHTWILLOW_SAPLING.flower());
		this.add(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE.get(), shears());

		this.add(ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES.get(), leaves ->
			infectedBlightwillow(
				leaves,
				ReduxFlowerSets.BLIGHTWILLOW_SAPLING.flower().get(),
				ReduxItems.WILLOW_SPORES.get(),
				BlockLootAccessor.aether$getNormalLeavesSaplingChances()
			)
		);

		this.add(ReduxBlocks.GILDLEAF_AMBER_LOG.get(), lgo -> droppingDoubleGoldenOak(
			lgo,
			ReduxWoodSets.GILDLEAF.log().get(),
			AetherItems.GOLDEN_AMBER.get()
		));
		this.add(ReduxBlocks.GILDLEAF_AMBER_WOOD.get(), wod -> droppingDoubleGoldenOak(
			wod,
			ReduxWoodSets.GILDLEAF.wood().get(),
			AetherItems.GOLDEN_AMBER.get()
		));

		this.dropDoubleWithSilk(
			ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get(),
			AetherBlocks.AETHER_DIRT.get()
		);

		this.add(ReduxBlocks.CAELGAE_PATCH.get(), shearsOr(ReduxItems.CAELGAE_CLUMP.get(), 0.25F));
		this.add(ReduxBlocks.BLOOMTAIL.get(), shears());
		this.add(ReduxBlocks.ECHYSIA.get(), shears());

		this.dropSelf(ReduxBlocks.CARVED_PILLAR.get());
		this.dropSelf(ReduxBlocks.SENTRY_PILLAR.get());
		this.dropSelf(ReduxBlocks.CARVED_BASE.get());
		this.dropSelf(ReduxBlocks.SENTRY_BASE.get());

		this.dropNone(ReduxBlocks.LOCKED_CARVED_PILLAR.get());
		this.dropNone(ReduxBlocks.LOCKED_SENTRY_PILLAR.get());
		this.dropNone(ReduxBlocks.LOCKED_CARVED_BASE.get());
		this.dropNone(ReduxBlocks.LOCKED_SENTRY_BASE.get());

		this.dropNone(ReduxBlocks.TRAPPED_CARVED_PILLAR.get());
		this.dropNone(ReduxBlocks.TRAPPED_SENTRY_PILLAR.get());
		this.dropNone(ReduxBlocks.TRAPPED_CARVED_BASE.get());
		this.dropNone(ReduxBlocks.TRAPPED_SENTRY_BASE.get());

		this.dropNone(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get());
		this.dropNone(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get());
		this.dropNone(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get());
		this.dropNone(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get());

		this.dropSelf(ReduxBlocks.RUNELIGHT.get());
		this.dropNone(ReduxBlocks.LOCKED_RUNELIGHT.get());
		this.dropNone(ReduxBlocks.LOCKED_POLISHED_SENTRITE.get());

		this.add(ReduxBlocks.WYNDSPROUTS.get(), shearsOr(ReduxItems.WYND_OATS.get(), 0.25F));
		this.add(ReduxBlocks.LUXWEED.get(), shears());
		this.add(ReduxBlocks.SKYSPROUTS.get(), shears());
		this.dropSelf(ReduxBlocks.TURBO_VERBENA.get());
		this.add(
			ReduxBlocks.WYNDOATS.get(),
			this.createCropDrops(
				ReduxBlocks.WYNDOATS.get(),
				ReduxItems.WYND_OAT_PANICLE.get(),
				ReduxItems.WYND_OATS.get(),
				LootItemBlockStatePropertyCondition.hasBlockStateProperties(
					ReduxBlocks.WYNDOATS.get()
				).setProperties(
					StatePropertiesPredicate.Builder.properties().hasProperty(
						WyndoatsBlock.AGE,
						WyndoatsBlock.MAX_AGE
					)
				)
			)
		);
		this.dropSelf(ReduxBlocks.SENTRITE_CHAIN.get());
		this.dropSelf(ReduxBlocks.SENTRITE_LANTERN.get());
		this.dropSelf(ReduxBlocks.SENTRITE_BARS.get());
		this.dropSelf(ReduxBlocks.REFINED_SENTRITE_BLOCK.get());
		this.dropSelf(ReduxBlocks.RUNIC_LANTERN.get());

		this.dropSelf(ReduxBlocks.VERIDIUM_ORE.get());
		this.dropSelf(ReduxBlocks.VERIDIUM_BLOCK.get());
		this.dropSelf(ReduxBlocks.RAW_VERIDIUM_BLOCK.get());
		
		this.dropSelf(ReduxBlocks.HOLYSILT.get());
		this.dropSelf(ReduxBlocks.CLOUD_CAP_BLOCK.get());
		
		this.add(ReduxBlocks.GOLDEN_CLOVERS.get(), this::createPetalsDrops);
		this.add(ReduxBlocks.GOLDEN_VINES.get(), shears());
		this.add(ReduxBlocks.GOLDEN_VINES_PLANT.get(), shears().apply(ReduxBlocks.GOLDEN_VINES.get()));

		this.add(ReduxBlocks.SHADED_VINES.get(), shears());
		this.add(ReduxBlocks.SHADED_VINES_PLANT.get(), shears().apply(ReduxBlocks.SHADED_VINES.get()));

		this.dropSelf(ReduxBlocks.BLEAKMOSS_BLOCK.get());
		this.mossyCarpet(ReduxBlocks.BLEAKMOSS_CARPET.get());

		this.dropSelf(ReduxBlocks.GILDENMOSS_BLOCK.get());
		this.dropSelf(ReduxBlocks.GILDENMOSS_CARPET.get());

		this.dropSelf(ReduxBlocks.LOGICATOR.get());
	}

	@Override
	public Iterable<Block> getKnownBlocks() {
		return ReduxBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
	}
}
