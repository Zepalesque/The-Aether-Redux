package net.zepalesque.redux.event.listener;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.QuicksoilHooks;

@EventBusSubscriber(modid = Redux.MODID)
public class MobListener {

    @SubscribeEvent
    public static void handleQuicksoilMovement(LivingEvent.LivingTickEvent event) {
        final LivingEntity entity = event.getEntity();
        if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && QuicksoilHooks.shouldAlterMovement(entity)) {
            QuicksoilHooks.alterMovement(entity);
        }
    }
}
