package net.zepalesque.redux.client.renderer;

import com.aetherteam.aether.client.renderer.entity.SliderRenderer;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.renderer.block.model.baked.AmbientOcclusionLightModel;
import net.zepalesque.redux.client.renderer.entity.EmberRenderer;
import net.zepalesque.redux.client.renderer.entity.ReduxEvilWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.ReduxWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.VeridiumDartRenderer;
import net.zepalesque.redux.client.renderer.entity.layer.SliderSignalLayer;
import net.zepalesque.redux.client.renderer.entity.model.WhirlwindModel;
import net.zepalesque.redux.entity.ReduxEntities;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ReduxRenderers {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayers.WHIRLWIND, WhirlwindModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Redux.BLOCK_SETS.forEach(set -> set.registerRenderers(event));
        event.registerEntityRenderer(AetherEntityTypes.WHIRLWIND.get(), ReduxWhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.EVIL_WHIRLWIND.get(), ReduxEvilWhirlwindRenderer::new);
        event.registerEntityRenderer(ReduxEntities.EMBER.get(), EmberRenderer::new);
        event.registerEntityRenderer(ReduxEntities.INFUSED_VERIDIUM_DART.get(), VeridiumDartRenderer::new);
        event.registerEntityRenderer(ReduxEntities.VERIDIUM_DART.get(), VeridiumDartRenderer.Uninfused::new);
    }

    @SubscribeEvent
    public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
        if (event.getRenderer(AetherEntityTypes.SLIDER.get()) instanceof SliderRenderer renderer)
            renderer.addLayer(new SliderSignalLayer(renderer));
    }

    public static void registerAccessoryRenderers() {
    }

    @SubscribeEvent
    public static void registerBakedModels(ModelEvent.ModifyBakingResult event) {
        modifyModels(event.getModels(), AmbientOcclusionLightModel::new, ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES);
    }

    @SafeVarargs
    private static void modifyModels(Map<ModelResourceLocation, BakedModel> models, UnaryOperator<BakedModel> factory, Supplier<? extends Block>... blocks) {
        modifyModels(models, factory, ((Function<Supplier<? extends Block>, Block>)Supplier::get).andThen(BuiltInRegistries.BLOCK::getKey), blocks);
    }

    @SafeVarargs
    private static void modifyModels(Map<ModelResourceLocation, BakedModel> models, UnaryOperator<BakedModel> factory, DeferredBlock<? extends Block>... blocks) {
        modifyModels(models, factory, DeferredHolder::getId, blocks);
    }

    @SafeVarargs
    private static <T> void modifyModels(Map<ModelResourceLocation, BakedModel> models, UnaryOperator<BakedModel> factory, Function<T, ResourceLocation> locationGetter, T... blocks) {
        Set<ResourceLocation> set = Arrays.stream(blocks).map(locationGetter).collect(Collectors.toSet());
        Set<Map.Entry<ModelResourceLocation, BakedModel>> entries = models.entrySet().stream().filter(entry -> set.contains(entry.getKey().id())).collect(Collectors.toSet());
        entries.forEach(entry -> models.put(entry.getKey(), factory.apply(entry.getValue())));

    }


    public static class ModelLayers {

        public static final ModelLayerLocation WHIRLWIND = register("whirlwind");

        private static ModelLayerLocation register(String name) {
            return register(name, "main");
        }

        private static ModelLayerLocation register(String name, String type) {
            return register(Redux.loc(name), type);
        }

        private static ModelLayerLocation register(ResourceLocation location, String type) {
            return new ModelLayerLocation(location, type);
        }
    }
}
