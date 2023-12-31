package net.zepalesque.redux.client.render.entity.layer.entity.cockatrice;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.BipedBirdModel;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class ReduxCockatriceMarkingsLayer<T extends Cockatrice, M extends BipedBirdModel<T>> extends RenderLayer<T, M> {
    private static final RenderType COCKATRICE_MARKINGS = RenderType.entityTranslucentEmissive(new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_emissive.png"));

    public ReduxCockatriceMarkingsLayer(RenderLayerParent<T, M> entityRenderer) {
        super(entityRenderer);
    }
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, @Nonnull T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.renderType());
        this.getParentModel().renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Nonnull
    public RenderType renderType() {
        return COCKATRICE_MARKINGS;
    }
}
