package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.Direction;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;

import java.util.EnumSet;

// TODO: ensure this works properly
@Mod.EventBusSubscriber(modid = Redux.MODID)
public class PlacementListener {


    @SubscribeEvent
    public static void placeBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().is(AetherBlocks.WHITE_FLOWER.get()) && event.getLevel().getBlockState(event.getPos().below()).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
            event.getLevel().setBlock(event.getPos(), ReduxBlocks.ENCHANTED_WHITE_FLOWER.get().defaultBlockState(), 3);
        }
    }

    @SubscribeEvent
    public static void update(BlockEvent.NeighborNotifyEvent event) {
        if (event.getNotifiedSides().contains(Direction.UP) && event.getState().is(AetherBlocks.WHITE_FLOWER.get()) && event.getLevel().getBlockState(event.getPos().below()).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
            event.getLevel().setBlock(event.getPos(), ReduxBlocks.ENCHANTED_WHITE_FLOWER.get().defaultBlockState(), 3);
        }
    }
}
