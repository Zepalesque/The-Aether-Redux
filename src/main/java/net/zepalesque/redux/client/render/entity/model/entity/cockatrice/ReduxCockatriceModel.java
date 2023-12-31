package net.zepalesque.redux.client.render.entity.model.entity.cockatrice;

import com.aetherteam.aether.client.renderer.entity.model.BipedBirdModel;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.zepalesque.redux.config.ReduxConfig;

import javax.annotation.Nonnull;


// TODO: MIXIN PLEASE
public class ReduxCockatriceModel extends BipedBirdModel<Cockatrice> {
    public ReduxCockatriceModel(ModelPart root) {
        super(root);
    }

    public void setupAnim(@Nonnull Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(cockatrice, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (ReduxConfig.CLIENT.cockatrice_improvements.get()) {
            this.jaw.xRot = 0.10F;
        } else {
            this.jaw.xRot = 0.35F;
        }
    }

    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if (!ReduxConfig.CLIENT.cockatrice_improvements.get()) {
            this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
            this.head.render(poseStack, consumer, packedLight, packedOverlay);
            this.rightTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.middleTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.leftTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
            this.body.render(poseStack, consumer, packedLight, packedOverlay);
        }
    }
}