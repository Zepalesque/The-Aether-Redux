
package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.living.VampireAmulet;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.QuicksoilSetting;
import net.zepalesque.redux.event.hook.MobHooks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class MobListener {


    @SubscribeEvent
    public static void modifyAI(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Cockatrice cockatrice && ReduxConfig.COMMON.cockatrice_ai_improvements.get()) {
            MobHooks.modifyCockatriceAI(cockatrice);
        }
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        @Nullable Player plr = null;
        if (event.getSource().getEntity() instanceof Player ent) {
            plr = ent;
        }
        if (event.getSource().getDirectEntity() instanceof Player dir) {
            plr = dir;
        }
        if (plr != null) {
            EntityType<?> type = event.getEntity().getType();
            ReduxPlayer.get(plr).ifPresent(reduxPlayer -> reduxPlayer.getLoreModule().tick(type));
        }
    }



    @SubscribeEvent
    public static void livingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        MobHooks.updateCapabilities(entity);

    }

    @SubscribeEvent
    public static void attack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity entity && EquipmentUtil.hasCurio(entity, ReduxItems.VAMPIRE_AMULET.get())) {
            VampireAmulet.get(entity).ifPresent(vampireAmulet ->
            {
                if (vampireAmulet.canUseAbility() && !vampireAmulet.getMob().level().isDay()) {

                    float life = getLifeSteal(event.getAmount());
                    vampireAmulet.getMob().heal(life);
                }
            });
        }

        if (EquipmentUtil.hasCurio(event.getEntity(), ReduxItems.VAMPIRE_AMULET.get()))
        {
            if (event.getEntity().level().isDay()) {
                float hurt = event.getAmount() * 1.25F;
                event.setAmount(hurt);
            }
        }
    }

    private static float getLifeSteal(float damage) {
        return (damage <= 0F ? 0F : damage <= 20F ? (0.5F * damage) - (0.01F * damage * damage) : (damage * 0.1F) + 4F) * 0.5F;
    }



    @SubscribeEvent
    public static void movementHandling(LivingEvent.LivingTickEvent event) {
        if (ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.highlands) {
            final LivingEntity entity = event.getEntity();
            if (!entity.onGround()) { return; }
            if (entity.isInWater()) { return; }
            if (Math.abs(entity.getDeltaMovement().x + entity.getDeltaMovement().y + entity.getDeltaMovement().z) < 0.001D) { return; }
            if (entity instanceof Player player && player.isSpectator())  { return; }

            if (entity.level().getBlockState(entity.getBlockPosBelowThatAffectsMyMovement()).is(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR)) {
                MobHooks.modifyEntityQuicksoil(entity);
            }
        }
    }


}

