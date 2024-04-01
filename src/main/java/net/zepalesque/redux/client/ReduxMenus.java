package net.zepalesque.redux.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menu;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.client.gui.screen.menu.ReduxTitleScreen;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.function.Supplier;

public class ReduxMenus {
    public static final DeferredRegister<Menu> MENUS = DeferredRegister.create(Cumulus.MENU_REGISTRY_KEY, Redux.MODID);

    private static final ResourceLocation ICON = Redux.locate("textures/gui/menu_api/menu_icon_redux.png");
    private static final Component CANDY_NAME = Component.translatable("aether_redux.menu_title.candy");
    private static final Component DEEP_NAME = Component.translatable("aether_redux.menu_title.deep");
    private static final Component CRYSTALLIUM_NAME = Component.translatable("aether_redux.menu_title.crystallium");


    private static final ResourceLocation THE_AETHER_REGULAR_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/options_background.png");
    private static final ResourceLocation THE_AETHER_DARK_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/light_sentry_background.png");
    private static final ResourceLocation THE_AETHER_HEADER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/header_separator.png");
    private static final ResourceLocation THE_AETHER_FOOTER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/footer_separator.png");
    private static final ResourceLocation THE_AETHER_TAB_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/title/tab_button.png");

    private static final Menu.Background REDUX_BACKGROUND = new Menu.Background()
            .regularBackground(THE_AETHER_REGULAR_BACKGROUND)
            .darkBackground(THE_AETHER_DARK_BACKGROUND)
            .headerSeparator(THE_AETHER_HEADER_SEPARATOR)
            .footerSeparator(THE_AETHER_FOOTER_SEPARATOR)
            .tabButton(THE_AETHER_TAB_BUTTON);


    public static final RegistryObject<Menu> CANDY_MENU = register("redux_candy", ICON, CANDY_NAME, () -> new ReduxTitleScreen("candy"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));
    public static final RegistryObject<Menu> DEEP_MENU = register("redux_deep", ICON, DEEP_NAME, () -> new ReduxTitleScreen("deep"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));
    public static final RegistryObject<Menu> DUNGEON_MENU = register("redux_crystallium", ICON, CRYSTALLIUM_NAME, () -> new ReduxTitleScreen("crystallium"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));

    public static RegistryObject<Menu> register(String id, ResourceLocation icon, Component name, Supplier<TitleScreen> screen, Supplier<Menu.Properties> properties) {
        return MENUS.register(id, () -> new Menu(icon, name, screen.get(), () -> CumulusConfig.CLIENT.active_menu.get().equals("aether_redux:" + id), properties.get()));
    }

    public static void cycle() {
        if (ReduxConfig.CLIENT_SPEC.isLoaded() && CumulusConfig.CLIENT_SPEC.isLoaded()) {
            if (ReduxConfig.CLIENT.cycle_menu.get() && CumulusConfig.CLIENT.active_menu.get().contains("aether_redux:")) {
                int index = Redux.RAND.nextInt(3);
                CumulusConfig.CLIENT.active_menu.set(
                        index == 0 ? ReduxMenus.DUNGEON_MENU.getId().toString() :
                        index == 1 ? ReduxMenus.DEEP_MENU.getId().toString() :
                        ReduxMenus.CANDY_MENU.getId().toString()
                );
                CumulusConfig.CLIENT.active_menu.save();
            }
        }
    }

}
