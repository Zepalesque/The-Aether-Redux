package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.ForgeHooksClient;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.CubeModel;
import net.zepalesque.redux.entity.projectile.Ember;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.Iterator;

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
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
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
