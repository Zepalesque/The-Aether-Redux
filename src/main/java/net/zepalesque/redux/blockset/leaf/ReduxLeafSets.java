package net.zepalesque.redux.blockset.leaf;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.world.treegrower.AetherTreeGrowers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.leaf.type.AetherLeafSet;
import net.zepalesque.redux.blockset.leaf.type.SnowableLeafSet;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.world.tree.ReduxTreeGrowers;
import net.zepalesque.unity.block.UnityBlocks;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.type.AbstractLeafSet;

public class ReduxLeafSets {
	public static final AetherLeafSet GILDLEAF = register(
		new AetherLeafSet(
			"gildleaf",
			"natural/",
			"natural/",
			AetherTreeGrowers.GOLDEN_OAK,
			MapColor.GOLD,
			MapColor.GOLD,
			ReduxParticles.GOLDEN_OAK_LEAF
		).saplingTabAfter(
			AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
			AetherBlocks.SKYROOT_SAPLING,
			BlockSet.TabAdditionPhase.BEFORE
		).leafTabAfter(
			AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
			UnityBlocks.SKYROOT_LEAF_PILE,
			BlockSet.TabAdditionPhase.AFTER // TODO: change to before once other leaf sets are added
		).pileTabAfter(
			AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
			() -> ReduxLeafSets.GILDLEAF.leaves().asItem(), // itemlike functional interface holy peak
			BlockSet.TabAdditionPhase.AFTER // TODO: change to before once other leaf sets are added
		)
		.saplingCompost(0.3F)
		.pileCompost(0.3F)
		.withSaplingLore("The sapling of the Gildleaf tree. It can be grown by waiting or using Bone Meal.")
		.withLeafItemTag(ItemTags.LEAVES)
		.withLeafTag(BlockTags.LEAVES)
		.withLeafTag(BlockTags.MINEABLE_WITH_HOE)
		.withSaplingItemTag(ItemTags.SAPLINGS)
		.withSaplingTag(BlockTags.SAPLINGS)
		.withLeafLore("The leaves of the Gildleaf tree. These sometimes drop Gildleaf Saplings.")
		.withPileTag(BlockTags.MINEABLE_WITH_HOE)
		.withPileLore("A pile of Gildleaf Leaves. These can be stacked on top of eachother to make various sizes!")
	);

	public static final SnowableLeafSet MOONFIR = register(
		new SnowableLeafSet(
			"moonfir",
			"natural/",
			"natural/",
			ReduxTreeGrowers.MOONFIR,
			MapColor.LAPIS,
			MapColor.LAPIS,
			ReduxParticles.MOONFIR_LEAF
		).saplingTabAfter(
			AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
			() -> ReduxFlowerSets.SILVEROOT_SAPLING.flower().asItem(),
			BlockSet.TabAdditionPhase.BEFORE
		).leafTabAfter(
			AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
			ReduxBlocks.SILVEROOT_LEAF_PILE,
			BlockSet.TabAdditionPhase.AFTER // TODO: change to before once other leaf sets are added
		).pileTabAfter(
			AetherCreativeTabs.AETHER_NATURAL_BLOCKS,
			() -> ReduxLeafSets.MOONFIR.leaves().asItem(), // itemlike functional interface holy peak
			BlockSet.TabAdditionPhase.AFTER // TODO: change to before once other leaf sets are added
		)
		.saplingCompost(0.3F)
		.pileCompost(0.3F)
		.withSaplingLore("The sapling of the Moonfir tree. It can be grown by waiting or using Bone Meal.")
		.withLeafItemTag(ItemTags.LEAVES)
		.withLeafTag(BlockTags.LEAVES)
		.withLeafTag(BlockTags.MINEABLE_WITH_HOE)
		.withSaplingItemTag(ItemTags.SAPLINGS)
		.withSaplingTag(BlockTags.SAPLINGS)
		.withLeafLore("The leaves of the Moonfir tree. These sometimes drop Moonfir Saplings.")
		.withPileTag(BlockTags.MINEABLE_WITH_HOE)
		.withPileLore("A pile of Moonfir Leaves. These can be stacked on top of eachother to make various sizes!")
	);

	public static <T extends AbstractLeafSet<T>> T register(T set) {
		Redux.BLOCK_SETS.add(set);
		return set;
	}
}
