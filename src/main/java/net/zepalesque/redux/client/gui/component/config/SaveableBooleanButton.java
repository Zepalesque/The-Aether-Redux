package net.zepalesque.redux.client.gui.component.config;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.client.gui.component.config.IDisplayPage;
import net.zepalesque.redux.client.gui.component.config.ISaveable;
import net.zepalesque.redux.client.gui.screen.config.PackConfigMenu;

import java.util.List;
import java.util.Optional;

public class SaveableBooleanButton extends Button implements ISaveable, IDisplayPage {
    public final PackConfig<Boolean> config;
    public final int page;
    private boolean value;
    private final PackConfigMenu menu;
    private Component tooltip;

    public SaveableBooleanButton(int x, int y, int width, int height, PackConfig<Boolean> config, int page, PackConfigMenu menu) {
        super(x, y, width, height, CommonComponents.EMPTY, SaveableBooleanButton::flip);
        this.config = config;
        this.value = config.get();
        this.page = page;
        this.menu = menu;
        if (config.hasComment()) {
            this.tooltip = Component.translatable("gui.aether_redux.config_desc." + config.id());
        }
    }

    private static void flip(Button b) {
        if (b instanceof SaveableBooleanButton booleanButton) {
            booleanButton.setValue(!booleanButton.getValue());
        }
    }

    @Override
    public void render(PoseStack guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.visible = this.menu.getCurrentPage() == this.page;
        this.active = this.menu.getCurrentPage() == this.page;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (this.isHoveredOrFocused() && this.tooltip != null) {
            this.menu.renderTooltip(guiGraphics, List.of(this.tooltip), Optional.empty(), mouseX, mouseY);
        }
    }


    @Override
    public Component getMessage() {
        return Component.literal(capitalise(String.valueOf(this.value)));
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void save() {
        this.menu.markChanged(this.config.set(this.getValue()));
    }

    @Override
    public int getPage() {
        return this.page;
    }

    private static String capitalise( String str )
    {
        if ( str == null )
        {
            return null;
        }
        else if ( str.length() == 0 )
        {
            return "";
        }
        else
        {
            return new StringBuilder( str.length() ).append( Character.toTitleCase( str.charAt( 0 ) ) ).append( str, 1,
                    str.length() ).toString();
        }
    }
}