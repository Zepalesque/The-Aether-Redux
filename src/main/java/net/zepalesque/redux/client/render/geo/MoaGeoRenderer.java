package net.zepalesque.redux.client.render.geo;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.geo.GeoMoa;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MoaGeoRenderer extends GeoReplacedEntityRenderer<Moa, GeoMoa> {
    public MoaGeoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MoaGeoModel(), new GeoMoa());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Moa moa) {
        return Objects.requireNonNullElse(getMoaSkinLocation(moa), MoaGeoModel.FALLBACK);
    }


    @Nullable
    public static ResourceLocation getMoaSkinLocation(Moa moa) {
        UUID lastRiderUUID = moa.getLastRider();
        UUID moaUUID = moa.getMoaUUID();
        Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
        Screen var6 = Minecraft.getInstance().screen;
        if (var6 instanceof MoaSkinsScreen moaSkinsScreen) {
            if (moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID)) {
                return moaSkinsScreen.getSelectedSkin().getSkinLocation();
            }
        }

        return userSkinsData.containsKey(lastRiderUUID) && ((MoaData)userSkinsData.get(lastRiderUUID)).moaUUID() != null && ((MoaData)userSkinsData.get(lastRiderUUID)).moaUUID().equals(moaUUID) ? ((MoaData)userSkinsData.get(lastRiderUUID)).moaSkin().getSkinLocation() : null;
    }

    @Override
    public void render(Moa moa, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ((MoaGeoModel)this.model).setup(this.currentEntity, partialTick);
        super.render(moa, entityYaw, partialTick, poseStack, bufferSource, packedLight);

    }

    @Override
    public float getMotionAnimThreshold(GeoMoa animatable) {
        return 0.001F;
    }


    public static class MoaGeoModel extends GeoModel<GeoMoa> {

        // TODO: Easter egg like paradise lost maybe lol
        private static final ResourceLocation FALLBACK = new ResourceLocation(Aether.MODID, "textures/entity/mobs/moa/blue_moa.png");
        private static final ResourceLocation GEO_LOCATION = Redux.locate("geo/moa.geo.json");
        private static final ResourceLocation ANIM_LOCATION = Redux.locate("animations/moa.animation.json");

        private @Nullable Moa current = null;
        private float partial;

        @Override
        public ResourceLocation getModelResource(GeoMoa moa) {
            return GEO_LOCATION;
        }

        @Override
        public ResourceLocation getTextureResource(GeoMoa moa) {
            if (this.current != null) {
                ResourceLocation loc = getMoaSkinLocation(this.current);
                if (loc != null) return loc;
            }
            return FALLBACK;
        }

        public void setup(Moa moa, float partial) {
            this.current = moa;
            this.partial = partial;
        }

        @Override
        public ResourceLocation getAnimationResource(GeoMoa moa) {
            return ANIM_LOCATION;
        }

        @Override
        public void setCustomAnimations(GeoMoa moa, long instanceId, AnimationState<GeoMoa> animationState) {
            // TODO: Head rotation
            super.setCustomAnimations(moa, instanceId, animationState);
        }

        @Override
        public void applyMolangQueries(GeoMoa animatable, double animTime) {
            super.applyMolangQueries(animatable, animTime);
            Minecraft mc = Minecraft.getInstance();
            MolangParser parser = MolangParser.INSTANCE;
            Moa m = this.current;
            parser.setMemoizedValue("query.limb_swing", () -> m.walkAnimation.position(partial));
            parser.setMemoizedValue("query.limb_swing_amount", () -> m.walkAnimation.speed(partial));
        }
    }
}
