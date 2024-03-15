package net.zepalesque.redux.client.render.entity.layer.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

// TODO: What was this for again?
// Answer: the old sheepuff wool layer model
public abstract class ReduxRenderLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public ReduxRenderLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    protected static <T extends LivingEntity> void coloredTranslucentModelCopyLayerRender(EntityModel<T> pModelParent, EntityModel<T> pModel, ResourceLocation pTextureLocation, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, float pPartialTicks, float pRed, float pGreen, float pBlue) {
        if (!pEntity.isInvisible()) {
            pModelParent.copyPropertiesTo(pModel);
            pModel.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
            pModel.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            renderColoredTranslucentModel(pModel, pTextureLocation, pMatrixStack, pBuffer, pPackedLight, pEntity, pRed, pGreen, pBlue);
        }

    }

    protected static <T extends LivingEntity> void renderColoredTranslucentModel(EntityModel<T> pModel, ResourceLocation pTextureLocation, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pEntity, float pRed, float pGreen, float pBlue) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucent(pTextureLocation));
        pModel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pEntity, 0.0F), pRed, pGreen, pBlue, 1.0F);
    }
}
