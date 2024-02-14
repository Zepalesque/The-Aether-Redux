package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.passive.Phyg;
import com.aetherteam.aether.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.render.entity.model.entity.PhygReduxModel;
import net.zepalesque.redux.client.render.entity.model.entity.SheepuffReduxModel;
import net.zepalesque.redux.config.ReduxConfig;

public class PhygReduxLayer extends RenderLayer<Phyg, PigModel<Phyg>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg_redux.png");

    private final PhygReduxModel<Phyg> model;

    public PhygReduxLayer(RenderLayerParent<Phyg, PigModel<Phyg>> renderer, PhygReduxModel<Phyg> model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Phyg phyg, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.phyg_model_upgrade.get()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(phyg, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(phyg, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (Minecraft.getInstance().player != null) {
                if (!phyg.isInvisibleTo(Minecraft.getInstance().player)) {
                    VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(phyg)));
                    this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(phyg, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
                }
            }
        }
    }

    @Override
    protected ResourceLocation getTextureLocation(Phyg phyg) {
        return TEXTURE;
    }
}
