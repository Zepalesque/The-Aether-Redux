package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.block.ReduxBlocks;

import java.util.Map;

public class BlockBreakHooks {


    private static final Map<Block, Block> BREAK_SPEED_REMAP = Maps.newHashMap((new ImmutableMap.Builder<Block, Block>())
            .put(AetherBlocks.HOLYSTONE.get(), Blocks.STONE)
            .put(AetherBlocks.HOLYSTONE_WALL.get(), Blocks.STONE)
            .put(AetherBlocks.HOLYSTONE_STAIRS.get(), Blocks.STONE)
            .put(AetherBlocks.HOLYSTONE_SLAB.get(), Blocks.STONE_SLAB)
            .put(AetherBlocks.MOSSY_HOLYSTONE.get(), Blocks.STONE)
            .put(AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), Blocks.STONE)
            .put(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get(), Blocks.STONE)
            .put(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), Blocks.STONE_SLAB)
            .put(AetherBlocks.HOLYSTONE_BRICKS.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.HOLYSTONE_BRICK_WALL.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), Blocks.STONE_BRICK_SLAB)
            .put(AetherBlocks.CARVED_STONE.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.SENTRY_STONE.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.CARVED_WALL.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.CARVED_STAIRS.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.CARVED_SLAB.get(), Blocks.STONE_BRICK_SLAB)
            .put(AetherBlocks.ANGELIC_STONE.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.ANGELIC_WALL.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.ANGELIC_STAIRS.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.ANGELIC_SLAB.get(), Blocks.STONE_BRICK_SLAB)
            .put(AetherBlocks.LIGHT_ANGELIC_STONE.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.HELLFIRE_STONE.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.HELLFIRE_WALL.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.HELLFIRE_STAIRS.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.HELLFIRE_SLAB.get(), Blocks.STONE_BRICK_SLAB)
            .put(AetherBlocks.LIGHT_HELLFIRE_STONE.get(), Blocks.STONE_BRICKS)
            .put(AetherBlocks.PILLAR.get(), Blocks.QUARTZ_PILLAR)
            .put(AetherBlocks.PILLAR_TOP.get(), Blocks.QUARTZ_PILLAR)
            .put(AetherBlocks.AETHER_DIRT.get(), Blocks.DIRT)
            .put(ReduxBlocks.LIGHTROOT_AETHER_DIRT.get(), Blocks.ROOTED_DIRT)
            .put(AetherBlocks.AETHER_FARMLAND.get(), Blocks.FARMLAND)
            .put(AetherBlocks.AETHER_DIRT_PATH.get(), Blocks.DIRT_PATH)
            .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), Blocks.GRASS_BLOCK)
            .put(ReduxBlocks.AEVELIUM.get(), Blocks.MYCELIUM)
            .put(ReduxBlocks.COARSE_AETHER_DIRT.get(), Blocks.COARSE_DIRT)
            .put(ReduxBlocks.GILDED_HOLYSTONE.get(), Blocks.STONE)
            .put(ReduxBlocks.GILDED_HOLYSTONE_WALL.get(), Blocks.STONE)
            .put(ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get(), Blocks.STONE)
            .put(ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(), Blocks.STONE_SLAB)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), Blocks.STONE)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get(), Blocks.STONE)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get(), Blocks.STONE)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get(), Blocks.STONE_SLAB)
            .put(ReduxBlocks.FROSTED_HOLYSTONE.get(), Blocks.STONE)
            .put(ReduxBlocks.FROSTED_HOLYSTONE_WALL.get(), Blocks.STONE)
            .put(ReduxBlocks.FROSTED_HOLYSTONE_STAIRS.get(), Blocks.STONE)
            .put(ReduxBlocks.FROSTED_HOLYSTONE_SLAB.get(), Blocks.STONE_SLAB)
            .put(ReduxBlocks.DIVINITE.get(), Blocks.ANDESITE)
            .put(ReduxBlocks.DIVINITE_WALL.get(), Blocks.ANDESITE)
            .put(ReduxBlocks.DIVINITE_STAIRS.get(), Blocks.ANDESITE)
            .put(ReduxBlocks.DIVINITE_SLAB.get(), Blocks.ANDESITE_SLAB)
            .build());

    public static float getBreakSpeed(Block block, float speed)
    {
        if (ReduxConfig.COMMON.vanilla_consistent_mine_speeds.get() && BREAK_SPEED_REMAP.containsKey(block))
        {
            return speed / (BREAK_SPEED_REMAP.get(block).properties.destroyTime / block.properties.destroyTime);
        }
        return speed;
    }
}
