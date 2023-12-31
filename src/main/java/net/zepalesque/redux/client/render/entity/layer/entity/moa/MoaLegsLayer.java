package net.zepalesque.redux.client.render.entity.layer.entity.moa;

import com.aetherteam.aether.Aether;
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
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.MoaFeetType;
import net.zepalesque.redux.capability.animation.moa.MoaAnimation;
import net.zepalesque.redux.client.render.entity.model.entity.moa.MoaLegsModel;
import net.zepalesque.redux.client.render.entity.model.entity.moa.ReduxMoaModel;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;

// TODO: Move to mixin, merge with other layers
public class MoaLegsLayer extends RenderLayer<Moa, ReduxMoaModel> {
    private final MoaLegsModel toes;
    private final MoaLegsModel talons;

    protected final RenderLayerParent<Moa, ReduxMoaModel> parent;

    private static final ResourceLocation MOS_TEXTURE = new ResourceLocation(Aether.MODID,"textures/entity/mobs/moa/mos_additions.png");
    private static final ResourceLocation RAPTOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/raptor_additions.png");

    public MoaLegsLayer(RenderLayerParent<Moa, ReduxMoaModel> entityRenderer, MoaLegsModel toesModel, MoaLegsModel talonsModel) {
        super(entityRenderer);
        this.toes = toesModel;
        this.talons = talonsModel;
        this.parent = entityRenderer;

    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.moa_improvements.get() && !MoaUtils.overrideModelChange(moa) && (!moa.isSitting() || !moa.isEntityOnGround() && moa.isSitting())) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0F, 1.5F, -0.125F);
            float progress = moa.isEntityOnGround() ? 0 : 1;

            if (MoaAnimation.get(moa).isPresent())
            {
                MoaAnimation moaAnimation = MoaAnimation.get(moa).orElse(null);
                if (moaAnimation != null)
                    progress = Mth.lerp(partialTicks, moaAnimation.getPrevLegAnim(), moaAnimation.getLegAnim()) * 0.2F;
            }
            float left = Mth.cos((float)((double)(limbSwing * 0.6662F) + Math.PI)) * 1.4F * limbSwingAmount;
            float right = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

                this.getLegs().leg1.xRot = MathUtil.costrp(progress, left * 0.8F, MathUtil.degToRad(15F));
                this.getLegs().leg2.xRot = MathUtil.costrp(progress, right * 0.8F, MathUtil.degToRad(15F));
                this.getLegs().lower_leg1.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos(limbSwing * 0.6662F), -left) * 0.3333F * limbSwingAmount, MathUtil.degToRad(25F));
                this.getLegs().lower_leg2.xRot = MathUtil.costrp(progress, -MathUtil.returnZeroWhenNegative(MathUtil.animCos((float) ((double) (limbSwing * 0.6662F) + Math.PI)), -right) * 0.333F * limbSwingAmount, MathUtil.degToRad(25F));
                this.getLegs().toes_stepanim_leg1.xRot = MathUtil.costrp(progress, -this.toes.lower_leg1.xRot * 0.3333F, 0F);
                this.getLegs().toes_stepanim_leg2.xRot = MathUtil.costrp(progress, -this.toes.lower_leg2.xRot * 0.3333F, 0F);
                this.getLegs().toes_leg1.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(10F));
                this.getLegs().toes_leg2.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(10F));
                this.getLegs().back_toes_leg1.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(-75F));
                this.getLegs().back_toes_leg2.xRot = MathUtil.costrp(progress, 0F, MathUtil.degToRad(-75F));
                this.getLegs().setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);


            if (!moa.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation feathersLoc = getTextureLocation(moa);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(feathersLoc));
                this.getLegs().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
            }
        }

    }

    protected MoaLegsModel getLegs()
    {
        return ReduxConfig.CLIENT.moa_feet_type.get() == MoaFeetType.toes ? this.toes : this.talons;
    }


    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(Moa moa) {
        ResourceLocation tex = this.parent.getTextureLocation(moa);
        return new ResourceLocation(tex.getNamespace(), tex.getPath().replace(".png", "_additions.png"));
    }
}