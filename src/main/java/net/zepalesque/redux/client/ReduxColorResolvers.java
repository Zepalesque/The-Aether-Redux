package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.client.accessor.BlockColorsAccessor;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;


// TODO: This feels incorrect, see if there's a better alternative??
public class ReduxColorResolvers {
    private static Map<Block, BlockColor> reregisters = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    static void preCache(RegisterColorHandlersEvent.Block event) {
        Map<Holder.Reference<Block>, BlockColor> blockColors = ((BlockColorsAccessor) event.getBlockColors()).aether$getBlockColors();
        if (reregisters.isEmpty()) {
            reregisters.put(Blocks.GRASS, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.GRASS)));
            reregisters.put(Blocks.FERN, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.FERN)));
            reregisters.put(Blocks.TALL_GRASS, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.TALL_GRASS)));
            reregisters.put(Blocks.LARGE_FERN, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.LARGE_FERN)));
        }
    }
    @SubscribeEvent(priority = EventPriority.LOW)
    static void replace(RegisterColorHandlersEvent.Block event) {
        for (Map.Entry<Block, BlockColor> entry : reregisters.entrySet()) {
            event.register(entry.getValue(), entry.getKey());
        }
    }
}
