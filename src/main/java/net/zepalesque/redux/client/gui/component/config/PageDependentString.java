package net.zepalesque.redux.client.gui.component.config;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.client.gui.component.RenderableString;
import net.zepalesque.redux.client.gui.screen.config.PackConfigMenu;

public class PageDependentString extends RenderableString implements IDisplayPage {

    protected final PackConfigMenu menu;
    protected final int page;

    public PageDependentString(Component text, PackConfigMenu menu, int x, int y, int width, int color, Font font, int page) {
        super(text, x, y, width, color, font);
        this.menu = menu;
        this.page = page;

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.menu.getCurrentPage() == this.page) {
            super.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public int getPage() {
        return this.page;
    }
}
