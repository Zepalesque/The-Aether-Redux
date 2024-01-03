package net.zepalesque.redux.client.render.entity.layer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;


// TODO: Go through these and remove any unused ones
public class ReduxModelLayers {
    public static final ModelLayerLocation MOA_EXTRAS = register("redux_moa", "extras");
    public static final ModelLayerLocation MOA_REDUX = register("moa", "redux_model");
    public static final ModelLayerLocation MOA_TOES = register("redux_moa", "toes");
    public static final ModelLayerLocation MOA_TALONS = register("redux_moa", "talons");
    public static final ModelLayerLocation MOA_WINGS = register("redux_moa", "wings");
    public static final ModelLayerLocation REDUX_COCKATRICE = register("redux_cockatrice");
    public static final ModelLayerLocation SHEEPUFF_BODY = register("sheepuff_body");
    public static final ModelLayerLocation SHEEPUFF_WOOL = register("sheepuff_wool");
    public static final ModelLayerLocation SHEEPUFF_WOOL_PUFFED = register("sheepuff_wool_puffed");

    public static final ModelLayerLocation CUBE = register("box", "redux_temporary_spark_test");

    public static final ModelLayerLocation MIMIC = register("mimic");
    public static final ModelLayerLocation SENTRY = register("sentry", "redux_model");
    public static final ModelLayerLocation BATTLE_SENTRY = register("batlle_sentry");


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
