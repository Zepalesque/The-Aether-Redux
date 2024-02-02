package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.MimicModel;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether_genesis.entity.monster.SkyrootMimic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.fml.ModList;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.client.render.entity.model.entity.MimicReduxModel;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.mixin.client.render.RenderAccessor;
import net.zepalesque.redux.util.math.EasingUtil;

import javax.annotation.Nonnull;
import java.util.Calendar;

public class MimicReduxLayer extends RenderLayer<Mimic, MimicModel> {
    private final MimicReduxModel model;
    private final MobRenderer<Mimic, MimicModel> parentRenderer;

    private boolean isChristmas;
    private static final ResourceLocation REDUX_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/normal_redux.png");
    private static final ResourceLocation REDUX_SKYROOT = new ResourceLocation("aether_genesis", "textures/entity/mobs/mimic/skyroot_redux.png");
    private static final ResourceLocation REDUX_XMAS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/christmas_redux.png");
    private static final ResourceLocation REDUX_LOOTR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/lootr_redux.png");

    public MimicReduxLayer(RenderLayerParent<Mimic, MimicModel> pRenderer, MimicReduxModel mimicModel) {
        super(pRenderer);
        this.model = mimicModel;
        if (pRenderer instanceof MobRenderer<Mimic, MimicModel> rdr) {
            this.parentRenderer = rdr;
        } else {
            this.parentRenderer = null;
        }
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {
            this.isChristmas = true;
        }
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Mimic mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (ReduxConfig.CLIENT.mimic_improvements.get().shouldUseModern())
        {
            this.model.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            MimicAnimation.get(mimic).ifPresent((anim) -> {

                float prevAnim = anim.getPrevOpenAnim() == 0 && anim.getOpenAnim() > 1 ? 10F : anim.getPrevOpenAnim();
                float progress = (10F - Mth.lerp(partialTicks, prevAnim, anim.getOpenAnim())) / 10F;
                if (anim.getOpenAnim() != 0 && anim.getPrevOpenAnim() != 0)
                {
                    this.setShadowRadius((EasingUtil.Sinusoidal.inOut(progress) * 0.5F) + 0.25F);
                } else {
                    this.setShadowRadius(mimic.tickCount <= 1 ? 0.25F : 0.75F);
                }
            });
            if (Minecraft.getInstance().player != null && !mimic.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation tex = getTextureLocation(mimic);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(tex));
                model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(mimic, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
            }
        } else {
            this.setShadowRadius(1.0F);
        }
    }

    private void setShadowRadius(float r) {
        if (this.parentRenderer != null) {
            ((RenderAccessor) this.parentRenderer).setShadowRadius(r);
        }
    }


    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Mimic mimic) {
        return ModList.get().isLoaded("lootr") ? REDUX_LOOTR_TEXTURE : this.isChristmas ? REDUX_XMAS_TEXTURE : Redux.aetherGenesisCompat() && mimic instanceof SkyrootMimic ? REDUX_SKYROOT : REDUX_TEXTURE;
    }
}
