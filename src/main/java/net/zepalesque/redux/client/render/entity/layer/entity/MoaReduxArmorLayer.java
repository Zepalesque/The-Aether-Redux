package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.protect_your_moa.capability.armor.MoaArmor;
import com.aetherteam.protect_your_moa.client.renderer.entity.ProtectModelLayers;
import com.aetherteam.protect_your_moa.item.combat.DyeableMoaArmorItem;
import com.aetherteam.protect_your_moa.item.combat.MoaArmorItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.capability.animation.moa.MoaAnimation;
import net.zepalesque.redux.client.render.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.MoaReduxModel;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.MoaModelType;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nullable;


// organization: zero
// my soul is in critical pain
public class MoaReduxArmorLayer extends RenderLayer<Moa, MoaModel> {
    private final MoaReduxModel newArmor, oldArmor;
    private final MoaReduxLayer layerParent;

    public MoaReduxArmorLayer(RenderLayerParent<Moa, MoaModel> renderer, MoaReduxLayer layerParent, EntityModelSet modelSet) {
        super(renderer);
        this.newArmor = new MoaReduxModel(modelSet.bakeLayer(ReduxModelLayers.MOA_ARMOR_NEW));
        this.oldArmor = new MoaReduxModel(modelSet.bakeLayer(ReduxModelLayers.MOA_ARMOR_OLD));
        this.layerParent = layerParent;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (MoaUtils.useNewModel(moa)) {
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0F, 1.5F, /*-0.125F*/ 0F);
            MoaArmor.get(moa).ifPresent((moaArmor) -> {
            ItemStack itemStack = moaArmor.getArmor();
            if (itemStack != null && !itemStack.isEmpty()) {
                Item item = itemStack.getItem();
                if (item instanceof MoaArmorItem moaArmorItem) {
                    MoaReduxModel model = this.setupAnimAndModel(moa, limbSwing, limbSwingAmount, partialTick);
                    model.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTick);
                    model.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    float f = 1.0F;
                    float f1 = 1.0F;
                    float f2 = 1.0F;
                    if (item instanceof DyeableMoaArmorItem dyeable) {
                        int i = dyeable.getColor(moaArmor.getArmor());
                        f = (float)(i >> 16 & 255) / 255.0F;
                        f1 = (float)(i >> 8 & 255) / 255.0F;
                        f2 = (float)(i & 255) / 255.0F;
                        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(dyeable.getOverlayTexture()));
                        model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    }

                    VertexConsumer vertexconsumerx = buffer.getBuffer(RenderType.entityCutoutNoCull(moaArmorItem.getTexture()));
                    model.renderToBuffer(poseStack, vertexconsumerx, packedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
                }
            }

        });
            poseStack.popPose();
        }
    }

    private MoaReduxModel setupAnimAndModel(Moa moa, float limbSwing, float limbSwingAmount, float partialTicks) {

        boolean useOriginalLegs = ReduxConfig.CLIENT.moa_model_type.get() == MoaModelType.refreshed;
        MoaReduxModel model = useOriginalLegs ? this.newArmor : this.oldArmor;


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

        model.feathers_3_wing1.xRot = (moa.isEntityOnGround() ? 0F : -45F * Mth.DEG_TO_RAD) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.0F);
        model.feathers_2_wing1.xRot = (moa.isEntityOnGround() ? 0F : -30F * Mth.DEG_TO_RAD) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.3333F);
        model.feathers_1_wing1.xRot = (moa.isEntityOnGround() ? 0F : -25F * Mth.DEG_TO_RAD) -  MathUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.6667F);
        model.feathers_3_wing2.xRot = model.feathers_3_wing1.xRot;
        model.feathers_2_wing2.xRot = model.feathers_2_wing1.xRot;
        model.feathers_1_wing2.xRot = model.feathers_1_wing1.xRot;

        float progress = moa.isEntityOnGround() ? 0 : 1;
        float swingCalc = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        if (MoaAnimation.get(moa).isPresent()) {
            MoaAnimation moaAnimation = MoaAnimation.get(moa).orElseThrow(() -> new IllegalStateException("Could not find MoaAnimation capability!"));
            progress = Mth.lerp(partialTicks, moaAnimation.getPrevLegAnim(), moaAnimation.getLegAnim()) * 0.2F;
        }

        if ((!moa.isSitting() || (!moa.isEntityOnGround() && moa.isSitting())) && !useOriginalLegs) {
            model.leg1.skipDraw = false;
            model.leg2.skipDraw = false;

//                float left = Mth.cos((float) ((double) (limbSwing * 0.6662F) + Math.PI)) * 1.4F * limbSwingAmount;
//                float right = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

            model.leg1.xRot = MathUtil.costrp(progress, -swingCalc * 0.8F, 15F * Mth.DEG_TO_RAD);
            model.leg2.xRot = MathUtil.costrp(progress, swingCalc * 0.8F, 15F * Mth.DEG_TO_RAD);
            model.lower_leg1.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos(limbSwing * 0.6662F), swingCalc) * 0.3333F * limbSwingAmount, 25F * Mth.DEG_TO_RAD);
            model.lower_leg2.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos((float) ((double) (limbSwing * 0.6662F) + Math.PI)), -swingCalc) * 0.333F * limbSwingAmount, 25F * Mth.DEG_TO_RAD);
            model.toes_stepanim_leg1.xRot = MathUtil.costrp(progress, -model.lower_leg1.xRot * 0.3333F, 0F);
            model.toes_stepanim_leg2.xRot = MathUtil.costrp(progress, -model.lower_leg2.xRot * 0.3333F, 0F);
            model.toes_leg1.xRot = MathUtil.costrp(progress, 0F, 10F * Mth.DEG_TO_RAD);
            model.toes_leg2.xRot = MathUtil.costrp(progress, 0F, 10F * Mth.DEG_TO_RAD);
            model.back_toes_leg1.xRot = MathUtil.costrp(progress, 0F, -75F * Mth.DEG_TO_RAD);
            model.back_toes_leg2.xRot = MathUtil.costrp(progress, 0F, -75F * Mth.DEG_TO_RAD);
        } else {
            model.leg1.skipDraw = true;
            model.leg2.skipDraw = true;
            this.getParentModel().rightLeg.xRot = MathUtil.costrp(progress, swingCalc, 0.6F);
            this.getParentModel().leftLeg.xRot = MathUtil.costrp(progress, -swingCalc, 0.6F);
        }
        return model;
    }
}
