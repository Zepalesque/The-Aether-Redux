package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.RebuxModel;
import net.zepalesque.redux.entity.misc.Rebux;
import net.zepalesque.redux.entity.projectile.ThrownSpear;

import java.util.HashMap;
import java.util.Map;

public class RebuxRenderer extends EntityRenderer<Rebux> {
    private final RebuxModel model;
    public static final ResourceLocation TEX = Redux.locate("textures/entity/rebux/rebux.png");
    private static final Map<Integer, ResourceLocation> SHINE_MAP = new HashMap<>();
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEX);

    public RebuxRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RebuxModel(context.bakeLayer(ReduxModelLayers.REBUX));
    }


    public void render(Rebux entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, -0.375F, 0.0F);
        float f = 0.5F;
        poseStack.scale(f, f, f);
        this.model.coin.yRot = (entity.tickCount + partialTicks) * Mth.PI / 25F;
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.0F, -0.375F, 0.0F);
        poseStack.scale(f, f, f);
        VertexConsumer shine = buffer.getBuffer(RenderType.entityTranslucentEmissive(this.getShineLocation(entity)));
        this.model.renderToBuffer(poseStack, shine, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(Rebux entity) {
        return TEX;
    }

    protected ResourceLocation getShineLocation(Rebux entity) {
        int ticks = entity.tickCount + entity.getId();
        int rem = 50 - (ticks % 50);
        int i =  rem > 13 || rem == 7 ? 0 : rem > 7 ? rem - 1 : rem;
        if (SHINE_MAP.containsKey(i)) {
            return SHINE_MAP.get(i);
        } else {
            ResourceLocation rl = Redux.locate("textures/entity/rebux/rebux_glow_" + i + ".png");
            SHINE_MAP.put(i, rl);
            return rl;
        }
    }
}