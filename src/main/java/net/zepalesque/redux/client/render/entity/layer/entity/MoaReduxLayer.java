package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.capability.animation.moa.MoaAnimation;
import net.zepalesque.redux.client.render.entity.model.entity.MoaReduxModel;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;

public class MoaReduxLayer extends RenderLayer<Moa, MoaModel> {

    protected final MoaRenderer parent;
    private final MoaReduxModel updated;

    public MoaReduxLayer(MoaRenderer entityRenderer, MoaReduxModel pUpdated) {
        super(entityRenderer);
        this.updated = pUpdated;
        this.parent = entityRenderer;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (MoaUtils.useNewModel(moa)) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0F, 1.5F, /*-0.125F*/ 0F);
            MoaReduxModel model = this.updated;
            model.neck.yRot = this.getParentModel().head.yRot * 0.333F;
            model.neck.xRot = this.getParentModel().head.xRot * 0.125F;
            model.head_part.yRot = this.getParentModel().head.yRot * 0.667F;
            model.head_part.xRot = this.getParentModel().head.xRot * 0.875F;

            model.jaw.xRot = this.getParentModel().jaw.xRot;


            float breathe = MathUtil.breathe(moa, partialTicks);
            if (moa.hurtTime > 0 && moa.hurtTime - partialTicks > 0.0F)
            {
                int hit = moa.hurtDuration - moa.hurtTime;
                float hitSmooth = hit + partialTicks;
                final float baseRot = hitSmooth >= (moa.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth));

                float rot = baseRot * (((float) Math.PI) * 0.125F);
                model.neck_hurtanim.xRot = 0.6667F * rot;
                model.body_additions.xRot = (0.3333F * rot) + breathe;
//                this.parent.getModel().body.xRot = (float) (Math.PI * 0.5F) + (0.3333F * rot) - breathe;
                model.head_hurtanim.xRot = -rot;
            } else {
                model.neck_hurtanim.xRot = breathe * 0.5F;
                model.head_hurtanim.xRot = -1.5F * breathe;
                model.neck_hurtanim.yRot = 0.0F;
                model.head_hurtanim.yRot =  0.0F;
                model.body_additions.xRot = breathe;
//                this.parent.getModel().body.xRot = (float) (Math.PI * 0.5F) - breathe;
            }


            model.middle_feather.xRot = MathUtil.breatheBase(moa, partialTicks, 0.1F, 0.1F, 0.0F);
            model.left_feather.xRot = MathUtil.breatheBase(moa, partialTicks, 0.1F, 0.1F, 0.6667F);
            model.right_feather.xRot = MathUtil.breatheBase(moa, partialTicks, 0.1F, 0.1F, 0.3333F);

            model.wing_1.xRot = (this.getParentModel().rightWing.xRot * 0.625F) + (( (float) Math.PI) * 0.5F) - (( (float) Math.PI) * 0.08333F) ;
            model.z_rot_wing_1.zRot = (this.getParentModel().rightWing.yRot * 0.875F) + (( (float) Math.PI) * 0.08333F) - breathe;
            model.wing_2.xRot = (this.getParentModel().leftWing.xRot * 0.625F) + (( (float) Math.PI) * 0.5F) - (( (float) Math.PI) * 0.08333F) ;
            model.z_rot_wing_2.zRot = (this.getParentModel().leftWing.yRot * 0.875F) - (( (float) Math.PI) * 0.08333F) + breathe;

            model.feathers_3_wing1.xRot = (moa.isEntityOnGround() ? 0F : MathUtil.degToRad(-45F)) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.0F);
            model.feathers_2_wing1.xRot = (moa.isEntityOnGround() ? 0F : MathUtil.degToRad(-30F)) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.3333F);
            model.feathers_1_wing1.xRot = (moa.isEntityOnGround() ? 0F : MathUtil.degToRad(-25F)) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.6667F);
            model.feathers_3_wing2.xRot = model.feathers_3_wing1.xRot;
            model.feathers_2_wing2.xRot = model.feathers_2_wing1.xRot;
            model.feathers_1_wing2.xRot = model.feathers_1_wing1.xRot;


            if (!moa.isSitting() || !moa.isEntityOnGround() && moa.isSitting()) {
                model.leg1.skipDraw = false;
                model.leg2.skipDraw = false;
                float progress = moa.isEntityOnGround() ? 0 : 1;

                if (MoaAnimation.get(moa).isPresent()) {
                    MoaAnimation moaAnimation = MoaAnimation.get(moa).orElse(null);
                    if (moaAnimation != null)
                        progress = Mth.lerp(partialTicks, moaAnimation.getPrevLegAnim(), moaAnimation.getLegAnim()) * 0.2F;
                }
                float left = Mth.cos((float) ((double) (limbSwing * 0.6662F) + Math.PI)) * 1.4F * limbSwingAmount;
                float right = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

                model.leg1.xRot = MathUtil.costrp(progress, left * 0.8F, MathUtil.degToRad(15F));
                model.leg2.xRot = MathUtil.costrp(progress, right * 0.8F, MathUtil.degToRad(15F));
                model.lower_leg1.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos(limbSwing * 0.6662F), -left) * 0.3333F * limbSwingAmount, MathUtil.degToRad(25F));
                model.lower_leg2.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos((float) ((double) (limbSwing * 0.6662F) + Math.PI)), -right) * 0.333F * limbSwingAmount, MathUtil.degToRad(25F));
                model.toes_stepanim_leg1.xRot = MathUtil.costrp(progress, -model.lower_leg1.xRot * 0.3333F, 0F);
                model.toes_stepanim_leg2.xRot = MathUtil.costrp(progress, -model.lower_leg2.xRot * 0.3333F, 0F);
                model.toes_leg1.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(10F));
                model.toes_leg2.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(10F));
                model.back_toes_leg1.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(-75F));
                model.back_toes_leg2.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(-75F));
            } else {
                model.leg1.skipDraw = true;
                model.leg2.skipDraw = true;
            }


            model.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (Minecraft.getInstance().player != null && !moa.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation feathersLoc = getTextureLocation(moa);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(feathersLoc));
                model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
            }
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(Moa moa) {
        ResourceLocation tex = this.parent.getTextureLocation(moa);
        ResourceLocation addTex = new ResourceLocation(tex.getNamespace(), tex.getPath().replace(".png", "_additions.png"));
        return addTex;
    }
}
