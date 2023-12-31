package net.zepalesque.redux.client.render.entity.model.entity.sheepuff;

import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.zepalesque.redux.config.ReduxConfig;

import javax.annotation.Nonnull;

public class ReduxSheepuffModel extends SheepuffModel {
    protected float headXRot;

    public ReduxSheepuffModel(ModelPart root) {
        super(root);
    }


    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        if (!ReduxConfig.CLIENT.sheepuff_improvements.get())
            super.renderToBuffer(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
    public void prepareMobModel(@Nonnull Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTicks);
        this.headXRot = sheepuff.getHeadEatAngleScale(partialTicks) == sheepuff.getXRot() * 0.017453292 ? (float) (Mth.lerp(partialTicks, sheepuff.xRotO, sheepuff.getXRot()) * 0.017453292) : sheepuff.getHeadEatAngleScale(partialTicks);
    }
    public void setupAnim(@Nonnull Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.xRot = this.headXRot;
    }

}
