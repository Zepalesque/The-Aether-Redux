package net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.battle;



import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.config.ReduxConfig;

import javax.annotation.Nonnull;

@Deprecated
public class ClassicBattleSentryEyeLayer<T extends BattleSentry> extends EyesLayer<T, EntityModel<T>> {
	private static final RenderType SENTRY_EYE = RenderType.eyes(new ResourceLocation("aether_genesis", "textures/entity/mobs/battle_sentry/eye.png"));


	private final EntityModel<T> model;
	public ClassicBattleSentryEyeLayer(RenderLayerParent<T, EntityModel<T>> entityRenderer, EntityModel<T> pModel) {
		super(entityRenderer);
		this.model = pModel;
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, MultiBufferSource buffer, int packedLight, @Nonnull T sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		VertexConsumer consumer = buffer.getBuffer(this.renderType());
		if (sentry.isAwake() && !ReduxConfig.CLIENT.sentry_improvements.get()) {
			this.model.renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Nonnull
	@Override
	public RenderType renderType() {
		return SENTRY_EYE;
	}
}
