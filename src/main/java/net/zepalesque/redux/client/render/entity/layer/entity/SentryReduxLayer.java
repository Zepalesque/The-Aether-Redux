package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.render.entity.model.entity.SentryReduxModel;
import net.zepalesque.redux.config.ReduxConfig;

public class SentryReduxLayer extends RenderLayer<Sentry, SlimeModel<Sentry>> {

    private static final ResourceLocation SENTRY_OFF = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry_dormant_redux.png");
    private static final ResourceLocation SENTRY_ON = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry_redux.png");
    private static final RenderType SENTRY_EYE = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry_eye_redux.png"));

    private final SentryReduxModel<Sentry> model;

    public SentryReduxLayer(RenderLayerParent<Sentry, SlimeModel<Sentry>> renderer, SentryReduxModel<Sentry> model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sentry sentry, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.sentry_model_upgrade.get() || ReduxConfig.CLIENT.enable_all_model_upgrades.get()) {
            this.model.setupAnim(sentry, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (Minecraft.getInstance().player != null) {
                if (!sentry.isInvisibleTo(Minecraft.getInstance().player)) {
                    ResourceLocation modelLoc = shouldBeOn(sentry) ? SENTRY_ON : SENTRY_OFF;
                    VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(modelLoc));
                    this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sentry, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);

                }
                VertexConsumer eyesConsumer = buffer.getBuffer(SENTRY_EYE);
                this.model.renderToBuffer(poseStack, eyesConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(sentry, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }


    public boolean shouldBeOn(Sentry sentry)
    {
        if (sentry.hurtTime > 0) {
            return Mth.sin(sentry.hurtTime) >= 0;
        } else {
            return sentry.isAwake();
        }
    }
}
