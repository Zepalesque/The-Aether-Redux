package net.zepalesque.redux.client.render.entity.model.entity;

import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

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

        wisp_left.addOrReplaceChild("string_left", CubeListBuilder.create().texOffs(44, -10).addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition wisp_right = head.addOrReplaceChild("wisp_right", CubeListBuilder.create(), PartPose.offset(-5.0F, -3.0F, -2.0F));

        wisp_right.addOrReplaceChild("string_right", CubeListBuilder.create().texOffs(44, -6).mirror().addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -0.2618F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void prepareMobModel(Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTicks);
        this.head.y = 6.0F + sheepuff.getHeadEatPositionScale(partialTicks) * 9.0F;
        this.headXRot = sheepuff.getHeadEatAngleScale(partialTicks);
    }

    public void setupAnim(Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headXRot;
    }
}
