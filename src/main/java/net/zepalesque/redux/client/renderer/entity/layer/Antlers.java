package net.zepalesque.redux.client.renderer.entity.layer;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.entity.model.DeerModel;
import net.zepalesque.redux.entity.Deer;

@ParametersAreNonnullByDefault
public class Antlers extends RenderLayer<Deer, DeerModel<Deer>> {
	public Antlers(RenderLayerParent<Deer, DeerModel<Deer>> parent) {
		super(parent);
	}

	@Override
	public void render(
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		Deer deer,
		float limbSwing,
		float limbSwingAmount,
		float partialTick,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		if (deer.isBaby() || deer.isInvisible() || !deer.getHasAntlers()) {
			return;
		}
		
		var antlers = Redux.loc("textures/entity/deer/antlers.png");
		var vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(antlers));
		var overlayCoords = LivingEntityRenderer.getOverlayCoords(deer, 0.0F);

		this
			.getParentModel()
			.renderToBuffer(poseStack, vertexConsumer, packedLight, overlayCoords);
	}
}
