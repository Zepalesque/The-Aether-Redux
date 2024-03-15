package net.zepalesque.redux.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.player.AdrenalineModule;
import net.zepalesque.redux.capability.player.ReduxPlayer;



@Mod.EventBusSubscriber(
        modid = Redux.MODID,
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxOverlays {

    private static final ResourceLocation ADRENALINE_OVERLAY = Redux.locate("textures/blur/adrenaline_vignette.png");

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {

        event.registerAboveAll("adrenaline_vignette", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                ReduxPlayer.get(player).ifPresent((reduxPlayer) -> {
                    renderAdrenalineOverlay(pStack, minecraft, window, reduxPlayer.getAdrenalineModule(), partialTicks);
                });
            }

        });
    }


    private static void renderAdrenalineOverlay(PoseStack guiGraphics, Minecraft minecraft, Window window, AdrenalineModule module, float partialTicks) {
        double effectScale = minecraft.options.screenEffectScale().get();
        float alpha = module.getTransparency(partialTicks);
        renderVignette(guiGraphics, window, effectScale, alpha, ADRENALINE_OVERLAY);

    }


    private static void renderVignette(PoseStack poseStack, Window window, double effectScale, float alpha, ResourceLocation resource) {
        poseStack.pushPose();
        alpha = (float)((double)alpha * Math.sqrt(effectScale));
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, resource);
        GuiComponent.blit(poseStack, 0, 0, -90, 0.0F, 0.0F, window.getGuiScaledWidth(), window.getGuiScaledHeight(), window.getGuiScaledWidth(), window.getGuiScaledHeight());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }
}
