package net.zepalesque.redux.client.render.entity.layer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;


public class ReduxModelLayers {
    public static final ModelLayerLocation MOA = register("redux_moa");
    public static final ModelLayerLocation COCKATRICE = register("redux_cockatrice");
    public static final ModelLayerLocation MIMIC = register("redux_mimic");
    public static final ModelLayerLocation SENTRY = register("redux_sentry");
    public static final ModelLayerLocation BATTLE_SENTRY = register("redux_batlle_sentry");

    public static final ModelLayerLocation GLIMMERCOW = register("glimmercow");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(Redux.locate(name), type);
    }

    private static ModelLayerLocation register(ResourceLocation identifier, String type) {
        return new ModelLayerLocation(identifier, type);
    }
}
