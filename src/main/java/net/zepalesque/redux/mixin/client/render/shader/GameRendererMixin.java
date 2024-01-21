package net.zepalesque.redux.mixin.client.render.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import net.zepalesque.redux.client.ReduxPostProcessHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// TODO: Investigate whether or not this is a good way to do this, because I'm not convinced it is...
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V", shift = At.Shift.BEFORE), method = "render")
    private void renderInvoke(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        if (ReduxPostProcessHandler.getAdrenaline() != null) {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            ReduxPostProcessHandler.getAdrenaline().process(partialTicks);
        }
    }
    @Inject(at = @At(value = "TAIL"), method = "resize")
    private void resize(int width, int height, CallbackInfo ci) {
        if (ReduxPostProcessHandler.getAdrenaline() != null) {
            ReduxPostProcessHandler.getAdrenaline().resize(width, height);
        }
    }
}
