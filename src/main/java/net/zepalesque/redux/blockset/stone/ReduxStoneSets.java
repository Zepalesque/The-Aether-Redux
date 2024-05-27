package net.zepalesque.redux.blockset.stone;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockset.stone.type.BaseStoneSet;
import net.zepalesque.redux.blockset.stone.type.BrickStoneSet;
import net.zepalesque.redux.blockset.util.CraftingMatrices;
import net.zepalesque.redux.blockset.wood.type.AbstractBookshelfSet;
import net.zepalesque.redux.blockset.wood.type.LogWallWoodSet;
import net.zepalesque.redux.blockset.wood.type.RegularBookshelfSet;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;

public class ReduxStoneSets {

    public static final BaseStoneSet SENTRITE = register(new BaseStoneSet("sentrite", MapColor.DEEPSLATE, SoundType.NETHER_ORE, 1.0F, 6.0F, "natural",
            "A dark rock found in the Aether. A mixture of this and holystone is what makes up the walls of the Bronze Dungeon.")).craftsInto(AetherBlocks.CARVED_STONE,
            new CraftingMatrix(4, builder ->
                    builder
                            .define('H', AetherBlocks.HOLYSTONE.get())
                            .pattern("#H")
                            .pattern("H#")))
            .craftsIntoSet(() -> ReduxStoneSets.SENTRITE_BRICKS, CraftingMatrices.SQUARE_2X2)
            .stonecutIntoSet(() -> ReduxStoneSets.SENTRITE_BRICKS)
            .creativeTab(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_WALL, true)
            .creativeTab(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, AetherBlocks.HOLYSTONE, false)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);

    public static final BaseStoneSet SENTRITE_BRICKS = register(new BrickStoneSet("sentrite_brick", MapColor.DEEPSLATE, SoundType.NETHER_BRICKS, 1.0F, 6.0F, "construction",
            "Bricks made of Sentrite. These can be used as a nice building block!"))
            .creativeTab(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, () -> ReduxStoneSets.SENTRITE.wall().get(), true)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);


    public static <T extends AbstractStoneSet> T register(T set) {
        Redux.STONE_SETS.add(set);
        return set;
    }

    public static void init() {}
}
