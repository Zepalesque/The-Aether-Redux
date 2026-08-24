package net.zepalesque.redux.client.renderer.entity.cockatrice;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.attachment.anim.CockatriceAnimAttachment;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.util.LegacyAnimUtil;
import net.zepalesque.zenith.util.math.EasingUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CockatriceReduxLayer extends RenderLayer<Cockatrice, CockatriceModel> {
    private final CockatriceReduxModel model;

    private static final ResourceLocation COCKATRICE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_redux.png");
    private static final RenderType COCKATRICE_MARKINGS = RenderType.entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_emissive_redux.png"));
    private static final RenderType COCKATRICE_EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_eyes_redux.png"));


    public CockatriceReduxLayer(RenderLayerParent<Cockatrice, CockatriceModel> entityRenderer, EntityRendererProvider.Context ctx) {
        super(entityRenderer);
        this.model = new CockatriceReduxModel(ctx.bakeLayer(ReduxRenderers.ModelLayers.COCKATRICE));
    }


    // TODO: chat wtf is this spaghetti code
    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @NotNull Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.improved_cockatrices.get() && (Minecraft.getInstance().player == null || !cockatrice.isInvisibleTo(Minecraft.getInstance().player))) {
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0F, 1.5F, /*-0.125F*/ 0F);
//            float swingCalc = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

            var model = this.model;
            
            var att = CockatriceAnimAttachment.get(cockatrice);
	        var progress = att.getLegAnim(cockatrice, partialTicks);
            var progressAttackAlways = att.getTargetAnim(cockatrice, partialTicks);
            var progressAttack = (1f - progress) * progressAttackAlways;
            
            var progressAttackDelta = EasingUtil.Sinusoidal.inOut(progressAttack);
            var progressAttackAlwaysDelta = EasingUtil.Sinusoidal.inOut(progressAttackAlways);
	        
	        model.leg1.skipDraw = true;
	        model.leg2.skipDraw = true;
	        
	        
	        model.neck.yRot = this.getParentModel().head.yRot * 0.333F;
            model.neck.xRot = this.getParentModel().head.xRot * 0.125F;
            model.head.yRot = this.getParentModel().head.yRot * 0.667F;
            model.head.xRot = this.getParentModel().head.xRot * 0.875F;
            model.jaw.xRot = this.getParentModel().jaw.xRot;
	        
	        var breathe = LegacyAnimUtil.cockatriceBreathing(cockatrice, partialTicks);
	        
	        var doHurtAnim = cockatrice.hurtTime > 0 && cockatrice.hurtTime - partialTicks > 0.0F;
	        var hit = cockatrice.hurtDuration - cockatrice.hurtTime;
	        var hitSmooth = hit + partialTicks;
            final var baseRot = hitSmooth >= cockatrice.hurtDuration * 0.25F + 0.0F ? -Mth.cos(0.133333333F * (float) Math.PI * (hitSmooth + 5.0F)) + 1 : -Mth.cos(0.4F * (float) Math.PI * hitSmooth);
	        
	        var rotDelta = EasingUtil.Sinusoidal.inOut(progressAttack);
	        var rot = Mth.lerp(rotDelta,baseRot * (Mth.PI * 0.175F), baseRot * (Mth.PI * 0.125F));
	        
	        var shortened = Math.min(0.75F, progressAttack) * (4F / 3F);
	        var shortenedAlways = Math.min(0.75F, progressAttackAlways) * (4F / 3F);
	        var delayed = Math.max(0F, progressAttack - 0.5F) * 2F;

            var shortenedDelta = EasingUtil.Sinusoidal.inOut(shortened);
            var shortenedAlwaysDelta = EasingUtil.Sinusoidal.inOut(shortenedAlways);
            var delayedDelta = EasingUtil.Sinusoidal.inOut(delayed);
            
            model.neck_hurtanim.xRot = (!doHurtAnim ? 0.0F : 0.6667F * rot) + Mth.lerp(shortenedDelta,0F, 40F * Mth.DEG_TO_RAD);
            
            
            model.body.xRot =  (!doHurtAnim ? breathe : 0.3333F * rot + breathe) + Mth.lerp(shortenedDelta,0F, -60F * Mth.DEG_TO_RAD);
            model.lower_tail.xRot = Mth.lerp(shortenedDelta,0F, 40F * Mth.DEG_TO_RAD);
            model.lower_tail.setPos(0F, Mth.lerp(delayedDelta, 0F, -10F) + 6f, 5F);
            model.head_hurtanim.xRot = (!doHurtAnim ? 0.0F : -rot)
                + Mth.lerp(shortenedDelta, 0F, 20F * Mth.DEG_TO_RAD);
            model.crown_feather.xRot = Mth.lerp(shortenedDelta,0F, 30F * Mth.DEG_TO_RAD)
                + breathe * 1.5F;
            model.wing_2.setPos(8.01F, 4F, -8 + Mth.lerp(delayedDelta, 0F, 1.5F));
            model.wing_1.z = model.wing_2.z;


            model.wing_1.xRot = Mth.lerp(
                shortenedDelta,
                this.getParentModel().rightWing.xRot
                    * 0.625F
                    + 10F
                    * Mth.DEG_TO_RAD,
                0F
            ) + 90F * Mth.DEG_TO_RAD;
            model.wing_1.yRot = Mth.lerp(shortenedDelta,0F, -40F * Mth.DEG_TO_RAD);
            model.wing_2.xRot = model.wing_1.xRot;
            model.wing_2.yRot = -model.wing_1.yRot;
            model.ribcage.xRot = Mth.lerp(shortenedDelta,0F, 90F * Mth.DEG_TO_RAD);

            model.claw1.xRot = Mth.lerp(shortenedDelta,0F, -65F * Mth.DEG_TO_RAD);
            model.claw1.yRot = Mth.lerp(shortenedDelta,0F, -35F * Mth.DEG_TO_RAD);

            model.claw2.xRot = model.claw1.xRot;
            model.claw2.yRot = -model.claw1.yRot;

            model.z_rot_wing_1.zRot = Mth.lerp(
                shortenedDelta,
                this.getParentModel().rightWing.yRot
                    * 0.875F
                    + Mth.PI
                    * 0.08333F,
                0F
            ) - breathe;
            model.z_rot_wing_1.xRot = Mth.lerp(delayedDelta,0F, -85F * Mth.DEG_TO_RAD);
            model.z_rot_wing_1.yRot = -model.z_rot_wing_1.xRot;
            model.z_rot_wing_2.zRot = -model.z_rot_wing_1.zRot;
            model.z_rot_wing_2.xRot = model.z_rot_wing_1.xRot;
            model.z_rot_wing_2.yRot = -model.z_rot_wing_1.yRot;


            model.feathers_3_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : -45F * Mth.DEG_TO_RAD) - LegacyAnimUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.0F) + Mth.lerp(shortenedAlwaysDelta,0F, 20F * Mth.DEG_TO_RAD);
            model.feathers_2_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : -30F * Mth.DEG_TO_RAD) - LegacyAnimUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.3333F) + Mth.lerp(shortenedAlwaysDelta,0F, 15F * Mth.DEG_TO_RAD);
            model.feathers_1_wing1.xRot = (cockatrice.isEntityOnGround() ? 0F : -25F * Mth.DEG_TO_RAD) - LegacyAnimUtil.breatheBase(cockatrice, partialTicks, 0.025F, 0.1F, 0.6667F) + Mth.lerp(shortenedAlwaysDelta,0F, 20F * Mth.DEG_TO_RAD);
            model.feathers_3_wing2.xRot = model.feathers_3_wing1.xRot;
            model.feathers_2_wing2.xRot = model.feathers_2_wing1.xRot;
            model.feathers_1_wing2.xRot = model.feathers_1_wing1.xRot;

            model.feathers_3_wing1.yRot = Mth.lerp(shortenedAlwaysDelta,0F, 20F * Mth.DEG_TO_RAD);
            model.feathers_2_wing1.yRot = Mth.lerp(shortenedAlwaysDelta,0F, 25F * Mth.DEG_TO_RAD);
            model.feathers_1_wing1.yRot = Mth.lerp(shortenedAlwaysDelta,0F, 25F * Mth.DEG_TO_RAD);
            model.feathers_3_wing2.yRot = -model.feathers_3_wing1.yRot;
            model.feathers_2_wing2.yRot = -model.feathers_2_wing1.yRot;
            model.feathers_1_wing2.yRot = -model.feathers_1_wing1.yRot;


            model.head_feather_left.yRot = Mth.lerp(shortenedAlwaysDelta,0F, 50F * Mth.DEG_TO_RAD) + LegacyAnimUtil.breatheBase(cockatrice, partialTicks, 0.15F, 0.1F, 0.125F);
            model.head_feather_right.yRot = -model.head_feather_left.yRot;
            model.head_feather_top.xRot = Mth.lerp(shortenedAlwaysDelta,0F, 50F * Mth.DEG_TO_RAD) + LegacyAnimUtil.breatheBase(cockatrice, partialTicks, 0.15F, 0.1F, 0.0F);

            model.middle_feather.xRot =  LegacyAnimUtil.staggeredBreatheBase(cockatrice, partialTicks, Mth.lerp(progressAttackDelta, 0.1F, 0.05F), 0.1F, 0.0F, 7.3F, 0.15F) + Mth.lerp(progressAttackDelta, 0F, 40F * Mth.DEG_TO_RAD);
            model.left_feather.xRot = LegacyAnimUtil.staggeredBreatheBase(cockatrice, partialTicks, Mth.lerp(progressAttackDelta, 0.1F, 0.05F), 0.1F, 0.6667F, 7.3F, 0.15F) + Mth.lerp(progressAttackDelta, 0F, 55F * Mth.DEG_TO_RAD);
            model.right_feather.xRot = LegacyAnimUtil.staggeredBreatheBase(cockatrice, partialTicks, Mth.lerp(progressAttackDelta, 0.1F, 0.05F), 0.1F, 0.3333F, 7.3F, 0.15F) + Mth.lerp(progressAttackDelta, 0F, 55F * Mth.DEG_TO_RAD);
            model.right_feather.zRot = Mth.lerp(progressAttackAlways, 0F, 10F * Mth.DEG_TO_RAD);
            model.left_feather.zRot = model.right_feather.zRot;

            model.top_feather_2.zRot = Mth.lerp(progressAttackAlwaysDelta,0F, 10F * Mth.DEG_TO_RAD) + breathe;
            model.top_feather_2.yRot = Mth.lerp(progressAttackAlwaysDelta,0F, 45F * Mth.DEG_TO_RAD) + breathe;
            model.top_feather_1.zRot = model.top_feather_2.zRot;
            model.top_feather_1.yRot = -model.top_feather_2.yRot;

            var mc = Minecraft.getInstance();

            if (mc.player != null &&!cockatrice.isInvisibleTo(mc.player)) {
	            var feathersLoc = this.getTextureLocation(cockatrice);
	            var consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(feathersLoc));
                model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F));
	            var emissiveConsumer = buffer.getBuffer(COCKATRICE_MARKINGS);
                model.renderToBuffer(poseStack, emissiveConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F));

            }
	        
	        var eyesConsumer = buffer.getBuffer(COCKATRICE_EYES);
            model.renderToBuffer(poseStack, eyesConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(cockatrice, 0.0F));
            poseStack.popPose();
        }

    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Cockatrice cockatrice) {
        return COCKATRICE_TEXTURE;
    }
}