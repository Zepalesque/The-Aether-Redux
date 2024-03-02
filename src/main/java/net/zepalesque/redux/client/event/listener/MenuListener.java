package net.zepalesque.redux.client.event.listener;

import com.aetherteam.cumulus.client.CumulusClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.event.hook.MenuHooks;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class MenuListener {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onGuiOpenHighest(ScreenEvent.Opening event) {
        MenuHooks.prepareCustomMenus(CumulusClient.MENU_HELPER);
    }
}