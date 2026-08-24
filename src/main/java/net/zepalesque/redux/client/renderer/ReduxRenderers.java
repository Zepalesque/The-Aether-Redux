package net.zepalesque.redux.client.renderer;

import com.aetherteam.aether.client.renderer.entity.CockatriceRenderer;
import com.aetherteam.aether.client.renderer.entity.FlyingCowRenderer;
import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.PhygRenderer;
import com.aetherteam.aether.client.renderer.entity.SheepuffRenderer;
import com.aetherteam.aether.client.renderer.entity.SliderRenderer;
import com.aetherteam.aether.entity.AetherEntityTypes;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
import net.zepalesque.redux.client.renderer.entity.aerbunny.ReduxAerbunnyModel;
import net.zepalesque.redux.client.renderer.entity.aerbunny.ReduxAerbunnyRenderer;
import net.zepalesque.redux.client.renderer.entity.artemid.ArtemidModel;
import net.zepalesque.redux.client.renderer.entity.artemid.ArtemidRenderer;
import net.zepalesque.redux.client.renderer.entity.catfish.CatFishModel;
import net.zepalesque.redux.client.renderer.entity.catfish.CatFishRenderer;
import net.zepalesque.redux.client.renderer.entity.cockatrice.CockatriceReduxLayer;
import net.zepalesque.redux.client.renderer.entity.cockatrice.CockatriceReduxModel;
import net.zepalesque.redux.client.renderer.entity.ember.EmberRenderer;
import net.zepalesque.redux.client.renderer.entity.flying_cow.FlyingCowReduxLayer;
import net.zepalesque.redux.client.renderer.entity.flying_cow.FlyingCowReduxModel;
import net.zepalesque.redux.client.renderer.entity.moa.MoaReduxLayer;
import net.zepalesque.redux.client.renderer.entity.moa.MoaReduxModel;
import net.zepalesque.redux.client.renderer.entity.phyg.PhygReduxLayer;
import net.zepalesque.redux.client.renderer.entity.phyg.PhygReduxModel;
import net.zepalesque.redux.client.renderer.entity.sheepuff.SheepuffReduxLayer;
import net.zepalesque.redux.client.renderer.entity.sheepuff.SheepuffReduxModel;
import net.zepalesque.redux.client.renderer.entity.slider.SliderSignalLayer;
import net.zepalesque.redux.client.renderer.entity.veridiumdart.VeridiumDartRenderer;
import net.zepalesque.redux.client.renderer.entity.whirlwind.ReduxEvilWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.whirlwind.ReduxWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.whirlwind.WhirlwindModel;
import net.zepalesque.redux.entity.ReduxEntities;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ReduxRenderers {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelLayers.WHIRLWIND, WhirlwindModel::createBodyLayer);
		event.registerLayerDefinition(ModelLayers.MOA, MoaReduxModel::createBodyLayer);
		event.registerLayerDefinition(ModelLayers.COCKATRICE, CockatriceReduxModel::createRefreshedLayer);
		event.registerLayerDefinition(ModelLayers.FLYING_COW, FlyingCowReduxModel::createBodyLayer);
		event.registerLayerDefinition(ModelLayers.PHYG, PhygReduxModel::createBodyLayer);
		event.registerLayerDefinition(ModelLayers.CAT_FISH, CatFishModel::createBodyLayer);
		event.registerLayerDefinition(ModelLayers.SHEEPUFF, SheepuffReduxModel::createBodyLayer);
		event.registerLayerDefinition(ArtemidModel.LAYER_LOCATION, ArtemidModel::createBodyLayer);
		event.registerLayerDefinition(ArtemidModel.ANTLERS_LAYER, ArtemidModel::createBodyLayer);
		event.registerLayerDefinition(ModelLayers.AERBUNNY, ReduxAerbunnyModel::adult);
		event.registerLayerDefinition(ModelLayers.BABY_AERBUNNY, ReduxAerbunnyModel::baby);
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		Redux.BLOCK_SETS.forEach(set -> set.registerRenderers(event));
		event.registerEntityRenderer(AetherEntityTypes.WHIRLWIND.get(), ReduxWhirlwindRenderer::new);
		event.registerEntityRenderer(AetherEntityTypes.EVIL_WHIRLWIND.get(), ReduxEvilWhirlwindRenderer::new);
		event.registerEntityRenderer(ReduxEntities.CAT_FISH.get(), CatFishRenderer::new);
		event.registerEntityRenderer(ReduxEntities.ARTEMID.get(), ArtemidRenderer::new);
		event.registerEntityRenderer(ReduxEntities.EMBER.get(), EmberRenderer::new);
		event.registerEntityRenderer(ReduxEntities.INFUSED_VERIDIUM_DART.get(), VeridiumDartRenderer::new);
		event.registerEntityRenderer(ReduxEntities.VERIDIUM_DART.get(), VeridiumDartRenderer.Uninfused::new);
		event.registerEntityRenderer(AetherEntityTypes.AERBUNNY.get(), ReduxAerbunnyRenderer::new);
	}

	@SubscribeEvent
	public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
		var ctx = event.getContext();
		if (event.getRenderer(AetherEntityTypes.SLIDER.get()) instanceof SliderRenderer rend)
			rend.addLayer(new SliderSignalLayer(rend));
		if (event.getRenderer(AetherEntityTypes.SHEEPUFF.get()) instanceof SheepuffRenderer rend)
			rend.addLayer(new SheepuffReduxLayer(rend, ctx));
		if (event.getRenderer(AetherEntityTypes.MOA.get()) instanceof MoaRenderer rend)
			rend.addLayer(new MoaReduxLayer(rend, ctx));
		if (event.getRenderer(AetherEntityTypes.COCKATRICE.get()) instanceof CockatriceRenderer rend)
			rend.addLayer(new CockatriceReduxLayer(rend, ctx));
		if (event.getRenderer(AetherEntityTypes.FLYING_COW.get()) instanceof FlyingCowRenderer rend)
			rend.addLayer(new FlyingCowReduxLayer(rend, ctx));
		if (event.getRenderer(AetherEntityTypes.PHYG.get()) instanceof PhygRenderer rend)
			rend.addLayer(new PhygReduxLayer(rend, ctx));
	}

	public static void registerAccessoryRenderers() {}

	@SubscribeEvent
	public static void registerBakedModels(ModelEvent.ModifyBakingResult event) {
		modifyModels(event.getModels(), AmbientOcclusionLightModel::new, ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES);
		modifyModels(event.getModels(), AmbientOcclusionLightModel::new, ReduxBlocks.CLOUD_CAP);
	}

	@SafeVarargs
	private static void modifyModels(Map<ModelResourceLocation, BakedModel> models, UnaryOperator<BakedModel> factory, DeferredBlock<? extends Block>... blocks) {
		modifyModels(models, factory, DeferredHolder::getId, blocks);
	}

	@SafeVarargs
	private static <T> void modifyModels(
		Map<ModelResourceLocation, BakedModel> models,
		UnaryOperator<BakedModel> factory,
		Function<T, ResourceLocation> locationGetter,
		T... blocks
	) {
		var set = Arrays.stream(blocks)
			.map(locationGetter)
			.collect(Collectors.toSet());
		var entries = models
			.entrySet()
			.stream()
			.filter(entry -> set.contains(entry.getKey().id()))
			.collect(Collectors.toSet());
		entries.forEach(entry -> models.put(entry.getKey(), factory.apply(entry.getValue())));
	}


	public static class ModelLayers {
		public static final ModelLayerLocation AERBUNNY = register("aerbunny");
		public static final ModelLayerLocation BABY_AERBUNNY = register("baby_aerbunny");
		public static final ModelLayerLocation WHIRLWIND = register("whirlwind");
		public static final ModelLayerLocation CAT_FISH = register("cat_fish");
		public static final ModelLayerLocation SHEEPUFF = register("sheepuff");
		public static final ModelLayerLocation MOA = register("moa");
		public static final ModelLayerLocation COCKATRICE = register("cockatrice");
		public static final ModelLayerLocation FLYING_COW = register("flying_cow");
		public static final ModelLayerLocation PHYG = register("phyg");

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
