package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.zepalesque.redux.client.event.hook.KeyHooks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.QuicksoilHooks;

public class ClientMobListener {

    @SubscribeEvent
    public static void cancelSneak(LivingEvent.LivingTickEvent event) {
        final LivingEntity entity = event.getEntity();
        if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && QuicksoilHooks.shouldAlterMovement(entity)) {
            KeyHooks.cancelKey(entity, Minecraft.getInstance().options.keyShift);
        }
    }
}
