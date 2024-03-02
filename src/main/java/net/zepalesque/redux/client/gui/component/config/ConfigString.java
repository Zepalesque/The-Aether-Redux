package net.zepalesque.redux.client.gui.component.config;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.client.gui.screen.config.PackConfigMenu;

public class ConfigString<T> extends PageDependentString{

    private final PackConfig<T> config;
    public ConfigString(PackConfig<T> config, PackConfigMenu menu, int x, int y, int width, int color, Font font, int page) {
        super(Component.translatable("gui.aether_redux.pack_config." + config.id()), menu, x, y, width, color, font, page);
        this.config = config;
    }
}
