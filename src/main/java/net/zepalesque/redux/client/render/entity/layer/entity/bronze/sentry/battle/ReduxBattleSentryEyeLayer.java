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
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.ReduxBattleSentryRenderer;
import net.zepalesque.redux.config.ReduxConfig;

import javax.annotation.Nonnull;

public class ReduxBattleSentryEyeLayer<T extends BattleSentry, M extends EntityModel<T>> extends EyesLayer<T, M> {
	private static final RenderType SENTRY_EYE = RenderType.eyes(new ResourceLocation(Redux.MODID, "textures/entity/mobs/battle_sentry/battle_sentry_eye.png"));

	protected final RenderLayerParent<T, M> renderer;

	public ReduxBattleSentryEyeLayer(RenderLayerParent<T, M> entityRenderer) {
		super(entityRenderer);
		this.renderer = entityRenderer;
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, MultiBufferSource buffer, int packedLight, @Nonnull T sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		VertexConsumer consumer = buffer.getBuffer(this.renderType());
		boolean shouldGlow = this.renderer instanceof ReduxBattleSentryRenderer sentryRenderer ? sentryRenderer.shouldBeOn(sentry) : sentry.isAwake();
		if (shouldGlow && ReduxConfig.CLIENT.sentry_improvements.get()) {
			this.getParentModel().renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Nonnull
	@Override
	public RenderType renderType() {
		return SENTRY_EYE;
	}
}
