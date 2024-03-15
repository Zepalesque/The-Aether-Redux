package net.zepalesque.redux.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.client.gui.backported.GuiGraphicsHelper;

import java.util.function.Supplier;

public class DynamicRenderableString implements Widget {

    protected final Supplier<Component> text;
    protected final int x, y, color;
    protected final Supplier<Integer> width;
    protected final Font font;

    public DynamicRenderableString(Supplier<Component> text, int x, int y, Supplier<Integer> width, int color, Font font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.color = color;
        this.font = font;
    }
//    public RenderableString()

    protected void renderScrollingString(PoseStack guiGraphics, Font font, int color) {
        int wid = this.width.get();
        int minX = this.x - (wid / 2);
        int maxX = this.x + (wid / 2);
        GuiGraphicsHelper.renderScrollingString(guiGraphics, font, this.text.get(), minX, this.y, maxX, this.y + this.font.lineHeight, color);
    }
    @Override
    public void render(PoseStack guiGraphics, int mouseX, int mouseY, float partialTick) {
//        drawString(guiGraphics, this.font, this.text.get(), this.x + (this.width.get() / 2) - this.font.width(this.text.get()) / 2, this.y, this.color);
        renderScrollingString(guiGraphics, this.font, this.color);
    }
}