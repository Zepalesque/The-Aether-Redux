//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.TranslucentGlowLayer;
import net.zepalesque.redux.client.render.entity.model.entity.BlightbunnyModel;
import net.zepalesque.redux.entity.monster.Blightbunny;

public class BlightbunnyRenderer extends MobRenderer<Blightbunny, BlightbunnyModel> {
    private static final ResourceLocation BLIGHTBUNNY_TEXTURE = Redux.locate("textures/entity/mobs/blightbunny/blightbunny.png");
    private static final ResourceLocation BLIGHTBUNNY_GLOW_TEXTURE = Redux.locate("textures/entity/mobs/blightbunny/blightbunny_glow.png");

    public BlightbunnyRenderer(EntityRendererProvider.Context context) {
        super(context, new BlightbunnyModel(context.bakeLayer(ReduxModelLayers.BLIGHTBUNNY)), 0.3F);
        this.addLayer(new TranslucentGlowLayer<>(this, BLIGHTBUNNY_GLOW_TEXTURE));
    }

    protected void scale(Blightbunny aerbunny, PoseStack poseStack, float partialTicks) {
        poseStack.translate(0.0, 1.2, 0.0);
    }

    protected void setupRotations(Blightbunny aerbunny, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(aerbunny, poseStack, ageInTicks, rotationYaw, partialTicks);
        if (!aerbunny.onGround()) {
            if (aerbunny.getDeltaMovement().y() > 0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTicks, 0.0F, 15.0F)));
            } else if (aerbunny.getDeltaMovement().y() < -0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTicks, 0.0F, -15.0F)));
            } else {
                poseStack.mulPose(Axis.XN.rotationDegrees((float)(aerbunny.getDeltaMovement().y() * 30.0)));
            }
        }

    }

    public ResourceLocation getTextureLocation(Blightbunny aerbunny) {
        return BLIGHTBUNNY_TEXTURE;
    }
}
