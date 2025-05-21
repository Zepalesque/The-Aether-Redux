package net.zepalesque.redux.client.render.entity.model;


import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.zepalesque.redux.util.math.EasingUtil;
import org.jetbrains.annotations.NotNull;

public class WhirlwindModel<T extends AbstractWhirlwind> extends EntityModel<T> {
	private final ModelPart whirl_body;
	private final ModelPart whirl_bottom;
	private final ModelPart bottom_render;
	private final ModelPart whirl_lower;
	private final ModelPart lower_render;
	private final ModelPart whirl_upper;
	private final ModelPart upper_render;
	private final ModelPart whirl_top;
	private final ModelPart top_render;

	private final float[] alpha = {1, 1, 1, 1};

	public WhirlwindModel(ModelPart root) {
		this.whirl_body = root.getChild("whirl_body");
		this.whirl_bottom = this.whirl_body.getChild("whirl_bottom");
		this.bottom_render = this.whirl_bottom.getChild("bottom_render");
		this.whirl_lower = this.whirl_bottom.getChild("whirl_lower");
		this.lower_render = this.whirl_lower.getChild("lower_render");
		this.whirl_upper = this.whirl_lower.getChild("whirl_upper");
		this.upper_render = this.whirl_upper.getChild("upper_render");
		this.whirl_top = this.whirl_upper.getChild("whirl_top");
		this.top_render = this.whirl_top.getChild("top_render");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition whirl_body = partdefinition.addOrReplaceChild("whirl_body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition whirl_bottom = whirl_body.addOrReplaceChild("whirl_bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		whirl_bottom.addOrReplaceChild("bottom_render", CubeListBuilder.create().texOffs(0, 126).addBox(-3.5F, -18.0F, -3.5F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition whirl_lower = whirl_bottom.addOrReplaceChild("whirl_lower", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));

		whirl_lower.addOrReplaceChild("lower_render", CubeListBuilder.create().texOffs(228, 103).addBox(-3.5F, -27.0F, -3.5F, 7.0F, 16.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(116, 98).addBox(-6.0F, -27.0F, -6.0F, 12.0F, 16.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 92).addBox(-9.0F, -27.0F, -9.0F, 18.0F, 16.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition whirl_upper = whirl_lower.addOrReplaceChild("whirl_upper", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		whirl_upper.addOrReplaceChild("upper_render", CubeListBuilder.create().texOffs(208, 60).addBox(-6.0F, -41.0F, -6.0F, 12.0F, 20.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(116, 54).addBox(-9.0F, -41.0F, -9.0F, 18.0F, 20.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-12.0F, -41.0F, -12.0F, 24.0F, 20.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition whirl_top = whirl_upper.addOrReplaceChild("whirl_top", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 0.0F));

		whirl_top.addOrReplaceChild("top_render", CubeListBuilder.create().texOffs(116, 18).addBox(-9.0F, -53.0F, -9.0F, 18.0F, 12.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(160, 12).addBox(-12.0F, -53.0F, -12.0F, 24.0F, 12.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-18.0F, -53.0F, -18.0F, 36.0F, 12.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final int mask = 0xffffff;
		final int offset = 3;
		final int length = 11;
		final int total = length + offset * 3;
		if (ageInTicks < total)
			for (int i = 0; i < 4; i++) {
				// holy progesterone real
				float prog = Mth.clamp(ageInTicks - 1 - offset * i, 0, length) / length;
				float a = EasingUtil.Sinusoidal.inOut(prog);
				alpha[i] = a;
			}
		else if (entity.deathTime < total && entity.deathTime > 0)
			for (int i = 0; i < 4; i++) {
				float prog = 1F - Mth.clamp(entity.deathTime + ageInTicks % 1 - offset * i, 0, length) / length;
				float a = EasingUtil.Sinusoidal.inOut(prog);
				alpha[i] = a;
			}
		if (entity.deathTime >= total) {
			alpha[0] = mask;
			alpha[1] = mask;
			alpha[2] = mask;
			alpha[3] = mask;
		}
		
		boolean flag = entity.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get();

		float speedModif = flag ? -0.075F : -0.1F;
		float amountModif = flag ? 4.0F : 3.0F;

		this.whirl_body.getAllParts().forEach(ModelPart::resetPose);
		float f = ageInTicks * (float) Math.PI * speedModif;
		this.bottom_render.x = Mth.cos(f) * -0.25F * 1.0F * amountModif;
		this.bottom_render.z = Mth.sin(f) * -0.25F * 1.0F * amountModif;

		this.lower_render.x = this.bottom_render.x + Mth.sin(f) * 0.5F * 0.8F * amountModif;
		this.lower_render.z = this.bottom_render.z + Mth.cos(f) * 0.8F * amountModif;

		this.upper_render.x = this.lower_render.x + Mth.cos(f) * 1.0F * 0.6F * amountModif;
		this.upper_render.z = this.lower_render.z + Mth.sin(f) * 1.0F * 0.6F * amountModif;

		this.top_render.x = this.top_render.x + Mth.sin(f) * 0.5F * 0.4F * amountModif;
		this.top_render.z = this.top_render.z + Mth.cos(f) * 0.4F * amountModif;
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bottom_render.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, this.alpha[0]);
		lower_render.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, this.alpha[1]);
		upper_render.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, this.alpha[2]);
		top_render.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, this.alpha[3]);
		
		this.alpha[0] = 1.0F;
		this.alpha[1] = 1.0F;
		this.alpha[2] = 1.0F;
		this.alpha[3] = 1.0F;
	}
}