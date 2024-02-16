package net.zepalesque.redux.client.render.entity.model.entity;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.entity.passive.Glimmercow;

@OnlyIn(Dist.CLIENT)
public class GlimmercowModel<T extends Glimmercow> extends QuadrupedModel<T> {

   protected final ModelPart mushrooms;
   public GlimmercowModel(ModelPart root) {
      super(root, false, 15.0F, 4.0F, 2.0F, 2.0F, 24);
      this.mushrooms = this.body.getChild("mushrooms");
   }


   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();

      PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 2.0F));
      body.addOrReplaceChild("plate_right", CubeListBuilder.create().texOffs(40, 33).mirror().addBox(-9.0F, 0.0F, -7.0F, 3.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
              .texOffs(40, 33).addBox(6.0F, -1.0F, -7.0F, 3.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
      body.addOrReplaceChild("udder", CubeListBuilder.create().texOffs(32, 35).addBox(-3.0F, 4.0F, 8.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -2.0F, 1.5708F, 0.0F, 0.0F));
      body.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -7.0F, -11.0F, 16.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

      PartDefinition mushrooms = body.addOrReplaceChild("mushrooms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

      PartDefinition m1 = mushrooms.addOrReplaceChild("m1", CubeListBuilder.create().texOffs(32, 40).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 5.0F, 0.0F, -0.2618F, 0.0F));
      m1.addOrReplaceChild("cross1_half2", CubeListBuilder.create().texOffs(32, 40).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

      PartDefinition m2 = mushrooms.addOrReplaceChild("m2", CubeListBuilder.create().texOffs(2, 44).addBox(0.0F, -4.0F, -6.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -8.0F, -4.0F, 0.0F, -0.829F, 0.0F));
      m2.addOrReplaceChild("cross2_half2", CubeListBuilder.create().texOffs(2, 44).addBox(0.0F, -4.0F, -6.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

     partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
              .texOffs(0, 0).addBox(4.0F, -7.0F, -4.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
              .texOffs(0, 0).mirror().addBox(-7.0F, -7.0F, -4.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, -8.0F));

      partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(24, 42).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, 7.0F));

      partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(24, 42).mirror().addBox(-3.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 15.0F, 7.0F));

      partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(24, 42).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, -6.0F));
      partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(24, 42).mirror().addBox(-3.0F, 0.0F, -1.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 15.0F, -6.0F));

      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public ModelPart getHead() {
      return this.head;
   }

   @Override
   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.mushrooms.skipDraw = this.young;
      if (entity.isCrazy()) {
         float realLimb = limbSwingAmount;
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