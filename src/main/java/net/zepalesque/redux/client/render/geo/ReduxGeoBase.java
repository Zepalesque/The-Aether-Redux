package net.zepalesque.redux.client.render.geo;

import com.aetherteam.aether.client.AetherClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.client.ReduxClient;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


/** A few Geckolib helper classes */
public class ReduxGeoBase {

    public static class Renderer<E extends Entity & GeoAnimatable> extends GeoEntityRenderer<E> {
        public Renderer(EntityRendererProvider.Context renderManager, GeoModel<E> model, float shadowRadius) {
            super(renderManager, model);
            this.shadowRadius = shadowRadius;
        }
    }

    public static abstract class LivingModel<T extends LivingEntity & GeoAnimatable> extends GeoModel<T> {
        // Geckolib helper function
        protected float calculateHeadY(LivingEntity entity, float partial) {
            boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
            float lerpBodyRot = Mth.rotLerp(partial, entity.yBodyRotO, entity.yBodyRot);
            float lerpHeadRot = Mth.rotLerp(partial, entity.yHeadRotO, entity.yHeadRot);
            float netHeadYaw = lerpHeadRot - lerpBodyRot;

            if (shouldSit && entity.getVehicle() instanceof LivingEntity livingentity) {
                lerpBodyRot = Mth.rotLerp(partial, livingentity.yBodyRotO, livingentity.yBodyRot);
                netHeadYaw = lerpHeadRot - lerpBodyRot;
                float clampedHeadYaw = Mth.clamp(Mth.wrapDegrees(netHeadYaw), -85, 85);
                lerpBodyRot = lerpHeadRot - clampedHeadYaw;

                if (clampedHeadYaw * clampedHeadYaw > 2500f)
                    lerpBodyRot += clampedHeadYaw * 0.2f;

                netHeadYaw = lerpHeadRot - lerpBodyRot;
            }
            return netHeadYaw;
        }

        @Override
        public void applyMolangQueries(T entity, double animTime) {
            super.applyMolangQueries(entity, animTime);
            MolangParser molangParser = MolangParser.INSTANCE;
            Minecraft mc = Minecraft.getInstance();

            // Limb swing calculation
            molangParser.setMemoizedValue(ReduxClient.LIMB_MOVEMENT, () -> {
                float limbSwingAmount = 0;
                float limbSwing = 0;
                boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
                if (!shouldSit && entity.isAlive()) {
                    limbSwingAmount = entity.walkAnimation.speed(mc.getPartialTick());
                    limbSwing = entity.walkAnimation.position(mc.getPartialTick());

                    if (entity.isBaby())
                        limbSwing *= 3f;

                    if (limbSwingAmount > 1f)
                        limbSwingAmount = 1f;
                }
                return Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * Mth.RAD_TO_DEG;
            });

            molangParser.setMemoizedValue(ReduxClient.HEAD_X_ROT, () -> Mth.lerp(mc.getPartialTick(), entity.xRotO, entity.getXRot()));
            molangParser.setMemoizedValue(ReduxClient.HEAD_Y_ROT, () -> this.calculateHeadY(entity, mc.getPartialTick()));
        }
    }

}
