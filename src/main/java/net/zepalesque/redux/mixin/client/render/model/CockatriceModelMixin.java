package net.zepalesque.redux.mixin.client.render.model;

import com.aetherteam.aether.client.renderer.entity.model.BipedBirdModel;
import com.aetherteam.aether.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.zepalesque.redux.capability.animation.moa.MoaAnimation;
import net.zepalesque.redux.capability.cockatrice.CockatriceExtension;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.CockatriceModelType;
import net.zepalesque.redux.config.enums.MoaModelType;
import net.zepalesque.redux.util.math.MathUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CockatriceModel.class)
public class CockatriceModelMixin extends BipedBirdModelMixin<Cockatrice> {

    @Unique private boolean useNewModel;

    @Inject(method = "setupAnim(Lcom/aetherteam/aether/entity/monster/Cockatrice;FFFFF)V", at = @At(value = "TAIL"), remap = false)
    public void setupAnim(Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        this.useNewModel = ReduxConfig.CLIENT.cockatrice_model_upgrade.get();
        if (this.useNewModel) {
            this.jaw.xRot = 0.10F;
        }
        float partial = ageInTicks % 1;
        if (ReduxConfig.CLIENT.cockatrice_model_type.get() == CockatriceModelType.refreshed) {
            float progress = cockatrice.isEntityOnGround() ? 0 : 1;
            float swingCalc = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;


            if (CockatriceExtension.get(cockatrice).isPresent()) {
                CockatriceExtension cockatriceAnim = CockatriceExtension.get(cockatrice).orElseThrow(() -> new IllegalStateException("Could not find CockatriceExtension capability!"));
                progress = Mth.lerp(partial, cockatriceAnim.getPrevLegAnim(), cockatriceAnim.getLegAnim()) * 0.2F;
            }
            this.rightLeg.xRot = MathUtil.costrp(progress, swingCalc, 0.6F);
            this.leftLeg.xRot = MathUtil.costrp(progress, -swingCalc, 0.6F);
        }
    }


    @Inject(method = "renderToBuffer", at = @At(value = "HEAD"), cancellable = true)
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        if (!this.useNewModel) {
            this.head.render(poseStack, consumer, packedLight, packedOverlay);
            this.rightTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.middleTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.body.render(poseStack, consumer, packedLight, packedOverlay);
        }
        if (!this.useNewModel || ReduxConfig.CLIENT.cockatrice_model_type.get() == CockatriceModelType.refreshed) {
            this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
        }
        ci.cancel();
    }


}
