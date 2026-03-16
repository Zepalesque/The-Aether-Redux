package net.zepalesque.redux.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.unity.block.UnityBlocks;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.util.item.TabBuilder;

@EventBusSubscriber(modid = Redux.MODID)
public class ReduxTabs {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
		ItemLike sup = null;
		for (BlockSet set : Redux.BLOCK_SETS)
			sup = set.addToCreativeTab(event, sup, BlockSet.TabAdditionPhase.BEFORE);

		new TabBuilder(event, AetherCreativeTabs.AETHER_NATURAL_BLOCKS.getKey())
			.remove(
				TabVisibility.PARENT_AND_SEARCH_TABS,
				AetherBlocks.GOLDEN_OAK_LEAVES,
				AetherBlocks.GOLDEN_OAK_SAPLING,
				UnityBlocks.GOLDEN_OAK_LEAF_PILE
			)
			.replace(AetherBlocks.GOLDEN_OAK_LOG, ReduxBlocks.GILDLEAF_AMBER_LOG)
			.putAfter(
				UnityBlocks.SHORT_AETHER_GRASS,
				ReduxBlocks.WYNDSPROUTS,
				ReduxBlocks.TURBO_VERBENA,
				ReduxItems.CAELGAE_PATCH,
				ReduxBlocks.BLOOMTAIL
			)
			.putAfter(
				AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK,
				ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK
				// AVELIUM
			)
			.putAfter(
				UnityBlocks.SKYROOT_LEAF_PILE,
				ReduxBlocks.SILVEROOT_LEAVES,
				ReduxBlocks.SILVEROOT_LEAF_PILE,
				ReduxBlocks.STORMFIR_LEAVES,
				ReduxBlocks.STORMFIR_LEAF_PILE,
				ReduxBlocks.BLIGHTWILLOW_LEAVES,
				ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES,
				ReduxBlocks.BLIGHTWILLOW_LEAF_PILE
			)
			.putAfter(ReduxFlowerSets.AURUM.flower(), ReduxBlocks.GOLDEN_CLOVERS)
			.putAfter(AetherBlocks.ZANITE_ORE.get(), ReduxBlocks.VERIDIUM_ORE)
			.putAfter(
				UnityBlocks.FLUTEMOSS_CARPET,
				ReduxBlocks.ECHYSIA,
				ReduxBlocks.GILDENMOSS_BLOCK,
				ReduxBlocks.GILDENMOSS_CARPET,
				ReduxBlocks.BLEAKMOSS_BLOCK,
				ReduxBlocks.BLEAKMOSS_CARPET
			);

		new TabBuilder(event, AetherCreativeTabs.AETHER_DUNGEON_BLOCKS.getKey())
			.putAfter(
				AetherBlocks.CARVED_STONE,
				ReduxBlocks.CARVED_BASE,
				ReduxBlocks.CARVED_PILLAR
			)
			.putAfter(
				AetherBlocks.LOCKED_CARVED_STONE,
				ReduxBlocks.LOCKED_CARVED_BASE,
				ReduxBlocks.LOCKED_CARVED_PILLAR
			)
			.putAfter(
				AetherBlocks.TRAPPED_CARVED_STONE,
				ReduxBlocks.TRAPPED_CARVED_BASE,
				ReduxBlocks.TRAPPED_CARVED_PILLAR
			)
			.putAfter(
				AetherBlocks.BOSS_DOORWAY_CARVED_STONE,
				ReduxBlocks.BOSS_DOORWAY_CARVED_BASE,
				ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR
			)
			.putAfter(
				AetherBlocks.SENTRY_STONE,
				ReduxBlocks.SENTRY_BASE,
				ReduxBlocks.SENTRY_PILLAR
			)
			.putAfter(
				AetherBlocks.LOCKED_SENTRY_STONE,
				ReduxBlocks.LOCKED_SENTRY_BASE,
				ReduxBlocks.LOCKED_SENTRY_PILLAR
			)
			.putAfter(
				AetherBlocks.TRAPPED_SENTRY_STONE,
				ReduxBlocks.TRAPPED_SENTRY_BASE,
				ReduxBlocks.TRAPPED_SENTRY_PILLAR
			)
			.putAfter(
				AetherBlocks.BOSS_DOORWAY_SENTRY_STONE,
				ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE,
				ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR,
				ReduxStoneSets.POLISHED_SENTRITE.block(),
				ReduxBlocks.LOCKED_POLISHED_SENTRITE,
				ReduxBlocks.RUNELIGHT,
				ReduxBlocks.LOCKED_RUNELIGHT,
				ReduxBlocks.RUNIC_LANTERN
			);

		new TabBuilder(event, AetherCreativeTabs.AETHER_BUILDING_BLOCKS.getKey())
			.replace(AetherBlocks.GOLDEN_OAK_LOG, ReduxBlocks.GILDLEAF_AMBER_LOG)
			.replace(AetherBlocks.GOLDEN_OAK_WOOD, ReduxBlocks.GILDLEAF_AMBER_WOOD)
			.putAfter(
				AetherBlocks.ZANITE_BLOCK,
				ReduxBlocks.RAW_VERIDIUM_BLOCK,
				ReduxBlocks.VERIDIUM_BLOCK,
				ReduxBlocks.REFINED_SENTRITE_BLOCK
			)
			.put(ReduxBlocks.SENTRITE_CHAIN, ReduxBlocks.SENTRITE_BARS);
		
		new TabBuilder(event, AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES.getKey())
			.putBefore(
				AetherItems.GRAVITITE_SWORD,
				ReduxItems.INFUSED_VERIDIUM_HOE,
				ReduxItems.INFUSED_VERIDIUM_AXE,
				ReduxItems.INFUSED_VERIDIUM_PICKAXE,
				ReduxItems.INFUSED_VERIDIUM_SHOVEL,
				ReduxItems.INFUSED_VERIDIUM_SWORD,
				ReduxItems.VERIDIUM_HOE,
				ReduxItems.VERIDIUM_AXE,
				ReduxItems.VERIDIUM_PICKAXE,
				ReduxItems.VERIDIUM_SHOVEL,
				ReduxItems.VERIDIUM_SWORD
			)
			.putAfter(
				AetherItems.ENCHANTED_DART,
				ReduxItems.VERIDIUM_DART_SHOOTER,
				ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER,
				ReduxItems.VERIDIUM_DART
			)
			.putBefore(AetherItems.BOOK_OF_LORE, ReduxItems.SENTRITE_SHEARS)
			.putAfter(
				AetherItems.MUSIC_DISC_ASCENDING_DAWN,
				ReduxItems.MUSIC_DISC_SENTIENCE
			);
		
		new TabBuilder(event, AetherCreativeTabs.AETHER_INGREDIENTS.getKey())
			.putAfter(
				AetherItems.ZANITE_GEMSTONE,
				ReduxItems.RAW_VERIDIUM,
				ReduxItems.VERIDIUM_INGOT,
				ReduxItems.VERIDIUM_NUGGET,
				ReduxItems.REFINED_SENTRITE,
				ReduxItems.SENTRITE_CHUNK
			)
			.putAfter(AetherItems.SKYROOT_STICK, ReduxItems.WILLOW_SPORES)
			.putAfter(
				AetherItems.AECHOR_PETAL,
				ReduxItems.WYND_OATS,
				ReduxItems.WYND_OAT_PANICLE
			);
		
		new TabBuilder(event, AetherCreativeTabs.AETHER_REDSTONE_BLOCKS.getKey())
			.put(ReduxBlocks.LOGICATOR);

		new TabBuilder(event, AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES.getKey())
			.putAfter(AetherItems.SWET_CAPE, ReduxItems.AERBOUND_CAPE);

		new TabBuilder(event, AetherCreativeTabs.AETHER_FUNCTIONAL_BLOCKS.getKey())
			.putAfter(AetherBlocks.AMBROSIUM_TORCH, ReduxBlocks.SENTRITE_LANTERN);
		
		new TabBuilder(event, AetherCreativeTabs.AETHER_FOOD_AND_DRINKS.getKey())
			.putAfter(
				AetherItems.WHITE_APPLE,
				ReduxItems.CAELGAE_CLUMP,
				ReduxItems.SEAWEED_SALAD
			);
		
		new TabBuilder(event, CreativeModeTabs.REDSTONE_BLOCKS)
			.putAfter(Items.COMPARATOR, ReduxBlocks.LOGICATOR);

		// SHOULD BE AT THE VERY END
		sup = null;
		for (var set : Redux.BLOCK_SETS) {
			sup = set.addToCreativeTab(event, sup, BlockSet.TabAdditionPhase.AFTER);
		}
	}
}
