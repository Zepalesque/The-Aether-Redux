//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.GlimmercowModel;
import net.zepalesque.redux.entity.passive.Glimmercow;

public class GlimmercowRenderer extends MobRenderer<Glimmercow, GlimmercowModel<Glimmercow>> {
    private static final ResourceLocation GLIMMERCOW_TEX = Redux.locate("textures/entity/mobs/glimmercow/glimmercow.png");
    private static final ResourceLocation CRAZY_COW_TEX = Redux.locate("textures/entity/mobs/glimmercow/crazy_cow.png");

    public GlimmercowRenderer(EntityRendererProvider.Context context) {
        super(context, new GlimmercowModel<>(context.bakeLayer(ReduxModelLayers.GLIMMERCOW)), 0.7F);
    }

    @Override
    public void render(Glimmercow p_entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1.25F, 1.25F, 1.25F);
        super.render(p_entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(Glimmercow shimmerCow) {
        return shimmerCow.isCrazy() ? CRAZY_COW_TEX : GLIMMERCOW_TEX;
    }

    @Override
    protected boolean isShaking(Glimmercow entity) {
        return super.isShaking(entity) || entity.isCrazy();
    }
}
