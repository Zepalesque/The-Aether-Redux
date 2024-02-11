//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.client.render.entity.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.zepalesque.redux.entity.monster.Blightbunny;

public class BlightbunnyModel extends EntityModel<Blightbunny> {
    public final ModelPart base;
    public final ModelPart head;
    public final ModelPart rightEar;
    public final ModelPart leftEar;
    public final ModelPart rightWhiskers;
    public final ModelPart leftWhiskers;
    public final ModelPart body;
    public final ModelPart puff;
    public final ModelPart tail;
    public final ModelPart rightFrontLeg;
    public final ModelPart leftFrontLeg;
    public final ModelPart rightBackLeg;
    public final ModelPart leftBackLeg;
    public float puffiness;

    public BlightbunnyModel(ModelPart root) {
        this.base = root.getChild("base");
        this.head = root.getChild("head");
        this.rightEar = this.head.getChild("right_ear");
        this.leftEar = this.head.getChild("left_ear");
        this.rightWhiskers = this.head.getChild("right_whiskers");
        this.leftWhiskers = this.head.getChild("left_whiskers");
        this.body = this.base.getChild("body");
        this.puff = this.base.getChild("puff");
        this.tail = this.body.getChild("tail");
        this.rightFrontLeg = this.body.getChild("right_front_leg");
        this.leftFrontLeg = this.body.getChild("left_front_leg");
        this.rightBackLeg = this.body.getChild("right_back_leg");
        this.leftBackLeg = this.body.getChild("left_back_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition head = base.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -4.0F));

        PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(23, 13).addBox(1.0F, -4.0F, -4.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 3).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(23, 7).addBox(-2.0F, -4.0F, -4.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightWhiskers = head.addOrReplaceChild("rightWhiskers", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftWhiskers = head.addOrReplaceChild("leftWhiskers", CubeListBuilder.create().texOffs(14, 0).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition puff = base.addOrReplaceChild("puff", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rotation = puff.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(0, 10).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(16, 21).addBox(0.0F, -5.5F, -2.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition body = base.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Box_r1 = tail.addOrReplaceChild("Box_r1", CubeListBuilder.create().texOffs(32, 19).addBox(-1.5F, -1.125F, -3.4F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -1.7453F, 0.0F, 0.0F));

        PartDefinition rightFrontLeg = body.addOrReplaceChild("rightFrontLeg", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, 0.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0F, -3.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leftFrontLeg = body.addOrReplaceChild("leftFrontLeg", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, 0.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, -3.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition rightBackLeg = body.addOrReplaceChild("rightBackLeg", CubeListBuilder.create().texOffs(8, 32).addBox(-2.0F, 0.0F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 4.0F, -3.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leftBackLeg = body.addOrReplaceChild("leftBackLeg", CubeListBuilder.create().texOffs(8, 26).addBox(0.0F, 0.0F, -3.75F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 4.0F, -3.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    public void prepareMobModel(Blightbunny aerbunny, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(aerbunny, limbSwing, limbSwingAmount, partialTicks);
        this.puffiness = Mth.lerp(partialTicks, (float)aerbunny.getPuffiness(), (float)(aerbunny.getPuffiness() - aerbunny.getPuffSubtract())) / 20.0F;
    }

    public void setupAnim(Blightbunny aerbunny, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount - this.body.xRot;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount - this.body.xRot;
        this.rightBackLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.2F * limbSwingAmount - this.body.xRot;
        this.leftBackLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.2F * limbSwingAmount - this.body.xRot;
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.pushPose();
        float a = 1.0F + this.puffiness * 0.5F;
        this.puff.xScale = a;
        this.puff.yScale = a;
        this.puff.zScale = a;
        this.puff.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
