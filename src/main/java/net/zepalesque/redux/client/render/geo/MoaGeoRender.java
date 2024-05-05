package net.zepalesque.redux.client.render.geo;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.geo.GeoMoa;
import net.zepalesque.redux.entity.passive.Mykapod;
import org.joml.Vector3f;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

public class MoaGeoRender extends GeoReplacedEntityRenderer<Moa, GeoMoa> {
    public MoaGeoRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MoaGeoModel(), new GeoMoa());
    }

    @Override
    public ResourceLocation getTextureLocation(Moa mykapod) {
        return null;
    }

    @Override
    public void render(Moa entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public float getMotionAnimThreshold(GeoMoa animatable) {
        return 0.001F;
    }


    public static class MoaGeoModel extends GeoModel<GeoMoa> {

        private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/mobs/mykapod/mykapod.png");
        private static final ResourceLocation DESHELLED_LOCATION = Redux.locate("textures/entity/mobs/mykapod/mykapod_shed.png");
        private static final ResourceLocation GEO_LOCATION = Redux.locate("geo/mykapod.geo.json");
        private static final ResourceLocation ANIM_LOCATION = Redux.locate("animations/mykapod.animation.json");

        @Override
        public ResourceLocation getModelResource(GeoMoa mykapod) {
            return GEO_LOCATION;
        }

        @Override
        public ResourceLocation getTextureResource(GeoMoa mykapod) {
            // TODO
            return null;
        }

        @Override
        public ResourceLocation getAnimationResource(GeoMoa mykapod) {
            return ANIM_LOCATION;
        }

        @Override
        public void setCustomAnimations(GeoMoa animatable, long instanceId, AnimationState<GeoMoa> animationState) {
            // TODO: Head rotation
            super.setCustomAnimations(animatable, instanceId, animationState);
        }

        @Override
        public void applyMolangQueries(GeoMoa animatable, double animTime) {
            animatable.
            super.applyMolangQueries(animatable, animTime);
        }
    }
}
