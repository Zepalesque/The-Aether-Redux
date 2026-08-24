package net.zepalesque.redux.client.renderer.entity.flying_cow;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.passive.FlyingCow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

public class FlyingCowReduxLayer extends RenderLayer<FlyingCow, CowModel<FlyingCow>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_redux.png");
    private static final ResourceLocation SILVER = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_redux_silver.png");
    private static final ResourceLocation BRONZE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_redux_bronze.png");

    private final FlyingCowReduxModel<FlyingCow> model;

    public FlyingCowReduxLayer(RenderLayerParent<FlyingCow, CowModel<FlyingCow>> renderer, EntityRendererProvider.Context ctx) {
        super(renderer);
        this.model = new FlyingCowReduxModel<>(ctx.bakeLayer(ReduxRenderers.ModelLayers.FLYING_COW));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, FlyingCow flycow, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.improved_flying_cows.get()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(flycow, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(flycow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         
            var mc = Minecraft.getInstance();
	        if (mc.player != null && !flycow.isInvisibleTo(mc.player)) {
		        var consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(flycow)));
		        poseStack.pushPose();
		        if (flycow.isBaby()) {
			        var f1 = 2.0F;
			        poseStack.scale(f1, f1, f1);
			        poseStack.translate(0.0, -0.75, 0.0);
		        }
		        this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(flycow, 0.0F));
		        poseStack.popPose();
	        }
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull FlyingCow phyg) {
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
