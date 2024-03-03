package net.zepalesque.redux.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menu;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.client.gui.screen.menu.ReduxTitleScreen;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ReduxMenus {
    public static final DeferredRegister<Menu> MENUS = DeferredRegister.create(Cumulus.MENU_REGISTRY_KEY, Redux.MODID);

    private static final ResourceLocation ICON = Redux.locate("textures/gui/menu_api/menu_icon_redux.png");
    private static final Component BLIGHT_NAME = Component.translatable("aether_redux.menu_title.blight");
    private static final Component GILDED_NAME = Component.translatable("aether_redux.menu_title.gilded");
    private static final Component DUNGEON_NAME = Component.translatable("aether_redux.menu_title.dungeon");
    private static final Component CLOUDCAPS_NAME = Component.translatable("aether_redux.menu_title.cloudcaps");
    private static final Component SKYFIELDS_NAME = Component.translatable("aether_redux.menu_title.skyfields");

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


    public static final RegistryObject<Menu> BLIGHT_MENU = register("redux_blight", ICON, BLIGHT_NAME, () -> new ReduxTitleScreen("blight"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));
    public static final RegistryObject<Menu> GILDED_MENU = register("redux_gilded", ICON, GILDED_NAME, () -> new ReduxTitleScreen("gilded"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));
    public static final RegistryObject<Menu> DUNGEON_MENU = register("redux_dungeon", ICON, DUNGEON_NAME, () -> new ReduxTitleScreen("dungeon"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));
    public static final RegistryObject<Menu> CLOUDCAPS_MENU = register("redux_cloudcaps", ICON, CLOUDCAPS_NAME, () -> new ReduxTitleScreen("cloudcaps"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));
    public static final RegistryObject<Menu> SKYFIELDS_MENU = register("redux_skyfields", ICON, SKYFIELDS_NAME, () -> new ReduxTitleScreen("skyfields"), () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));

    public static RegistryObject<Menu> register(String id, ResourceLocation icon, Component name, Supplier<TitleScreen> screen, Supplier<Menu.Properties> properties) {
        return MENUS.register(id, () -> new Menu(icon, name, screen.get(), () -> CumulusConfig.CLIENT.active_menu.get().equals("aether_redux:" + id), properties.get()));
    }

    public static void cycle() {
        if (ReduxConfig.CLIENT_SPEC.isLoaded() && CumulusConfig.CLIENT_SPEC.isLoaded()) {
            if (ReduxConfig.CLIENT.cycle_menu.get() && CumulusConfig.CLIENT.active_menu.get().contains("aether_redux:")) {
                int index = Redux.RAND.nextInt(5);
                CumulusConfig.CLIENT.active_menu.set(
                        index == 0 ? ReduxMenus.CLOUDCAPS_MENU.getId().toString() :
                        index == 1 ? ReduxMenus.DUNGEON_MENU.getId().toString() :
                        index == 2 ? ReduxMenus.GILDED_MENU.getId().toString() :
                        index == 3 ? ReduxMenus.BLIGHT_MENU.getId().toString() : ReduxMenus.SKYFIELDS_MENU.getId().toString()
                );
            }
        }
    }

}
