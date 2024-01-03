package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.CubeModel;
import net.zepalesque.redux.entity.projectile.Ember;

// TODO - Particle-like rendering
public class EmberRenderer extends EntityRenderer<Ember> {

    private final CubeModel box;
    public EmberRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.box = new CubeModel(context.bakeLayer(ReduxModelLayers.CUBE));
    }

    @Override
    public void render(Ember entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
        this.box.renderToBuffer(poseStack, consumer, packedLight, packedLight, 1.0F, 1.0F, 1.0F, 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(Ember entity) {
        return new ResourceLocation("todo");
    }
}
