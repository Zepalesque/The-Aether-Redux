package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.loading.FMLLoader;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.CubeModel;
import net.zepalesque.redux.entity.passive.Mykapod;
import net.zepalesque.redux.entity.projectile.Ember;


public class CubeRenderer<E extends LivingEntity> extends LivingEntityRenderer<E, EntityModel<E>> {
    private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/test.png");

    private CubeRenderer(EntityRendererProvider.Context context) {
        super(context, new CubeModel<>(context.bakeLayer(ReduxModelLayers.CUBE)), 0);

    }

    public CubeRenderer<?> create(EntityRendererProvider.Context context) {
        if (FMLLoader.isProduction()) {
            Redux.LOGGER.warn("Error occured in renderer registry");
            throw new RuntimeException("Should not be creating CubeRenderer in non-dev environment!");
        } else {
            return new CubeRenderer<>(context);
        }
    }

    @Override
    public void render(E entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(E entity) {
        return TEXTURE_LOCATION;
    }
}