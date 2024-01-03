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

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Redux.MODID, "textures/entity/ember.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(TEXTURE_LOCATION);

    protected final ItemRenderer itemRenderer;

    private final CubeModel box;
    public EmberRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.box = new CubeModel(context.bakeLayer(ReduxModelLayers.CUBE));
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(Ember entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
//        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
        float size = 0.25F;
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, 0.0F, 0, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, size, 0.0F, 1, 1);
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, size, size, 1, 0);
        vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, size, 0, 0);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f pose, Matrix3f normal, int lightmapUV, float x, float y, int u, int v) {
        consumer.vertex(pose, x - 0.125F, y + 0.125F, 0F).color(255, 255, 255, 255).uv((float)u, (float)v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapUV).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
    }


    @Override
    public ResourceLocation getTextureLocation(Ember entity) {
        return TEXTURE_LOCATION;
    }
}
