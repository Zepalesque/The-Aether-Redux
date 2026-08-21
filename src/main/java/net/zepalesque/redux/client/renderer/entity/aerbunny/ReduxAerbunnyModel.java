package net.zepalesque.redux.client.renderer.entity.aerbunny;

import com.aetherteam.aether.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.util.Mth;

public final class ReduxAerbunnyModel extends EntityModel<Aerbunny> {
	private final ModelPart body;
	private final ModelPart bodyRot;
	private final ModelPart puff;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart rightWhisker;
	private final ModelPart leftWhisker;
	private final ModelPart frontRightLeg;
	private final ModelPart frontLeftLeg;
	private final ModelPart backRightLeg;
	private final ModelPart backLeftLeg;
	public float puffiness;
	
	public ReduxAerbunnyModel(ModelPart root) {
		this.body = root.getChild("body");
		this.bodyRot = this.body.getChild("body_rot");
		this.puff = this.bodyRot.getChild("puff");
		this.tail = this.bodyRot.getChild("tail");
		this.head = this.body.getChild("head");
		this.leftEar = this.head.getChild("left_ear");
		this.rightEar = this.head.getChild("right_ear");
		this.rightWhisker = this.head.getChild("right_whisker");
		this.leftWhisker = this.head.getChild("left_whisker");
		this.frontRightLeg = this.body.getChild("front_right_leg");
		this.frontLeftLeg = this.body.getChild("front_left_leg");
		this.backRightLeg = this.body.getChild("back_right_leg");
		this.backLeftLeg = this.body.getChild("back_left_leg");
	}
	
	public static LayerDefinition createBodyLayer() {
		var mesh = new MeshDefinition();
		var root = mesh.getRoot();
		
		var body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, -1.75F));
		
		var body_rot = body.addOrReplaceChild("body_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, 5.0F, -1.25F, -0.2618F, 0.0F, 0.0F));
		
		var puff = body_rot.addOrReplaceChild("puff", CubeListBuilder.create().texOffs(0, 9).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -3.0F, 4.0F));
		
		var tail = body_rot.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(25, 11).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -5.0F, 8.5F));
		
		var head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -0.5F));
		
		var left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(42, 20).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, -2.0F));
		
		var right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(35, 20).addBox(-0.5F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -3.0F, -2.0F));
		
		var right_whisker = head.addOrReplaceChild("right_whisker", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, -2.0F));
		
		var left_whisker = head.addOrReplaceChild("left_whisker", CubeListBuilder.create().texOffs(6, 9).addBox(0.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, -2.0F));
		
		var front_right_leg = body.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(0, 27).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 5.5F, 0.0F));
		
		var front_left_leg = body.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(9, 27).addBox(3.0F, -0.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 5.5F, 0.0F));
		
		var back_right_leg = body.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(21, 0).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.0F, 6.5F, 0.0F, 0.2618F, 0.0F));
		
		var back_left_leg = body.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.0F, 6.5F, 0.0F, -0.2618F, 0.0F));
		
		return LayerDefinition.create(mesh, 64, 32);
	}
	
	@Override
	public void prepareMobModel(Aerbunny aerbunny, float limbSwing, float limbSwingAmount, float partialTicks) {
		super.prepareMobModel(aerbunny, limbSwing, limbSwingAmount, partialTicks);
		this.puffiness = Mth.lerp(partialTicks, aerbunny.getPuffiness(), aerbunny.getPuffiness() - aerbunny.getPuffSubtract()) / 20.0F;
	}
	
	@Override
	public void setupAnim(Aerbunny aerbunny, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * Mth.DEG_TO_RAD;
		this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.frontRightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount - this.body.xRot;
		this.frontLeftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount - this.body.xRot;
		this.backRightLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.2F * limbSwingAmount - this.body.xRot;
		this.backLeftLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.2F * limbSwingAmount - this.body.xRot;
	}
	
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer vertices, int light, int overlay, int color) {
		var a = 1.0F + this.puffiness * 0.5F;
		this.puff.xScale = a;
		this.puff.yScale = a;
		this.puff.zScale = a;
		this.body.render(stack, vertices, light, overlay, color);
	}
}