package net.zepalesque.redux.mixin.client.render.layer;

import com.aetherteam.aether.client.renderer.entity.layers.SentryGlowLayer;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SentryGlowLayer.class)
public class SentryGlowLayerMixin {

    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILcom/aetherteam/aether/entity/monster/dungeon/Sentry;FFFFFF)V", cancellable = true)
    public void renderMob(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sentry sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.sentry_improvements.get()) {
            ci.cancel();
        }
    }
}
