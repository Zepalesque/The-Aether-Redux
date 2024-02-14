package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.aetherteam.aether.entity.passive.Sheepuff;
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
import net.zepalesque.redux.client.render.entity.model.entity.SheepuffReduxModel;
import net.zepalesque.redux.config.ReduxConfig;

public class SheepuffReduxLayer extends RenderLayer<Sheepuff, SheepuffModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff_redux.png");

    private final SheepuffReduxModel model;

    public SheepuffReduxLayer(RenderLayerParent<Sheepuff, SheepuffModel> renderer, SheepuffReduxModel model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.sheepuff_model_upgrade.get()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (Minecraft.getInstance().player != null) {
                if (!sheepuff.isInvisibleTo(Minecraft.getInstance().player)) {
                    VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(sheepuff)));
                    this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
                }
            }
        }
    }

    @Override
    protected ResourceLocation getTextureLocation(Sheepuff sheepuff) {
        return TEXTURE;
    }
}
