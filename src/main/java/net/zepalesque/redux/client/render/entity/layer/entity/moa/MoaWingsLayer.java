package net.zepalesque.redux.client.render.entity.layer.entity.moa;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.render.entity.model.entity.moa.MoaWingsModel;
import net.zepalesque.redux.client.render.entity.model.entity.moa.ReduxMoaModel;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;

// TODO: Move to mixin, merge with other layers
public class MoaWingsLayer extends RenderLayer<Moa, ReduxMoaModel> {
    private final MoaWingsModel wings;
    protected final RenderLayerParent<Moa, ReduxMoaModel> parent;

    public MoaWingsLayer(RenderLayerParent<Moa, ReduxMoaModel> entityRenderer, MoaWingsModel wingsModel) {
        super(entityRenderer);
        this.wings = wingsModel;
        this.parent = entityRenderer;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.moa_improvements.get() && !MoaUtils.overrideModelChange(moa)) {

            float breathe = MathUtil.breathe(moa, partialTicks);

            this.wings.wing_1.xRot = (this.getParentModel().rightWing.xRot * 0.625F) + (( (float) Math.PI) * 0.5F) - (( (float) Math.PI) * 0.08333F) ;
            this.wings.z_rot_wing_1.zRot = (this.getParentModel().rightWing.yRot * 0.875F) + (( (float) Math.PI) * 0.08333F) - breathe;
            this.wings.wing_2.xRot = (this.getParentModel().leftWing.xRot * 0.625F) + (( (float) Math.PI) * 0.5F) - (( (float) Math.PI) * 0.08333F) ;
            this.wings.z_rot_wing_2.zRot = (this.getParentModel().leftWing.yRot * 0.875F) - (( (float) Math.PI) * 0.08333F) + breathe;

            this.wings.feathers_3_wing1.xRot = (moa.isEntityOnGround() ? 0F : MathUtil.degToRad(-45F)) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.0F);
            this.wings.feathers_2_wing1.xRot = (moa.isEntityOnGround() ? 0F : MathUtil.degToRad(-30F)) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.3333F);
            this.wings.feathers_1_wing1.xRot = (moa.isEntityOnGround() ? 0F : MathUtil.degToRad(-25F)) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.6667F);
            this.wings.feathers_3_wing2.xRot = this.wings.feathers_3_wing1.xRot;
            this.wings.feathers_2_wing2.xRot = this.wings.feathers_2_wing1.xRot;
            this.wings.feathers_1_wing2.xRot = this.wings.feathers_1_wing1.xRot;
            if (moa.hurtTime > 0 && moa.hurtTime - partialTicks > 0.0F)
            {
                int hit = moa.hurtDuration - moa.hurtTime;
                float hitSmooth = hit + partialTicks;
                final float baseRot = hitSmooth >= (moa.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth));
                float rot = baseRot * (((float) Math.PI) * 0.125F);
                this.wings.body_additions.xRot = (0.3333F * rot) + breathe;
            } else {
                this.wings.body_additions.xRot = breathe;
            }
            this.wings.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (!moa.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation feathersLoc = getTextureLocation(moa);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(feathersLoc));
                this.wings.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
            }
        }

    }



    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(Moa moa) {
        ResourceLocation tex = this.parent.getTextureLocation(moa);
        return new ResourceLocation(tex.getNamespace(), tex.getPath().replace(".png", "_additions.png"));
    }
}