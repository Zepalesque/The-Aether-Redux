package net.zepalesque.redux.client.gui.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

public class GraphicsHelper {

    public static void blit(GuiGraphics gui, ResourceLocation atlasLocation, float x, float y, int blitOffset, float uOffset, float vOffset, float uWidth, float vHeight, int textureWidth, int textureHeight) {
        blit(gui, atlasLocation, x, x + uWidth, y, y + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }


    static void blit(GuiGraphics gui, ResourceLocation atlasLocation, float x1, float x2, float y1, float y2, int blitOffset, float uWidth, float vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        innerBlit(gui, atlasLocation, x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + vHeight) / (float)textureHeight);
    }

    static void innerBlit(GuiGraphics gui, ResourceLocation atlasLocation, float x1, float x2, float y1, float y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = gui.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, x1, y1, (float)blitOffset).uv(minU, minV).endVertex();
        bufferbuilder.vertex(matrix4f, x1, y2, (float)blitOffset).uv(minU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, x2, y2, (float)blitOffset).uv(maxU, maxV).endVertex();
        bufferbuilder.vertex(matrix4f, x2, y1, (float)blitOffset).uv(maxU, minV).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    public static void drawCenteredString(GuiGraphics gui, Font font, Component text, float x, float y, int color) {
        FormattedCharSequence formattedcharsequence = text.getVisualOrderText();
        drawString(gui, font, formattedcharsequence, x - font.width(formattedcharsequence) / 2F, y, color);
    }

    public static int drawString(GuiGraphics gui, Font font, FormattedCharSequence text, float x, float y, int color) {
        return drawString(gui, font, text, x, y, color, true);
    }

    public static int drawString(GuiGraphics gui, Font font, FormattedCharSequence text, float x, float y, int color, boolean dropShadow) {
        return gui.drawString(font, text, x, y, color, dropShadow);
    }
}
