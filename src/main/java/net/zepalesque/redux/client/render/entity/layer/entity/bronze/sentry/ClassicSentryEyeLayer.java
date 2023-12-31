package net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
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
public class ClassicSentryEyeLayer<T extends Sentry> extends EyesLayer<T, EntityModel<T>> {
	private static final RenderType SENTRY_EYE = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/eye.png"));


	private final EntityModel<T> model;
	public ClassicSentryEyeLayer(RenderLayerParent<T, EntityModel<T>> entityRenderer, EntityModel<T> pModel) {
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
