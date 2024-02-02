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

		// Animation, this was unfun
		AnimState animState = null;
		if (limbSwingAmount > 0D) {
			animState = AnimState.MOVE;
		} else {
			animState = AnimState.IDLE;
		}
		if (myka.hideAnim > 0) {
			boolean isExitingShell = myka.prevHideAnim >= 30 && myka.hideAnim >= 31;
			if (isExitingShell) {
				animState = AnimState.UNHIDE;
			} else {
				animState = AnimState.HIDE;
			}
		}

		animState.animate(this, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

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
				model.head.xRot = Mth.lerp(EasingUtil.Back.in(delta), 0F, rad(-1.25F));
			} else if (frame < 36) {
				float delta = MathUtil.clampedInverp(frame, 18, 36);
				model.head.xRot = Mth.lerp(EasingUtil.Sinusoidal.out(delta), rad(-1.25F), rad(-2.5F));
			} else {
				float delta = MathUtil.clampedInverp(frame, 36, 60);
				model.head.xRot = Mth.lerp(EasingUtil.Quadratic.inOut(delta), rad(-2.5F), rad(0F));
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
			model.tail.z = 0F;
			model.head.z = 0F;
			model.head.y = 0F;
			model.shell.xRot = 0F;
			model.shell_rot.zRot = 0F;
			model.shell_rot.y = 0F;
			model.body.x = 0F;
			model.head.yRot = 0F;
			model.antennae.xRot = 0F;
			model.neck.yRot = 0F;
		}),

		MOVE((model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch) -> {
			float animTime = ageInTicks % 30;
			float frame = frameFromTicks(animTime, 24);
			// half-and-half animation
			if (frame < 18) {
				float delta = MathUtil.clampedInverp(frame, 0, 18);
				model.head.xRot = Mth.lerp(EasingUtil.Cubic.inOut(delta), 0F, rad(10F));
				model.shell.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, rad(10F));
				model.rot_stand.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, rad(-2.5F));
				model.rot_stand.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, -0.25F);
				model.tail.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, rad(5F));
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 36);
				model.head.xRot = Mth.lerp(EasingUtil.Sinusoidal.in(delta), rad(10F), 0F);
				model.shell.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), rad(10F), 0F);
				model.rot_stand.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), rad(-2.5F), 0F);
				model.rot_stand.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), -0.25F, 0F);
				model.tail.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), rad(5F), 0F);
			}
			model.head.z = 0F;
			model.head.y = 0F;
			model.tail.z = 0F;
			model.shell.y = 0F;
			model.shell_rot.zRot = 0F;
			model.shell_rot.y = 0F;
			model.body.x = 0F;
			model.head.yRot = 0F;
			model.antennae.xRot = -Mth.cos((ageInTicks * Mth.PI) / 5F) + 1;
			model.neck.yRot = 0F;
		}),
		HIDE((model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch) -> {
			float partial = ageInTicks % 1;
			float animTime = Mth.clamp(Mth.lerp(partial, myka.hideAnim, myka.prevHideAnim), 0F, 30F);
			float frame = frameFromTicks(animTime, 24);
			// Head animation
			if (frame < 3) {
				float delta = MathUtil.clampedInverp(frame, 0, 3);
				model.head.xRot = 0F;
				model.head.z = 0F;
				model.head.y = Mth.lerp(EasingUtil.Sinusoidal.out(delta), 0F, -2.5F);
			} else if (frame < 12) {
				float delta = MathUtil.clampedInverp(frame, 3, 12);
				model.head.xRot = Mth.lerp(EasingUtil.Sinusoidal.out(delta), 0F, rad(25F));
				model.head.y = Mth.lerp(EasingUtil.Sinusoidal.out(delta), -2.5F, 1F);
				model.head.z = Mth.lerp(EasingUtil.Sinusoidal.out(delta), 0F, -0.5F);
			} else {
				float delta = MathUtil.clampedInverp(frame, 12, 24);
				model.head.xRot = Mth.lerp(EasingUtil.Back.in(delta), rad(0F), rad(85F));
				model.head.y = 1F;
				model.head.z = Mth.lerp(EasingUtil.Exponential.in(delta), -0.5F, 4F);
			}
			// Tail anim
			if (frame < 18) {
				model.tail.z = 0F;
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 24);
				model.tail.z = Mth.lerp(EasingUtil.Exponential.in(delta), 0, -2F);
			}
			// Shell anim
			if (frame < 18) {
				model.shell.y = 0F;
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 24);
				model.shell.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0, -0.5F);
			}
			// Shell_rot anim
			if (frame < 21) {
				model.shell_rot.zRot = 0F;
				model.shell_rot.y = 0F;
			} else {
				float delta = MathUtil.clampedInverp(frame, 21, 36);
				model.shell_rot.zRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, rad(-90F));
				model.shell_rot.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, -2F);
			}
			// Body anim
			if (frame < 3) {
				float delta = MathUtil.clampedInverp(frame, 0, 3);
				model.body.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, rad(5F));
				model.body.y = 0F;
			} else if (frame < 6) {
				float delta = MathUtil.clampedInverp(frame, 3, 6);
				model.body.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), rad(5F), rad(-10F));
				model.body.y = 0F;
			} else if (frame < 18) {
				float delta = MathUtil.clampedInverp(frame, 6, 18);
				model.body.xRot = Mth.lerp(EasingUtil.Sinusoidal.out(delta), rad(-10F), rad(-15F));
				model.body.y = Mth.lerp(EasingUtil.Cubic.out(delta), 0F, 4F);
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 36);
				model.body.xRot = Mth.lerp(EasingUtil.Bounce.in(delta), rad(-15F), 0F);
				model.body.y = Mth.lerp(EasingUtil.Bounce.in(delta), 4F, 0F);
			}
			// Antennae anim
			if (frame < 21) {
				model.antennae.xRot = 0F;
			} else {
				float delta = MathUtil.clampedInverp(frame, 21, 24);
				model.antennae.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), 0F, rad(-90F));
			}
			model.shell.xRot = 0F;
			model.rot_stand.xRot = 0F;
			model.rot_stand.y = 0F;
			model.tail.xRot = 0F;
			model.neck.yRot = 0F;

		}),
		UNHIDE((model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch) -> {
			float partial = ageInTicks % 1;
			float animTime = Mth.clamp(Mth.lerp(partial, myka.hideAnim, myka.prevHideAnim), 30F, 60F);
			float frame = frameFromTicks(animTime, 24);

			// Rot_stand anim
			if (frame < 9) {
				model.rot_stand.xRot = 0F;
			} else if (frame < 18) {
				float delta = MathUtil.clampedInverp(frame, 9, 18);
				model.rot_stand.xRot = Mth.lerp(EasingUtil.Sinusoidal.out(delta), 0F, rad(-15F));
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 24);
				model.rot_stand.xRot = Mth.lerp(EasingUtil.Sinusoidal.in(delta), rad(-15F), 0F);
			}
			// Shell_rot anim
			if (frame < 9) {
				model.shell_rot.y = -2F;
				model.shell_rot.zRot = rad(-90F);
			} else {
				float delta = MathUtil.clampedInverp(frame, 9, 24);
				model.shell_rot.y = Mth.lerp(EasingUtil.Back.inOut(delta), -2F, 0F);
				model.shell_rot.zRot = Mth.lerp(EasingUtil.Back.inOut(delta), rad(-90F), 0F);
			}
			// Shell anim
			if (frame < 18) {
				model.shell.y = -0.5F;
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 24);
				model.shell.y = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), -0.5F, 0F);
			}
			// Tail anim
			if (frame < 19) {
				model.tail.z = -2F;
			} else {
				float delta = MathUtil.clampedInverp(frame, 19, 24);
				model.tail.z = Mth.lerp(EasingUtil.Cubic.out(delta), -2F, 0F);
			}
			// Neck anim
			if (frame < 6) {
				float delta = MathUtil.clampedInverp(frame, 0, 6);
				model.neck.yRot = Mth.lerp(EasingUtil.Quadratic.out(delta), 0F, rad(-35F));
			} else if (frame < 15) {
				float delta = MathUtil.clampedInverp(frame, 6, 15);
				model.neck.yRot = Mth.lerp(EasingUtil.Sinusoidal.out(delta), rad(-35F), rad(35F));
			} else {
				float delta = MathUtil.clampedInverp(frame, 15, 24);
				model.neck.yRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), rad(35F), 0F);
			}
			// Head xRot anim
			if (frame < 18) {
				model.head.xRot = rad(85F);
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 27);
				model.head.xRot = Mth.lerp(EasingUtil.Quadratic.inOut(delta), rad(85F), 0F);
			}
			// Head y/z anim
			if (frame < 6) {
				float delta = MathUtil.clampedInverp(frame, 0, 6);
				model.head.z = Mth.lerp(EasingUtil.Back.out(delta), 4F, -1F);
				model.head.y = 1F;
			} else if (frame < 21) {
				model.head.z = -1F;
				model.head.y = 1F;
			} else {
				float delta = MathUtil.clampedInverp(frame, 21, 24);
				model.head.z = Mth.lerp(EasingUtil.Quadratic.inOut(delta), -1F, 0F);
				model.head.y = Mth.lerp(EasingUtil.Quadratic.inOut(delta), 1F, 0F);
			}
			// Antennae anim
			if (frame < 6) {
				float delta = MathUtil.clampedInverp(frame, 0, 6);
				model.antennae.xRot = Mth.lerp(EasingUtil.Sinusoidal.inOut(delta), rad(-90F), rad(-30F));
			} else if (frame < 18) {
				model.antennae.xRot = rad(-30);
			} else {
				float delta = MathUtil.clampedInverp(frame, 18, 24);
				model.antennae.xRot = Mth.lerp(EasingUtil.Back.inOut(delta), rad(-30F), 0F);
			}


			model.body.xRot = 0F;
			model.body.y = 0F;
		});

		private final SeptConsumer<MykapodModel, Mykapod, Float, Float, Float, Float, Float> animation;
		AnimState(SeptConsumer<MykapodModel, Mykapod, Float, Float, Float, Float, Float> animation) {
			this.animation = animation;
		}

		public void animate(MykapodModel model, Mykapod myka, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			this.animation.accept(model, myka, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
		private static float rad(float deg) {
			return MathUtil.degToRad(deg);
		}

	}



}
