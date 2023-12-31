package net.zepalesque.redux.client.gui.component;

import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class RenderableString implements Renderable {

    protected final Component text;
    protected final int x, y, width, color;
    protected final Font font;

    public RenderableString(Component text, int x, int y, int width, int color, Font font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.color = color;
        this.font = font;
    }


    protected void renderScrollingString(GuiGraphics guiGraphics, Font font, int color) {
        int i = this.x + this.width;
        renderScrollingString(guiGraphics, font, this.text, this.x, this.y, i, this.y + this.font.lineHeight, color);
    }


    protected static void renderScrollingString(GuiGraphics guiGraphics, Font font, Component text, int minX, int minY, int maxX, int maxY, int color) {
        int i = font.width(text);
        int j = (minY + maxY - 9) / 2 + 1;
        int k = maxX - minX;
        if (i > k) {
            int l = i - k;
            double d0 = (double) Util.getMillis() / 1000.0D;
            double d1 = Math.max((double)l * 0.5D, 3.0D);
            double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / 2.0D + 0.5D;
            double d3 = Mth.lerp(d2, 0.0D, l);
            guiGraphics.enableScissor(minX, minY, maxX, maxY);
            guiGraphics.drawString(font, text, minX - (int)d3, j, color);
            guiGraphics.disableScissor();
        } else {
            guiGraphics.drawCenteredString(font, text, (minX + maxX) / 2, j, color);
        }

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderScrollingString(guiGraphics, this.font, this.color);
    }
}
