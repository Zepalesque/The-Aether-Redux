package net.zepalesque.redux.client.render.entity.model.entity.sheepuff;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class PuffedConfiguredSheepuffWoolModel extends AbstractSheepuffWoolModel {

    public PuffedConfiguredSheepuffWoolModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(Entity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hurtanim_base = partdefinition.addOrReplaceChild("hurtanim_base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 6.5F));

        PartDefinition main_body = hurtanim_base.addOrReplaceChild("main_body", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));

        PartDefinition wool = main_body.addOrReplaceChild("wool", CubeListBuilder.create().texOffs(60, 61).addBox(-5.5F, -20.5F, -9.5F, 11.0F, 11.0F, 13.0F, new CubeDeformation(1.35F)), PartPose.offset(0.0F, 13.0F, -6.5F));

        PartDefinition sheepuff_wool = wool.addOrReplaceChild("sheepuff_wool", CubeListBuilder.create().texOffs(59, 14).addBox(-4.5F, -0.5F, -11.0F, 9.0F, 9.0F, 19.0F, new CubeDeformation(1.5F)), PartPose.offset(0.0F, -19.0F, 2.0F));

        PartDefinition kirrid_wool = wool.addOrReplaceChild("kirrid_wool", CubeListBuilder.create().texOffs(0, 47).addBox(-4.5F, -19.5F, -9.0F, 9.0F, 9.0F, 19.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg1 = main_body.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(-4.5F, 2.0F, -12.0F));

        PartDefinition wool_leg1 = leg1.addOrReplaceChild("wool_leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg2 = main_body.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(4.5F, 2.0F, -12.0F));

        PartDefinition wool_leg2 = leg2.addOrReplaceChild("wool_leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg3 = main_body.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(-4.5F, 0.0F, 0.0F));

        PartDefinition wool_leg3 = leg3.addOrReplaceChild("wool_leg3", CubeListBuilder.create().texOffs(62, 46).addBox(-2.5F, -3.0F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg4 = main_body.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition wool_leg4 = leg4.addOrReplaceChild("wool_leg4", CubeListBuilder.create().texOffs(62, 46).mirror().addBox(-2.5F, -3.0F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = main_body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -16.5F));

        PartDefinition head_baserot = head.addOrReplaceChild("head_baserot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_wool = head_baserot.addOrReplaceChild("head_wool", CubeListBuilder.create().texOffs(79, 1).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = main_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 3.0F));

        PartDefinition sheepuff_tail = tail.addOrReplaceChild("sheepuff_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition sheepuff_wooltail = sheepuff_tail.addOrReplaceChild("sheepuff_wooltail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_wool_r1 = sheepuff_wooltail.addOrReplaceChild("tail_wool_r1", CubeListBuilder.create().texOffs(40, 76).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition kirrid_tail = tail.addOrReplaceChild("kirrid_tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition kirrid_wooltail = kirrid_tail.addOrReplaceChild("kirrid_wooltail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition kirrid_tail_wool_r1 = kirrid_wooltail.addOrReplaceChild("kirrid_tail_wool_r1", CubeListBuilder.create().texOffs(96, 58).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.hurtanim_base.render(pPoseStack,pBuffer, pPackedLight,pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
}
