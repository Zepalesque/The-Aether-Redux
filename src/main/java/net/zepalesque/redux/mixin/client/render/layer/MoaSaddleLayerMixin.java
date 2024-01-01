package net.zepalesque.redux.mixin.client.render.layer;

import com.aetherteam.aether.client.renderer.entity.layers.MoaSaddleLayer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.util.math.MathUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoaSaddleLayer.class)
public class MoaSaddleLayerMixin {

    @Shadow @Final private MoaModel saddle;

    @Inject(at = @At("HEAD"), remap = false, method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILcom/aetherteam/aether/entity/passive/Moa;FFFFFF)V")
    public void renderMob(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (MoaUtils.useNewModel(moa))
        {
            float breathe = MathUtil.breathe(moa, partialTicks);

            if (moa.hurtTime > 0 && moa.hurtTime - partialTicks > 0.0F)
            {
                int hit = moa.hurtDuration - moa.hurtTime;
                float hitSmooth = hit + partialTicks;
                final float baseRot = hitSmooth >= (moa.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth));

                float rot = baseRot * (((float) Math.PI) * 0.125F);

                this.saddle.body.xRot = (float) ((0.3333F * rot) + (Math.PI * 0.5F)) + breathe;
            } else {

                this.saddle.body.xRot = (float) (Math.PI * 0.5F) + breathe;
            }
        }
    }
}