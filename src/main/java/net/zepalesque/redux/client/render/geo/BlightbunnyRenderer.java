package net.zepalesque.redux.client.render.geo;

import com.aetherteam.aether.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.monster.Blightbunny;
import org.joml.Vector3f;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlightbunnyRenderer extends GeoEntityRenderer<Blightbunny /*TODO*/> {
    public BlightbunnyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlightbunnyModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Blightbunny blightbunny) {
        return BlightbunnyModel.TEXTURE_LOCATION;
    }

    @Override
    public void render(Blightbunny blightbunny, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(blightbunny, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        if (!blightbunny.onGround()) {
            if (blightbunny.getDeltaMovement().y() > 0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTick, 0.0F, 15.0F)));
            } else if (blightbunny.getDeltaMovement().y() < -0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTick, 0.0F, -15.0F)));
            } else {
                poseStack.mulPose(Axis.XN.rotationDegrees((float)(blightbunny.getDeltaMovement().y() * 30.0)));
            }
        }
    }

    public static class BlightbunnyModel extends GeoModel<Blightbunny> {

        private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/mobs/blightbunny.png");
        private static final ResourceLocation GEO_LOCATION = Redux.locate("geo/blightbunny.geo.json");
        private static final ResourceLocation ANIM_LOCATION = Redux.locate("animations/blightbunny.animation.json");

        @Override
        public ResourceLocation getModelResource(Blightbunny blightbunny) {
            return GEO_LOCATION;
        }

        @Override
        public ResourceLocation getTextureResource(Blightbunny blightbunny) {
            return TEXTURE_LOCATION;
        }

        @Override
        public ResourceLocation getAnimationResource(Blightbunny blightbunny) {
            return ANIM_LOCATION;
        }

        @Override
        public void setCustomAnimations(Blightbunny animatable, long instanceId, AnimationState<Blightbunny> animationState) {
            // TODO: Head rotation
            super.setCustomAnimations(animatable, instanceId, animationState);


        }
    }
}
