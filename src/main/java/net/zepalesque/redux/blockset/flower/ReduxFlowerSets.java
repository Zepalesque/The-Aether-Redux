package net.zepalesque.redux.blockset.flower;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.natural.bush.CustomBoundsFlowerBlock;
import net.zepalesque.redux.blockset.flower.type.AetherFlowerSet;
import net.zepalesque.redux.blockset.flower.type.BaseFlowerSet;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.zenith.api.blockset.AbstractFlowerSet;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.block.util.CommonPlantBounds;

public class ReduxFlowerSets {

    public static final BaseFlowerSet<CustomBoundsFlowerBlock> AURUM = register(new AetherFlowerSet<>("aurum", "natural/",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FLOWER,
                    () -> MobEffects.LUCK, 60, Properties.ofFullCopy(Blocks.DANDELION)), 1, /*TODO*/ 0xFFED96))
            .creativeTab(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, AetherBlocks.WHITE_FLOWER)
            .craftsIntoShapeless(1, () -> Items.YELLOW_DYE, 1, RecipeCategory.MISC);

    public static <T extends AbstractFlowerSet> T register(T set) {
        Redux.FLOWER_SETS.add(set);
        return set;
    }

    public static void init() {}
}
