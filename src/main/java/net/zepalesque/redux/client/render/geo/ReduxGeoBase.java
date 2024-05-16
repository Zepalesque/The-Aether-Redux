package net.zepalesque.redux.client.render.geo;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


/** A few Geckolib helper classes */
public class ReduxGeoBase {

    public static class Renderer<E extends LivingEntity & IAnimatable> extends GeoEntityRenderer<E> {
        public Renderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<E> model, float shadowRadius) {
            super(renderManager, model);
            this.shadowRadius = shadowRadius;
        }
    }
}
