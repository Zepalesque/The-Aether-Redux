package net.zepalesque.redux.client.gui.backported;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class SlicedButton extends Button {


    public SlicedButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
    }

    public SlicedButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, OnTooltip pOnTooltip) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderWidget(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    protected void renderWidget(PoseStack guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        GuiGraphicsHelper.blitNineSliced(WIDGETS_LOCATION, guiGraphics, this.x, this.y, this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = getFGColor();
        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
//        drawCenteredString(guiGraphics, minecraft.font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public void renderString(PoseStack guiGraphics, Font font, int color) {
        this.renderScrollingString(guiGraphics, font, 2, color);
    }
    protected void renderScrollingString(PoseStack guiGraphics, Font font, int edges, int color) {
        int minX = this.x + edges;
        int maxX = this.x + this.getWidth() - edges;
        GuiGraphicsHelper.renderScrollingString(guiGraphics, font, this.getMessage(), minX, this.y, maxX, this.y + this.getHeight(), color);
    }


    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }

        return 46 + i * 20;
    }

}