package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.passive.Phyg;
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
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

public class PhygReduxLayer extends RenderLayer<Phyg, PigModel<Phyg>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg_redux.png");
    private static final ResourceLocation SILVER = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg_redux_silver.png");
    private static final ResourceLocation BRONZE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/phyg/phyg_redux_bronze.png");

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
                    poseStack.pushPose();
                    if (phyg.isBaby()) {
                        float f1 = 2.0F;
                        poseStack.scale(f1, f1, f1);
                        poseStack.translate(0.0, -0.75, 0.0);
                    }
                    this.model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(phyg, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
                    poseStack.popPose();
                }
            }
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull Phyg phyg) {
        // TODO 1.19.2: Reimplement once Lost Content ports
        /*if (Redux.lostAetherCompat()) {
            IWingedAnimal cap = WingedAnimalCap.get(phyg);
            if (cap != null && cap.shouldDisplayWings()) {
                int type = cap.getWingType();
                return type == WingedAnimalCap.WingType.SILVER.ordinal() ? SILVER : BRONZE;
            }
        }*/
        return TEXTURE;
    }
}
