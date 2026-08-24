package net.zepalesque.redux.client.renderer.entity.phyg;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.passive.Phyg;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.client.renderer.entity.flying_cow.FlyingCowReduxModel;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

public class PhygReduxLayer extends RenderLayer<Phyg, PigModel<Phyg>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/phyg/phyg_redux.png");
    private static final ResourceLocation SILVER = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/phyg/phyg_redux_silver.png");
    private static final ResourceLocation BRONZE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/phyg/phyg_redux_bronze.png");

    private final PhygReduxModel<Phyg> model;

    public PhygReduxLayer(RenderLayerParent<Phyg, PigModel<Phyg>> renderer, EntityRendererProvider.Context ctx) {
        super(renderer);
	    this.model = new PhygReduxModel<>(ctx.bakeLayer(ReduxRenderers.ModelLayers.PHYG));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Phyg phyg, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.improved_phygs.get()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(phyg, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(phyg, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	        var mc = Minecraft.getInstance();
            if (mc.player != null && !phyg.isInvisibleTo(mc.player)) {
		        var consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(phyg)));
		        poseStack.pushPose();
		        if (phyg.isBaby()) {
			        var f1 = 2.0F;
			        poseStack.scale(f1, f1, f1);
			        poseStack.translate(0.0, -0.75, 0.0);
		        }
		        this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(phyg, 0.0F));
		        poseStack.popPose();
	        }
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull Phyg phyg) {
//        if (Redux.lostAetherCompat()) {
//            IWingedAnimal cap = WingedAnimalCap.get(phyg);
//            if (cap != null && cap.shouldDisplayWings()) {
//                int type = cap.getWingType();
//                return type == WingedAnimalCap.WingType.SILVER.ordinal() ? SILVER : BRONZE;
//            }
//        }
        return TEXTURE;
    }
}
