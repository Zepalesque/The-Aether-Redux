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


    private static final Map<Block, Float> BREAK_SPEED_REMAP = Maps.newHashMap((new ImmutableMap.Builder<Block, Float>()
            .put(AetherBlocks.HOLYSTONE.get(), getSpeed(Blocks.STONE)))
            .put(AetherBlocks.HOLYSTONE_WALL.get(), getSpeed(Blocks.STONE))
            .put(AetherBlocks.HOLYSTONE_STAIRS.get(), getSpeed(Blocks.STONE))
            .put(AetherBlocks.HOLYSTONE_SLAB.get(), getSpeed(Blocks.STONE_SLAB))
            .put(AetherBlocks.MOSSY_HOLYSTONE.get(), 1.0F)
            .put(AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), 1.0F)
            .put(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get(), 1.0F)
            .put(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), 1.5F)
            .put(AetherBlocks.HOLYSTONE_BRICKS.get(), 1.0F)
            .put(AetherBlocks.HOLYSTONE_BRICK_WALL.get(), 1.0F)
            .put(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get(), 1.0F)
            .put(AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), 1.5F)
            .put(AetherBlocks.CARVED_STONE.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.SENTRY_STONE.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.CARVED_WALL.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.CARVED_STAIRS.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.CARVED_SLAB.get(), getSpeed(Blocks.STONE_BRICK_SLAB))
            .put(AetherBlocks.ANGELIC_STONE.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.ANGELIC_WALL.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.ANGELIC_STAIRS.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.ANGELIC_SLAB.get(), getSpeed(Blocks.STONE_BRICK_SLAB))
            .put(AetherBlocks.LIGHT_ANGELIC_STONE.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.HELLFIRE_STONE.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.HELLFIRE_WALL.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.HELLFIRE_STAIRS.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.HELLFIRE_SLAB.get(), getSpeed(Blocks.STONE_BRICK_SLAB))
            .put(AetherBlocks.LIGHT_HELLFIRE_STONE.get(), getSpeed(Blocks.STONE_BRICKS))
            .put(AetherBlocks.PILLAR.get(), getSpeed(Blocks.QUARTZ_PILLAR))
            .put(AetherBlocks.PILLAR_TOP.get(), getSpeed(Blocks.QUARTZ_PILLAR))
            .put(AetherBlocks.AETHER_DIRT.get(), getSpeed(Blocks.DIRT))
            .put(AetherBlocks.AETHER_FARMLAND.get(), getSpeed(Blocks.FARMLAND))
            .put(AetherBlocks.AETHER_DIRT_PATH.get(), getSpeed(Blocks.DIRT_PATH))
            .put(AetherBlocks.AETHER_GRASS_BLOCK.get(), getSpeed(Blocks.GRASS_BLOCK))
            .put(ReduxBlocks.AVELIUM.get(), getSpeed(Blocks.MYCELIUM))
            .put(ReduxBlocks.COARSE_AETHER_DIRT.get(), getSpeed(Blocks.COARSE_DIRT))
            .put(ReduxBlocks.GILDED_HOLYSTONE.get(), 1.0F)
            .put(ReduxBlocks.GILDED_HOLYSTONE_WALL.get(), 1.0F)
            .put(ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get(), 1.0F)
            .put(ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(), 1.5F)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), 1.0F)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get(), 1.0F)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get(), 1.0F)
            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get(), 1.5F)
            .put(ReduxBlocks.DIVINITE.get(), getSpeed(Blocks.ANDESITE))
            .put(ReduxBlocks.DIVINITE_WALL.get(), getSpeed(Blocks.ANDESITE))
            .put(ReduxBlocks.DIVINITE_STAIRS.get(), getSpeed(Blocks.ANDESITE))
            .put(ReduxBlocks.DIVINITE_SLAB.get(), getSpeed(Blocks.ANDESITE_SLAB))
            .build());

    public static float modify(Block block, float speed)
    {
        if (ReduxConfig.COMMON.consistent_mine_speeds.get() && BREAK_SPEED_REMAP.containsKey(block))
        {
            return speed / (BREAK_SPEED_REMAP.get(block) / block.properties.destroyTime);
        }
        return speed;
    }

    private static float getSpeed(Block block) {
        return block.properties.destroyTime;
    }


}
