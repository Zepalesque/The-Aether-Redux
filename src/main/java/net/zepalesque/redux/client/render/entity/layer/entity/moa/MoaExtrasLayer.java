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
import net.zepalesque.redux.client.render.entity.model.entity.moa.MoaExtrasModel;
import net.zepalesque.redux.client.render.entity.model.entity.moa.ReduxMoaModel;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;

// TODO: Move to mixin, merge with other layers
public class MoaExtrasLayer extends RenderLayer<Moa, ReduxMoaModel> {
    private final MoaExtrasModel updated;

    private static final ResourceLocation MOS_TEXTURE = new  ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/mos_additions.png");
    private static final ResourceLocation RAPTOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/raptor_additions.png");

    protected final RenderLayerParent<Moa, ReduxMoaModel> parent;

    public MoaExtrasLayer(RenderLayerParent<Moa, ReduxMoaModel> entityRenderer, MoaExtrasModel pUpdated) {
        super(entityRenderer);
        this.updated = pUpdated;
        this.parent = entityRenderer;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.moa_improvements.get() && !MoaUtils.overrideModelChange(moa)) {
            MoaExtrasModel model = this.updated;
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
                model.head_hurtanim.xRot = -rot;
            } else {
                model.neck_hurtanim.xRot = breathe * 0.5F;
                model.head_hurtanim.xRot = -1.5F * breathe;
                model.neck_hurtanim.yRot = 0.0F;
                model.head_hurtanim.yRot =  0.0F;
                model.body_additions.xRot = breathe;
            }
            model.middle_feather.xRot = MathUtil.breatheBase(moa, partialTicks, 0.1F, 0.1F, 0.0F);
            model.left_feather.xRot = MathUtil.breatheBase(moa, partialTicks, 0.1F, 0.1F, 0.6667F);
            model.right_feather.xRot = MathUtil.breatheBase(moa, partialTicks, 0.1F, 0.1F, 0.3333F);

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