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
import net.zepalesque.redux.client.keys.ReduxKeys;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.ShootFireballPacket;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ShootFireballListener {

    private static boolean prevFireballBindState = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        Level world = Minecraft.getInstance().level;

        AbstractClientPlayer player = mc.player;

        if (world != null && player != null) {
            ReduxPlayer.get(player).ifPresent((reduxPlayer) ->
            {
                if (ReduxKeys.SHOOT_FIREBALL.isDown() && !prevFireballBindState && !player.isInWater() && !player.isSpectator()) {
                    ReduxPacketHandler.sendToServer(new ShootFireballPacket(player.getUUID()));
                    reduxPlayer.fireballSetup();
                }
            });
            prevFireballBindState = ReduxKeys.SHOOT_FIREBALL.isDown();
        }
    }
}
