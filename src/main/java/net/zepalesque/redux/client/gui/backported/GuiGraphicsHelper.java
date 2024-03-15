package net.zepalesque.redux.client.gui.backported;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntIterator;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class GuiGraphicsHelper {


    public static void enableScissor(int minX, int minY, int maxX, int maxY) {
        applyScissor(minX, minY, maxX - minX, maxY - minY);
    }

    private static void applyScissor(int x, int y, int width, int height) {
            Window window = Minecraft.getInstance().getWindow();
            int i = window.getHeight();
            double scale = window.getGuiScale();
            double calcX = (double) x * scale;
            double calcY = (double) i - (double) (y + height) * scale;
            double calcWidth = (double) width * scale;
            double calcHeight = (double) height * scale;
            RenderSystem.enableScissor((int)calcX, (int)calcY, Math.max(0, (int)calcWidth), Math.max(0, (int)calcHeight));

    }

    public static void blitNineSliced(ResourceLocation atlasLocation, PoseStack poseStack, int x, int y, int width, int height, int leftSliceWidth, int topSliceHeight, int rightSliceWidth, int bottomSliceHeight, int uWidth, int vHeight, int textureX, int textureY) {
        leftSliceWidth = Math.min(leftSliceWidth, width / 2);
        rightSliceWidth = Math.min(rightSliceWidth, width / 2);
        topSliceHeight = Math.min(topSliceHeight, height / 2);
        bottomSliceHeight = Math.min(bottomSliceHeight, height / 2);
        RenderSystem.setShaderTexture(0, atlasLocation);
        poseStack.pushPose();
        if (width == uWidth && height == vHeight) {
            GuiComponent.blit(poseStack, x, y, 0, textureX, textureY, width, height, 256, 256);
        } else if (height == vHeight) {
            GuiComponent.blit(poseStack, x, y, 0, textureX, textureY, leftSliceWidth, height, 256, 256);
            blitRepeating(poseStack, x + leftSliceWidth, y, width - rightSliceWidth - leftSliceWidth, height, textureX + leftSliceWidth, textureY, uWidth - rightSliceWidth - leftSliceWidth, vHeight);
            GuiComponent.blit(poseStack, x + width - rightSliceWidth, y, 0, textureX + uWidth - rightSliceWidth, textureY, rightSliceWidth, height, 256, 256);
        } else if (width == uWidth) {
            GuiComponent.blit(poseStack, x, y, textureX, textureY, width, topSliceHeight, 256, 256);
            blitRepeating(poseStack, x, y + topSliceHeight, width, height - bottomSliceHeight - topSliceHeight, textureX, textureY + topSliceHeight, uWidth, vHeight - bottomSliceHeight - topSliceHeight);
            GuiComponent.blit(poseStack, x, y + height - bottomSliceHeight,  textureX, textureY + vHeight - bottomSliceHeight, width, bottomSliceHeight, 256, 256);
        } else {
            GuiComponent.blit(poseStack, x, y,  textureX, textureY, leftSliceWidth, topSliceHeight, 256, 256);
            blitRepeating(poseStack, x + leftSliceWidth, y, width - rightSliceWidth - leftSliceWidth, topSliceHeight, textureX + leftSliceWidth, textureY, uWidth - rightSliceWidth - leftSliceWidth, topSliceHeight);
            GuiComponent.blit(poseStack, x + width - rightSliceWidth, y,  textureX + uWidth - rightSliceWidth, textureY, rightSliceWidth, topSliceHeight, 256, 256);
            GuiComponent.blit(poseStack, x, y + height - bottomSliceHeight,  textureX, textureY + vHeight - bottomSliceHeight, leftSliceWidth, bottomSliceHeight, 256, 256);
            blitRepeating(poseStack, x + leftSliceWidth, y + height - bottomSliceHeight, width - rightSliceWidth - leftSliceWidth, bottomSliceHeight, textureX + leftSliceWidth, textureY + vHeight - bottomSliceHeight, uWidth - rightSliceWidth - leftSliceWidth, bottomSliceHeight);
            GuiComponent.blit(poseStack, x + width - rightSliceWidth, y + height - bottomSliceHeight,  textureX + uWidth - rightSliceWidth, textureY + vHeight - bottomSliceHeight, rightSliceWidth, bottomSliceHeight, 256, 256);
            blitRepeating(poseStack, x, y + topSliceHeight, leftSliceWidth, height - bottomSliceHeight - topSliceHeight, textureX, textureY + topSliceHeight, leftSliceWidth, vHeight - bottomSliceHeight - topSliceHeight);
            blitRepeating(poseStack, x + leftSliceWidth, y + topSliceHeight, width - rightSliceWidth - leftSliceWidth, height - bottomSliceHeight - topSliceHeight, textureX + leftSliceWidth, textureY + topSliceHeight, uWidth - rightSliceWidth - leftSliceWidth, vHeight - bottomSliceHeight - topSliceHeight);
            blitRepeating(poseStack, x + width - rightSliceWidth, y + topSliceHeight, leftSliceWidth, height - bottomSliceHeight - topSliceHeight, textureX + uWidth - rightSliceWidth, textureY + topSliceHeight, rightSliceWidth, vHeight - bottomSliceHeight - topSliceHeight);
        }
        poseStack.popPose();

    }

    public static void blitNineSliced(ResourceLocation atlasLocation, PoseStack poseStack, int x, int y, int width, int height, int sliceWidth, int sliceHeight, int uWidth, int vHeight, int textureX, int textureY) {
        blitNineSliced(atlasLocation, poseStack, x, y, width, height, sliceWidth, sliceHeight, sliceWidth, sliceHeight, uWidth, vHeight, textureX, textureY);
    }

    public static void blitRepeating(PoseStack poseStack, int x, int y, int width, int height, int uOffset, int vOffset, int sourceWidth, int sourceHeight) {
        blitRepeating(poseStack, x, y, width, height, uOffset, vOffset, sourceWidth, sourceHeight, 256, 256);
    }

    public static void blitRepeating(PoseStack poseStack, int x, int y, int width, int height, int uOffset, int vOffset, int sourceWidth, int sourceHeight, int textureWidth, int textureHeight) {
        int i = x;

        int j;
        for(IntIterator intiterator = slices(width, sourceWidth); intiterator.hasNext(); i += j) {
            j = intiterator.nextInt();
            int k = (sourceWidth - j) / 2;
            int l = y;

            int i1;
            for(IntIterator intiterator1 = slices(height, sourceHeight); intiterator1.hasNext(); l += i1) {
                i1 = intiterator1.nextInt();
                int j1 = (sourceHeight - i1) / 2;
                GuiComponent.blit(poseStack,  i, l, uOffset + k, vOffset + j1, j, i1, textureWidth, textureHeight);
            }
        }

    }

    private static IntIterator slices(int target, int total) {
        int i = Mth.positiveCeilDiv(target, total);
        return new Divisor(target, i);
    }

    public static void renderScrollingString(PoseStack guiGraphics, Font font, Component text, int minX, int minY, int maxX, int maxY, int color) {
        int textWidth = font.width(text);
        int textY = (minY + maxY - 9) / 2 + 1;
        int width = maxX - minX;
        int height = maxY - minY;
        if (textWidth > width) {
            int diff = textWidth - width;
            double time = (double) Util.getMillis() / 1000.0D;
            double duration = Math.max((double)diff * 0.5D, 3.0D);
            double percent = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * time / duration)) / 2.0D + 0.5D;
            double offset = Mth.lerp(percent, 0.0D, diff);
            enableScissor(minX, minY, maxX, maxY);
            drawString(guiGraphics, font, text, minX - (int)offset, textY, color);
            RenderSystem.disableScissor();
        } else {
            drawCenteredString(guiGraphics, font, text, (minX + maxX) / 2, textY, color);
        }
    }

    public static void drawCenteredString(PoseStack pPoseStack, Font pFont, Component pText, int pX, int pY, int pColor) {
        FormattedCharSequence formattedcharsequence = pText.getVisualOrderText();
        pFont.drawShadow(pPoseStack, formattedcharsequence, (float)(pX - pFont.width(formattedcharsequence) / 2), (float)pY, pColor);
    }

    public static void drawString(PoseStack pPoseStack, Font pFont, Component pText, int pX, int pY, int pColor) {
        FormattedCharSequence formattedcharsequence = pText.getVisualOrderText();
        pFont.drawShadow(pPoseStack, formattedcharsequence, (float)pX, (float)pY, pColor);
    }
}