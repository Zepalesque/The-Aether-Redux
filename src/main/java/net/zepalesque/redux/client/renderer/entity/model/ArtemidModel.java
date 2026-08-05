package net.zepalesque.redux.client.renderer.entity.model;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.entity.anim.ArtemidAnimation;
import net.zepalesque.redux.entity.Artemid;

@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public class ArtemidModel<T extends Artemid> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Redux.loc("artemid"), "main");
	public static final ModelLayerLocation ANTLERS_LAYER = new ModelLayerLocation(Redux.loc("artemid_antlers"), "antlers");

	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart legs;
	private final ModelPart frontleft;
	private final ModelPart backleft;
	private final ModelPart backright;
	private final ModelPart frontright;

	final AnimationState idleAnimationState = new AnimationState();

	public ArtemidModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.body = this.root.getChild("body");
		this.tail = this.root.getChild("tail");
		this.legs = this.root.getChild("legs");
		this.frontleft = this.legs.getChild("frontleft");
		this.backleft = this.legs.getChild("backleft");
		this.backright = this.legs.getChild("backright");
		this.frontright = this.legs.getChild("frontright");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(1.0F, 19.0F, -3.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, -15.0F, -5.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 7).addBox(-3.0F, -8.0F, -1.0F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, -2.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 31).addBox(-16.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -6.0F, -5.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 31).addBox(0.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -6.0F, -5.0F, 0.0F, -0.3491F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 47).addBox(-5.0F, -5.0F, -1.0F, 6.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -3.0F, -9.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -17.0F, -6.0F, 10.0F, 11.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-1.0F, -14.0F, 14.0F));


		PartDefinition cube_r5 = tail.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(4, 12).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition frontleft = legs.addOrReplaceChild("frontleft", CubeListBuilder.create().texOffs(30, 47).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -7.0F, -3.0F));

		PartDefinition backleft = legs.addOrReplaceChild("backleft", CubeListBuilder.create().texOffs(46, 47).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -7.0F, 10.0F));

		PartDefinition backright = legs.addOrReplaceChild("backright", CubeListBuilder.create().texOffs(46, 47).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -7.0F, 10.0F));

		PartDefinition frontright = legs.addOrReplaceChild("frontright", CubeListBuilder.create().texOffs(30, 47).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -7.0F, -3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(
		T entity,
		float limbSwing,
		float limbSwingAmount,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.idleAnimationState.startIfStopped(entity.tickCount);

		this.animateWalk(ArtemidAnimation.WALK, limbSwing, limbSwingAmount, 1.5f, 2f);
		this.animate(this.idleAnimationState, ArtemidAnimation.IDLE, ageInTicks);
	}

	protected void applyHeadRotation(float headYaw, float headPitch) {
		headYaw = Mth.clamp(headYaw, -30, 30);
		headPitch = Mth.clamp(headPitch, -25, 45);

		this.head.yRot = headYaw * ((float)Math.PI / 180);
		this.head.xRot = headPitch * ((float)Math.PI / 180);
	}

	@Override
	public void renderToBuffer(
		PoseStack poseStack,
		VertexConsumer buffer,
		int packedLight,
		int packedOverlay,
		int color
	) {
		if (this.young) {
			poseStack.scale(0.5f, 0.5f, 0.5f);
			poseStack.translate(0, 1.5, 0);
		} else {
			//this.head.xScale = 1;
			//this.head.yScale = 1;
			//this.head.zScale = 1;
		}

		super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}