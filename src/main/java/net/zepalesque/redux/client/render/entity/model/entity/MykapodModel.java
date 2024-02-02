package net.zepalesque.redux.client.render.entity.model.entity;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.zepalesque.redux.entity.passive.Mykapod;
import net.zepalesque.redux.util.function.SeptConsumer;
import net.zepalesque.redux.util.math.EasingUtil;
import net.zepalesque.redux.util.math.MathUtil;

public class MykapodModel extends EntityModel<Mykapod> {
	public final ModelPart rot_stand;
	public final ModelPart body;
	public final ModelPart shell_rot;
	public final ModelPart shell;
	public final ModelPart tail;
	public final ModelPart neck;
	public final ModelPart head;
	public final ModelPart head_piece;
	public final ModelPart antennae;

	public MykapodModel(ModelPart root) {
		this.rot_stand = root.getChild("rot_stand");
		this.body = rot_stand.getChild("body");
		this.shell_rot = body.getChild("shell_rot");
		this.shell = shell_rot.getChild("shell");
		this.tail = shell_rot.getChild("tail");
		this.neck = shell_rot.getChild("neck");
		this.head = neck.getChild("head");
		this.head_piece = head.getChild("head_piece");
		this.antennae = head_piece.getChild("antennae");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rot_stand = partdefinition.addOrReplaceChild("rot_stand", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 3.0F));

		PartDefinition body = rot_stand.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition shell_rot = body.addOrReplaceChild("shell_rot", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition shell = shell_rot.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(10, 10).addBox(-1.0F, -5.5F, 0.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.1F))
		.texOffs(0, 0).addBox(0.0F, -7.5F, 0.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -3.0F));

		PartDefinition tail = shell_rot.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 3.0F));

		PartDefinition neck = shell_rot.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -3.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head_piece = head.addOrReplaceChild("head_piece", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition antennae = head_piece.addOrReplaceChild("antennae", CubeListBuilder.create().create().texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -4.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Mykapod myka, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partial = ageInTicks % 1;
		float lerped = Mth.lerp(partial, myka.prevHideAnim, myka.hideAnim);
		int fps = 24;
		float anim = lerped * (24F / 20F);
		double walkDelta = MathUtil.clampedInverp(limbSwingAmount, 0, 0.05);
		double animateDelta = lerped < 30 ? MathUtil.clampedInverp(anim, 0D, 1D) : MathUtil.clampedInverp(anim, 59D, 60D);

		// Do idle animation





		double walkDelta = MathUtil.clampedInverp(limbSwingAmount, 0, 0.05);


		boolean isExitingShell = myka.prevHideAnim >= 30 && myka.hideAnim >= 31;
		// potential keyframes per second, NOT actual frames per second
		if (!isExitingShell && myka.isHiding()) {
			float frame = anim * fps;


		} else if (isExitingShell && myka.) {

		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rot_stand.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	protected static float frameFromTicks(float ticks, int fps) {
		return ticks * ((float) fps / 20F);
	}


	// help
	private enum AnimState {

		IDLE((model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch) -> {
			float animTime = ageInTicks % 60;
			float frame = frameFromTicks(animTime, 24);
			// Head animation
			if (frame < 18) {
				float delta = MathUtil.clampedInverp(frame, 0, 18);
				model.head.xRot = Mth.lerp(EasingUtil.Back.in(delta), 0F, MathUtil.degToRad(-1.25F));
			} else if (frame < 36) {
				float delta = MathUtil.clampedInverp(frame, 18, 36);
				model.head.xRot = Mth.lerp(EasingUtil.Sinusoidal.out(delta), MathUtil.degToRad(-1.25F), MathUtil.degToRad(-2.5F));
			} else {
				float delta = MathUtil.clampedInverp(frame, 36, 60);
				model.head.xRot = Mth.lerp(EasingUtil.Quadratic.inOut(delta), MathUtil.degToRad(-2.5F), MathUtil.degToRad(0F));
			}

			// Shell animation
			if (frame < 45) {
				float delta = MathUtil.clampedInverp(frame, 0, 45);
				model.shell.y = Mth.lerp(EasingUtil.Quintic.inOut(delta), 0F, 0.15F);
			} else {
				float delta = MathUtil.clampedInverp(frame, 45, 60);
				model.shell.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0.15F, 0F);
			}
			model.rot_stand.xRot = 0F;
			model.rot_stand.y = 0F;
			model.tail.xRot = 0F;

		}),

		MOVE((model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch) -> {
			float animTime = ageInTicks % 36;
			float frame = frameFromTicks(animTime, 24);
			// half-and-half animation
			if (frame < 18) {
				float delta = MathUtil.clampedInverp(frame, 0, 18);
				model.head.xRot = Mth.lerp(EasingUtil.Cubic.inOut(delta), 0F, MathUtil.degToRad(10F));
				model.shell.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, MathUtil.degToRad(10F));
				model.rot_stand.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, MathUtil.degToRad(-2.5F));
				model.rot_stand.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, -0.25F);
				model.tail.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, MathUtil.degToRad(5F));
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 36);
				model.head.xRot = Mth.lerp(EasingUtil.Sinusoidal.in(delta), MathUtil.degToRad(10F), 0F);
				model.shell.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), MathUtil.degToRad(10F), 0F);
				model.rot_stand.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), MathUtil.degToRad(-2.5F), 0F);
				model.rot_stand.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), -0.25F, 0F);
				model.tail.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), MathUtil.degToRad(5F), 0F);
			}

		}),
		HIDE((model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch) -> {

		}),
		UNHIDE((model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch) -> {

		});

		private final SeptConsumer<MykapodModel, Mykapod, Float, Float, Float, Float, Float> animation;
		AnimState(SeptConsumer<MykapodModel, Mykapod, Float, Float, Float, Float, Float> animation) {
			this.animation = animation;
		}

		public void animate(MykapodModel model, Mykapod myka, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			this.animation.accept(model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}

		}
}
