package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.item.ReduxItems;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class ReduxAccessoryListener {

    @SubscribeEvent
    public static void increaseXP(LivingExperienceDropEvent event)
    {
        if (event.getAttackingPlayer() != null && EquipmentUtil.hasCurio(event.getAttackingPlayer(), ReduxItems.ENCHANTED_RING.get()))
        {
            float multiplier = 1F;
            List<SlotResult> ringList = EquipmentUtil.getCurios(event.getAttackingPlayer(), ReduxItems.ENCHANTED_RING.get());
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
    public static void removeInebriation(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player && EquipmentUtil.hasCurio(player, ReduxItems.COCKATRICE_FEATHER.get())) {
            player.removeEffect(AetherEffects.INEBRIATION.get());
        }
    }

}
