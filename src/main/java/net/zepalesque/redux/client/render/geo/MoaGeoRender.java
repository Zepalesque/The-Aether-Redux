package net.zepalesque.redux.client.render.geo;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.geo.GeoMoa;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;

public class MoaGeoRender extends GeoReplacedEntityRenderer<Moa, GeoMoa> {
    public MoaGeoRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MoaGeoModel(), new GeoMoa());
    }

    @Override
    public ResourceLocation getTextureLocation(Moa mykapod) {
        return null;
    }

    @Override
    public void render(Moa entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ((MoaGeoModel)this.model).setup(this.currentEntity, partialTick);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public float getMotionAnimThreshold(GeoMoa animatable) {
        return 0.001F;
    }


    public static class MoaGeoModel extends GeoModel<GeoMoa> {

        private static final ResourceLocation TEXTURE_LOCATION = Redux.locate("textures/entity/mobs/mykapod/mykapod.png");
        private static final ResourceLocation DESHELLED_LOCATION = Redux.locate("textures/entity/mobs/mykapod/mykapod_shed.png");
        private static final ResourceLocation GEO_LOCATION = Redux.locate("geo/mykapod.geo.json");
        private static final ResourceLocation ANIM_LOCATION = Redux.locate("animations/mykapod.animation.json");

        private @Nullable Moa current = null;
        private float partial;

        @Override
        public ResourceLocation getModelResource(GeoMoa moa) {
            return GEO_LOCATION;
        }

        @Override
        public ResourceLocation getTextureResource(GeoMoa moa) {
            // TODO
            return null;
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
