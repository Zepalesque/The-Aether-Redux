package net.zepalesque.redux.client.render.entity.model.entity;

import com.aetherteam.aether.entity.NotGrounded;
import com.aetherteam.aether.entity.WingedBird;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class MoaReduxModel extends EntityModel<Moa> {


    private final ModelPart neck;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;
    public final ModelPart head;
    public float swimAmount;

    public MoaReduxModel(ModelPart root) {
        this.head = root.getChild("head");
        this.neck = root.getChild("neck");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -8.0F, -12.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 2.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(92, 42).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 8.0F, 2.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(108, 42).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 8.0F, 2.0F));
        partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 2.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public void prepareMobModel(Moa entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.swimAmount = entity.getSwimAmount(partialTick);
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setupAnim(Moa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entity.getFallFlyingTicks() > 4;
        boolean flag1 = entity.isVisuallySwimming();
        this.head.yRot = netHeadYaw * 0.017453292F;
        float f = 1.0F;
        if (flag) {
            this.head.xRot = -0.7853982F;
        } else if (this.swimAmount > 0.0F) {
            if (flag1) {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, -0.7853982F);
            } else {
                this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, headPitch * 0.017453292F);
            }
        } else {
            this.head.xRot = headPitch * 0.017453292F;
        }
        if (!entity.isEntityOnGround()) {
            this.rightLeg.xRot = 0.6F;
            this.leftLeg.xRot = this.rightLeg.xRot;
        } else {
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        }
        this.rightLeg.yRot = 0.005F;
        this.leftLeg.yRot = -0.005F;
        this.rightLeg.zRot = 0.005F;
        this.leftLeg.zRot = -0.005F;
        if (this.riding) {
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = 0.31415927F;
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = -0.31415927F;
            this.leftLeg.zRot = -0.07853982F;
        }

        if (this.swimAmount > 0.0F) {
            this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F + 3.1415927F));
            this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F));
        }
    }


    public float setupWingsAnimation(Moa bipedBird, float partialTicks) {
        float rotVal = Mth.lerp(partialTicks, ((WingedBird)bipedBird).getPrevWingRotation(), ((WingedBird)bipedBird).getWingRotation());
        float destVal = Mth.lerp(partialTicks, ((WingedBird)bipedBird).getPrevWingDestPos(), ((WingedBird)bipedBird).getWingDestPos());
        return (Mth.sin(rotVal * 0.225F) + 1.0F) * destVal;
    }


    protected float rotlerpRad(float angle, float maxAngle, float mul) {
        float f = (mul - maxAngle) % 6.2831855F;
        if (f < -3.1415927F) {
            f += 6.2831855F;
        }

        if (f >= 3.1415927F) {
            f -= 6.2831855F;
        }

        return maxAngle + angle * f;
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        neck.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
