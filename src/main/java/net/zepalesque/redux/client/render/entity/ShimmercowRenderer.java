//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.entity.passive.FlyingCow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.MushroomCowMushroomLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.ShimmercowLayer;
import net.zepalesque.redux.client.render.entity.model.entity.ShimmercowModel;
import net.zepalesque.redux.entity.passive.Shimmercow;

public class ShimmercowRenderer extends MobRenderer<Shimmercow, ShimmercowModel<Shimmercow>> {
    private static final ResourceLocation SHIMMERCOW_TEX = Redux.locate("textures/entity/mobs/shimmercow/shimmercow.png");
    private static final ResourceLocation CRAZY_COW_TEX = Redux.locate("textures/entity/mobs/shimmercow/crazy_cow.png");

    public ShimmercowRenderer(EntityRendererProvider.Context context) {
        super(context, new ShimmercowModel<>(context.bakeLayer(ReduxModelLayers.SHIMMERCOW)), 0.7F);
        this.addLayer(new ShimmercowLayer<>(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public void render(Shimmercow p_entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1.25F, 1.25F, 1.25F);
        super.render(p_entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(Shimmercow shimmerCow) {
        return shimmerCow.isCrazy() ? CRAZY_COW_TEX : SHIMMERCOW_TEX;
    }
}
