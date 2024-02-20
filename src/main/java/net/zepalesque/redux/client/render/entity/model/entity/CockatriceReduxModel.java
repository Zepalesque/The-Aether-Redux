package net.zepalesque.redux.client.render.entity.model.entity;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CockatriceReduxModel extends EntityModel<Cockatrice> {
    public final ModelPart cockatrice;
    public final ModelPart body_base;
    public final ModelPart body;
    public final ModelPart tail_feathers;
    public final ModelPart middle_feather;
    public final ModelPart right_feather;
    public final ModelPart left_feather;
    public final ModelPart wing_1;
    public final ModelPart z_rot_wing_1;
    public final ModelPart feathers_1_wing1;
    public final ModelPart feathers_2_wing1;
    public final ModelPart feathers_3_wing1;
    public final ModelPart claw1;
    public final ModelPart wing_2;
    public final ModelPart z_rot_wing_2;
    public final ModelPart feathers_1_wing2;
    public final ModelPart feathers_2_wing2;
    public final ModelPart feathers_3_wing2;
    public final ModelPart claw2;
    public final ModelPart head_pieces;
    public final ModelPart neck_hurtanim;
    public final ModelPart neck;
    public final ModelPart head_hurtanim;
    public final ModelPart head;
    public final ModelPart head_feathers;
    public final ModelPart head_feather_top;
    public final ModelPart top_feather_2;
    public final ModelPart top_feather_1;
    public final ModelPart head_feather_right;
    public final ModelPart head_feather_left;
    public final ModelPart crown_feather;
    public final ModelPart cheek_feathers;
    public final ModelPart jaw;
    public final ModelPart jaw_natural_rotation;
    public final ModelPart lower_tail;
    public final ModelPart ribcage;
    public final ModelPart right_upper;
    public final ModelPart left_upper;
    public final ModelPart leg1;
    public final ModelPart base_leg1;
    public final ModelPart toes_leg1;
    public final ModelPart toes_stepanim_leg1;
    public final ModelPart back_toes_leg1;
    public final ModelPart upper_fluff_leg1;
    public final ModelPart feathers_leg1;
    public final ModelPart nail;
    public final ModelPart lower_leg1;
    public final ModelPart leg2;
    public final ModelPart base_leg2;
    public final ModelPart upper_fluff_leg2;
    public final ModelPart feathers_leg2;
    public final ModelPart toes_leg2;
    public final ModelPart lower_leg2;
    public final ModelPart nail2;
    public final ModelPart toes_stepanim_leg2;
    public final ModelPart back_toes_leg2;

    public CockatriceReduxModel(ModelPart root) {
        this.cockatrice = root.getChild("cockatrice");
        this.body_base = cockatrice.getChild("body_base");
        this.body = body_base.getChild("body");
        this.tail_feathers = body.getChild("tail_feathers");
        this.middle_feather = tail_feathers.getChild("middle_feather");
        this.right_feather = tail_feathers.getChild("right_feather");
        this.left_feather = tail_feathers.getChild("left_feather");
        this.wing_1 = body.getChild("wing_1");
        this.z_rot_wing_1 = wing_1.getChild("z_rot_wing_1");
        this.feathers_1_wing1 = z_rot_wing_1.getChild("feathers_1_wing1");
        this.feathers_2_wing1 = z_rot_wing_1.getChild("feathers_2_wing1");
        this.feathers_3_wing1 = z_rot_wing_1.getChild("feathers_3_wing1");
        this.claw1 = z_rot_wing_1.getChild("claw1");
        this.wing_2 = body.getChild("wing_2");
        this.z_rot_wing_2 = wing_2.getChild("z_rot_wing_2");
        this.feathers_1_wing2 = z_rot_wing_2.getChild("feathers_1_wing2");
        this.feathers_2_wing2 = z_rot_wing_2.getChild("feathers_2_wing2");
        this.feathers_3_wing2 = z_rot_wing_2.getChild("feathers_3_wing2");
        this.claw2 = z_rot_wing_2.getChild("claw2");
        this.head_pieces = body.getChild("head_pieces");
        this.neck_hurtanim = head_pieces.getChild("neck_hurtanim");
        this.neck = neck_hurtanim.getChild("neck");
        this.head_hurtanim = neck.getChild("head_hurtanim");
        this.head = head_hurtanim.getChild("head");
        this.head_feathers = head.getChild("head_feathers");
        this.head_feather_top = head_feathers.getChild("head_feather_top");
        this.top_feather_2 = head_feather_top.getChild("top_feather_2");
        this.top_feather_1 = head_feather_top.getChild("top_feather_1");
        this.head_feather_right = head_feathers.getChild("head_feather_right");
        this.head_feather_left = head_feathers.getChild("head_feather_left");
        this.crown_feather = head_feathers.getChild("crown_feather");
        this.cheek_feathers = head.getChild("cheek_feathers");
        this.jaw = head.getChild("jaw");
        this.jaw_natural_rotation = jaw.getChild("jaw_natural_rotation");
        this.lower_tail = body.getChild("lower_tail");
        this.ribcage = body.getChild("ribcage");
        this.right_upper = ribcage.getChild("right_upper");
        this.left_upper = ribcage.getChild("left_upper");
        this.leg1 = cockatrice.getChild("leg1");
        this.base_leg1 = leg1.getChild("base_leg1");
        this.upper_fluff_leg1 = base_leg1.getChild("upper_fluff_leg1");
        this.feathers_leg1 = base_leg1.getChild("feathers_leg1");
        this.nail = base_leg1.getChild("nail");
        this.lower_leg1 = base_leg1.getChild("lower_leg1");
        this.toes_leg1 = lower_leg1.getChild("toes_leg1");
        this.toes_stepanim_leg1 = toes_leg1.getChild("toes_stepanim_leg1");
        this.back_toes_leg1 = toes_stepanim_leg1.getChild("back_toes_leg1");
        this.leg2 = cockatrice.getChild("leg2");
        this.base_leg2 = leg2.getChild("base_leg2");
        this.upper_fluff_leg2 = base_leg2.getChild("upper_fluff_leg2");
        this.feathers_leg2 = base_leg2.getChild("feathers_leg2");
        this.nail2 = base_leg2.getChild("nail2");
        this.lower_leg2 = base_leg2.getChild("lower_leg2");
        this.toes_leg2 = lower_leg2.getChild("toes_leg2");
        this.toes_stepanim_leg2 = toes_leg2.getChild("toes_stepanim_leg2");
        this.back_toes_leg2 = toes_stepanim_leg2.getChild("back_toes_leg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cockatrice = partdefinition.addOrReplaceChild("cockatrice", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_base = cockatrice.addOrReplaceChild("body_base", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition body = body_base.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 5.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 88).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition head_pieces = body.addOrReplaceChild("head_pieces", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, -15.0F));

        PartDefinition neck_hurtanim = head_pieces.addOrReplaceChild("neck_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

        PartDefinition neck = neck_hurtanim.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition neck_piece_r1 = neck.addOrReplaceChild("neck_piece_r1", CubeListBuilder.create().texOffs(88, 24).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -2.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition head_hurtanim = neck.addOrReplaceChild("head_hurtanim", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -3.0F));

        PartDefinition head = head_hurtanim.addOrReplaceChild("head", CubeListBuilder.create().texOffs(80, 0).addBox(-4.0F, -7.0F, -13.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_feathers = head.addOrReplaceChild("head_feathers", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, -1.0F));

        PartDefinition head_feather_top = head_feathers.addOrReplaceChild("head_feather_top", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 4.0F));

        PartDefinition feathers_top_r1 = head_feather_top.addOrReplaceChild("feathers_top_r1", CubeListBuilder.create().texOffs(88, 48).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition top_feather_1 = head_feather_top.addOrReplaceChild("top_feather_1", CubeListBuilder.create(), PartPose.offset(-1.0F, -5.0F, 0.0F));

        PartDefinition feather_r1 = top_feather_1.addOrReplaceChild("feather_r1", CubeListBuilder.create().texOffs(122, 34).addBox(-4.0F, -8.0F, 0.0F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.0F, 0.0F, -1.5272F, 0.0F, 0.0F));

        PartDefinition top_feather_2 = head_feather_top.addOrReplaceChild("top_feather_2", CubeListBuilder.create(), PartPose.offset(1.0F, -5.0F, 0.0F));

        PartDefinition feather_r2 = top_feather_2.addOrReplaceChild("feather_r2", CubeListBuilder.create().texOffs(116, 34).addBox(0.0F, -8.0F, 0.0F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -1.5272F, 0.0F, 0.0F));

        PartDefinition head_feather_left = head_feathers.addOrReplaceChild("head_feather_left", CubeListBuilder.create(), PartPose.offset(4.0F, -2.0F, 4.0F));

        PartDefinition feathers_r1 = head_feather_left.addOrReplaceChild("feathers_r1", CubeListBuilder.create().texOffs(80, -7).addBox(0.0F, -4.0F, 0.0F, 0.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition head_feather_right = head_feathers.addOrReplaceChild("head_feather_right", CubeListBuilder.create(), PartPose.offset(-4.0F, -2.0F, 4.0F));

        PartDefinition feathers_r2 = head_feather_right.addOrReplaceChild("feathers_r2", CubeListBuilder.create().texOffs(80, 1).mirror().addBox(0.0F, -4.0F, 0.0F, 0.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition crown_feather = head_feathers.addOrReplaceChild("crown_feather", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition feather_r3 = crown_feather.addOrReplaceChild("feather_r3", CubeListBuilder.create().texOffs(115, 0).addBox(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, -2.0F));

        PartDefinition jaw_natural_rotation = jaw.addOrReplaceChild("jaw_natural_rotation", CubeListBuilder.create().texOffs(116, 44).addBox(-3.0F, 8.0F, -10.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(92, 30).addBox(-3.0F, 6.0F, -10.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(-0.1F))
                .texOffs(92, 44).addBox(-3.0F, 6.0F, -10.0F, 6.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cheek_feathers = head.addOrReplaceChild("cheek_feathers", CubeListBuilder.create().texOffs(0, 116).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 1.0F, -3.0F));

        PartDefinition tail_feathers = body.addOrReplaceChild("tail_feathers", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, -5.0F));

        PartDefinition middle_feather = tail_feathers.addOrReplaceChild("middle_feather", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 10.0F));

        PartDefinition feather_r4 = middle_feather.addOrReplaceChild("feather_r4", CubeListBuilder.create().texOffs(103, 6).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9599F, 0.0F, 0.0F));

        PartDefinition right_feather = tail_feathers.addOrReplaceChild("right_feather", CubeListBuilder.create(), PartPose.offset(-2.0F, -18.0F, 10.0F));

        PartDefinition feather_r5 = right_feather.addOrReplaceChild("feather_r5", CubeListBuilder.create().texOffs(57, 56).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, -0.5236F, 0.0F));

        PartDefinition left_feather = tail_feathers.addOrReplaceChild("left_feather", CubeListBuilder.create(), PartPose.offset(2.0F, -18.0F, 10.0F));

        PartDefinition feather_r6 = left_feather.addOrReplaceChild("feather_r6", CubeListBuilder.create().texOffs(69, 56).addBox(0.0F, 0.0F, 0.0F, 4.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.5236F, 0.0F));

        PartDefinition lower_tail = body.addOrReplaceChild("lower_tail", CubeListBuilder.create().texOffs(74, 102).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 5.0F));

        PartDefinition wing_1 = body.addOrReplaceChild("wing_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.2F, 4.0F, -9.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_1 = wing_1.addOrReplaceChild("z_rot_wing_1", CubeListBuilder.create(), PartPose.offset(3.0F, -2.0F, 2.0F));

        PartDefinition wing_r1 = z_rot_wing_1.addOrReplaceChild("wing_r1", CubeListBuilder.create().texOffs(44, 40).addBox(-1.0F, -8.0F, -4.0F, 2.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition feathers_1_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_1_wing1", CubeListBuilder.create().texOffs(60, 32).addBox(-1.5F, -2.0F, -1.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 4.0F, -1.0F));

        PartDefinition feathers_2_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_2_wing1", CubeListBuilder.create(), PartPose.offset(-1.0F, 9.0F, -2.0F));

        PartDefinition wing_feathers_r1 = feathers_2_wing1.addOrReplaceChild("wing_feathers_r1", CubeListBuilder.create().texOffs(60, 28).addBox(-1.5F, -3.0F, -2.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing1 = z_rot_wing_1.addOrReplaceChild("feathers_3_wing1", CubeListBuilder.create(), PartPose.offset(-1.0F, 11.0F, -1.0F));

        PartDefinition wing_feathers_r2 = feathers_3_wing1.addOrReplaceChild("wing_feathers_r2", CubeListBuilder.create().texOffs(60, 26).addBox(-1.5F, -2.0F, -13.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 9.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition claw1 = z_rot_wing_1.addOrReplaceChild("claw1", CubeListBuilder.create(), PartPose.offset(2.0F, 14.0F, -1.5F));

        PartDefinition claw_r1 = claw1.addOrReplaceChild("claw_r1", CubeListBuilder.create().texOffs(59, 7).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, -1.5708F));

        PartDefinition wing_2 = body.addOrReplaceChild("wing_2", CubeListBuilder.create(), PartPose.offsetAndRotation(8.2F, 4.0F, -9.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition z_rot_wing_2 = wing_2.addOrReplaceChild("z_rot_wing_2", CubeListBuilder.create(), PartPose.offset(-3.0F, -2.0F, 2.0F));

        PartDefinition wing_r2 = z_rot_wing_2.addOrReplaceChild("wing_r2", CubeListBuilder.create().texOffs(12, 40).mirror().addBox(-1.0F, -8.0F, -4.0F, 2.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition feathers_1_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_1_wing2", CubeListBuilder.create().texOffs(60, 20).mirror().addBox(1.5F, -2.0F, -1.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 4.0F, -1.0F));

        PartDefinition feathers_2_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_2_wing2", CubeListBuilder.create(), PartPose.offset(1.0F, 9.0F, -2.0F));

        PartDefinition wing_feathers_r3 = feathers_2_wing2.addOrReplaceChild("wing_feathers_r3", CubeListBuilder.create().texOffs(60, 16).mirror().addBox(1.5F, -3.0F, -2.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition feathers_3_wing2 = z_rot_wing_2.addOrReplaceChild("feathers_3_wing2", CubeListBuilder.create(), PartPose.offset(1.0F, 11.0F, -1.0F));

        PartDefinition wing_feathers_r4 = feathers_3_wing2.addOrReplaceChild("wing_feathers_r4", CubeListBuilder.create().texOffs(60, 14).mirror().addBox(1.5F, -2.0F, -13.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, 9.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition claw2 = z_rot_wing_2.addOrReplaceChild("claw2", CubeListBuilder.create(), PartPose.offset(-2.0F, 14.0F, -1.5F));

        PartDefinition claw_r2 = claw2.addOrReplaceChild("claw_r2", CubeListBuilder.create().texOffs(59, 0).mirror().addBox(-2.5F, 0.0F, -3.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 1.5708F));

        PartDefinition ribcage = body.addOrReplaceChild("ribcage", CubeListBuilder.create().texOffs(14, 69).addBox(-6.0F, -1.5F, -4.51F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -6.5F));

        PartDefinition right_upper = ribcage.addOrReplaceChild("right_upper", CubeListBuilder.create(), PartPose.offset(-5.0F, -23.5F, -6.5F));

        PartDefinition rib1_r1 = right_upper.addOrReplaceChild("rib1_r1", CubeListBuilder.create().texOffs(116, 66).addBox(0.0F, -4.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 26.0F, 2.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition left_upper = ribcage.addOrReplaceChild("left_upper", CubeListBuilder.create(), PartPose.offset(5.0F, -5.5F, -4.5F));

        PartDefinition rib2_r1 = left_upper.addOrReplaceChild("rib2_r1", CubeListBuilder.create().texOffs(116, 58).addBox(-6.0F, -4.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition leg1 = cockatrice.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(4.025F, -17.0F, 2.0F));

        PartDefinition base_leg1 = leg1.addOrReplaceChild("base_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r1 = base_leg1.addOrReplaceChild("upper_leg1_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg1 = base_leg1.addOrReplaceChild("upper_fluff_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r1 = upper_fluff_leg1.addOrReplaceChild("fluff_leg1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition feathers_leg1 = base_leg1.addOrReplaceChild("feathers_leg1", CubeListBuilder.create().texOffs(0, 85).addBox(-2.0F, 0.0F, 0.05F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.5F, 7.7F));

        PartDefinition nail = base_leg1.addOrReplaceChild("nail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 9.4F, 7.75F, 0.1745F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r2 = nail.addOrReplaceChild("upper_leg1_r2", CubeListBuilder.create().texOffs(20, 26).addBox(0.0F, 8.0F, 2.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.4F, -7.75F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg1 = base_leg1.addOrReplaceChild("lower_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition lower_piece_leg1_r1 = lower_leg1.addOrReplaceChild("lower_piece_leg1_r1", CubeListBuilder.create().texOffs(16, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg1 = lower_leg1.addOrReplaceChild("toes_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 9.25F, -4.0F));

        PartDefinition toes_stepanim_leg1 = toes_leg1.addOrReplaceChild("toes_stepanim_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition toe3_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe3_leg1_r1", CubeListBuilder.create().texOffs(2, 40).addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.25F, -1.0F, -0.2618F, -0.3491F, -0.1745F));

        PartDefinition toe2_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe2_leg1_r1", CubeListBuilder.create().texOffs(2, 32).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.75F, -5.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe1_leg1_r1", CubeListBuilder.create().texOffs(2, 53).mirror().addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 1.25F, -1.0F, -0.2618F, 0.3491F, 0.1745F));

        PartDefinition back_toes_leg1 = toes_stepanim_leg1.addOrReplaceChild("back_toes_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_toe_leg1_r1 = back_toes_leg1.addOrReplaceChild("back_toe_leg1_r1", CubeListBuilder.create().texOffs(13, 32).addBox(-1.0F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.75F, 4.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition leg2 = cockatrice.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(-4.025F, -17.0F, 2.0F));

        PartDefinition base_leg2 = leg2.addOrReplaceChild("base_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r3 = base_leg2.addOrReplaceChild("upper_leg1_r3", CubeListBuilder.create().texOffs(32, 12).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg2 = base_leg2.addOrReplaceChild("upper_fluff_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r2 = upper_fluff_leg2.addOrReplaceChild("fluff_leg1_r2", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition feathers_leg2 = base_leg2.addOrReplaceChild("feathers_leg2", CubeListBuilder.create().texOffs(0, 92).mirror().addBox(-2.0F, 0.0F, 0.05F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 10.5F, 7.7F));

        PartDefinition nail2 = base_leg2.addOrReplaceChild("nail2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 9.4F, 7.75F, 0.1745F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r4 = nail2.addOrReplaceChild("upper_leg1_r4", CubeListBuilder.create().texOffs(52, 26).mirror().addBox(0.0F, 8.0F, 2.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -9.4F, -7.75F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg2 = base_leg2.addOrReplaceChild("lower_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition lower_piece_leg1_r2 = lower_leg2.addOrReplaceChild("lower_piece_leg1_r2", CubeListBuilder.create().texOffs(48, 12).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg2 = lower_leg2.addOrReplaceChild("toes_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 9.25F, -4.0F));

        PartDefinition toes_stepanim_leg2 = toes_leg2.addOrReplaceChild("toes_stepanim_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition toe3_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe3_leg1_r2", CubeListBuilder.create().texOffs(34, 53).mirror().addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 1.25F, -1.0F, -0.2618F, 0.3491F, 0.1745F));

        PartDefinition toe2_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe2_leg1_r2", CubeListBuilder.create().texOffs(34, 32).mirror().addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.75F, -5.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe1_leg1_r2", CubeListBuilder.create().texOffs(34, 40).addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.25F, -1.0F, -0.2618F, -0.3491F, -0.1745F));

        PartDefinition back_toes_leg2 = toes_stepanim_leg2.addOrReplaceChild("back_toes_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_toe_leg1_r2 = back_toes_leg2.addOrReplaceChild("back_toe_leg1_r2", CubeListBuilder.create().texOffs(45, 32).mirror().addBox(0.0F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.75F, 4.0F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Cockatrice entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        cockatrice.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}