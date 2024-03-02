package net.zepalesque.redux.client.event.hook;

import com.aetherteam.cumulus.api.MenuHelper;
import net.zepalesque.redux.client.ReduxMenus;

public class MenuHooks {
    public static void prepareCustomMenus(MenuHelper menuHelper) {
        menuHelper.prepareMenu(ReduxMenus.THE_AETHER_REDUX.get());
    }
}
