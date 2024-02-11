package net.zepalesque.redux.client.render;

import com.aetherteam.aether.client.renderer.entity.CockatriceRenderer;
import com.aetherteam.aether.client.renderer.entity.MimicRenderer;
import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.SentryRenderer;
import com.aetherteam.aether_genesis.client.renderer.entity.BattleSentryRenderer;
import com.aetherteam.aether_genesis.client.renderer.entity.SkyrootMimicRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.client.render.entity.*;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.*;
import net.zepalesque.redux.client.render.entity.misc.ReduxBoatRenderer;
import net.zepalesque.redux.client.render.entity.model.entity.*;
import net.zepalesque.redux.client.render.entity.model.entity.MimicReduxModel;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(
        modid = Redux.MODID,
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxRenderers {



    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS) {
            event.registerBlockEntityRenderer(woodHandler.signEntity.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(woodHandler.hangingSignEntity.get(), HangingSignRenderer::new);
            event.registerEntityRenderer(woodHandler.boatEntity.get(), (context) -> new ReduxBoatRenderer(context, false, woodHandler.woodName));
            event.registerEntityRenderer(woodHandler.chestBoatEntity.get(), (context) -> new ReduxBoatRenderer(context, true, woodHandler.woodName));
        }



//        event.registerEntityRenderer(AetherEntityTypes.MOA.get(), ReduxMoaRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.COCKATRICE.get(), ReduxCockatriceRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.VANILLA_SWET.get(), VanillaSwetRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.SPECTRAL_DART.get(), SpectralDartRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.VERIDIUM_ARROW.get(), VeridiumArrowRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.VOLATILE_FIRE_CRYSTAL.get(), VolatileFireCrystalRenderer::new);

        event.registerEntityRenderer(ReduxEntityTypes.EMBER.get(), EmberRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.GLIMMERCOW.get(), GlimmercowRenderer::new);

        event.registerEntityRenderer(ReduxEntityTypes.BLIGHTBUNNY.get(), BlightbunnyRenderer::new);

//        event.registerEntityRenderer(AetherEntityTypes.MIMIC.get(), ReduxMimicRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.SENTRY.get(), ReduxSentryRenderer::new);

        if (Redux.aetherGenesisCompat())
        {
//            event.registerEntityRenderer(GenesisEntityTypes.SKYROOT_MIMIC.get(), ReduxSkyrootMimicRenderer::new);
//            event.registerEntityRenderer(GenesisEntityTypes.BATTLE_SENTRY.get(), ReduxBattleSentryRenderer::new);
        }
    }

    public static void registerCuriosRenderers() {
        CuriosRendererRegistry.register(ReduxItems.VAMPIRE_AMULET.get(), VampireAmuletRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReduxModelLayers.MOA, MoaReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.COCKATRICE, CockatriceReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.GLIMMERCOW, GlimmercowModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.MIMIC, MimicReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.SENTRY, SentryReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.BLIGHTBUNNY, BlightbunnyModel::createBodyLayer);

        if (Redux.aetherGenesisCompat()) {
            event.registerLayerDefinition(ReduxModelLayers.BATTLE_SENTRY, BattleSentryReduxModel::createBodyLayer);
        }

        if (!FMLLoader.isProduction()) {
            event.registerLayerDefinition(ReduxModelLayers.CUBE, CubeModel::create);
        }
    }

    @SubscribeEvent
    public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
        Minecraft mc = Minecraft.getInstance();
        for (EntityRenderer<?> renderer : mc.getEntityRenderDispatcher().renderers.values()) {
            if (renderer instanceof MoaRenderer moa) {
                moa.addLayer(new MoaReduxLayer(moa, new MoaReduxModel(mc.getEntityModels().bakeLayer(ReduxModelLayers.MOA))));
            }
            if (renderer instanceof MimicRenderer mimic) {
                mimic.addLayer(new MimicReduxLayer(mimic, new MimicReduxModel(mc.getEntityModels().bakeLayer(ReduxModelLayers.MIMIC))));
            }
            if (renderer instanceof CockatriceRenderer cockatrice) {
                cockatrice.addLayer(new CockatriceReduxLayer(cockatrice, new CockatriceReduxModel(mc.getEntityModels().bakeLayer(ReduxModelLayers.COCKATRICE))));
            }
            if (renderer instanceof SentryRenderer sentry) {
                sentry.addLayer(new SentryReduxLayer(sentry, new SentryReduxModel<>(mc.getEntityModels().bakeLayer(ReduxModelLayers.SENTRY))));
            }
            if (Redux.aetherGenesisCompat() && renderer instanceof BattleSentryRenderer sentry) {
                sentry.addLayer(new BattleSentryReduxLayer(sentry, new BattleSentryReduxModel<>(mc.getEntityModels().bakeLayer(ReduxModelLayers.BATTLE_SENTRY))));
            }
            if (Redux.aetherGenesisCompat() && renderer instanceof SkyrootMimicRenderer mimic) {
                mimic.addLayer(new MimicReduxLayer(mimic, new MimicReduxModel(mc.getEntityModels().bakeLayer(ReduxModelLayers.MIMIC))));
            }
        }
    }

}