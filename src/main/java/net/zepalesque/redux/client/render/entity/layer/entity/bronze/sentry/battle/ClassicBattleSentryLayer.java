package net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.battle;

import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.config.ReduxConfig;

import javax.annotation.Nonnull;

@Deprecated
public class ClassicBattleSentryLayer<T extends BattleSentry> extends RenderLayer<T, EntityModel<T>> {
	private static final ResourceLocation SENTRY_TEXTURE = new ResourceLocation("aether_genesis", "textures/entity/mobs/battle_sentry/battle_sentry.png");
	private static final ResourceLocation SENTRY_LIT_TEXTURE = new ResourceLocation("aether_genesis", "textures/entity/mobs/battle_sentry/battle_sentry_lit.png");

	private final EntityModel<T> model;
	public ClassicBattleSentryLayer(RenderLayerParent<T, EntityModel<T>> entityRenderer, EntityModel<T> pModel) {
		super(entityRenderer);
		this.model = pModel;
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, MultiBufferSource buffer, int packedLight, @Nonnull T sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(sentry)));
		if (!ReduxConfig.CLIENT.sentry_improvements.get() && Minecraft.getInstance().player != null && !sentry.isInvisibleTo(Minecraft.getInstance().player)) {
			this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sentry, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(T sentry) {
		return sentry.isAwake() ? SENTRY_LIT_TEXTURE : SENTRY_TEXTURE;
	}
}
