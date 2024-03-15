package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.projectile.Ember;

public class EmberRenderer extends EntityRenderer<Ember> {

    private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/ember.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE_LOCATION);


    public EmberRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Ember entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        int i = 10;
        int alpha = entity.lifetime - entity.tickCount < i ? (int) (255F * ((float)(entity.lifetime - entity.tickCount) / i)) : 255;
        int light = 15728880;
        float size = 0.125F;
        float radius = size / 2;
        poseStack.pushPose();
        poseStack.translate(0.0F, radius, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
        vertex(vertexconsumer, matrix4f, matrix3f, light, -radius, -radius, 0, 1, alpha);
        vertex(vertexconsumer, matrix4f, matrix3f, light, radius, -radius, 1, 1, alpha);
        vertex(vertexconsumer, matrix4f, matrix3f, light, radius, radius, 1, 0, alpha);
        vertex(vertexconsumer, matrix4f, matrix3f, light, -radius, radius, 0, 0, alpha);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f pose, Matrix3f normal, int lightmapUV, float x, float y, int u, int v) {
        vertex(consumer, pose, normal, lightmapUV, x, y, u, v, 255);
    }
    private static void vertex(VertexConsumer consumer, Matrix4f pose, Matrix3f normal, int lightmapUV, float x, float y, int u, int v, int alpha) {
        consumer.vertex(pose, x, y, 0F).color(255, 255, 255, alpha).uv((float)u, (float)v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapUV).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
    }


    @Override
    public ResourceLocation getTextureLocation(Ember entity) {
        return TEXTURE_LOCATION;
    }
}
