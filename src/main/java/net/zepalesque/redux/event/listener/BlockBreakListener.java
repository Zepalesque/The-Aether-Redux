package net.zepalesque.redux.event.listener;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.event.hook.BlockBreakHooks;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockBreakListener {

    @SubscribeEvent
    public static void modifyMiningSpeed(PlayerEvent.BreakSpeed event) {
        float modifiedSpeed = BlockBreakHooks.modify(event.getState().getBlock(), event.getNewSpeed());
        if (modifiedSpeed != event.getNewSpeed())
        {
            event.setNewSpeed(modifiedSpeed);
        }
    }
}
