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
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.capability.cockatrice.CockatriceExtension;
import net.zepalesque.redux.client.render.entity.model.entity.CockatriceReduxModel;
import net.zepalesque.redux.util.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CockatriceReduxLayer extends RenderLayer<Cockatrice, CockatriceModel> {
    private final CockatriceReduxModel model;

    private static final ResourceLocation COCKATRICE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_redux.png");
    private static final RenderType COCKATRICE_MARKINGS = RenderType.entityTranslucentEmissive(new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_emissive_redux.png"));
    private static final RenderType COCKATRICE_EYES = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_eyes_redux.png"));


    public CockatriceReduxLayer(RenderLayerParent<Cockatrice, CockatriceModel> entityRenderer, CockatriceReduxModel legsModel) {
        super(entityRenderer);
        this.model = legsModel;

    }


    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @NotNull Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.cockatrice_model_upgrade.get() && !cockatrice.isInvisibleTo(Minecraft.getInstance().player)) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0F, 1.5F, -0.125F);
            float progress = cockatrice.isEntityOnGround() ? 0 : 1;
            float progressAttack = 0F;
            float progressAttackAlways = 0F;

            if (CockatriceExtension.get(cockatrice).isPresent())
            {
                CockatriceExtension cockatriceAnim = CockatriceExtension.get(cockatrice).orElse(null);
                if (cockatriceAnim != null) {
                    progress = Mth.lerp(partialTicks, cockatriceAnim.getPrevLegAnim(), cockatriceAnim.getLegAnim()) * 0.2F;
                    progressAttack = (1F - progress) * Mth.lerp(partialTicks, cockatriceAnim.getPrevTargetAnim(), cockatriceAnim.getTargetAnim()) * 0.1F;
                    progressAttackAlways = Mth.lerp(partialTicks, cockatriceAnim.getPrevTargetAnim(), cockatriceAnim.getTargetAnim()) * 0.1F;
                }
            }
            float left = Mth.cos((float)((double)(limbSwing * 0.6662F) + Math.PI)) * 1.4F * limbSwingAmount;
            float right = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

            this.model.leg1.xRot = MathUtil.costrp(progress, left * 0.8F, MathUtil.degToRad(15F));
            this.model.leg2.xRot = MathUtil.costrp(progress, right * 0.8F, MathUtil.degToRad(15F));
            this.model.lower_leg1.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos(limbSwing * 0.6662F), -left) * 0.3333F * limbSwingAmount, MathUtil.degToRad(25F));
            this.model.lower_leg2.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos((float)((double)(limbSwing * 0.6662F) + Math.PI)), -right) * 0.333F * limbSwingAmount, MathUtil.degToRad(25F));
            this.model.toes_stepanim_leg1.xRot = MathUtil.costrp(progress, -this.model.lower_leg1.xRot * 0.3333F, 0F);
            this.model.toes_stepanim_leg2.xRot = MathUtil.costrp(progress, -this.model.lower_leg2.xRot * 0.3333F, 0F);
            this.model.toes_leg1.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(10F));
            this.model.toes_leg2.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(10F));
            this.model.back_toes_leg1.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(-75F));
            this.model.back_toes_leg2.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(-75F));

            this.model.neck.yRot = this.getParentModel().head.yRot * 0.333F;
            this.model.neck.xRot = (this.getParentModel().head.xRot * 0.125F);
            this.model.head.yRot = this.getParentModel().head.yRot * 0.667F;
            this.model.head.xRot = this.getParentModel().head.xRot * 0.875F;
            this.model.jaw.xRot = this.getParentModel().jaw.xRot;

            float breathe = MathUtil.cockatriceBreathing(cockatrice, partialTicks);

            boolean doHurtAnim = cockatrice.hurtTime > 0 && cockatrice.hurtTime - partialTicks > 0.0F;
            int hit = cockatrice.hurtDuration - cockatrice.hurtTime;
            float hitSmooth = hit + partialTicks;
            final float baseRot = hitSmooth >= (cockatrice.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth));
            float rot = MathUtil.costrp(progressAttack,baseRot * (((float) Math.PI) * 0.175F), baseRot * (((float) Math.PI) * 0.125F));
            float shortened = (Math.min(0.75F, progressAttack)) * (4F / 3F);
            float shortenedAlways = (Math.min(0.75F, progressAttackAlways)) * (4F / 3F);
            float delayed = (Math.max(0F, progressAttack - 0.5F)) * 2F;

            this.model.neck_hurtanim.xRot = (!doHurtAnim ? 0.0F : 0.6667F * rot) + MathUtil.costrp(shortened,0F, MathUtil.degToRad(40F));
            this.model.body.xRot =  (!doHurtAnim ? breathe : (0.3333F * rot) + breathe) +MathUtil.costrp(shortened,0F, MathUtil.degToRad(-60F));
            this.model.lower_tail.xRot = MathUtil.costrp(shortened,0F, MathUtil.degToRad(40F));
            this.model.lower_tail.setPos(0F, MathUtil.costrp(delayed,0F,-10F) + 6f,5F);
            this.model.head_hurtanim.xRot = (!doHurtAnim ? 0.0F : -rot) + MathUtil.costrp(shortened,0F, MathUtil.degToRad(20F));
            this.model.crown_feather.xRot = MathUtil.costrp(shortened,0F, MathUtil.degToRad(30F)) +(breathe * 1.5F);
            this.model.wing_2.setPos(8.01F, 4F,-8 + MathUtil.costrp(delayed,0F,1.5F));
            this.model.wing_1.z = this.model.wing_2.z;


            this.model.wing_1.xRot = MathUtil.costrp(shortened,(this.getParentModel().rightWing.xRot * 0.625F) + MathUtil.degToRad(10F), 0F ) + MathUtil.degToRad(90F);
            this.model.wing_1.yRot = MathUtil.costrp(shortened,0F, -MathUtil.degToRad(40F));
            this.model.wing_2.xRot = this.model.wing_1.xRot;
            this.model.wing_2.yRot = -this.model.wing_1.yRot;
            this.model.ribcage.xRot = MathUtil.costrp(shortened,0F, MathUtil.degToRad(90F));

            this.model.claw1.xRot = MathUtil.costrp(shortened,0F, MathUtil.degToRad(-65F));
            this.model.claw1.yRot = MathUtil.costrp(shortened,0F, MathUtil.degToRad(-35F));

            this.model.claw2.xRot = this.model.claw1.xRot;
            this.model.claw2.yRot = -this.model.claw1.yRot;

            this.model.z_rot_wing_1.zRot = MathUtil.costrp(shortened,(this.getParentModel().rightWing.yRot * 0.875F) + (( (float) Math.PI) * 0.08333F),0F) - breathe;
            this.model.z_rot_wing_1.xRot = MathUtil.costrp(delayed,0F, MathUtil.degToRad(-85F));
            this.model.z_rot_wing_1.yRot = -this.model.z_rot_wing_1.xRot;
            this.model.z_rot_wing_2.zRot = -this.model.z_rot_wing_1.zRot;
            this.model.z_rot_wing_2.xRot = this.model.z_rot_wing_1.xRot;
            this.model.z_rot_wing_2.yRot = -this.model.z_rot_wing_1.yRot;


            this.model.feathers_3_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : MathUtil.degToRad(-45F)) - MathUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.0F) + MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(20F));
            this.model.feathers_2_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : MathUtil.degToRad(-30F)) - MathUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.3333F) + MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(15F));
            this.model.feathers_1_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : MathUtil.degToRad(-25F)) - MathUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.6667F) + MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(20F));
            this.model.feathers_3_wing2.xRot = this.model.feathers_3_wing1.xRot;
            this.model.feathers_2_wing2.xRot = this.model.feathers_2_wing1.xRot;
            this.model.feathers_1_wing2.xRot = this.model.feathers_1_wing1.xRot;

            this.model.feathers_3_wing1.yRot = MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(20F));
            this.model.feathers_2_wing1.yRot = MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(25F));
            this.model.feathers_1_wing1.yRot = MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(25F));
            this.model.feathers_3_wing2.yRot = -this.model.feathers_3_wing1.yRot;
            this.model.feathers_2_wing2.yRot = -this.model.feathers_2_wing1.yRot;
            this.model.feathers_1_wing2.yRot = -this.model.feathers_1_wing1.yRot;


            this.model.head_feather_left.yRot = MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(50F)) + MathUtil.breatheBase(cockatrice, partialTicks, 0.15F, 0.1F, 0.125F);
            this.model.head_feather_right.yRot = -this.model.head_feather_left.yRot;
            this.model.head_feather_top.xRot = MathUtil.costrp(shortenedAlways,0F, MathUtil.degToRad(50F)) + MathUtil.breatheBase(cockatrice, partialTicks, 0.15F, 0.1F, 0.0F);

            this.model.middle_feather.xRot =  MathUtil.staggeredBreatheBase(cockatrice, partialTicks, MathUtil.costrp(progressAttack, 0.1F, 0.05F), 0.1F, 0.0F, 7.3F, 0.15F) + MathUtil.costrp(progressAttack, 0F, MathUtil.degToRad(40F));
            this.model.left_feather.xRot = MathUtil.staggeredBreatheBase(cockatrice, partialTicks, MathUtil.costrp(progressAttack, 0.1F, 0.05F), 0.1F, 0.6667F, 7.3F, 0.15F) + MathUtil.costrp(progressAttack, 0F, MathUtil.degToRad(55F));
            this.model.right_feather.xRot = MathUtil.staggeredBreatheBase(cockatrice, partialTicks, MathUtil.costrp(progressAttack, 0.1F, 0.05F), 0.1F, 0.3333F, 7.3F, 0.15F) + MathUtil.costrp(progressAttack, 0F, MathUtil.degToRad(55F));
            this.model.right_feather.zRot = MathUtil.costrp(progressAttack, 0F, MathUtil.degToRad(10F));
            this.model.left_feather.zRot = this.model.right_feather.zRot;

            this.model.top_feather_2.zRot = MathUtil.costrp(progressAttackAlways,0F, MathUtil.degToRad(10F)) + breathe;
            this.model.top_feather_2.yRot = MathUtil.costrp(progressAttackAlways,0F, MathUtil.degToRad(45F)) + breathe;
            this.model.top_feather_1.zRot = this.model.top_feather_2.zRot;
            this.model.top_feather_1.yRot = -this.model.top_feather_2.yRot;


            if (!cockatrice.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation feathersLoc = getTextureLocation(cockatrice);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(feathersLoc));
                this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
                VertexConsumer emissiveConsumer = buffer.getBuffer(COCKATRICE_MARKINGS);
                this.model.renderToBuffer(poseStack, emissiveConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);

            }

            VertexConsumer eyesConsumer = buffer.getBuffer(COCKATRICE_EYES);
            this.model.renderToBuffer(poseStack, eyesConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);

        }

    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Cockatrice cockatrice) {
        return COCKATRICE_TEXTURE;
    }

}