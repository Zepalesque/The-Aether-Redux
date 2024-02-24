package net.zepalesque.redux.event.listener;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
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
