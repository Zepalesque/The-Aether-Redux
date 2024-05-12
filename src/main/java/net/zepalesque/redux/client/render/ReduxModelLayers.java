package net.zepalesque.redux.client.render;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;


public class ReduxModelLayers {
    public static final ModelLayerLocation MOA_OLD = register("redux_moa", "legacy_model");
    public static final ModelLayerLocation MOA_TALONS = register("redux_moa", "talons_model");
    public static final ModelLayerLocation MOA_REFRESHED = register("redux_moa", "refreshed");
    public static final ModelLayerLocation COCKATRICE_OLD = register("redux_cockatrice", "legacy_model");
    public static final ModelLayerLocation COCKATRICE_REFRESHED = register("redux_cockatrice", "refreshed");
    public static final ModelLayerLocation MIMIC = register("redux_mimic");
    public static final ModelLayerLocation SENTRY = register("redux_sentry");
    public static final ModelLayerLocation BATTLE_SENTRY = register("redux_batlle_sentry");

    // for debugging
    public static final ModelLayerLocation CUBE = register("cube");

    public static final ModelLayerLocation PIN = register("pin");

    public static final ModelLayerLocation BLIGHTBUNNY = register("blightbunny");
    public static final ModelLayerLocation SPEAR = register("blight_spear");

    public static final ModelLayerLocation GLIMMERCOW = register("glimmercow");

    public static final ModelLayerLocation SHEEPUFF = register("redux_sheepuff");

    public static final ModelLayerLocation PHYG = register("redux_phyg");

    public static final ModelLayerLocation FLYING_COW = register("redux_flying_cow");

    public static final ModelLayerLocation SKYROOT_MIMIC = register("skyroot_mimic");
    public static final ModelLayerLocation SKYROOT_CHEST_MIMIC = register("skyroot_chest_mimic");

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
