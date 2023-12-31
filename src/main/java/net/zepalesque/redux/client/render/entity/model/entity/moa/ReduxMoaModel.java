package net.zepalesque.redux.client.render.entity.model.entity.moa;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.zepalesque.redux.config.ReduxConfig;

import javax.annotation.Nonnull;

// TODO: Mixin
public class ReduxMoaModel extends MoaModel {
    public ReduxMoaModel(ModelPart root) {
        super(root);
    }

    public void prepareMobModel(@Nonnull Moa moa, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
        this.renderLegs = ((!moa.isSitting() || !moa.isEntityOnGround() && moa.isSitting()) && !ReduxConfig.CLIENT.moa_improvements.get()) ;
    }

    public void setupAnim(@Nonnull Moa moa, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (moa.isSitting()) {
            this.jaw.xRot = 0.0F;
        } else {
            if (ReduxConfig.CLIENT.moa_improvements.get()) {
                this.jaw.xRot = 0.15F;
            }
            else {
                this.jaw.xRot = 0.35F;
            }
        }



    }
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.body.render(poseStack, consumer, packedLight, packedOverlay);
        this.leftWing.skipDraw = (ReduxConfig.CLIENT.moa_improvements.get());
        this.rightWing.skipDraw = (ReduxConfig.CLIENT.moa_improvements.get());

        if (!ReduxConfig.CLIENT.moa_improvements.get()) {

            this.head.render(poseStack, consumer, packedLight, packedOverlay);
            this.rightTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.middleTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftTailFeather.render(poseStack, consumer, packedLight, packedOverlay);}

        if (this.renderLegs) {
            this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
        }
    }

}
