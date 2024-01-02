package net.zepalesque.redux.mixin.client.render.layer;

import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.aetherteam.aether_genesis.client.renderer.entity.layers.BattleSentryLayer;
import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BattleSentryLayer.class)
public class BattleSentryLayerMixin {

    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILcom/aetherteam/aether_genesis/entity/monster/BattleSentry;FFFFFF)V", cancellable = true)
    public void renderMob(PoseStack poseStack, MultiBufferSource buffer, int packedLight, BattleSentry sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.sentry_improvements.get()) {
            ci.cancel();
        }
    }
}
