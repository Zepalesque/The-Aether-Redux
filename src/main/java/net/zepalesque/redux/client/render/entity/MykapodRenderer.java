package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.CubeModel;
import net.zepalesque.redux.client.render.entity.model.entity.MykapodModel;
import net.zepalesque.redux.entity.passive.Mykapod;


public class MykapodRenderer extends LivingEntityRenderer<Mykapod, MykapodModel> {
    private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/mobs/mykapod/mykapod.png");

    public MykapodRenderer(EntityRendererProvider.Context context) {
        super(context, new MykapodModel(context.bakeLayer(ReduxModelLayers.MYKAPOD)), 0);
    }

    @Override
    public void render(Mykapod entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Mykapod entity) {
        return TEXTURE_LOCATION;
    }
}