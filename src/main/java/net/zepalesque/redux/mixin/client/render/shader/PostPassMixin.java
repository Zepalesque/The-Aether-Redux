package net.zepalesque.redux.mixin.client.render.shader;

import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostPass;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.client.ReduxPostProcessHandler;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: Investigate whether or not this is a good way to do this, because I'm not convinced it is...
@Mixin(PostPass.class)
public class PostPassMixin {

    @Shadow @Final private EffectInstance effect;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EffectInstance;apply()V", shift = At.Shift.BEFORE), method = "process")
    private void processInvoke(float partialTicks, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.enable_adrenaline_postproccess.get()) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().options.graphicsMode().get() == GraphicsStatus.FABULOUS) {
                ReduxPlayer.get(Minecraft.getInstance().player).ifPresent(reduxPlayer -> this.effect.safeGetUniform("AetherRedux_AdrenalineStrength").set(reduxPlayer.getAdrenalineModule().getShaderStrength()));
            } else {
                this.effect.safeGetUniform("AetherRedux_AdrenalineStrength").set(0.0F);
            }
        }
    }
}
