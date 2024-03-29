package net.zepalesque.redux.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.player.AdrenalineModule;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.effect.ReduxEffects;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.util.math.MathUtil;
import org.jetbrains.annotations.Nullable;


@Mod.EventBusSubscriber(
        modid = Redux.MODID,
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxOverlays {

    public static int rebuxY = 0;
    public static int rebuxCooldown = 100;
    public static boolean rebuxTarget = false;
    public static final int max = 16;
    public static final Lazy<ItemStack> stack = Lazy.of(() -> new ItemStack(ReduxItems.REBUX_ICON.get()));

    private static final ResourceLocation ADRENALINE_OVERLAY = Redux.locate("textures/blur/adrenaline_vignette.png");
    private static final ResourceLocation LOBOTOMY = Redux.locate("textures/blur/there_is_no_fire_in_this_hole.png");
    private static final ResourceLocation REBUX = Redux.locate("textures/gui/rebux_toast.png");

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

        event.registerAboveAll("there_is_no_fire_in_this_hole", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderLobotomy(pStack, minecraft, window, partialTicks);
            }

        });
        event.registerAboveAll("rebux_counter", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                ReduxPlayer.get(player).ifPresent(reduxPlayer -> renderRebux(pStack, window, 1.0F, font, reduxPlayer.rebuxCount()));
            }

        });
    }

    private static boolean shouldShowRebuxCounter(@Nullable Screen screen) {
        if (screen == null) {
            return false;
        }
        return true;
    }


    public static void tick() {
        Minecraft minecraft = Minecraft.getInstance();
        if ((rebuxTarget) && rebuxY < max) {
            rebuxY++;
        } else if (rebuxY > 0) {
            if (!rebuxTarget)
                rebuxY--;
        }
        if (rebuxCooldown <= 0) {
            rebuxTarget = false;
            rebuxCooldown = 100;
        } else {
            rebuxCooldown--;
        }
    }

    public static void rebux(boolean set) {
        rebuxTarget = set;
    }

    private static int getRebuxOffset(Minecraft mc) {
        if (!ReduxConfig.CLIENT.slide_coin_indicator.get()) {
            return rebuxTarget ? 32 : 0;
        }
        if (shouldShowRebuxCounter(mc.screen)) {
            return max;
        } else {
            return (int) MathUtil.costrp(rebuxY / 16D, 0, 32);
        }
    }


    private static void renderAdrenalineOverlay(GuiGraphics guiGraphics, Minecraft minecraft, Window window, AdrenalineModule module, float partialTicks) {
        double effectScale = minecraft.options.screenEffectScale().get();
        float alpha = module.getTransparency(partialTicks);
        renderVignette(guiGraphics, window, effectScale, alpha, ADRENALINE_OVERLAY);

    }

    private static void renderLobotomy(GuiGraphics guiGraphics, Minecraft minecraft, Window window, float partialTicks) {
        double effectScale = minecraft.options.screenEffectScale().get();
        if (minecraft.player.hasEffect(ReduxEffects.THE_LOBOTOMY.get())) {
            float alpha = minecraft.player.getEffect(ReduxEffects.THE_LOBOTOMY.get()).getDuration() / 200F;
            renderVignette(guiGraphics, window, effectScale, alpha, LOBOTOMY);
        }
    }


    private static void renderVignette(GuiGraphics guiGraphics, Window window, double effectScale, float alpha, ResourceLocation resource) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        alpha = (float)((double)alpha * Math.sqrt(effectScale));
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        guiGraphics.blit(resource, 0, 0, -90, 0.0F, 0.0F, window.getGuiScaledWidth(), window.getGuiScaledHeight(), window.getGuiScaledWidth(), window.getGuiScaledHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }

    private static void renderRebux(GuiGraphics guiGraphics, Window window, float alpha, Font font, int coinCount) {
        int topY = getRebuxOffset(Minecraft.getInstance()) - 32;
        if (topY > -32) {
            int x = (window.getGuiScaledWidth() / (shouldOffset() ? 4 : 2));
            int y = topY + 16;
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
            guiGraphics.blit(REBUX, x - 80, topY, -90, 0.0F, 0.0F, 160, 32, 160, 32);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            poseStack.popPose();
            Component text = Component.translatable("gui.aether_redux.coin_count", coinCount);
            int width = font.width(text);
            guiGraphics.drawCenteredString(font, text, x + 16, y, 0xFFFFFF);
            guiGraphics.renderFakeItem(stack.get(), x - (width / 2), y - 8);
        }
    }

    private static boolean shouldOffset() {
        return ReduxConfig.CLIENT.always_offset_coin_indicator.get() || ModList.get().isLoaded("jade");
    }

}
