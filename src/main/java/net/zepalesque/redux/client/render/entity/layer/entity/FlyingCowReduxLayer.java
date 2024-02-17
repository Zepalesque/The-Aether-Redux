package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.passive.FlyingCow;
import com.aetherteam.aether.entity.passive.Phyg;
import com.legacy.lost_aether.capability.entity.IWingedAnimal;
import com.legacy.lost_aether.capability.entity.WingedAnimalCap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.model.entity.FlyingCowReduxModel;
import net.zepalesque.redux.client.render.entity.model.entity.PhygReduxModel;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

public class FlyingCowReduxLayer extends RenderLayer<FlyingCow, CowModel<FlyingCow>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_redux.png");
    private static final ResourceLocation SILVER = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_redux_silver.png");
    private static final ResourceLocation BRONZE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/flying_cow/flying_cow_redux_bronze.png");

    private final FlyingCowReduxModel<FlyingCow> model;

    public FlyingCowReduxLayer(RenderLayerParent<FlyingCow, CowModel<FlyingCow>> renderer, FlyingCowReduxModel<FlyingCow> model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, FlyingCow phyg, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.flying_cow_model_upgrade.get() || ReduxConfig.CLIENT.override_model_upgrades.get()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(phyg, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(phyg, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (Minecraft.getInstance().player != null) {
                if (!phyg.isInvisibleTo(Minecraft.getInstance().player)) {
                    VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(phyg)));
                    poseStack.pushPose();
                    if (phyg.isBaby()) {
                        float f1 = 2.0F;
                        poseStack.scale(f1, f1, f1);
                        poseStack.translate(0.0, -0.75, 0.0);
                    }
                    this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(phyg, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
                    poseStack.popPose();
                }
            }
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull FlyingCow phyg) {
        if (Redux.lostAetherCompat()) {
            IWingedAnimal cap = WingedAnimalCap.get(phyg);
            if (cap != null && cap.shouldDisplayWings()) {
                int type = cap.getWingType();
                return type == WingedAnimalCap.WingType.SILVER.ordinal() ? SILVER : BRONZE;
            }
        }
        return TEXTURE;
    }
}
