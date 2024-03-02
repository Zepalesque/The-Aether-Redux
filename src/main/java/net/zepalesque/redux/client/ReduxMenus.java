package net.zepalesque.redux.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.client.gui.screen.menu.ReduxTitleScreen;

import java.util.function.BooleanSupplier;

public class ReduxMenus {
    public static final DeferredRegister<Menu> MENUS = DeferredRegister.create(Cumulus.MENU_REGISTRY_KEY, Redux.MODID);

    private static final ResourceLocation THE_AETHER_ICON = Redux.locate("textures/gui/menu_api/menu_icon_redux.png");

    private static final Component REDUX_NAME = Component.translatable("aether_redux.menu_title.the_aether_redux");
    private static final ResourceLocation THE_AETHER_REGULAR_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/options_background.png");
    private static final ResourceLocation THE_AETHER_DARK_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/light_sentry_background.png");
    private static final ResourceLocation THE_AETHER_HEADER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/header_separator.png");
    private static final ResourceLocation THE_AETHER_FOOTER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/footer_separator.png");
    private static final ResourceLocation THE_AETHER_TAB_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/title/tab_button.png");

    private static final BooleanSupplier REDUX_ENABLED = () -> CumulusConfig.CLIENT.active_menu.get().equals("aether_redux:the_aether_redux");
    private static final Menu.Background REDUX_BACKGROUND = new Menu.Background()
            .regularBackground(THE_AETHER_REGULAR_BACKGROUND)
            .darkBackground(THE_AETHER_DARK_BACKGROUND)
            .headerSeparator(THE_AETHER_HEADER_SEPARATOR)
            .footerSeparator(THE_AETHER_FOOTER_SEPARATOR)
            .tabButton(THE_AETHER_TAB_BUTTON);


    public static final RegistryObject<Menu> THE_AETHER_REDUX = MENUS.register("the_aether_redux", () -> new Menu(THE_AETHER_ICON, REDUX_NAME, new ReduxTitleScreen(), REDUX_ENABLED, new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND)));

}
