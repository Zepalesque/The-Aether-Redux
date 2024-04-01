package net.zepalesque.redux.client.event.hook;

import com.aetherteam.cumulus.api.MenuHelper;
import net.zepalesque.redux.client.ReduxMenus;

public class MenuHooks {
    public static void prepareCustomMenus(MenuHelper menuHelper) {
        menuHelper.prepareMenu(ReduxMenus.CANDY_MENU.get());
        menuHelper.prepareMenu(ReduxMenus.DEEP_MENU.get());
        menuHelper.prepareMenu(ReduxMenus.DUNGEON_MENU.get());
        menuHelper.prepareMenu(ReduxMenus.CLOUDCAPS_MENU.get());
        menuHelper.prepareMenu(ReduxMenus.SKYFIELDS_MENU.get());
    }
}
