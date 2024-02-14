package net.zepalesque.redux.client.render;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;


public class ReduxModelLayers {
    public static final ModelLayerLocation MOA = register("redux_moa");
    public static final ModelLayerLocation COCKATRICE = register("redux_cockatrice");
    public static final ModelLayerLocation MIMIC = register("redux_mimic");
    public static final ModelLayerLocation SENTRY = register("redux_sentry");
    public static final ModelLayerLocation BATTLE_SENTRY = register("redux_batlle_sentry");

    // for debugging
    public static final ModelLayerLocation CUBE = register("cube");

    public static final ModelLayerLocation BLIGHTBUNNY = register("blightbunny");
    public static final ModelLayerLocation SPEAR = register("spear_of_the_blight");

    public static final ModelLayerLocation GLIMMERCOW = register("glimmercow");

    public static final ModelLayerLocation SHEEPUFF = register("redux_sheepuff");

    public static final ModelLayerLocation PHYG = register("redux_phyg");

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
