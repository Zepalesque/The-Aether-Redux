package net.zepalesque.redux.client.render.geo;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.model.entity.MykapodModel;
import net.zepalesque.redux.entity.passive.Mykapod;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class MykapodGeoRenderer extends GeoEntityRenderer<Mykapod> {
    public MykapodGeoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MykapodGeoModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Mykapod mykapod) {
        return mykapod.hasShell() ? MykapodGeoModel.TEXTURE_LOCATION : MykapodGeoModel.DESHELLED_LOCATION;
    }

    @Override
    public void render(Mykapod entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        if (entity.isBaby()) {
            float babyScale = 0.5F;
            poseStack.scale(babyScale, babyScale, babyScale);
        }
    }

    public static class MykapodGeoModel extends GeoModel<Mykapod> {

        private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/mobs/mykapod/mykapod.png");
        private static final ResourceLocation DESHELLED_LOCATION = Redux.locate("textures/entity/mobs/mykapod/mykapod_shed.png");
        private static final ResourceLocation GEO_LOCATION = Redux.locate("geo/mykapod.geo.json");
        private static final ResourceLocation ANIM_LOCATION = Redux.locate("animations/mykapod.animation.json");

        @Override
        public ResourceLocation getModelResource(Mykapod mykapod) {
            return GEO_LOCATION;
        }

        @Override
        public ResourceLocation getTextureResource(Mykapod mykapod) {
            return mykapod.hasShell() ? TEXTURE_LOCATION : DESHELLED_LOCATION;
        }

        @Override
        public ResourceLocation getAnimationResource(Mykapod mykapod) {
            return ANIM_LOCATION;
        }

        @Override
        public void setCustomAnimations(Mykapod animatable, long instanceId, AnimationState<Mykapod> animationState) {
            // TODO: Head rotation
            super.setCustomAnimations(animatable, instanceId, animationState);
        }
    }
}
