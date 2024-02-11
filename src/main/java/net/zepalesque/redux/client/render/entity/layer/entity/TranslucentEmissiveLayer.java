package net.zepalesque.redux.client.render.entity.layer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class TranslucentEmissiveLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M> {

    private final ResourceLocation texture;
    public TranslucentEmissiveLayer(RenderLayerParent<T, M> renderer, ResourceLocation texture) {
        super(renderer);
        this.texture = texture;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexconsumer = buffer.getBuffer(this.renderType());
        int overlay = OverlayTexture.NO_OVERLAY;
        if (entity instanceof LivingEntity le) {
            overlay = LivingEntityRenderer.getOverlayCoords(le, this.getWhiteOverlayProgress(entity, partialTicks));
        }
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    protected float getWhiteOverlayProgress(T livingEntity, float partialTicks) {
        return 0.0F;
    }

    @Override
    public RenderType renderType() {
        return RenderType.entityTranslucentEmissive(texture);
    }
}
