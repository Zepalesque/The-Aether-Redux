package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.item.ReduxItems;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;


@Mod.EventBusSubscriber(modid = Redux.MODID)
public class FallListener {

    @SubscribeEvent
    public static void fall(LivingFallEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof Player player) {
            ReduxPlayer.get(player).ifPresent((reduxPlayer) -> {
                List<SlotResult> ringList = EquipmentUtil.getCurios(player, ReduxItems.AIRBOUND_CAPE.get());
                if (ringList != null && ringList.size() > 0 && reduxPlayer.getPrevTickAirJumps() > 0) {
                    boolean isHigher = EquipmentUtil.getCurios(player, ReduxItems.AIRBOUND_CAPE.get()).size() == 2;
                    float distance = event.getDistance() - (isHigher ? 4 : 2);
                    event.setDistance(distance);
                }
        });
        }
    }

}
