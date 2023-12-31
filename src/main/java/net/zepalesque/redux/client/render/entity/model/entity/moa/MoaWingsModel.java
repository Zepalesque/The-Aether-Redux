package net.zepalesque.redux.client.render.entity.model.entity.moa;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

// TODO: Merge with other moa parts, possibly try to improve feather textures
public class MoaWingsModel extends EntityModel<Moa> {
    public final ModelPart body_additions;
    public final ModelPart wing_1;
    public final ModelPart z_rot_wing_1;
    public final ModelPart feathers_1_wing1;
    public final ModelPart feathers_2_wing1;
    public final ModelPart feathers_3_wing1;
    public final ModelPart wing_2;
    public final ModelPart z_rot_wing_2;
    public final ModelPart feathers_1_wing2;
    public final ModelPart feathers_2_wing2;
    public final ModelPart feathers_3_wing2;


    public MoaWingsModel(ModelPart root) {
        this.body_additions = root.getChild("body_additions");
        this.wing_1 = body_additions.getChild("wing_1");
        this.z_rot_wing_1 = wing_1.getChild("z_rot_wing_1");
        this.feathers_1_wing1 = z_rot_wing_1.getChild("feathers_1_wing1");
        this.feathers_2_wing1 = z_rot_wing_1.getChild("feathers_2_wing1");
        this.feathers_3_wing1 = z_rot_wing_1.getChild("feathers_3_wing1");
        this.wing_2 = body_additions.getChild("wing_2");
        this.z_rot_wing_2 = wing_2.getChild("z_rot_wing_2");
        this.feathers_1_wing2 = z_rot_wing_2.getChild("feathers_1_wing2");
        this.feathers_2_wing2 = z_rot_wing_2.getChild("feathers_2_wing2");
        this.feathers_3_wing2 = z_rot_wing_2.getChild("feathers_3_wing2");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body_additions = partdefinition.addOrReplaceChild("body_additions", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition wing_1 = body_additions.addOrReplaceChild("wing_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.1F, -2.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_1 = wing_1.addOrReplaceChild("z_rot_wing_1", CubeListBuilder.create().texOffs(27, 20).addBox(-1.5F, -0.5F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -0.5F, 1.0F));

        PartDefinition feathers_1_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_1_wing1", CubeListBuilder.create().texOffs(11, 21).addBox(-0.75F, -1.0F, 0.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.5F, -1.0F));

        PartDefinition feathers_2_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_2_wing1", CubeListBuilder.create(), PartPose.offset(-0.5F, 3.0F, -1.0F));

        PartDefinition wing_feathers_r1 = feathers_2_wing1.addOrReplaceChild("wing_feathers_r1", CubeListBuilder.create().texOffs(11, 21).addBox(-0.75F, -1.0F, 0.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_3_wing1", CubeListBuilder.create(), PartPose.offset(-0.5F, 4.5F, -1.0F));

        PartDefinition wing_feathers_r2 = feathers_3_wing1.addOrReplaceChild("wing_feathers_r2", CubeListBuilder.create().texOffs(11, 20).addBox(-0.75F, -1.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition wing_2 = body_additions.addOrReplaceChild("wing_2", CubeListBuilder.create(), PartPose.offsetAndRotation(4.1F, -2.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_2 = wing_2.addOrReplaceChild("z_rot_wing_2", CubeListBuilder.create().texOffs(27, 20).mirror().addBox(0.5F, -0.5F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, -0.5F, 1.0F));

        PartDefinition feathers_1_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_1_wing2", CubeListBuilder.create().texOffs(11, 21).mirror().addBox(0.75F, -1.0F, 0.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 1.5F, -1.0F));

        PartDefinition feathers_2_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_2_wing2", CubeListBuilder.create(), PartPose.offset(0.5F, 3.0F, -1.0F));

        PartDefinition wing_feathers_r3 = feathers_2_wing2.addOrReplaceChild("wing_feathers_r3", CubeListBuilder.create().texOffs(11, 21).mirror().addBox(0.75F, -1.0F, 0.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_3_wing2", CubeListBuilder.create(), PartPose.offset(0.5F, 4.5F, -1.0F));

        PartDefinition wing_feathers_r4 = feathers_3_wing2.addOrReplaceChild("wing_feathers_r4", CubeListBuilder.create().texOffs(11, 20).mirror().addBox(0.75F, -1.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Moa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body_additions.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}