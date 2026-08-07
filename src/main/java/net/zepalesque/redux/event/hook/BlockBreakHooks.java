package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.Lazy;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.unity.block.UnityBlocks;

public class BlockBreakHooks {
    
    private static final Lazy<Map<Block, Float>> BREAK_SPEED_REMAP = Lazy.of(() -> Maps.newHashMap(new ImmutableMap.Builder<Block, Float>()
        .put(AetherBlocks.HOLYSTONE.get(), 1.0F)
        .put(AetherBlocks.HOLYSTONE_WALL.get(), 1.0F)
        .put(AetherBlocks.HOLYSTONE_STAIRS.get(), 1.0F)
        .put(AetherBlocks.HOLYSTONE_SLAB.get(), 1.5F)
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
        .put(ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get(), getSpeed(Blocks.GRASS_BLOCK))
        .put(ReduxBlocks.AVELIUM.get(), getSpeed(Blocks.GRASS_BLOCK))
        .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), getSpeed(Blocks.GRASS_BLOCK))
//        .put(ReduxBlocks.AVELIUM.get(), getSpeed(Blocks.MYCELIUM))
        .put(UnityBlocks.COARSE_AETHER_DIRT.get(), getSpeed(Blocks.COARSE_DIRT))
        .put(ReduxStoneSets.GILDED_HOLYSTONE.block().get(), 1.0F)
        .put(ReduxStoneSets.GILDED_HOLYSTONE.wall().get(), 1.0F)
        .put(ReduxStoneSets.GILDED_HOLYSTONE.stairs().get(), 1.0F)
        .put(ReduxStoneSets.GILDED_HOLYSTONE.slab().get(), 1.5F)
        .put(ReduxStoneSets.BLEAKMOSS_HOLYSTONE.block().get(), 1.0F)
        .put(ReduxStoneSets.BLEAKMOSS_HOLYSTONE.wall().get(), 1.0F)
        .put(ReduxStoneSets.BLEAKMOSS_HOLYSTONE.stairs().get(), 1.0F)
        .put(ReduxStoneSets.BLEAKMOSS_HOLYSTONE.slab().get(), 1.5F)
//        .put(ReduxBlocks.DRIFTSHALE.get(), 1.1F)
//        .put(ReduxStoneSets.POLISHED_DRIFTSHALE.block().get(), 1.1F)
//        .put(ReduxStoneSets.POLISHED_DRIFTSHALE.wall().get(), 1.1F)
//        .put(ReduxStoneSets.POLISHED_DRIFTSHALE.stairs().get(), 1.1F)
//        .put(ReduxStoneSets.POLISHED_DRIFTSHALE.slab().get(), 1.35F)
        .put(ReduxStoneSets.ANGILITE.block().get(), 1.25F)
        .put(ReduxStoneSets.ANGILITE.wall().get(), 1.25F)
        .put(ReduxStoneSets.ANGILITE.stairs().get(), 1.25F)
        .put(ReduxStoneSets.ANGILITE.slab().get(), 1.5F)
        .put(ReduxStoneSets.SENTRITE.block().get(), 1.75F)
        .put(ReduxStoneSets.SENTRITE.wall().get(), 1.75F)
        .put(ReduxStoneSets.SENTRITE.stairs().get(), 1.75F)
        .put(ReduxStoneSets.SENTRITE.slab().get(), 2F)
        .put(ReduxStoneSets.SENTRITE_BRICKS.block().get(), 1.75F)
        .put(ReduxStoneSets.SENTRITE_BRICKS.wall().get(), 1.75F)
        .put(ReduxStoneSets.SENTRITE_BRICKS.stairs().get(), 1.75F)
        .put(ReduxStoneSets.SENTRITE_BRICKS.slab().get(), 2F)
        .build()));
    
    public static float modify(Block block, float speed) {
        if (ReduxConfig.SERVER.consistent_break_speeds.get() && BREAK_SPEED_REMAP.get().containsKey(block))
            return speed / (BREAK_SPEED_REMAP.get().get(block) / block.properties.destroyTime);
        return speed;
    }
    
    private static float getSpeed(Block block) {
        return block.properties.destroyTime;
    }
}
