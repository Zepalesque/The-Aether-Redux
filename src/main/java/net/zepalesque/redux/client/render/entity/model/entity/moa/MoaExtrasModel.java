package net.zepalesque.redux.client.render.entity.model.entity.moa;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

// TODO: Merge with other moa parts, maybe include body
public class MoaExtrasModel extends EntityModel<Moa> {

    public final ModelPart body_additions;
    public final ModelPart head;
    public final ModelPart neck_hurtanim;
    public final ModelPart neck;
    public final ModelPart neck_piece;
    public final ModelPart head_hurtanim;
    public final ModelPart head_part;
    public final ModelPart feathers;
    public final ModelPart jaw;
    public final ModelPart tail_feathers;
    public final ModelPart middle_feather;
    public final ModelPart right_feather;
    public final ModelPart left_feather;

    public MoaExtrasModel(ModelPart root) {
        this.body_additions = root.getChild("body_additions");
        this.head = body_additions.getChild("head");
        this.neck_hurtanim = head.getChild("neck_hurtanim");
        this.neck = neck_hurtanim.getChild("neck");
        this.neck_piece = neck.getChild("neck_piece");
        this.head_hurtanim = neck.getChild("head_hurtanim");
        this.head_part = head_hurtanim.getChild("head_part");
        this.feathers = head_part.getChild("feathers");
        this.jaw = head_part.getChild("jaw");
        this.tail_feathers = body_additions.getChild("tail_feathers");
        this.middle_feather = tail_feathers.getChild("middle_feather");
        this.right_feather = tail_feathers.getChild("right_feather");
        this.left_feather = tail_feathers.getChild("left_feather");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body_additions = partdefinition.addOrReplaceChild("body_additions", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition head = body_additions.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -5.0F));

        PartDefinition neck_hurtanim = head.addOrReplaceChild("neck_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 1.0F));

        PartDefinition neck = neck_hurtanim.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck_piece = neck.addOrReplaceChild("neck_piece", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition neck_piece_r1 = neck_piece.addOrReplaceChild("neck_piece_r1", CubeListBuilder.create().texOffs(44, 12).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition head_hurtanim = neck.addOrReplaceChild("head_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -1.5F));

        PartDefinition head_part = head_hurtanim.addOrReplaceChild("head_part", CubeListBuilder.create().texOffs(40, 0).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition feathers = head_part.addOrReplaceChild("feathers", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition head_feather_top = feathers.addOrReplaceChild("head_feather_top", CubeListBuilder.create().texOffs(35, 3).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 2.0F));

        PartDefinition head_feather_left = feathers.addOrReplaceChild("head_feather_left", CubeListBuilder.create().texOffs(54, 53).addBox(0.0F, -2.5F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 2.0F));

        PartDefinition head_feather_right = feathers.addOrReplaceChild("head_feather_right", CubeListBuilder.create().texOffs(54, 53).mirror().addBox(0.0F, -2.5F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -1.0F, 2.0F));

        PartDefinition jaw = head_part.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(46, 15).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(-0.1F))
                .texOffs(46, 22).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition tail_feathers = body_additions.addOrReplaceChild("tail_feathers", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition middle_feather = tail_feathers.addOrReplaceChild("middle_feather", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 5.0F));

        PartDefinition tail1_r1 = middle_feather.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(45, 31).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition right_feather = tail_feathers.addOrReplaceChild("right_feather", CubeListBuilder.create(), PartPose.offset(-2.0F, -13.0F, 5.0F));

        PartDefinition tail2_r1 = right_feather.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(47, 43).addBox(-2.5F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.2618F, -0.5236F, 0.0F));

        PartDefinition left_feather = tail_feathers.addOrReplaceChild("left_feather", CubeListBuilder.create(), PartPose.offset(-2.0F, -13.0F, 5.0F));

        PartDefinition tail3_r1 = left_feather.addOrReplaceChild("tail3_r1", CubeListBuilder.create().texOffs(53, 43).addBox(-0.5F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    public static LayerDefinition createLoreLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body_additions = partdefinition.addOrReplaceChild("body_additions", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition head = body_additions.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -4.0F));

        PartDefinition neck_hurtanim = head.addOrReplaceChild("neck_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 1.0F));

        PartDefinition neck = neck_hurtanim.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition neck_piece = neck.addOrReplaceChild("neck_piece", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition neck_piece_r1 = neck_piece.addOrReplaceChild("neck_piece_r1", CubeListBuilder.create().texOffs(44, 12).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -1.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition head_hurtanim = neck.addOrReplaceChild("head_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 1.0F));

        PartDefinition head_part = head_hurtanim.addOrReplaceChild("head_part", CubeListBuilder.create().texOffs(17, 40).addBox(-2.0F, -4.0F, -2.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

        PartDefinition helmet_r1 = head_part.addOrReplaceChild("helmet_r1", CubeListBuilder.create().texOffs(0, 44).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -2.5F, -1.5F, -0.1309F, 0.0F, 0.0F));

        PartDefinition feathers = head_part.addOrReplaceChild("feathers", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -0.5F));

        PartDefinition head_feather_top = feathers.addOrReplaceChild("head_feather_top", CubeListBuilder.create().texOffs(35, 3).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 2.0F));

        PartDefinition head_feather_left = feathers.addOrReplaceChild("head_feather_left", CubeListBuilder.create().texOffs(29, 45).addBox(0.0F, -2.5F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 2.0F));

        PartDefinition head_feather_right = feathers.addOrReplaceChild("head_feather_right", CubeListBuilder.create().texOffs(29, 45).mirror().addBox(0.0F, -2.5F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -1.0F, 2.0F));

        PartDefinition jaw = head_part.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition beak = head_part.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(31, 57).addBox(-2.0F, -1.0F, -6.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(27, 57).addBox(-2.0F, -0.5F, -6.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(21, 32).addBox(-1.5F, -3.0F, -6.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.0F));

        PartDefinition tail_feathers = body_additions.addOrReplaceChild("tail_feathers", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition middle_feather = tail_feathers.addOrReplaceChild("middle_feather", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 5.0F));

        PartDefinition tail1_r1 = middle_feather.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(34, 52).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition right_feather = tail_feathers.addOrReplaceChild("right_feather", CubeListBuilder.create(), PartPose.offset(-2.0F, -13.0F, 5.0F));

        PartDefinition tail2_r1 = right_feather.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(11, 56).addBox(-2.5F, 0.0F, 0.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -0.4363F, -0.3491F, 0.0F));

        PartDefinition left_feather = tail_feathers.addOrReplaceChild("left_feather", CubeListBuilder.create(), PartPose.offset(-2.0F, -13.0F, 5.0F));

        PartDefinition tail3_r1 = left_feather.addOrReplaceChild("tail3_r1", CubeListBuilder.create().texOffs(17, 56).addBox(-0.5F, 0.0F, 0.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, -0.4363F, 0.3491F, 0.0F));

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