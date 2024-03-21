package net.zepalesque.redux.client.render.entity.model.entity;


import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.util.math.EasingUtil;
import net.zepalesque.redux.util.math.MathUtil;

public class MimicReduxModel extends EntityModel<Mimic> {
	private final ModelPart main;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart head;
	private final ModelPart lowerBody;
	private final ModelPart upperBody;


	public MimicReduxModel(ModelPart root) {
		this.main = root.getChild("main");
		this.leftLeg = this.main.getChild("leftLeg");
		this.rightLeg = this.main.getChild("rightLeg");
		this.head = this.main.getChild("head");
		this.lowerBody = this.head.getChild("lowerBody");
		this.upperBody = this.lowerBody.getChild("upperBody");

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition leftLeg = main.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(30, 43).addBox(-4.0F, -2.5F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -9.5F, 0.0F));

		PartDefinition rightLeg = main.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(30, 43).mirror().addBox(-1.0F, -2.5F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.0F, -9.5F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(8.0F, -22.0F, -8.0F));

		PartDefinition lowerBody = head.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(47, 31).addBox(-14.0F, 3.0F, 2.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mouth_r1 = lowerBody.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(-14, 43).addBox(-7.0F, 20.0F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 19).addBox(-7.0F, 11.0F, -7.0F, 14.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 27.0F, 8.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition upperBody = lowerBody.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, 15.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition teeth_lower_r1 = upperBody.addOrReplaceChild("teeth_lower_r1", CubeListBuilder.create().texOffs(44, 7).addBox(-6.0F, -1.5F, -6.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -1.5F, 7.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition knob = upperBody.addOrReplaceChild("knob", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 1.0F, 14.5F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}


	// TODO: rename variable to something other than egg??????
	@Override
	public void setupAnim(Mimic mimic, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean flag = mimic.getFallFlyingTicks() > 4;

		float egg = 1.0F;
		if (flag) {
			egg = (float)mimic.getDeltaMovement().lengthSqr();
			egg /= 0.2F;
			egg *= egg * egg;
		}

		if (egg < 1.0F) {
			egg = 1.0F;
		}
		this.main.zRot = (Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / egg) * 0.075F;
		float f = Math.max(ageInTicks, 1F) - 1F;
		float maxValue = 25F;
		float ticks = f % maxValue;
		float rotation;
		float maxRot = MathUtil.degToRad(65);
		if (ticks >= 10F)
		{
			rotation = ((float) Math.PI) - (maxRot - (EasingUtil.Bounce.out((ticks - 10F) / 15F) * maxRot));

		} else {
			rotation = ((float) Math.PI) + MathUtil.costrp(ticks / 10F, (float) (2F * Math.PI ), (float) (2F * Math.PI ) - maxRot); }

		this.upperBody.xRot = rotation;
		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

		if (ageInTicks <= 1)
		{
			this.head.y = -16f;
			this.leftLeg.x = -2F;
			this.rightLeg.x = 2F;
		} else {
			this.head.y = -22f;
			this.leftLeg.x = -7F;
			this.rightLeg.x = 7F;
		}

		MimicAnimation.get(mimic).ifPresent((anim) ->
		{
			if (anim.getOpenAnim() != 0 && anim.getPrevOpenAnim() != 0)
			{
				float prevAnim = anim.getPrevOpenAnim() == 0 && anim.getOpenAnim() > 1 ? 10F : anim.getPrevOpenAnim();
				float progress = (10F - Mth.lerp(ageInTicks % 1, prevAnim, anim.getOpenAnim())) / 10F;
				this.head.y = -16f + (-6F * EasingUtil.Back.inOut(progress));
				float progressLegs = Math.min(progress / 0.5F, 1.0F);
				float legX =  (5F * EasingUtil.Back.out(progressLegs));
				this.leftLeg.x = 5F - 7F - legX;
				this.rightLeg.x = -5F + 7F + legX;

			}



		});
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (ReduxConfig.COMMON.smaller_mimic_hitbox.get()) {
			main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
}