package net.zepalesque.redux.client.render.entity.model.entity.sheepuff;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class ConfiguredSheepuffModel extends AbstractSheepuffModel {


    public final ModelPart string1_side1;
    public final ModelPart string2_side1;
    public final ModelPart string1_side2;
    public final ModelPart string2_side2;
    public final ModelPart hanging_object1;
    public final ModelPart hanging_object2;
    public final ModelPart alpha_horns;
    public final ModelPart ears;
    public final ModelPart kirrid_horn;
    public final ModelPart sheepuff_horn;
    public final ModelPart horn1;
    public final ModelPart horn2;



    public ConfiguredSheepuffModel(ModelPart root) {
        super(root);
        this.string1_side1 = head.getChild("string1_side1");
        this.string2_side1 = head.getChild("string2_side1");
        this.string1_side2 = head.getChild("string1_side2");
        this.string2_side2 = head.getChild("string2_side2");
        this.hanging_object1 = head.getChild("hanging_object1");
        this.hanging_object2 = head.getChild("hanging_object2");
        this.alpha_horns = head_baserot.getChild("alpha_horns");
        this.ears = head_baserot.getChild("ears");
        this.kirrid_horn = head_baserot.getChild("kirrid_horn");
        this.sheepuff_horn = head_baserot.getChild("sheepuff_horn");
        this.horn1 = head_baserot.getChild("horn1");
        this.horn2 = head_baserot.getChild("horn2");
    }

    @Override
    public void setupAnim(Entity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hurtanim_base = partdefinition.addOrReplaceChild("hurtanim_base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 6.5F));

        PartDefinition main_body = hurtanim_base.addOrReplaceChild("main_body", CubeListBuilder.create().texOffs(0, 18).addBox(-4.5F, -6.5F, -15.5F, 9.0F, 9.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        PartDefinition leg1 = main_body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(20, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 22).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(0, 22).addBox(-1.0F, 3.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 2.0F, -12.0F));

        PartDefinition leg2 = main_body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(8, 22).mirror().addBox(-1.0F, 3.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
                .texOffs(0, 22).mirror().addBox(-1.0F, 3.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 0).mirror().addBox(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.5F, 2.0F, -12.0F));

        PartDefinition leg3 = main_body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(90, 49).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(82, 49).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(106, 49).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 0.0F, 0.0F));

        PartDefinition leg4 = main_body.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(90, 49).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
                .texOffs(106, 49).mirror().addBox(-2.0F, -3.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(82, 49).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition head = main_body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -16.5F));

        PartDefinition head_baserot = head.addOrReplaceChild("head_baserot", CubeListBuilder.create().texOffs(44, 5).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition alpha_horns = head_baserot.addOrReplaceChild("alpha_horns", CubeListBuilder.create().texOffs(46, 21).addBox(3.02F, -3.0F, -3.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(46, 21).mirror().addBox(-6.02F, -3.0F, -3.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ears = head_baserot.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ear2_r1 = ears.addOrReplaceChild("ear2_r1", CubeListBuilder.create().texOffs(37, 4).mirror().addBox(0.0F, 0.5F, -0.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, -2.0F, -2.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition ear1_r1 = ears.addOrReplaceChild("ear1_r1", CubeListBuilder.create().texOffs(37, 4).addBox(-1.0F, 0.5F, -0.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -2.0F, -2.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition kirrid_horn = head_baserot.addOrReplaceChild("kirrid_horn", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition kirrid_plating_r1 = kirrid_horn.addOrReplaceChild("kirrid_plating_r1", CubeListBuilder.create().texOffs(63, 0).addBox(-3.0F, -8.0F, 0.0F, 6.0F, 8.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -2.0F, -4.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition sheepuff_horn = head_baserot.addOrReplaceChild("sheepuff_horn", CubeListBuilder.create().texOffs(47, 0).addBox(-3.0F, -5.0F, -4.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition horn1 = head_baserot.addOrReplaceChild("horn1", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -2.75F, -2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition horn_r1 = horn1.addOrReplaceChild("horn_r1", CubeListBuilder.create().texOffs(1, 17).mirror().addBox(0.0F, -0.5F, -1.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, -0.5F, 0.0F, 0.0F, 0.8727F));

        PartDefinition horn2 = head_baserot.addOrReplaceChild("horn2", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -2.75F, -2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition horn_r2 = horn2.addOrReplaceChild("horn_r2", CubeListBuilder.create().texOffs(1, 17).addBox(-6.0F, -0.5F, -1.25F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -0.5F, 0.0F, 0.0F, -0.8727F));

        PartDefinition string1_side1 = head.addOrReplaceChild("string1_side1", CubeListBuilder.create(), PartPose.offset(5.75F, -2.25F, 0.5F));

        PartDefinition string_r1 = string1_side1.addOrReplaceChild("string_r1", CubeListBuilder.create().texOffs(46, 19).addBox(2.27F, -3.25F, 3.75F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, 1.75F, -2.75F, 0.0F, 0.3491F, 0.0F));

        PartDefinition string2_side1 = head.addOrReplaceChild("string2_side1", CubeListBuilder.create(), PartPose.offset(5.5F, -0.25F, -0.25F));

        PartDefinition string_r2 = string2_side1.addOrReplaceChild("string_r2", CubeListBuilder.create().texOffs(46, 23).addBox(2.52F, -0.25F, 2.75F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.25F, -2.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition string1_side2 = head.addOrReplaceChild("string1_side2", CubeListBuilder.create(), PartPose.offset(-5.75F, -2.75F, 0.5F));

        PartDefinition string_r3 = string1_side2.addOrReplaceChild("string_r3", CubeListBuilder.create().texOffs(46, 19).mirror().addBox(-2.27F, -3.25F, 3.75F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.25F, 2.25F, -2.75F, 0.0F, -0.3491F, 0.0F));

        PartDefinition string2_side2 = head.addOrReplaceChild("string2_side2", CubeListBuilder.create(), PartPose.offset(-5.5F, -0.25F, -0.25F));

        PartDefinition string_r4 = string2_side2.addOrReplaceChild("string_r4", CubeListBuilder.create().texOffs(46, 23).mirror().addBox(-2.52F, -0.25F, 2.75F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -0.25F, -2.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition hanging_object1 = head.addOrReplaceChild("hanging_object1", CubeListBuilder.create().texOffs(62, 21).addBox(0.02F, -0.75F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 18).addBox(-0.48F, 4.25F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, -1.5F));

        PartDefinition hanging_object2 = head.addOrReplaceChild("hanging_object2", CubeListBuilder.create().texOffs(62, 21).mirror().addBox(-0.02F, -0.75F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(56, 18).mirror().addBox(-0.52F, 4.25F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 0.0F, -1.5F));

        PartDefinition tail = main_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 3.0F));

        PartDefinition sheepuff_tail = tail.addOrReplaceChild("sheepuff_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_base_r1 = sheepuff_tail.addOrReplaceChild("tail_base_r1", CubeListBuilder.create().texOffs(41, 84).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition kirrid_tail = tail.addOrReplaceChild("kirrid_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition kirrid_tail_r1 = kirrid_tail.addOrReplaceChild("kirrid_tail_r1", CubeListBuilder.create().texOffs(98, 49).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.hurtanim_base.render(pPoseStack,pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
}
