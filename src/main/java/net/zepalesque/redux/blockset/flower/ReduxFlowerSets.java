package net.zepalesque.redux.blockset.flower;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.dungeon.Flareblossom;
import net.zepalesque.redux.block.natural.AetherMushroom;
import net.zepalesque.redux.block.natural.GloomshadeBlock;
import net.zepalesque.redux.blockset.flower.type.AetherFlowerSet;
import net.zepalesque.redux.blockset.flower.type.BaseFlowerSet;
import net.zepalesque.redux.blockset.flower.type.CloverSet;
import net.zepalesque.redux.blockset.flower.type.DualGlowingFlowerSet;
import net.zepalesque.redux.blockset.flower.type.EnchantedFlowerSet;
import net.zepalesque.redux.blockset.flower.type.GlowingFlowerSet;
import net.zepalesque.redux.blockset.flower.type.UntintedFlowerSet;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;
import net.zepalesque.redux.world.tree.ReduxTreeGrowers;
import net.zepalesque.unity.block.natural.bush.CustomBoundsBushBlock;
import net.zepalesque.unity.block.natural.bush.CustomBoundsFlowerBlock;
import net.zepalesque.unity.client.UnityColors;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.type.AbstractFlowerSet;
import net.zepalesque.zenith.util.block.CommonPlantBounds;

public class ReduxFlowerSets {
	public static final BaseFlowerSet<CustomBoundsFlowerBlock.Enchanted> AURUM = register(
		new EnchantedFlowerSet<>(
			"aurum",
			"natural/",
			() -> new CustomBoundsFlowerBlock.Enchanted(
				CommonPlantBounds.FLOWER,
				MobEffects.LUCK,
				60,
				Properties.ofFullCopy(Blocks.DANDELION).hasPostProcess((s, l, p) -> true)
			),
			1,
			0xFFFFFF
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				AetherBlocks.WHITE_FLOWER,
				BlockSet.TabAdditionPhase.BEFORE
			)
			.craftsIntoShapeless(1, () -> Items.YELLOW_DYE, 1, RecipeCategory.MISC)
			.withFlowerTag(BlockTags.FLOWERS)
			.compost(0.3F)
			.withLore("A golden flower found in the Gilded Groves. Some say it brings good luck!")
	);

	public static final BaseFlowerSet<SaplingBlock> SILVEROOT_SAPLING = register(
		new UntintedFlowerSet<>("silveroot_sapling", "natural/", () ->
			new SaplingBlock(
				ReduxTreeGrowers.SILVEROOT,
				Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.QUARTZ)
			)
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				AetherBlocks.SKYROOT_SAPLING,
				BlockSet.TabAdditionPhase.BEFORE
			)
			.withFlowerTag(BlockTags.SAPLINGS)
			.compost(0.3F)
			.withLore("The sapling of the Silveroot tree. It can be grown by waiting or using Bone Meal.")
	);

	public static final BaseFlowerSet<SaplingBlock> PRISMA_SAPLING = register(
		new UntintedFlowerSet<>("prisma_sapling", "natural/", () ->
			new SaplingBlock(
				ReduxTreeGrowers.PRISMA,
				Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.DIAMOND)
			)
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				AetherBlocks.SKYROOT_SAPLING,
				BlockSet.TabAdditionPhase.BEFORE
			)
			.withFlowerTag(BlockTags.SAPLINGS)
			.compost(0.3F)
			.withLore("The sapling of the Prisma tree. It can be grown by waiting or using Bone Meal.")
	);

	public static final BaseFlowerSet<SaplingBlock> STORMFIR_SAPLING = register(
		new UntintedFlowerSet<>("stormfir_sapling", "natural/", () ->
			new SaplingBlock(
				ReduxTreeGrowers.STORMFIR,
				Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.TERRACOTTA_PURPLE)
			)
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				() -> ReduxFlowerSets.SILVEROOT_SAPLING.flower().asItem(),
				BlockSet.TabAdditionPhase.BEFORE
			)
			.withFlowerTag(BlockTags.SAPLINGS)
			.compost(0.3F)
			.withLore("The sapling of the Stormfir tree. It can be grown by waiting or using Bone Meal.")
	);

	public static final BaseFlowerSet<SaplingBlock> BLIGHTWILLOW_SAPLING = register(
		new UntintedFlowerSet<>("blightwillow_sapling", "natural/", () ->
			new SaplingBlock(
				ReduxTreeGrowers.BLIGHTWILLOW,
				Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_PURPLE)
			)
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				ReduxFlowerSets.STORMFIR_SAPLING.flower(),
				BlockSet.TabAdditionPhase.BEFORE
			)
			.withFlowerTag(BlockTags.SAPLINGS)
			.compost(0.3F)
			.withLore(
				"The sapling of the Blightwillow tree. It can be grown by waiting or using Bone Meal."
			)
	);

	public static final BaseFlowerSet<AetherMushroom> CLOUDCAP_MUSHLING = register(
		new GlowingFlowerSet<>(
			"cloudcap_mushling",
			"natural/",
			() -> new AetherMushroom(
				Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D),
				Properties.ofFullCopy(Blocks.NETHER_SPROUTS).lightLevel($ -> 6),
				ReduxFeatureConfig.CLOUDCAP_MUSHROOM
			)
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				ReduxFlowerSets.BLIGHTWILLOW_SAPLING.flower(),
				BlockSet.TabAdditionPhase.BEFORE
			)
			.withFlowerTag(BlockTags.SAPLINGS)
			.compost(0.3F)
			.withLore(
				"A mushroom found commonly in the Cloudcap Ridges. It gives off a faint glow, and can be grown into a larger variation."
			)
	);


	public static final BaseFlowerSet<CustomBoundsBushBlock> LUCKY_CLOVER = register(
		new CloverSet<>("lucky_clover", "natural/", () ->
			new CustomBoundsBushBlock(
				Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D),
				Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.GOLD)
			)
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				ReduxBlocks.GOLDEN_CLOVERS,
				BlockSet.TabAdditionPhase.AFTER
			)
			.withLore(
				"A large four-leaved clover found in the Gilded Groves. Makes a nice decoration, and can be placed in a flower pot!"
			)
	);
	
	public static final BaseFlowerSet<CustomBoundsFlowerBlock> SPIROLYCTIL = register(
		new AetherFlowerSet<>(
			"spirolyctil",
			"natural/",
			() -> new CustomBoundsFlowerBlock(
				CommonPlantBounds.FLOWER,
				MobEffects.LEVITATION,
				4,
				Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
			),
			1,
			ReduxColors.Tints.BLIGHT_GRASS_COLOR
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				AetherBlocks.WHITE_FLOWER,
				BlockSet.TabAdditionPhase.BEFORE
			)
			.craftsIntoShapeless(1, () -> Items.LIGHT_BLUE_DYE, 1, RecipeCategory.MISC)
			.withFlowerTag(BlockTags.FLOWERS)
			.withLore(
				"An indigo flower found in the Blight. This plant almost feels like an outlier, as it gives off a much more peaceful vibe than other surrounding Blight foliage."
			)
	);

	public static final BaseFlowerSet<Flareblossom> FLAREBLOSSOM = register(
		new DualGlowingFlowerSet<>(
			"flareblossom",
			"dungeon/",
			() -> new Flareblossom(
				MobEffects.BLINDNESS,
				60,
				Properties.ofFullCopy(Blocks.POPPY)
					.lightLevel(state -> 11)
					.mapColor(MapColor.GOLD)
			),
			1,
			UnityColors.AETHER_GRASS_COLOR
		)
			// GlowingFlowerSet flags (wouldn't be an issue if only java had a Self or This type :anguish:
			.useGlowAsParticle()
			// Base
			.tabAfter(
				AetherCreativeTabs.AETHER_DUNGEON_BLOCKS,
				AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE,
				BlockSet.TabAdditionPhase.BEFORE
			)
			.craftsIntoShapeless(1, () -> Items.BLAZE_POWDER, 1, RecipeCategory.MISC)
			.withFlowerTag(BlockTags.FLOWERS)
			// TODO: change once the use has been implemented
			.withLore(
				"A rare, exotic flower found on Gold Dungeons. Its properties have not yet been discovered..."
			)
	);

	public static final BaseFlowerSet<GloomshadeBlock> GLOOMSHADE = register(
		new AetherFlowerSet<>(
			"gloomshade",
			"natural/",
			() -> new GloomshadeBlock(
				CommonPlantBounds.BUSH,
				// TODO
				MobEffects.DARKNESS,
				60,
				Properties.ofFullCopy(Blocks.WITHER_ROSE).mapColor(MapColor.COLOR_BLACK)
			),
			1,
			ReduxColors.Tints.BLIGHT_GRASS_COLOR
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				ReduxFlowerSets.SPIROLYCTIL.flower(),
				BlockSet.TabAdditionPhase.BEFORE
			)
			.craftsIntoShapeless(1, () -> Items.BLACK_DYE, 1, RecipeCategory.MISC)
			.withFlowerTag(BlockTags.FLOWERS)
			.withLore(
				"Dark purple flowers found in the Blight. These release a gas that limits their target's vision."
			)
	);

	public static final BaseFlowerSet<CustomBoundsFlowerBlock> LUMINA = register(
		new DualGlowingFlowerSet<>(
			"lumina",
			"natural/",
			() -> new CustomBoundsFlowerBlock(
				CommonPlantBounds.FLOWER,
				MobEffects.NIGHT_VISION,
				60,
				Properties.ofFullCopy(Blocks.POPPY)
					.lightLevel(state -> 9)
					.mapColor(MapColor.WOOL)
			),
			1,
			ReduxColors.Tints.FROSTED_GRASS_COLOR
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				ReduxFlowerSets.GLOOMSHADE.flower(),
				BlockSet.TabAdditionPhase.BEFORE
			)
			.craftsIntoShapeless(1, () -> Items.BLACK_DYE, 1, RecipeCategory.MISC)
			.withFlowerTag(BlockTags.FLOWERS)
			// aura (brainrot) :anguish:
			.withLore(
				"A flower found in the Frosted Forests. It has a subtle luminant aura (hence the name), lighting the area around it."
			)
	);

	public static final BaseFlowerSet<CustomBoundsFlowerBlock> DAGGERBLOOM = register(
		new AetherFlowerSet<>(
			"daggerbloom",
			"natural/",
			() -> new CustomBoundsFlowerBlock(
				CommonPlantBounds.FERN,
				MobEffects.MOVEMENT_SLOWDOWN,
				60,
				Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.ICE)
			),
			1,
			ReduxColors.Tints.FROSTED_GRASS_COLOR
		)
			.tabAfter(
				AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
				ReduxFlowerSets.LUMINA.flower(),
				BlockSet.TabAdditionPhase.BEFORE
			)
			.craftsIntoShapeless(1, () -> Items.WHITE_DYE, 1, RecipeCategory.MISC)
			.withFlowerTag(BlockTags.FLOWERS)
			// aura (brainrot) :anguish:
			.withLore("A flower found in the Frosted Forests. Its pointed petals are as cold as ice.")
	);

	public static <T extends AbstractFlowerSet> T register(T set) {
		Redux.BLOCK_SETS.add(set);
		return set;
	}
}
