package net.zepalesque.redux.client.event.listener;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.event.hook.ClientQuicksoilHooks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.QuicksoilSetting;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.util.level.LevelUtil;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ClientQuicksoilListener {

    @SubscribeEvent
    public static void movementHandling(LivingEvent.LivingTickEvent event) {
        final LivingEntity entity = event.getEntity();
        if (ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.highlands) {
            if (!entity.isOnGround()) { return; }
            if (entity.isInWater()) { return; }
            if (Math.abs(entity.getDeltaMovement().x + entity.getDeltaMovement().y + entity.getDeltaMovement().z) < 0.001D) { return; }
            if (entity instanceof Player player && player.isSpectator()) { return; }

            AABB bb = entity.getBoundingBox();
            AABB bbBelow = new AABB(bb.minX + 0.1D, bb.minY - 0.3D, bb.minZ +0.1D, bb.maxX - 0.1D, bb.minY, bb.maxZ - 0.1D);

            if (LevelUtil.isTagInAABB1(bbBelow, entity.level, ReduxTags.Blocks.QUICKSOIL_BEHAVIOR)) {
                ClientQuicksoilHooks.cancelSneak(entity);
            }
        }
    }
}
