package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.DoubleJumpPacket;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class DoubleJumpListener {

    private static boolean prevJumpBindState = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END)
        {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        Level world = Minecraft.getInstance().level;

        AbstractClientPlayer player = mc.player;

        if (world != null && player != null)
        {
            ReduxPlayer.get(player).ifPresent((reduxPlayer) ->
            {
                if (mc.options.keyJump.isDown() && !prevJumpBindState && !player.isInWater() && reduxPlayer.ticksInAir() > 2 && !player.isCreative() && !player.isSpectator() && !player.isPassenger())
                {
                    ReduxPacketHandler.sendToServer(new DoubleJumpPacket(player.getUUID()));
                    reduxPlayer.doubleJump();
                }
            });
                prevJumpBindState = mc.options.keyJump.isDown();
        }
    }
}
