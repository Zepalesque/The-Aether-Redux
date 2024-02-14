package net.zepalesque.redux.client.render.entity.model.entity;

import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.zepalesque.redux.util.math.MathUtil;

public class SheepuffReduxModel extends QuadrupedModel<Sheepuff> {


    public float headXRot;
    protected final ModelPart wisp_right;
    protected final ModelPart wisp_left;
    protected final ModelPart earring_right;
    protected final ModelPart earring_left;

    public SheepuffReduxModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        this.wisp_right = this.head.getChild("wisp_right");
        this.wisp_left = this.head.getChild("wisp_left");
        this.earring_right = this.head.getChild("earring_right");
        this.earring_left = this.head.getChild("earring_left");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 12.0F, 7.0F));

        partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 12.0F, -5.0F));

        partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 12.0F, 7.0F));

        partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 14).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 12.0F, -5.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 1).mirror().addBox(-5.0F, -4.0F, -5.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 1).addBox(3.0F, -4.0F, -5.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -8.0F));

        head.addOrReplaceChild("earring_right", CubeListBuilder.create().texOffs(30, 1).mirror().addBox(0.0F, -2.0F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(34, 3).mirror().addBox(-0.5F, 3.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, -3.5F));

        head.addOrReplaceChild("earring_left", CubeListBuilder.create().texOffs(34, 3).addBox(-0.5F, 3.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 1).addBox(0.0F, -2.0F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, -3.5F));


        PartDefinition wisp_left = head.addOrReplaceChild("wisp_left", CubeListBuilder.create(), PartPose.offset(5.0F, -3.0F, -2.0F));

        PartDefinition bend_left = wisp_left.addOrReplaceChild("bend_left", CubeListBuilder.create().texOffs(54, -5).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));
        bend_left.addOrReplaceChild("string_left", CubeListBuilder.create().texOffs(44, -5).addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 5.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition wisp_right = head.addOrReplaceChild("wisp_right", CubeListBuilder.create(), PartPose.offset(-5.0F, -3.0F, -2.0F));

        PartDefinition bend_right = wisp_right.addOrReplaceChild("bend_right", CubeListBuilder.create().texOffs(54, -5).mirror().addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));
        bend_right.addOrReplaceChild("string_right", CubeListBuilder.create().texOffs(44, -5).mirror().addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, 5.0F, 0.0F, 0.3491F, 0.0F));

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-3.0F, 12.0F, 7.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void setupAnim(Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        float partialTicks = ageInTicks % 1;

        this.head.y = 6.0F + sheepuff.getHeadEatPositionScale(partialTicks) * 9.0F;
        this.headXRot = sheepuff.getHeadEatAngleScale(partialTicks);
        this.head.xRot = this.headXRot;
        float hangSwing = Mth.cos(limbSwing * 0.6662F * 0.5F + (float) Math.PI) * 0.0625F * limbSwingAmount;
        float hangWave = MathUtil.breathe(sheepuff, partialTicks, 0.5F + (3.5F * limbSwingAmount), 1F, (float) (Math.PI * 0.75));
        float wave1 = MathUtil.breathe(sheepuff, partialTicks, 0.4F + (2.6F * limbSwingAmount), 2F, 0F);
        float wave2 = MathUtil.breathe(sheepuff, partialTicks, 0.4F + (2.6F * limbSwingAmount), 2F, (float) (Math.PI * 0.25));
        this.wisp_left.yRot = wave1 - (this.head.yRot  * 0.5f);
        this.wisp_right.yRot = -wave2 - (this.head.yRot  * 0.5f);
//        this.wisp_left2.yRot = wave2 - (this.head.yRot  * 0.5f);
//        this.wisp_right2.yRot = -wave2 - (this.head.yRot  * 0.5f);
        this.wisp_left.xRot = Math.max( -(this.head.xRot * 0.5F), 0F);
        this.wisp_right.xRot = Math.min( -(this.head.xRot * 0.5F), 0F);
        this.earring_left.xRot = hangWave + hangSwing * 2 - (this.head.xRot);
        this.earring_right.xRot = -this.earring_left.xRot - (this.head.xRot * 2);
    }
}
