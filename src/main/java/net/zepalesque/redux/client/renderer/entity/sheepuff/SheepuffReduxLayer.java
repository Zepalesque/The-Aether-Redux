package net.zepalesque.redux.client.renderer.entity.sheepuff;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.config.ReduxConfig;

public class SheepuffReduxLayer extends RenderLayer<Sheepuff, SheepuffModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff_redux.png");

    private final SheepuffReduxModel model;

    public SheepuffReduxLayer(RenderLayerParent<Sheepuff, SheepuffModel> renderer, EntityRendererProvider.Context ctx) {
        super(renderer);
        this.model = new SheepuffReduxModel(ctx.bakeLayer(ReduxRenderers.ModelLayers.SHEEPUFF));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.improved_sheepuffs.get()) {
            var mc = Minecraft.getInstance();
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (mc.player != null)
	            if (!sheepuff.isInvisibleTo(mc.player)) {
		            var consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(sheepuff)));
		            this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F));
	            }
        }
    }

    @Override
    protected ResourceLocation getTextureLocation(Sheepuff sheepuff) {
        return TEXTURE;
    }
}
