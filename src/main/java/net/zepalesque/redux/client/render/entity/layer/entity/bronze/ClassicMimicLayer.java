package net.zepalesque.redux.client.render.entity.layer.entity.bronze;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.MimicModel;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether_genesis.entity.monster.SkyrootMimic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Calendar;

@Deprecated
public class ClassicMimicLayer<T extends Mimic> extends RenderLayer<T, EntityModel<T>> {
    private final MimicModel classicMimic;

    private boolean isChristmas;

    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/normal.png");
    private static final ResourceLocation SKYROOT = new ResourceLocation("aether_genesis", "textures/entity/mobs/mimic/normal.png");
    private static final ResourceLocation XMAS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/christmas.png");
    private static final ResourceLocation LOOTR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/mimic/lootr.png");
    public ClassicMimicLayer(RenderLayerParent<T, EntityModel<T>> pRenderer, MimicModel mimicModel) {
        super(pRenderer);
        this.classicMimic = mimicModel;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {
            this.isChristmas = true;
        }
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!ReduxConfig.COMMON.smaller_mimic_hitbox.get() && Minecraft.getInstance().player != null && !mimic.isInvisibleTo(Minecraft.getInstance().player)) {
            this.classicMimic.prepareMobModel(mimic, limbSwing, limbSwingAmount, partialTicks);
            this.classicMimic.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(mimic)));
            this.classicMimic.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(mimic, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
    public @NotNull ResourceLocation getTextureLocation(@Nonnull Mimic mimic) {
        ResourceLocation baseTexture = ModList.get().isLoaded("lootr") ? LOOTR_TEXTURE : this.isChristmas ? XMAS_TEXTURE : shouldUseSkyroot(mimic) ? SKYROOT : TEXTURE;
        return baseTexture;
    }

    private boolean shouldUseSkyroot(Mimic mimic)
    {
        if (Redux.aetherGenesisCompat())
        {
            return mimic instanceof SkyrootMimic;
        } else { return false; }
    }
}
