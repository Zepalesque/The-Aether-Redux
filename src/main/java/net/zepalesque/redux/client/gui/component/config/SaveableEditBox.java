package net.zepalesque.redux.client.gui.component.config;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.client.gui.screen.config.PackConfigMenu;

import java.util.List;
import java.util.Optional;

public class SaveableEditBox extends EditBox implements ISaveable, IDisplayPage
{
    public final PackConfig<?> config;
    public final int page;
    private Component tooltip;

    public final PackConfigMenu menu;
    public SaveableEditBox(Font font, int x, int y, int width, int height, PackConfig<?> config,/* JsonParser gson,*/ int page, PackConfigMenu menu) {
        super(font, x, y, width, height, Component.literal(config.id()));
        this.config = config;
        this.setValue(config.asText());
        this.page = page;
        this.menu = menu;
        this.setResponder(s -> this.validateColor());
        if (config.hasComment()) {
            this.tooltip = Component.translatable("gui.aether_redux.config_desc." + config.id());
        }
    }

    @Override
    public int getPage() {
        return this.page;
    }

/*    @Override
    public void setValue(String text) {
        super.setValue(text);
        this.validateColor();
    }*/

    public boolean validate()
    {
        return this.config.validate(this.getValue());
    }



    public void validateColor()
    {
        boolean valid = this.validate();
        this.setTextColor((valid || this.getValue().isEmpty()) ? 0xFFFFFFFF : 0xFFFF0000);
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

    public void save() {
        if (this.validate()) {
            this.menu.markChanged(this.config.parseString(this.getValue()));
        }
    }

}