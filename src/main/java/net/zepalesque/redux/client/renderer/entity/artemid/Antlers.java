package net.zepalesque.redux.client.renderer.entity.artemid;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.Artemid;

@ParametersAreNonnullByDefault
public class Antlers extends RenderLayer<Artemid, ArtemidModel<Artemid>> {
	public Antlers(RenderLayerParent<Artemid, ArtemidModel<Artemid>> parent) {
		super(parent);
	}

	@Override
	public void render(
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		Artemid artemid,
		float limbSwing,
		float limbSwingAmount,
		float partialTick,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		if (artemid.isBaby() || artemid.isInvisible()) {
			return;
		}
		
		var antlers = Redux.loc("textures/entity/artemid/antlers.png");
		var vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(antlers));
		var overlayCoords = LivingEntityRenderer.getOverlayCoords(artemid, 0.0F);

		this
			.getParentModel()
			.renderToBuffer(poseStack, vertexConsumer, packedLight, overlayCoords);
	}
}
