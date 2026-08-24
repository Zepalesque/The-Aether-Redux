package net.zepalesque.redux.mixin.mixins.client.renderer;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.zepalesque.redux.attachment.anim.MoaAnimAttachment;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.zenith.util.math.EasingUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoaModel.class)
public class MoaModelMixin extends BipedBirdModelMixin<Moa> {


    @Shadow(remap = false) public boolean renderLegs;

    @Unique public boolean useNewModel;

    @Inject(method = "setupAnim(Lcom/aetherteam/aether/entity/passive/Moa;FFFFF)V", at = @At(value = "TAIL"), remap = false)
    public void setupAnim(Moa moa, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (this.useNewModel && !moa.isSitting()) this.jaw.xRot = 0.15F;
        
        var mc = Minecraft.getInstance();
        var ticker = moa.level().tickRateManager();
        
        var partial = mc.getTimer().getGameTimeDeltaPartialTick(!ticker.isEntityFrozen(moa));

        if (ReduxConfig.CLIENT.improved_moas.get()) {
	        var swingCalc = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

            var attachment = MoaAnimAttachment.get(moa);
            var progress = attachment.getLegAnim(moa, partial);
            
            var sin = EasingUtil.Sinusoidal.inOut(progress);
            this.rightLeg.xRot = Mth.lerp(sin, swingCalc, 0.6F);
            this.leftLeg.xRot = Mth.lerp(sin, -swingCalc, 0.6F);
        }
    }

    @Inject(method = "prepareMobModel(Lcom/aetherteam/aether/entity/passive/Moa;FFF)V", at = @At(value = "TAIL"), remap = false)
    public void redux$moaPrepare(Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, CallbackInfo ci) {
        this.useNewModel = net.zepalesque.redux.client.renderer.entity.moa.MoaUtils.useNewModel(moa);
        this.renderLegs = !moa.isSitting() || !moa.isEntityOnGround() && moa.isSitting();

    }


    @Inject(method = "renderToBuffer", at = @At(value = "HEAD"), cancellable = true)
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color, CallbackInfo ci) {
        this.leftWing.skipDraw = this.useNewModel;
        this.rightWing.skipDraw = this.useNewModel;
        this.body.render(poseStack, consumer, packedLight, packedOverlay);

        if (!this.useNewModel) {
            this.head.render(poseStack, consumer, packedLight, packedOverlay);
            this.rightTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.middleTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
        }

        if (this.renderLegs) {
            this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
        }
        ci.cancel();
    }


}
