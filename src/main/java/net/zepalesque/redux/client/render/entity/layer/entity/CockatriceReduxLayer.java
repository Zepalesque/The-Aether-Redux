package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aether.entity.monster.Cockatrice;
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
import net.zepalesque.redux.capability.cockatrice.CockatriceExtension;
import net.zepalesque.redux.client.render.entity.model.entity.CockatriceReduxModel;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.CockatriceModelType;
import net.zepalesque.redux.util.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CockatriceReduxLayer extends RenderLayer<Cockatrice, CockatriceModel> {
    private final CockatriceReduxModel legacy, refreshed;

    private static final ResourceLocation COCKATRICE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_redux.png");
    private static final RenderType COCKATRICE_MARKINGS = RenderType.entityTranslucentEmissive(new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_emissive_redux.png"));
    private static final RenderType COCKATRICE_EYES = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_eyes_redux.png"));


    public CockatriceReduxLayer(RenderLayerParent<Cockatrice, CockatriceModel> entityRenderer, CockatriceReduxModel legsModel, CockatriceReduxModel refreshed) {
        super(entityRenderer);
        this.legacy = legsModel;
        this.refreshed = refreshed;

    }


    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @NotNull Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if ((ReduxConfig.CLIENT.cockatrice_model_upgrade.get()) && (Minecraft.getInstance().player == null || !cockatrice.isInvisibleTo(Minecraft.getInstance().player))) {
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0F, 1.5F, /*-0.125F*/ 0F);
            CockatriceReduxModel model = ReduxConfig.CLIENT.cockatrice_model_type.get() == CockatriceModelType.legacy ? this.legacy : this.refreshed;
            float progress = cockatrice.isEntityOnGround() ? 0 : 1;
            float progressAttack = 0F;
            float progressAttackAlways = 0F;
            float swingCalc = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;


            if (CockatriceExtension.get(cockatrice).isPresent()) {
                CockatriceExtension cockatriceAnim = CockatriceExtension.get(cockatrice).orElseThrow(() -> new IllegalStateException("Could not find CockatriceExtension capability!"));
                progress = Mth.lerp(partialTicks, cockatriceAnim.getPrevLegAnim(), cockatriceAnim.getLegAnim()) * 0.2F;
                progressAttack = (1F - progress) * Mth.lerp(partialTicks, cockatriceAnim.getPrevTargetAnim(), cockatriceAnim.getTargetAnim()) * 0.1F;
                progressAttackAlways = Mth.lerp(partialTicks, cockatriceAnim.getPrevTargetAnim(), cockatriceAnim.getTargetAnim()) * 0.1F;
            }
            if (ReduxConfig.CLIENT.cockatrice_model_type.get() != CockatriceModelType.refreshed) {
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


            model.neck.yRot = this.getParentModel().head.yRot * 0.333F;
            model.neck.xRot = (this.getParentModel().head.xRot * 0.125F);
            model.head.yRot = this.getParentModel().head.yRot * 0.667F;
            model.head.xRot = this.getParentModel().head.xRot * 0.875F;
            model.jaw.xRot = this.getParentModel().jaw.xRot;

            float breathe = MathUtil.cockatriceBreathing(cockatrice, partialTicks);

            boolean doHurtAnim = cockatrice.hurtTime > 0 && cockatrice.hurtTime - partialTicks > 0.0F;
            int hit = cockatrice.hurtDuration - cockatrice.hurtTime;
            float hitSmooth = hit + partialTicks;
            final float baseRot = hitSmooth >= (cockatrice.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth));
            float rot = MathUtil.costrp(progressAttack,baseRot * (((float) Math.PI) * 0.175F), baseRot * (((float) Math.PI) * 0.125F));
            float shortened = (Math.min(0.75F, progressAttack)) * (4F / 3F);
            float shortenedAlways = (Math.min(0.75F, progressAttackAlways)) * (4F / 3F);
            float delayed = (Math.max(0F, progressAttack - 0.5F)) * 2F;

            model.neck_hurtanim.xRot = (!doHurtAnim ? 0.0F : 0.6667F * rot) + MathUtil.costrp(shortened,0F, 40F * Mth.DEG_TO_RAD);
            model.body.xRot =  (!doHurtAnim ? breathe : (0.3333F * rot) + breathe) +MathUtil.costrp(shortened,0F, -60F * Mth.DEG_TO_RAD);
            model.lower_tail.xRot = MathUtil.costrp(shortened,0F, 40F * Mth.DEG_TO_RAD);
            model.lower_tail.setPos(0F, MathUtil.costrp(delayed,0F,-10F) + 6f,5F);
            model.head_hurtanim.xRot = (!doHurtAnim ? 0.0F : -rot) + MathUtil.costrp(shortened,0F, 20F * Mth.DEG_TO_RAD);
            model.crown_feather.xRot = MathUtil.costrp(shortened,0F, 30F * Mth.DEG_TO_RAD) +(breathe * 1.5F);
            model.wing_2.setPos(8.01F, 4F,-8 + MathUtil.costrp(delayed,0F,1.5F));
            model.wing_1.z = model.wing_2.z;


            model.wing_1.xRot = MathUtil.costrp(shortened,(this.getParentModel().rightWing.xRot * 0.625F) + (10F* Mth.DEG_TO_RAD), 0F ) + (90F * Mth.DEG_TO_RAD);
            model.wing_1.yRot = MathUtil.costrp(shortened,0F, (-40F * Mth.DEG_TO_RAD));
            model.wing_2.xRot = model.wing_1.xRot;
            model.wing_2.yRot = -model.wing_1.yRot;
            model.ribcage.xRot = MathUtil.costrp(shortened,0F, 90F * Mth.DEG_TO_RAD);

            model.claw1.xRot = MathUtil.costrp(shortened,0F, -65F * Mth.DEG_TO_RAD);
            model.claw1.yRot = MathUtil.costrp(shortened,0F, -35F * Mth.DEG_TO_RAD);

            model.claw2.xRot = model.claw1.xRot;
            model.claw2.yRot = -model.claw1.yRot;

            model.z_rot_wing_1.zRot = MathUtil.costrp(shortened,(this.getParentModel().rightWing.yRot * 0.875F) + (( (float) Math.PI) * 0.08333F),0F) - breathe;
            model.z_rot_wing_1.xRot = MathUtil.costrp(delayed,0F, -85F * Mth.DEG_TO_RAD);
            model.z_rot_wing_1.yRot = -model.z_rot_wing_1.xRot;
            model.z_rot_wing_2.zRot = -model.z_rot_wing_1.zRot;
            model.z_rot_wing_2.xRot = model.z_rot_wing_1.xRot;
            model.z_rot_wing_2.yRot = -model.z_rot_wing_1.yRot;


            model.feathers_3_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : -45F * Mth.DEG_TO_RAD) - MathUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.0F) + MathUtil.costrp(shortenedAlways,0F, 20F * Mth.DEG_TO_RAD);
            model.feathers_2_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : -30F * Mth.DEG_TO_RAD) - MathUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.3333F) + MathUtil.costrp(shortenedAlways,0F, 15F * Mth.DEG_TO_RAD);
            model.feathers_1_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : -25F * Mth.DEG_TO_RAD) - MathUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.6667F) + MathUtil.costrp(shortenedAlways,0F, 20F * Mth.DEG_TO_RAD);
            model.feathers_3_wing2.xRot = model.feathers_3_wing1.xRot;
            model.feathers_2_wing2.xRot = model.feathers_2_wing1.xRot;
            model.feathers_1_wing2.xRot = model.feathers_1_wing1.xRot;

            model.feathers_3_wing1.yRot = MathUtil.costrp(shortenedAlways,0F, 20F * Mth.DEG_TO_RAD);
            model.feathers_2_wing1.yRot = MathUtil.costrp(shortenedAlways,0F, 25F * Mth.DEG_TO_RAD);
            model.feathers_1_wing1.yRot = MathUtil.costrp(shortenedAlways,0F, 25F * Mth.DEG_TO_RAD);
            model.feathers_3_wing2.yRot = -model.feathers_3_wing1.yRot;
            model.feathers_2_wing2.yRot = -model.feathers_2_wing1.yRot;
            model.feathers_1_wing2.yRot = -model.feathers_1_wing1.yRot;


            model.head_feather_left.yRot = MathUtil.costrp(shortenedAlways,0F, 50F * Mth.DEG_TO_RAD) + MathUtil.breatheBase(cockatrice, partialTicks, 0.15F, 0.1F, 0.125F);
            model.head_feather_right.yRot = -model.head_feather_left.yRot;
            model.head_feather_top.xRot = MathUtil.costrp(shortenedAlways,0F, 50F * Mth.DEG_TO_RAD) + MathUtil.breatheBase(cockatrice, partialTicks, 0.15F, 0.1F, 0.0F);

            model.middle_feather.xRot =  MathUtil.staggeredBreatheBase(cockatrice, partialTicks, MathUtil.costrp(progressAttack, 0.1F, 0.05F), 0.1F, 0.0F, 7.3F, 0.15F) + MathUtil.costrp(progressAttack, 0F, 40F * Mth.DEG_TO_RAD);
            model.left_feather.xRot = MathUtil.staggeredBreatheBase(cockatrice, partialTicks, MathUtil.costrp(progressAttack, 0.1F, 0.05F), 0.1F, 0.6667F, 7.3F, 0.15F) + MathUtil.costrp(progressAttack, 0F, 55F * Mth.DEG_TO_RAD);
            model.right_feather.xRot = MathUtil.staggeredBreatheBase(cockatrice, partialTicks, MathUtil.costrp(progressAttack, 0.1F, 0.05F), 0.1F, 0.3333F, 7.3F, 0.15F) + MathUtil.costrp(progressAttack, 0F, 55F * Mth.DEG_TO_RAD);
            model.right_feather.zRot = MathUtil.costrp(progressAttack, 0F, 10F * Mth.DEG_TO_RAD);
            model.left_feather.zRot = model.right_feather.zRot;

            model.top_feather_2.zRot = MathUtil.costrp(progressAttackAlways,0F, 10F * Mth.DEG_TO_RAD) + breathe;
            model.top_feather_2.yRot = MathUtil.costrp(progressAttackAlways,0F, 45F * Mth.DEG_TO_RAD) + breathe;
            model.top_feather_1.zRot = model.top_feather_2.zRot;
            model.top_feather_1.yRot = -model.top_feather_2.yRot;


            if (!cockatrice.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation feathersLoc = getTextureLocation(cockatrice);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(feathersLoc));
                model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
                VertexConsumer emissiveConsumer = buffer.getBuffer(COCKATRICE_MARKINGS);
                model.renderToBuffer(poseStack, emissiveConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);

            }

            VertexConsumer eyesConsumer = buffer.getBuffer(COCKATRICE_EYES);
            model.renderToBuffer(poseStack, eyesConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }

    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Cockatrice cockatrice) {
        return COCKATRICE_TEXTURE;
    }

}