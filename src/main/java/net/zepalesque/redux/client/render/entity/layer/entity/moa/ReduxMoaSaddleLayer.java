package net.zepalesque.redux.client.render.entity.layer.entity.moa;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.render.entity.model.entity.moa.ReduxMoaModel;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;

@Deprecated
public class ReduxMoaSaddleLayer extends RenderLayer<Moa, ReduxMoaModel> {
    private final ReduxMoaModel saddle;

    public ReduxMoaSaddleLayer(RenderLayerParent<Moa, ReduxMoaModel> entityRenderer, ReduxMoaModel saddleModel) {
        super(entityRenderer);
        this.saddle = saddleModel;
    }

    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (moa.isSaddled()) {
            if (ReduxConfig.CLIENT.moa_improvements.get() && !MoaUtils.overrideModelChange(moa))
            {
                float breathe = MathUtil.breathe(moa, partialTicks);

                if (moa.hurtTime > 0 && moa.hurtTime - partialTicks > 0.0F)
                {
                    int hit = moa.hurtDuration - moa.hurtTime;
                    float hitSmooth = hit + partialTicks;
                    final float baseRot = hitSmooth >= (moa.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth));

                    float rot = baseRot * (((float) Math.PI) * 0.125F);

                    this.saddle.body.xRot = (float) ((0.3333F * rot) + (Math.PI * 0.5F)) + breathe;
                } else {

                    this.saddle.body.xRot = (float) (Math.PI * 0.5F) + breathe;
                }
            }
            this.getParentModel().copyPropertiesTo(this.saddle);
            this.saddle.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
            this.saddle.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(moa.getMoaType().getSaddleTexture()));
            this.saddle.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

    }
}