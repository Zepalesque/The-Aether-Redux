package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.CubeModel;
import net.zepalesque.redux.entity.passive.Mykapod;
import net.zepalesque.redux.entity.projectile.Ember;


public class CubeRenderer<E extends Entity> extends EntityRenderer<E> {
    private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/test.png");

    private final CubeModel box;
    public CubeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.box = new CubeModel(context.bakeLayer(ReduxModelLayers.CUBE));
    }

    @Override
    public void render(E entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        if (entity instanceof Mykapod m) {
            float f = Mth.lerp(partialTick, m.prevHideAnim, m.hideAnim) / 60;
            this.box.renderToBuffer(poseStack, consumer, packedLight, packedLight, 1.0F, 1.0F, 1.0F, 1.0F - f);
        } else {
            this.box.renderToBuffer(poseStack, consumer, packedLight, packedLight, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(E entity) {
        return TEXTURE_LOCATION;
    }
}