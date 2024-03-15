package net.zepalesque.redux.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.client.gui.backported.GuiGraphicsHelper;

public class RenderableString implements Widget {

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
//    public RenderableString()


    protected void renderScrollingString(PoseStack guiGraphics, Font font, int color) {
        GuiGraphicsHelper.renderScrollingString(guiGraphics, font, this.text, this.x, this.y, this.x + this.width, this.y + this.font.lineHeight, color);
    }

    @Override
    public void render(PoseStack guiGraphics, int mouseX, int mouseY, float partialTick) {
//        drawString(guiGraphics, this.font, this.text, this.x + (this.width / 2) - this.font.width(this.text) / 2, this.y, this.color);
        renderScrollingString(guiGraphics, this.font, this.color);
    }
}