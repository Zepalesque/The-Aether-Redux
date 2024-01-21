package net.zepalesque.redux.client.render.entity.model.entity;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.entity.passive.Shimmercow;

@OnlyIn(Dist.CLIENT)
public class ShimmercowModel<T extends Shimmercow> extends QuadrupedModel   <T> {
   public ShimmercowModel(ModelPart root) {
      super(root, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
   }


   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();

      PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 2.0F));
      body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(31, 35).addBox(-4.0F, 2.0F, -11.0F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
              .texOffs(0, 0).addBox(-8.0F, -10.0F, -10.0F, 16.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
      PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
              .texOffs(0, 0).mirror().addBox(-7.0F, -7.0F, -4.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, -8.0F));
      head.addOrReplaceChild("hornL_r1", CubeListBuilder.create().texOffs(0, 0).addBox(4.0F, -27.0F, -12.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 8.0F, 0.0F, 0.0436F, 0.0F));
      partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(40, 42).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, 7.0F));
      PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.0F, 15.0F, 7.0F));
      right_hind_leg.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(40, 42).mirror().addBox(-7.0F, -9.0F, 5.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 9.0F, -7.0F, -0.0436F, 0.0F, 0.0F));
      partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(24, 42).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, -6.0F));
      partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(24, 42).mirror().addBox(-3.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 15.0F, -6.0F));

      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public ModelPart getHead() {
      return this.head;
   }

   @Override
   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity.isCrazy()) {
         float realLimb = limbSwingAmount /* * 1.5F*/;
         this.head.xRot = headPitch * ((float)Math.PI / 180F) + (Mth.cos(limbSwing * 2F) * 1.4F * realLimb);
         this.head.yRot = netHeadYaw * ((float)Math.PI / 180F) + (Mth.cos(limbSwing * 2F) * 1.4F * realLimb);
         this.rightHindLeg.xRot = Mth.cos(limbSwing * 2F) * 1.4F * realLimb;
         this.body.xRot = Mth.cos(limbSwing * 2F) * 1.4F * realLimb;
         this.leftHindLeg.xRot = Mth.cos(limbSwing * 2F + (float)Math.PI) * 1.4F * realLimb;
         this.rightFrontLeg.xRot = Mth.cos(limbSwing * 2F + (float)Math.PI) * 1.4F * realLimb;
         this.leftFrontLeg.xRot = Mth.cos(limbSwing * 2F) * 1.4F * realLimb;

      } else{
         super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.body.xRot = 0F;
      }
   }
}