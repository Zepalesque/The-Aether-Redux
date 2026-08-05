package net.zepalesque.redux.client.renderer.entity.catfish;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.entity.CatFish;

public class CatFishRenderer extends MobRenderer<CatFish, CatFishModel<CatFish>> {
	private static final ResourceLocation TEXTURE = Redux.loc("textures/entity/cat_fish.png");

	public CatFishRenderer(EntityRendererProvider.Context context) {
		super(context, new CatFishModel<CatFish>(context.bakeLayer(ReduxRenderers.ModelLayers.CAT_FISH)), 0.3F);
	}

	public ResourceLocation getTextureLocation(CatFish entity) {
		return TEXTURE;
	}

	protected void setupRotations(
		CatFish entity,
		PoseStack poseStack,
		float bob,
		float yBodyRot,
		float partialTick,
		float scale
	) {
		super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
		float f = 4.3F * Mth.sin(0.6F * bob);
		poseStack.mulPose(Axis.YP.rotationDegrees(f));
		if (!entity.isInWater()) {
			poseStack.translate(0.1F, 0.1F, -0.1F);
			poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}

	}
}
