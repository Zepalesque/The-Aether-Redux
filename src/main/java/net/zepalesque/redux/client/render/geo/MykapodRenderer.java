package net.zepalesque.redux.client.render.geo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.passive.Mykapod;
import org.joml.Vector3f;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MykapodRenderer extends GeoEntityRenderer<Mykapod> {
    public MykapodRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MykapodModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Mykapod mykapod) {
        return mykapod.hasShell() ? MykapodModel.TEXTURE_LOCATION : MykapodModel.DESHELLED_LOCATION;
    }

    @Override
    public void render(Mykapod entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (!Minecraft.getInstance().isPaused()) {
            if (entity.getHurtAngle() != 0.0F) {
                poseStack.mulPose(Axis.of(new Vector3f(entity.getHurtAngleX(), 0.0F, -entity.getHurtAngleZ())).rotationDegrees(entity.getHurtAngle() * -15.0F));
            }

            if ((double)entity.getHurtAngle() > 0.0) {
                entity.setHurtAngle(Mth.lerp(partialTick, entity.getHurtAngle(), entity.getHurtAngle() * 0.78F));
            }

            if (LivingEntityRenderer.isEntityUpsideDown(entity)) {
                poseStack.translate(0.0, entity.getBbHeight() + 0.1F, 0.0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }

        if (entity.isBaby()) {
            float babyScale = 0.5F;
            poseStack.scale(babyScale, babyScale, babyScale);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);



    }

    @Override
    public float getMotionAnimThreshold(Mykapod animatable) {
        return 0.005F;
    }


    public static class MykapodModel extends GeoModel<Mykapod> {

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
