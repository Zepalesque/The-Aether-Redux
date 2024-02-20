package net.zepalesque.redux.client.render.entity.model.entity;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MoaReduxModel extends EntityModel<Moa> {


    public final ModelPart base;
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
    public final ModelPart leg1;
    public final ModelPart base_leg1;
    public final ModelPart upper_fluff_leg1;
    public final ModelPart nail_leg1;
    public final ModelPart lower_leg1;
    public final ModelPart toes_leg1;
    public final ModelPart toes_stepanim_leg1;
    public final ModelPart back_toes_leg1;
    public final ModelPart leg2;
    public final ModelPart base_leg2;
    public final ModelPart upper_fluff_leg2;
    public final ModelPart nail_leg2;
    public final ModelPart lower_leg2;
    public final ModelPart toes_leg2;
    public final ModelPart toes_stepanim_leg2;
    public final ModelPart back_toes_leg2;
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

    public MoaReduxModel(ModelPart root) {
        this.base = root.getChild("base");
        this.body_additions = base.getChild("body_additions");
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
        this.leg1 = base.getChild("leg1");
        this.base_leg1 = leg1.getChild("base_leg1");
        this.upper_fluff_leg1 = base_leg1.getChild("upper_fluff_leg1");
        this.nail_leg1 = base_leg1.getChild("nail_leg1");
        this.lower_leg1 = base_leg1.getChild("lower_leg1");
        this.toes_leg1 = lower_leg1.getChild("toes_leg1");
        this.toes_stepanim_leg1 = toes_leg1.getChild("toes_stepanim_leg1");
        this.back_toes_leg1 = toes_stepanim_leg1.getChild("back_toes_leg1");
        this.leg2 = base.getChild("leg2");
        this.base_leg2 = leg2.getChild("base_leg2");
        this.upper_fluff_leg2 = base_leg2.getChild("upper_fluff_leg2");
        this.nail_leg2 = base_leg2.getChild("nail_leg2");
        this.lower_leg2 = base_leg2.getChild("lower_leg2");
        this.toes_leg2 = lower_leg2.getChild("toes_leg2");
        this.toes_stepanim_leg2 = toes_leg2.getChild("toes_stepanim_leg2");
        this.back_toes_leg2 = toes_stepanim_leg2.getChild("back_toes_leg2");
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

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(4.0F, 7.0F, 2.0F));

        PartDefinition body_additions = base.addOrReplaceChild("body_additions", CubeListBuilder.create(), PartPose.offset(-4.0F, 1.0F, -2.0F));

        PartDefinition head = body_additions.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, -10.0F));

        PartDefinition neck_hurtanim = head.addOrReplaceChild("neck_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 2.0F));

        PartDefinition neck = neck_hurtanim.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck_piece = neck.addOrReplaceChild("neck_piece", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition neck_piece_r1 = neck_piece.addOrReplaceChild("neck_piece_r1", CubeListBuilder.create().texOffs(88, 24).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -2.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition head_hurtanim = neck.addOrReplaceChild("head_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -3.0F));

        PartDefinition head_part = head_hurtanim.addOrReplaceChild("head_part", CubeListBuilder.create().texOffs(80, 0).addBox(-4.0F, -8.0F, -12.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition feathers = head_part.addOrReplaceChild("feathers", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition head_feather_top = feathers.addOrReplaceChild("head_feather_top", CubeListBuilder.create().texOffs(108, 3).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 4.0F));

        PartDefinition head_feather_left = feathers.addOrReplaceChild("head_feather_left", CubeListBuilder.create().texOffs(80, -8).addBox(0.0F, -5.0F, 0.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 4.0F));

        PartDefinition head_feather_right = feathers.addOrReplaceChild("head_feather_right", CubeListBuilder.create().texOffs(80, 0).mirror().addBox(0.0F, -5.0F, 0.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -2.0F, 4.0F));

        PartDefinition jaw = head_part.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(92, 30).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(-0.1F))
                .texOffs(92, 44).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition tail_feathers = body_additions.addOrReplaceChild("tail_feathers", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition middle_feather = tail_feathers.addOrReplaceChild("middle_feather", CubeListBuilder.create(), PartPose.offset(0.0F, -26.0F, 10.0F));

        PartDefinition tail1_r1 = middle_feather.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(102, 6).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition right_feather = tail_feathers.addOrReplaceChild("right_feather", CubeListBuilder.create(), PartPose.offset(-4.0F, -26.0F, 10.0F));

        PartDefinition tail2_r1 = right_feather.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(66, 54).addBox(-5.0F, 0.0F, 0.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.2618F, -0.5236F, 0.0F));

        PartDefinition left_feather = tail_feathers.addOrReplaceChild("left_feather", CubeListBuilder.create(), PartPose.offset(-4.0F, -26.0F, 10.0F));

        PartDefinition tail3_r1 = left_feather.addOrReplaceChild("tail3_r1", CubeListBuilder.create().texOffs(54, 54).addBox(-1.0F, 0.0F, 0.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition wing_1 = body_additions.addOrReplaceChild("wing_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.2F, -4.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_1 = wing_1.addOrReplaceChild("z_rot_wing_1", CubeListBuilder.create(), PartPose.offset(3.0F, -1.0F, 2.0F));

        PartDefinition wing_r1 = z_rot_wing_1.addOrReplaceChild("wing_r1", CubeListBuilder.create().texOffs(44, 40).addBox(-1.0F, -8.0F, -4.0F, 2.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition feathers_1_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_1_wing1", CubeListBuilder.create().texOffs(60, 32).addBox(-1.5F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 3.0F, -2.0F));

        PartDefinition feathers_2_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_2_wing1", CubeListBuilder.create(), PartPose.offset(-1.0F, 6.0F, -2.0F));

        PartDefinition wing_feathers_r1 = feathers_2_wing1.addOrReplaceChild("wing_feathers_r1", CubeListBuilder.create().texOffs(60, 28).addBox(-1.25F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_3_wing1", CubeListBuilder.create(), PartPose.offset(-1.0F, 9.0F, -2.0F));

        PartDefinition wing_feathers_r2 = feathers_3_wing1.addOrReplaceChild("wing_feathers_r2", CubeListBuilder.create().texOffs(60, 26).addBox(-1.0F, -2.0F, 0.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition wing_2 = body_additions.addOrReplaceChild("wing_2", CubeListBuilder.create(), PartPose.offsetAndRotation(8.2F, -4.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_2 = wing_2.addOrReplaceChild("z_rot_wing_2", CubeListBuilder.create(), PartPose.offset(-3.0F, -1.0F, 2.0F));

        PartDefinition wing_r2 = z_rot_wing_2.addOrReplaceChild("wing_r2", CubeListBuilder.create().texOffs(12, 40).mirror().addBox(-1.0F, -8.0F, -4.0F, 2.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 7.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition feathers_1_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_1_wing2", CubeListBuilder.create().texOffs(60, 20).mirror().addBox(1.5F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 3.0F, -2.0F));

        PartDefinition feathers_2_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_2_wing2", CubeListBuilder.create(), PartPose.offset(1.0F, 6.0F, -2.0F));

        PartDefinition wing_feathers_r3 = feathers_2_wing2.addOrReplaceChild("wing_feathers_r3", CubeListBuilder.create().texOffs(60, 16).mirror().addBox(1.25F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_3_wing2", CubeListBuilder.create(), PartPose.offset(1.0F, 9.0F, -2.0F));

        PartDefinition wing_feathers_r4 = feathers_3_wing2.addOrReplaceChild("wing_feathers_r4", CubeListBuilder.create().texOffs(60, 14).mirror().addBox(1.0F, -2.0F, 0.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition leg1 = base.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition base_leg1 = leg1.addOrReplaceChild("base_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r1 = base_leg1.addOrReplaceChild("upper_leg1_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg1 = base_leg1.addOrReplaceChild("upper_fluff_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r1 = upper_fluff_leg1.addOrReplaceChild("fluff_leg1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg1 = base_leg1.addOrReplaceChild("nail_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r1 = nail_leg1.addOrReplaceChild("upper_nail_leg1_r1", CubeListBuilder.create().texOffs(20, 27).addBox(0.0F, 10.0F, 2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg1 = base_leg1.addOrReplaceChild("lower_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r1 = lower_leg1.addOrReplaceChild("boots_leg1_r1", CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg1 = lower_leg1.addOrReplaceChild("toes_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 11.25F, 2.0F));

        PartDefinition toes_stepanim_leg1 = toes_leg1.addOrReplaceChild("toes_stepanim_leg1", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -6.25F, -12.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(-0.5F, -6.25F, -10.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe1_leg1_r1", CubeListBuilder.create().texOffs(16, 21).addBox(0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.75F, -7.0F, 0.0F, 0.3491F, 0.1745F));

        PartDefinition toe3_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe3_leg1_r1", CubeListBuilder.create().texOffs(16, 21).addBox(-0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.75F, -7.0F, 0.0F, -0.3491F, -0.1745F));

        PartDefinition back_toes_leg1 = toes_stepanim_leg1.addOrReplaceChild("back_toes_leg1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.25F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -6.0F));

        PartDefinition leg2 = base.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition base_leg2 = leg2.addOrReplaceChild("base_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r2 = base_leg2.addOrReplaceChild("upper_leg1_r2", CubeListBuilder.create().texOffs(32, 12).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg2 = base_leg2.addOrReplaceChild("upper_fluff_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r2 = upper_fluff_leg2.addOrReplaceChild("fluff_leg1_r2", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg2 = base_leg2.addOrReplaceChild("nail_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r2 = nail_leg2.addOrReplaceChild("upper_nail_leg1_r2", CubeListBuilder.create().texOffs(52, 27).mirror().addBox(0.0F, 10.0F, 2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg2 = base_leg2.addOrReplaceChild("lower_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r2 = lower_leg2.addOrReplaceChild("boots_leg1_r2", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 12).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg2 = lower_leg2.addOrReplaceChild("toes_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 11.25F, 2.0F));

        PartDefinition toes_stepanim_leg2 = toes_leg2.addOrReplaceChild("toes_stepanim_leg2", CubeListBuilder.create().texOffs(32, 2).mirror().addBox(-8.0F, -6.25F, -12.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(50, 0).mirror().addBox(-8.5F, -6.25F, -10.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe1_leg1_r2", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -3.75F, -7.0F, 0.0F, -0.3491F, -0.1745F));

        PartDefinition toe3_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe3_leg1_r2", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.5F, -3.75F, -7.0F, 0.0F, 0.3491F, 0.1745F));

        PartDefinition back_toes_leg2 = toes_stepanim_leg2.addOrReplaceChild("back_toes_leg2", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-8.0F, -0.25F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -5.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }



    public static LayerDefinition createTalonsLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(4.0F, 7.0F, 2.0F));

        PartDefinition body_additions = base.addOrReplaceChild("body_additions", CubeListBuilder.create(), PartPose.offset(-4.0F, 1.0F, -2.0F));

        PartDefinition head = body_additions.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, -10.0F));

        PartDefinition neck_hurtanim = head.addOrReplaceChild("neck_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 2.0F));

        PartDefinition neck = neck_hurtanim.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck_piece = neck.addOrReplaceChild("neck_piece", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition neck_piece_r1 = neck_piece.addOrReplaceChild("neck_piece_r1", CubeListBuilder.create().texOffs(88, 24).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -2.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition head_hurtanim = neck.addOrReplaceChild("head_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -3.0F));

        PartDefinition head_part = head_hurtanim.addOrReplaceChild("head_part", CubeListBuilder.create().texOffs(80, 0).addBox(-4.0F, -8.0F, -12.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition feathers = head_part.addOrReplaceChild("feathers", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition head_feather_top = feathers.addOrReplaceChild("head_feather_top", CubeListBuilder.create().texOffs(108, 3).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 4.0F));

        PartDefinition head_feather_left = feathers.addOrReplaceChild("head_feather_left", CubeListBuilder.create().texOffs(80, -8).addBox(0.0F, -5.0F, 0.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 4.0F));

        PartDefinition head_feather_right = feathers.addOrReplaceChild("head_feather_right", CubeListBuilder.create().texOffs(80, 0).mirror().addBox(0.0F, -5.0F, 0.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -2.0F, 4.0F));

        PartDefinition jaw = head_part.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(92, 30).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(-0.1F))
                .texOffs(92, 44).addBox(-3.0F, 0.0F, -10.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition tail_feathers = body_additions.addOrReplaceChild("tail_feathers", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition middle_feather = tail_feathers.addOrReplaceChild("middle_feather", CubeListBuilder.create(), PartPose.offset(0.0F, -26.0F, 10.0F));

        PartDefinition tail1_r1 = middle_feather.addOrReplaceChild("tail1_r1", CubeListBuilder.create().texOffs(102, 6).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition right_feather = tail_feathers.addOrReplaceChild("right_feather", CubeListBuilder.create(), PartPose.offset(-4.0F, -26.0F, 10.0F));

        PartDefinition tail2_r1 = right_feather.addOrReplaceChild("tail2_r1", CubeListBuilder.create().texOffs(66, 54).addBox(-5.0F, 0.0F, 0.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.2618F, -0.5236F, 0.0F));

        PartDefinition left_feather = tail_feathers.addOrReplaceChild("left_feather", CubeListBuilder.create(), PartPose.offset(-4.0F, -26.0F, 10.0F));

        PartDefinition tail3_r1 = left_feather.addOrReplaceChild("tail3_r1", CubeListBuilder.create().texOffs(54, 54).addBox(-1.0F, 0.0F, 0.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition wing_1 = body_additions.addOrReplaceChild("wing_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.2F, -4.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_1 = wing_1.addOrReplaceChild("z_rot_wing_1", CubeListBuilder.create(), PartPose.offset(3.0F, -1.0F, 2.0F));

        PartDefinition wing_r1 = z_rot_wing_1.addOrReplaceChild("wing_r1", CubeListBuilder.create().texOffs(44, 40).addBox(-1.0F, -8.0F, -4.0F, 2.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition feathers_1_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_1_wing1", CubeListBuilder.create().texOffs(60, 32).addBox(-1.5F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 3.0F, -2.0F));

        PartDefinition feathers_2_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_2_wing1", CubeListBuilder.create(), PartPose.offset(-1.0F, 6.0F, -2.0F));

        PartDefinition wing_feathers_r1 = feathers_2_wing1.addOrReplaceChild("wing_feathers_r1", CubeListBuilder.create().texOffs(60, 28).addBox(-1.25F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_3_wing1", CubeListBuilder.create(), PartPose.offset(-1.0F, 9.0F, -2.0F));

        PartDefinition wing_feathers_r2 = feathers_3_wing1.addOrReplaceChild("wing_feathers_r2", CubeListBuilder.create().texOffs(60, 26).addBox(-1.0F, -2.0F, 0.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition wing_2 = body_additions.addOrReplaceChild("wing_2", CubeListBuilder.create(), PartPose.offsetAndRotation(8.2F, -4.0F, -4.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_2 = wing_2.addOrReplaceChild("z_rot_wing_2", CubeListBuilder.create(), PartPose.offset(-3.0F, -1.0F, 2.0F));

        PartDefinition wing_r2 = z_rot_wing_2.addOrReplaceChild("wing_r2", CubeListBuilder.create().texOffs(12, 40).mirror().addBox(-1.0F, -8.0F, -4.0F, 2.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 7.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition feathers_1_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_1_wing2", CubeListBuilder.create().texOffs(60, 20).mirror().addBox(1.5F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 3.0F, -2.0F));

        PartDefinition feathers_2_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_2_wing2", CubeListBuilder.create(), PartPose.offset(1.0F, 6.0F, -2.0F));

        PartDefinition wing_feathers_r3 = feathers_2_wing2.addOrReplaceChild("wing_feathers_r3", CubeListBuilder.create().texOffs(60, 16).mirror().addBox(1.25F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_3_wing2", CubeListBuilder.create(), PartPose.offset(1.0F, 9.0F, -2.0F));

        PartDefinition wing_feathers_r4 = feathers_3_wing2.addOrReplaceChild("wing_feathers_r4", CubeListBuilder.create().texOffs(60, 14).mirror().addBox(1.0F, -2.0F, 0.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition leg1 = base.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition base_leg1 = leg1.addOrReplaceChild("base_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r1 = base_leg1.addOrReplaceChild("upper_leg1_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg1 = base_leg1.addOrReplaceChild("upper_fluff_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r1 = upper_fluff_leg1.addOrReplaceChild("fluff_leg1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg1 = base_leg1.addOrReplaceChild("nail_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r1 = nail_leg1.addOrReplaceChild("upper_nail_leg1_r1", CubeListBuilder.create().texOffs(20, 27).addBox(0.0F, 10.0F, 2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg1 = base_leg1.addOrReplaceChild("lower_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r1 = lower_leg1.addOrReplaceChild("boots_leg1_r1", CubeListBuilder.create().texOffs(60, 10).addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg1 = lower_leg1.addOrReplaceChild("toes_leg1", CubeListBuilder.create(), PartPose.offset(0.525F, 9.25F, -4.0F));

        PartDefinition toes_stepanim_leg1 = toes_leg1.addOrReplaceChild("toes_stepanim_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition toe3_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe3_leg1_r1", CubeListBuilder.create().texOffs(2, 40).addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.25F, -1.0F, -0.2618F, -0.3491F, -0.1745F));

        PartDefinition toe2_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe2_leg1_r1", CubeListBuilder.create().texOffs(2, 32).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.75F, -5.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe1_leg1_r1", CubeListBuilder.create().texOffs(2, 53).mirror().addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 1.25F, -1.0F, -0.2618F, 0.3491F, 0.1745F));

        PartDefinition back_toes_leg1 = toes_stepanim_leg1.addOrReplaceChild("back_toes_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_toe_leg1_r1 = back_toes_leg1.addOrReplaceChild("back_toe_leg1_r1", CubeListBuilder.create().texOffs(13, 32).addBox(-1.0F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.75F, 4.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition leg2 = base.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition base_leg2 = leg2.addOrReplaceChild("base_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r2 = base_leg2.addOrReplaceChild("upper_leg1_r2", CubeListBuilder.create().texOffs(32, 12).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg2 = base_leg2.addOrReplaceChild("upper_fluff_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r2 = upper_fluff_leg2.addOrReplaceChild("fluff_leg1_r2", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg2 = base_leg2.addOrReplaceChild("nail_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r2 = nail_leg2.addOrReplaceChild("upper_nail_leg1_r2", CubeListBuilder.create().texOffs(52, 27).mirror().addBox(0.0F, 10.0F, 2.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg2 = base_leg2.addOrReplaceChild("lower_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r2 = lower_leg2.addOrReplaceChild("boots_leg1_r2", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 12).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg2 = lower_leg2.addOrReplaceChild("toes_leg2", CubeListBuilder.create(), PartPose.offset(-8.025F, 9.25F, -4.0F));

        PartDefinition toes_stepanim_leg2 = toes_leg2.addOrReplaceChild("toes_stepanim_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition toe3_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe3_leg1_r2", CubeListBuilder.create().texOffs(34, 53).mirror().addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 1.25F, -1.0F, -0.2618F, 0.3491F, 0.1745F));

        PartDefinition toe2_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe2_leg1_r2", CubeListBuilder.create().texOffs(34, 32).mirror().addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.75F, -5.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe1_leg1_r2", CubeListBuilder.create().texOffs(34, 40).addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.25F, -1.0F, -0.2618F, -0.3491F, -0.1745F));

        PartDefinition back_toes_leg2 = toes_stepanim_leg2.addOrReplaceChild("back_toes_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_toe_leg1_r2 = back_toes_leg2.addOrReplaceChild("back_toe_leg1_r2", CubeListBuilder.create().texOffs(45, 32).mirror().addBox(0.0F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.75F, 4.0F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }


    @Override
    public void setupAnim(Moa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
