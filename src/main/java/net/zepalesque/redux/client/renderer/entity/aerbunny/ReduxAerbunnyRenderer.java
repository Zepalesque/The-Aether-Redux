package net.zepalesque.redux.client.renderer.entity.aerbunny;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.AerbunnyRenderer;
import com.aetherteam.aether.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.renderer.ReduxRenderers;

// TODO: implement switching between models and base aether model usage and whatnot
public final class ReduxAerbunnyRenderer extends MobRenderer<Aerbunny, ReduxAerbunnyModel> {
	private static final ResourceLocation REDUX_AERBUNNY_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/aerbunny/aerbunny_redux.png");
	
	public ReduxAerbunnyRenderer(EntityRendererProvider.Context context) {
		super(context, new ReduxAerbunnyModel(context.bakeLayer(ReduxRenderers.ModelLayers.AERBUNNY)), 0.3F);
	}
	
	@Override
	protected void scale(Aerbunny aerbunny, PoseStack poseStack, float partialTicks) {
		if (aerbunny.isBaby()) poseStack.scale(0.5F, 0.5F, 0.5F);
//		poseStack.translate(0.0, 0.2, 0.0);
	}
	
	@Override
	protected void setupRotations(Aerbunny aerbunny, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
		super.setupRotations(aerbunny, poseStack, bob, yBodyRot, partialTick, scale);
		if (!aerbunny.onGround()) if (aerbunny.getDeltaMovement().y() > 0.5)
			poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTick, 0.0F, 15.0F)));
		else if (aerbunny.getDeltaMovement().y() < -0.5)
			poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTick, 0.0F, -15.0F)));
		else
			poseStack.mulPose(Axis.XN.rotationDegrees((float) (aerbunny.getDeltaMovement().y() * 30.0)));
	}
	
	@Override
	public ResourceLocation getTextureLocation(Aerbunny aerbunny) {
		return REDUX_AERBUNNY_TEXTURE;
	}
}
