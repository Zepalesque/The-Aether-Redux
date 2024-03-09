package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.SwetRenderer;
import com.aetherteam.aether.entity.monster.Swet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SwetRenderer.class)
public class SwetRendererMixin extends MobRendererMixin<Swet, SlimeModel<Swet>> {

    @Inject(at = @At("HEAD"), method = "scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", cancellable = true)
    protected void redux$scale(LivingEntity livingEntity, PoseStack poseStack, float partialTickTime, CallbackInfo ci) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            Swet swet = (Swet) livingEntity;
            float f = 0.999F;
            poseStack.scale(0.999F, 0.999F, 0.999F);
            poseStack.translate(0.0F, 0.001F, 0.0F);
            float size = (float) swet.getSize() / 2F;
            float squishLerp = Mth.lerp(partialTickTime, swet.oSquish, swet.squish) / (size * 0.5F + 1.0F);
            float f3 = 1.0F / (squishLerp + 1.0F);
            poseStack.scale(f3 * size, 1.0F / f3 * size, f3 * size);
            float f4 = (float) (1 - (swet.getWaterDamageScale() / 0.9));
            poseStack.scale(f4, f4, f4);
            ci.cancel();
        }
    }

    @Override
    public void renderMob(Swet entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            this.shadowRadius = 0.25F * (float) entity.getSize();
        }
        super.renderMob(entity, entityYaw, partialTicks, poseStack, buffer, packedLight, ci);
    }
}
