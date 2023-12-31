package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.api.registers.MoaType;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.moa.*;
import net.zepalesque.redux.client.render.entity.model.entity.moa.*;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

// TODO: Move to mixin, merge all extra layers and add glow layer
public class ReduxMoaRenderer extends MobRenderer<Moa, ReduxMoaModel> {
    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation("aether", "textures/entity/mobs/moa/white_moa.png");
    private static final ResourceLocation MOS_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/mos.png");
    private static final ResourceLocation RAPTOR_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/raptor.png");

    public ReduxMoaRenderer(EntityRendererProvider.Context context) {
        super(context, new ReduxMoaModel(context.bakeLayer(AetherModelLayers.MOA)), 0.7F);
        this.addLayer(new ReduxMoaSaddleLayer(this, new ReduxMoaModel(context.bakeLayer(AetherModelLayers.MOA_SADDLE))));
        this.addLayer(new MoaExtrasLayer(this, new MoaExtrasModel(context.bakeLayer(ReduxModelLayers.MOA_EXTRAS))));
        this.addLayer(new MoaWingsLayer(this, new MoaWingsModel(context.bakeLayer(ReduxModelLayers.MOA_WINGS))));
        this.addLayer(new MoaLegsLayer(this, new MoaLegsModel(context.bakeLayer(ReduxModelLayers.MOA_TOES)), new MoaLegsModel(context.bakeLayer(ReduxModelLayers.MOA_TALONS))));
    }

    protected void scale(Moa moa, PoseStack poseStack, float partialTickTime) {
        float moaScale = moa.isBaby() ? 1.0F : 1.8F;
        poseStack.scale(moaScale, moaScale, moaScale);
        if (moa.isSitting()) {
            poseStack.translate(0.0, 0.5, 0.0);
        }

    }

    protected float getBob(@Nonnull Moa moa, float partialTicks) {
        return this.model.setupWingsAnimation(moa, partialTicks);
    }


    public ResourceLocation getTextureLocation(Moa moa) {
        ResourceLocation moaSkin = this.getMoaSkinLocation(moa);
        if (moaSkin != null) {
            return moaSkin;
        } else if (moa.hasCustomName() && moa.getName().getString().equals("Mos")) {
            return MOS_TEXTURE;
        } else if (moa.hasCustomName() && moa.getName().getString().equals("Raptor__") && moa.getMoaType() == AetherMoaTypes.BLUE.get() || moa.getRider() != null && moa.getRider().equals(UUID.fromString("c3e6871e-8e60-490a-8a8d-2bbe35ad1604"))) {
            return RAPTOR_TEXTURE;
        } else {
            MoaType moaType = moa.getMoaType();
            return moaType == null ? DEFAULT_TEXTURE : moaType.getMoaTexture();
        }
    }

    @Nullable
    public ResourceLocation getMoaSkinLocation(Moa moa) {
        UUID lastRiderUUID = moa.getLastRider();
        UUID moaUUID = moa.getMoaUUID();
        Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
        Screen var6 = Minecraft.getInstance().screen;
        if (var6 instanceof MoaSkinsScreen moaSkinsScreen) {
            if (moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID)) {
                return moaSkinsScreen.getSelectedSkin().getSkinLocation();
            }
        }

        return userSkinsData.containsKey(lastRiderUUID) && userSkinsData.get(lastRiderUUID).moaUUID() != null && userSkinsData.get(lastRiderUUID).moaUUID().equals(moaUUID) ? userSkinsData.get(lastRiderUUID).moaSkin().getSkinLocation() : null;
    }

    @Override
    public void render(Moa moa, float pEntityYaw, float partialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (ReduxConfig.CLIENT.moa_improvements.get() && !MoaUtils.overrideModelChange(moa))
        {
            float breathe = MathUtil.breathe(moa, partialTicks);

            if (moa.hurtTime > 0 && moa.hurtTime - partialTicks > 0.0F)
            {
                int hit = moa.hurtDuration - moa.hurtTime;
                float hitSmooth = hit + partialTicks;
                final float baseRot = hitSmooth >= (moa.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth));

                float rot = baseRot * (((float) Math.PI) * 0.125F);

                this.model.body.xRot = (float) ((0.3333F * rot) + (Math.PI * 0.5F)) + breathe;
            } else {

                this.model.body.xRot = (float) (Math.PI * 0.5F) + breathe;
            }
        }
        super.render(moa, pEntityYaw, partialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
