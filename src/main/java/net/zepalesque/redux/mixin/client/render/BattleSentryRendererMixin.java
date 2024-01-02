package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.SentryRenderer;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.aetherteam.aether_genesis.client.renderer.entity.BattleSentryRenderer;
import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BattleSentryRenderer.class)
public class BattleSentryRendererMixin extends MobRendererMixin<Sentry, SlimeModel<Sentry>> {

    @Override
    public void renderMob(Sentry moa, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        this.model.root().skipDraw = ReduxConfig.CLIENT.sentry_improvements.get();
        super.renderMob(moa, entityYaw, partialTicks, poseStack, buffer, packedLight, ci);
    }

    @Inject(at = @At("HEAD"), method = "scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", cancellable = true)
    public void scaleMob(LivingEntity par1, PoseStack par2, float par3, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.sentry_improvements.get()) {
            ci.cancel();
        }
    }

}
