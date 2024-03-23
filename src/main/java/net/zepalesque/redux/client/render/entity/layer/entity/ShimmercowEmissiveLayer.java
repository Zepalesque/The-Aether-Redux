package net.zepalesque.redux.client.render.entity.layer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.passive.Shimmercow;

public class ShimmercowEmissiveLayer<T extends Shimmercow, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private static final ResourceLocation SHIMMERCOW_TEX = Redux.locate("textures/entity/mobs/shimmercow/shimmercow_glow.png");
    private static final ResourceLocation CRAZY_COW_TEX = Redux.locate("textures/entity/mobs/shimmercow/crazy_cow_glow.png");


    private static final RenderType SHIMMERCOW = RenderType.entityTranslucentEmissive(SHIMMERCOW_TEX);
    private static final RenderType CRAZY_COW = RenderType.entityTranslucentEmissive(CRAZY_COW_TEX);

    public ShimmercowEmissiveLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexconsumer = buffer.getBuffer(entity.isCrazy() ? CRAZY_COW : SHIMMERCOW);
        int overlay = LivingEntityRenderer.getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTicks));
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    protected float getWhiteOverlayProgress(T livingEntity, float partialTicks) {
        return 0.0F;
    }

    @Override
    public RenderType renderType() {
        return SHIMMERCOW;
    }
}
