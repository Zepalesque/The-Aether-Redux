package net.zepalesque.redux.client.gui.component;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.client.gui.screen.PackConfigMenu;

public class SaveableEditBox extends EditBox implements ISaveable, IDisplayPage
{
    public final PackConfig<?> config;
    public final int page;
    public final PackConfigMenu menu;
        public SaveableEditBox(Font font, int x, int y, int width, int height, PackConfig<?> config, int page, PackConfigMenu menu) {
        super(font, x, y, width, height, Component.literal(config.id()));
        this.config = config;
        this.setValue(config.asText());
        this.page = page;
        this.menu = menu;
        this.setResponder(s -> this.validateColor());
            if (config.hasComment()) {
                this.setTooltip(Tooltip.create(Component.translatable("gui.aether_redux.pack_config.config_desc." + config.id())));
            }
    }

    @Override
    public int getPage() {
        return this.page;
    }

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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.visible = this.menu.getCurrentPage() == this.page;
        this.active = this.menu.getCurrentPage() == this.page;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    public void save() {
        if (this.validate()) {
            this.menu.markChanged(this.config.parseString(this.getValue()));
        }
    }

}
