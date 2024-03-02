package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.ReduxCapabilities;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.effect.ReduxEffects;
import net.zepalesque.redux.entity.projectile.Ember;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.util.math.MathUtil;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class EquipmentListener {

    @SubscribeEvent
    public static void increaseXP(LivingExperienceDropEvent event)
    {
        RegistryObject<Item> ring = ReduxItems.RING_OF_WISDOM;
        if (event.getAttackingPlayer() != null && EquipmentUtil.hasCurio(event.getAttackingPlayer(), ring.get()))
        {
            float multiplier = 1F;
            List<SlotResult> ringList = EquipmentUtil.getCurios(event.getAttackingPlayer(), ring.get());
            for (SlotResult ignored : ringList)
            {
                multiplier *= (1.2F + event.getAttackingPlayer().getRandom().nextFloat() * 0.1F);
            }

            if (multiplier != 1F)
            {
                int xp = Mth.floor((float) event.getDroppedExperience() * multiplier);
                event.setDroppedExperience(xp);
            }

        }
    }

    @SubscribeEvent
    public static void joinAndSetDoubleJumps(EntityJoinLevelEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide())
        {
            ReduxPlayer.get(player).ifPresent((reduxPlayer) -> {
                    reduxPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setMaxAirJumps", EquipmentUtil.hasCurio(player, ReduxItems.AIRBOUND_CAPE.get()) ? 1 : 0);
            });
        }
    }

    @SubscribeEvent
    public static void removeInebriation(MobEffectEvent.Added event) {
        if (!event.getEntity().level().isClientSide()) {
            EquipmentUtil.findFirstCurio(event.getEntity(), ReduxItems.FEATHER_OF_WARDING.get()).ifPresent(slotResult -> {
                if (event.getEffectInstance().getEffect() == AetherEffects.INEBRIATION.get()) {
                    slotResult.stack().hurtAndBreak(1, slotResult.slotContext().entity(), livingEntity -> CuriosApi.broadcastCurioBreakEvent(slotResult.slotContext()));
                    event.getEntity().removeEffect(event.getEffectInstance().getEffect());
                }
            });
        }
    }

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();

        if (!target.level().isClientSide() && EquipmentUtil.hasCurio(target, ReduxItems.SHROOM_RING.get()) && (target.getHealth() / target.getMaxHealth()) <= 0.66666667F) {
                if (target.hasEffect(ReduxEffects.ADRENALINE_RUSH.get()) && !target.hasEffect(ReduxEffects.ADRENAL_FATIGUE.get())) {
                    float delta = Mth.clampedLerp(1F - ((target.getHealth() / target.getMaxHealth()) * 1.5F), 0.25F, 0.5F);
                    if (target.level().getRandom().nextFloat() <= delta) {
                        MobEffectInstance i = target.getEffect(ReduxEffects.ADRENALINE_RUSH.get());
                        if (i != null) {
                            int curr = i.getAmplifier();
                            if (curr < 2) {
                                MobEffectInstance instance = new MobEffectInstance(i.getEffect(), i.getDuration(), i.getAmplifier() + 1, i.isAmbient(), i.isVisible(), i.showIcon());
                                target.removeEffect(ReduxEffects.ADRENALINE_RUSH.get());
                                target.addEffect(instance);
                            }
                        }
                    }
                } else {
                    float delta = Mth.clampedLerp(1F - ((target.getHealth() / target.getMaxHealth()) * 1.5F), 0.5F, 0.75F);
                    if (target.level().getRandom().nextFloat() <= delta && !target.hasEffect(ReduxEffects.ADRENAL_FATIGUE.get())) {
                        if ((target instanceof Player p && ReduxPlayer.get(p).isPresent() && ReduxPlayer.get(p).orElseThrow(ReduxCapabilities::error).getAdrenalineModule().cooledDown()) || !(target instanceof Player)) {
                            target.addEffect(new MobEffectInstance(ReduxEffects.ADRENALINE_RUSH.get(), 600, 0, false, false, true));
                        }
                    }
                }
        }

        if (target != null && event.getSource().getDirectEntity() instanceof Player player && !event.getSource().is(ReduxDamageTypes.EMBER) && event.getAmount() > 1.0) {
            if (EquipmentUtil.hasCurio(player, ReduxItems.SENTRY_RING.get())) {
                int ringcount = EquipmentUtil.getCurios(player, ReduxItems.SENTRY_RING.get()).size();
                RandomSource source = target.level().getRandom();
                int embers = event.getAmount() < 1.5 || player.getMainHandItem().isEmpty() ? 0 : Mth.ceil(((event.getAmount() * 0.5D) + 1D) * (ringcount == 2 ? 1.5D : 1D)) - source.nextInt(1);
                for (int i = 1; i <= embers; i++) {
                    float rotation = Mth.wrapDegrees(source.nextInt(360));
                    Ember ember = new Ember(target.level(), player, target);
                    ember.setPos(target.getX(), target.getY() + (target.getBbHeight() / 2) + ((source.nextFloat() * 2) - 1), target.getZ());
                    ember.shootFromRotation(target, -45 + (90 * source.nextFloat()), rotation, 0.0F, 0.5F, 1.0F);
                    if (!player.level().isClientSide()) {
                        player.level().addFreshEntity(ember);
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public static void tickPlayer(LivingEvent.LivingTickEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (EquipmentUtil.hasCurio(entity, ReduxItems.GRAND_VICTORY_MEDAL.get())) {
            // 'i' is a value that decreases as your health goes down, starting at 200 at full health, going down to a minimum of 20 at 1/10 health
            int i = MathUtil.clampedLerpInt((entity.getHealth() / entity.getMaxHealth()), 20, 200);
            // Once every i ticks, the player has a 50% chance of getting healed 1 point of health
            if (i > 0 && entity.tickCount % i == 0 && entity.level().getRandom().nextBoolean())
            {
                entity.heal(1);
            }
        }
    }
}
