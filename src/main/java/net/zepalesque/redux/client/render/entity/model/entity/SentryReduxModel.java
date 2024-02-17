package net.zepalesque.redux.client.render.entity.model.entity;


import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.capability.animation.sentry.SentryAnimation;
import net.zepalesque.redux.util.math.EasingUtil;

public class SentryReduxModel<T extends Sentry> extends EntityModel<T> {
	private final ModelPart main;
	private final ModelPart spring_part;
	public	 final ModelPart spring_control;
	public	 final ModelPart spring_main;

	public SentryReduxModel(ModelPart root) {
		this.main = root.getChild("main");
		this.spring_part = this.main.getChild("spring_part");
		this.spring_main = this.spring_part.getChild("spring_main");
		this.spring_control = this.spring_part.getChild("spring_control");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -2.0F, -6.5F, 12.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.5F));

		PartDefinition spring_part = main.addOrReplaceChild("spring_part", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition spring_control = spring_part.addOrReplaceChild("spring_control", CubeListBuilder.create().texOffs(0, 21).addBox(-6.0F, -3.0F, -6.5F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition spring_main = spring_part.addOrReplaceChild("spring_main", CubeListBuilder.create().texOffs(26, 36).addBox(-3.0F, -11.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	// TODO: PLEASE create some better system to do this, with keyframes or smth idk
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		SentryAnimation.get(entity).ifPresent((anim) -> {

			int jump = anim.getJumpAnim();
			int prevJump = jump > 1 && anim.getPrevJumpAnim() == 0 ? 10 : anim.getPrevJumpAnim();
			float trueJump = Mth.lerp(ageInTicks % 1, prevJump, jump);
			float progress = (10 - trueJump) / 10;
			if (progress <= 0.83F)
			{
				float progressEase = progress / 0.83F;
				this.spring_control.y = EasingUtil.Quadratic.out(progressEase) * 10F;
				this.spring_main.y = EasingUtil.Quadratic.out(progressEase) * 8F - 1F;
			} else {
				float progressEase = (progress - 0.83F) / 0.17F;
				this.spring_control.y = EasingUtil.Cubic.out(progressEase) * -10F + 10F;
				this.spring_main.y = EasingUtil.Cubic.out(progressEase) * -8F + 10F - 1F;
			}
		});
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (ReduxConfig.CLIENT.sentry_model_upgrade.get() || ReduxConfig.CLIENT.override_model_upgrades.get()) { main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha); }
	}
}