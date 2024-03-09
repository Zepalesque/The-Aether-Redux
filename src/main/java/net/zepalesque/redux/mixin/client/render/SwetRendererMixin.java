package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.SwetRenderer;
import com.aetherteam.aether.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.builderdog.ancient_aether.client.renderer.entity.renderer.FestiveSwetRenderer;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.SwetHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = { SwetRenderer.class, FestiveSwetRenderer.class })
public class SwetRendererMixin extends MobRendererMixin<Swet, SlimeModel<Swet>> {

    @Inject(at = @At("HEAD"), method = "scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", cancellable = true)
    protected void redux$scale(LivingEntity livingEntity, PoseStack poseStack, float partialTickTime, CallbackInfo ci) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            Swet swet = (Swet) livingEntity;
            float f = 0.999F;
            poseStack.scale(f, f, f);
            poseStack.translate(0.0F, 0.001F, 0.0F);
            float size = (float) SwetHooks.getTrueScale(swet);
            float squishLerp = Mth.lerp(partialTickTime, swet.oSquish, swet.squish) / (size * 0.5F + 1.0F);
            float f3 = 1.0F / (squishLerp + 1.0F);
            poseStack.scale(f3 * size, 1.0F / f3 * size, f3 * size);
            ci.cancel();
        }
    }

    @Override
    public void renderMob(Swet swet, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            this.shadowRadius = 0.25F * (float) SwetHooks.getTrueScale(swet);
        }
        super.renderMob(swet, entityYaw, partialTicks, poseStack, buffer, packedLight, ci);
    }
}
