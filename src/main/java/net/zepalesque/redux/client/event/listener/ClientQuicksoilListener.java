package net.zepalesque.redux.client.event.listener;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.event.hook.ClientQuicksoilHooks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.QuicksoilSetting;
import net.zepalesque.redux.misc.ReduxTags;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ClientQuicksoilListener {

    @SubscribeEvent
    public static void movementHandling(LivingEvent.LivingTickEvent event) {
        final LivingEntity entity = event.getEntity();
        if (ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.highlands) {
            if (!entity.onGround()) { return; }
            if (entity.isInWater()) { return; }
            if (Math.abs(entity.getDeltaMovement().x + entity.getDeltaMovement().y + entity.getDeltaMovement().z) < 0.001D) { return; }
            if (entity instanceof Player player && player.isSpectator()) { return; }

            entity.mainSupportingBlockPos.ifPresent(pos ->
                    {
                        if (entity.level().getBlockState(pos).is(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR)) {
                            ClientQuicksoilHooks.cancelSneak(entity);
                        }}
                    );
        }
    }
}
