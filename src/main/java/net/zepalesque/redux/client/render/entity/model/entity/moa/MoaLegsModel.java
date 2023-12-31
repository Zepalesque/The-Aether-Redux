package net.zepalesque.redux.client.render.entity.model.entity.moa;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;


// TODO: Merge with other moa parts, maybe include body
public class MoaLegsModel extends EntityModel<Moa> {
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

    public MoaLegsModel(ModelPart root) {
        this.leg1 = root.getChild("leg1");
        this.base_leg1 = leg1.getChild("base_leg1");
        this.upper_fluff_leg1 = base_leg1.getChild("upper_fluff_leg1");
        this.nail_leg1 = base_leg1.getChild("nail_leg1");
        this.lower_leg1 = base_leg1.getChild("lower_leg1");
        this.toes_leg1 = lower_leg1.getChild("toes_leg1");
        this.toes_stepanim_leg1 = toes_leg1.getChild("toes_stepanim_leg1");
        this.back_toes_leg1 = toes_stepanim_leg1.getChild("back_toes_leg1");
        this.leg2 = root.getChild("leg2");
        this.base_leg2 = leg2.getChild("base_leg2");
        this.upper_fluff_leg2 = base_leg2.getChild("upper_fluff_leg2");
        this.nail_leg2 = base_leg2.getChild("nail_leg2");
        this.lower_leg2 = base_leg2.getChild("lower_leg2");
        this.toes_leg2 = lower_leg2.getChild("toes_leg2");
        this.toes_stepanim_leg2 = toes_leg2.getChild("toes_stepanim_leg2");
        this.back_toes_leg2 = toes_stepanim_leg2.getChild("back_toes_leg2");
    }


    public static LayerDefinition createToesLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(4.0F, 7.0F, 2.0F));

        PartDefinition base_leg1 = leg1.addOrReplaceChild("base_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r1 = base_leg1.addOrReplaceChild("upper_leg1_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg1 = base_leg1.addOrReplaceChild("upper_fluff_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r1 = upper_fluff_leg1.addOrReplaceChild("fluff_leg1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg1 = base_leg1.addOrReplaceChild("nail_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r1 = nail_leg1.addOrReplaceChild("upper_nail_leg1_r1", CubeListBuilder.create().texOffs(12, 25).addBox(0.0F, 9.0F, 2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg1 = base_leg1.addOrReplaceChild("lower_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r1 = lower_leg1.addOrReplaceChild("boots_leg1_r1", CubeListBuilder.create().texOffs(0, 118).addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg1 = lower_leg1.addOrReplaceChild("toes_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 11.25F, 2.0F));

        PartDefinition toes_stepanim_leg1 = toes_leg1.addOrReplaceChild("toes_stepanim_leg1", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -6.25F, -12.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(-0.5F, -6.25F, -10.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe1_leg1_r1", CubeListBuilder.create().texOffs(16, 21).addBox(0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.75F, -7.0F, 0.0F, 0.3491F, 0.1745F));

        PartDefinition toe3_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe3_leg1_r1", CubeListBuilder.create().texOffs(16, 21).addBox(-0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.75F, -7.0F, 0.0F, -0.3491F, -0.1745F));

        PartDefinition back_toes_leg1 = toes_stepanim_leg1.addOrReplaceChild("back_toes_leg1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.25F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -6.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(4.0F, 7.0F, 2.0F));

        PartDefinition base_leg2 = leg2.addOrReplaceChild("base_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r2 = base_leg2.addOrReplaceChild("upper_leg1_r2", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg2 = base_leg2.addOrReplaceChild("upper_fluff_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r2 = upper_fluff_leg2.addOrReplaceChild("fluff_leg1_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg2 = base_leg2.addOrReplaceChild("nail_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r2 = nail_leg2.addOrReplaceChild("upper_nail_leg1_r2", CubeListBuilder.create().texOffs(12, 25).mirror().addBox(0.0F, 9.0F, 2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg2 = base_leg2.addOrReplaceChild("lower_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r2 = lower_leg2.addOrReplaceChild("boots_leg1_r2", CubeListBuilder.create().texOffs(0, 118).mirror().addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(16, 12).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg2 = lower_leg2.addOrReplaceChild("toes_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 11.25F, 2.0F));

        PartDefinition toes_stepanim_leg2 = toes_leg2.addOrReplaceChild("toes_stepanim_leg2", CubeListBuilder.create().texOffs(0, 2).mirror().addBox(-8.0F, -6.25F, -12.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(18, 0).mirror().addBox(-8.5F, -6.25F, -10.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe1_leg1_r2", CubeListBuilder.create().texOffs(16, 21).mirror().addBox(-0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -3.75F, -7.0F, 0.0F, -0.3491F, -0.1745F));

        PartDefinition toe3_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe3_leg1_r2", CubeListBuilder.create().texOffs(16, 21).mirror().addBox(0.5F, -1.5F, -3.5F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.5F, -3.75F, -7.0F, 0.0F, 0.3491F, 0.1745F));

        PartDefinition back_toes_leg2 = toes_stepanim_leg2.addOrReplaceChild("back_toes_leg2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, -0.25F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -5.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    public static LayerDefinition createTalonsLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(4.5F, 7.0F, 2.0F));

        PartDefinition base_leg1 = leg1.addOrReplaceChild("base_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r1 = base_leg1.addOrReplaceChild("upper_leg1_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg1 = base_leg1.addOrReplaceChild("upper_fluff_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r1 = upper_fluff_leg1.addOrReplaceChild("fluff_leg1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg1 = base_leg1.addOrReplaceChild("nail_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r1 = nail_leg1.addOrReplaceChild("upper_nail_leg1_r1", CubeListBuilder.create().texOffs(12, 25).addBox(0.0F, 9.0F, 2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg1 = base_leg1.addOrReplaceChild("lower_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r1 = lower_leg1.addOrReplaceChild("boots_leg1_r1", CubeListBuilder.create().texOffs(0, 118).addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg1 = lower_leg1.addOrReplaceChild("toes_leg1", CubeListBuilder.create(), PartPose.offset(0.025F, 9.25F, -4.0F));

        PartDefinition toes_stepanim_leg1 = toes_leg1.addOrReplaceChild("toes_stepanim_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition toe3_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe3_leg1_r1", CubeListBuilder.create().texOffs(28, 66).addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.25F, -1.0F, -0.2618F, -0.3491F, -0.1745F));

        PartDefinition toe2_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe2_leg1_r1", CubeListBuilder.create().texOffs(28, 58).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.75F, -5.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r1 = toes_stepanim_leg1.addOrReplaceChild("toe1_leg1_r1", CubeListBuilder.create().texOffs(28, 66).mirror().addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 1.25F, -1.0F, -0.2618F, 0.3491F, 0.1745F));

        PartDefinition back_toes_leg1 = toes_stepanim_leg1.addOrReplaceChild("back_toes_leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_toe_leg1_r1 = back_toes_leg1.addOrReplaceChild("back_toe_leg1_r1", CubeListBuilder.create().texOffs(28, 79).addBox(-1.0F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.75F, 4.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(-4.5F, 7.0F, 2.0F));

        PartDefinition base_leg2 = leg2.addOrReplaceChild("base_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upper_leg1_r2 = base_leg2.addOrReplaceChild("upper_leg1_r2", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition upper_fluff_leg2 = base_leg2.addOrReplaceChild("upper_fluff_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.25F, 1.0F, -0.829F, 0.0F, 0.0F));

        PartDefinition fluff_leg1_r2 = upper_fluff_leg2.addOrReplaceChild("fluff_leg1_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.25F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition nail_leg2 = base_leg2.addOrReplaceChild("nail_leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.25F, 8.25F, 0.2182F, 0.0F, 0.0F));

        PartDefinition upper_nail_leg1_r2 = nail_leg2.addOrReplaceChild("upper_nail_leg1_r2", CubeListBuilder.create().texOffs(12, 25).mirror().addBox(0.0F, 9.0F, 2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -10.25F, -8.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition lower_leg2 = base_leg2.addOrReplaceChild("lower_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 5.0F));

        PartDefinition boots_leg1_r2 = lower_leg2.addOrReplaceChild("boots_leg1_r2", CubeListBuilder.create().texOffs(0, 118).mirror().addBox(-2.0F, 4.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(16, 12).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition toes_leg2 = lower_leg2.addOrReplaceChild("toes_leg2", CubeListBuilder.create(), PartPose.offset(-0.025F, 9.25F, -4.0F));

        PartDefinition toes_stepanim_leg2 = toes_leg2.addOrReplaceChild("toes_stepanim_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition toe3_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe3_leg1_r2", CubeListBuilder.create().texOffs(28, 66).mirror().addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 1.25F, -1.0F, -0.2618F, 0.3491F, 0.1745F));

        PartDefinition toe2_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe2_leg1_r2", CubeListBuilder.create().texOffs(28, 58).mirror().addBox(-0.5F, -3.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.75F, -5.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition toe1_leg1_r2 = toes_stepanim_leg2.addOrReplaceChild("toe1_leg1_r2", CubeListBuilder.create().texOffs(28, 66).addBox(-0.5F, -1.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 1.25F, -1.0F, -0.2618F, -0.3491F, -0.1745F));

        PartDefinition back_toes_leg2 = toes_stepanim_leg2.addOrReplaceChild("back_toes_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_toe_leg1_r2 = back_toes_leg2.addOrReplaceChild("back_toe_leg1_r2", CubeListBuilder.create().texOffs(28, 79).mirror().addBox(0.0F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.75F, 4.0F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(Moa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}