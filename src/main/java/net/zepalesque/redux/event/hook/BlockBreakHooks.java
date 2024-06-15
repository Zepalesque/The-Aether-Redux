package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.Lazy;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.Map;

public class BlockBreakHooks {

    private static final Lazy<Map<Block, Float>> BREAK_SPEED_REMAP = Lazy.of(() -> Maps.newHashMap((new ImmutableMap.Builder<Block, Float>()
            .put(AetherBlocks.HOLYSTONE.get(), 1.0F))
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
            .put(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), getSpeed(Blocks.GRASS_BLOCK))
//            .put(ReduxBlocks.AVELIUM.get(), getSpeed(Blocks.MYCELIUM))
//            .put(ReduxBlocks.COARSE_AETHER_DIRT.get(), getSpeed(Blocks.COARSE_DIRT))
//            .put(ReduxBlocks.GILDED_HOLYSTONE.get(), 1.0F)
//            .put(ReduxBlocks.GILDED_HOLYSTONE_WALL.get(), 1.0F)
//            .put(ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get(), 1.0F)
//            .put(ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(), 1.5F)
//            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), 1.0F)
//            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get(), 1.0F)
//            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get(), 1.0F)
//            .put(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get(), 1.5F)
//            .put(ReduxBlocks.DRIFTSHALE.get(), 1.1F)
//            .put(ReduxBlocks.POLISHED_DRIFTSHALE.get(), 1.1F)
//            .put(ReduxBlocks.POLISHED_DRIFTSHALE_WALL.get(), 1.1F)
//            .put(ReduxBlocks.POLISHED_DRIFTSHALE_STAIRS.get(), 1.1F)
//            .put(ReduxBlocks.POLISHED_DRIFTSHALE_SLAB.get(), 1.35F)
//            .put(ReduxBlocks.DIVINITE.get(), 1.25F)
//            .put(ReduxBlocks.DIVINITE_WALL.get(), 1.25F)
//            .put(ReduxBlocks.DIVINITE_STAIRS.get(), 1.25F)
//            .put(ReduxBlocks.DIVINITE_SLAB.get(), 1.5F)
//            .put(ReduxBlocks.SENTRITE.get(), 1.75F)
//            .put(ReduxBlocks.SENTRITE_WALL.get(), 1.75F)
//            .put(ReduxBlocks.SENTRITE_STAIRS.get(), 1.75F)
//            .put(ReduxBlocks.SENTRITE_SLAB.get(), 2F)
//            .put(ReduxBlocks.SENTRITE_BRICKS.get(), 1.75F)
//            .put(ReduxBlocks.SENTRITE_BRICK_WALL.get(), 1.75F)
//            .put(ReduxBlocks.SENTRITE_BRICK_STAIRS.get(), 1.75F)
//            .put(ReduxBlocks.SENTRITE_BRICK_SLAB.get(), 2F)
            .build()));

    public static float modify(Block block, float speed) {
        if (ReduxConfig.SERVER.consistent_break_speeds.get() && BREAK_SPEED_REMAP.get().containsKey(block)) {
            return speed / (BREAK_SPEED_REMAP.get().get(block) / block.properties.destroyTime);
        }
        return speed;
    }

    private static float getSpeed(Block block) {
        return block.properties.destroyTime;
    }


}
