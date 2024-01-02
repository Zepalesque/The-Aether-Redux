package net.zepalesque.redux.client.render;

import com.aetherteam.aether.client.renderer.entity.CockatriceRenderer;
import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.SentryRenderer;
import com.aetherteam.aether_genesis.client.renderer.entity.BattleSentryRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.client.render.entity.*;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.BattleSentryReduxLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.SentryReduxLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.CockatriceReduxLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.MoaReduxLayer;
import net.zepalesque.redux.client.render.entity.misc.ReduxBoatRenderer;
import net.zepalesque.redux.client.render.entity.model.entity.BattleSentryReduxModel;
import net.zepalesque.redux.client.render.entity.model.entity.bronze.ReduxMimicModel;
import net.zepalesque.redux.client.render.entity.model.entity.SentryReduxModel;
import net.zepalesque.redux.client.render.entity.model.entity.CockatriceReduxModel;
import net.zepalesque.redux.client.render.entity.model.entity.MoaReduxModel;
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
        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS) {
            event.registerBlockEntityRenderer(woodHandler.signEntity.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(woodHandler.hangingSignEntity.get(), HangingSignRenderer::new);
            event.registerEntityRenderer(woodHandler.boatEntity.get(), (context) -> new ReduxBoatRenderer(context, false, woodHandler.woodName));
            event.registerEntityRenderer(woodHandler.chestBoatEntity.get(), (context) -> new ReduxBoatRenderer(context, true, woodHandler.woodName));
        }



//        event.registerEntityRenderer(AetherEntityTypes.MOA.get(), ReduxMoaRenderer::new);
//        event.registerEntityRenderer(AetherEntityTypes.COCKATRICE.get(), ReduxCockatriceRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.VANILLA_SWET.get(), VanillaSwetRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.SPECTRAL_DART.get(), SpectralDartRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.VOLATILE_FIRE_CRYSTAL.get(), VolatileFireCrystalRenderer::new);

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
        event.registerLayerDefinition(ReduxModelLayers.MOA_REDUX, MoaReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.REDUX_COCKATRICE, CockatriceReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.MIMIC, ReduxMimicModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.SENTRY, SentryReduxModel::createBodyLayer);
        if (Redux.aetherGenesisCompat()) {
            event.registerLayerDefinition(ReduxModelLayers.BATTLE_SENTRY, BattleSentryReduxModel::createBodyLayer);
        }
    }

    @SubscribeEvent
    public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
        Minecraft mc = Minecraft.getInstance();
        for (EntityRenderer<?> renderer : mc.getEntityRenderDispatcher().renderers.values()) {
            if (renderer instanceof MoaRenderer moa) {
                moa.addLayer(new MoaReduxLayer(moa, new MoaReduxModel(mc.getEntityModels().bakeLayer(ReduxModelLayers.MOA_REDUX))));
            }
            if (renderer instanceof CockatriceRenderer cockatrice) {
                cockatrice.addLayer(new CockatriceReduxLayer(cockatrice, new CockatriceReduxModel(mc.getEntityModels().bakeLayer(ReduxModelLayers.REDUX_COCKATRICE))));
            }
            if (renderer instanceof SentryRenderer sentry) {
                sentry.addLayer(new SentryReduxLayer(sentry, new SentryReduxModel<>(mc.getEntityModels().bakeLayer(ReduxModelLayers.SENTRY))));
            }
            if (Redux.aetherGenesisCompat() && renderer instanceof BattleSentryRenderer sentry) {
                sentry.addLayer(new BattleSentryReduxLayer(sentry, new BattleSentryReduxModel<>(mc.getEntityModels().bakeLayer(ReduxModelLayers.BATTLE_SENTRY))));
            }
        }
    }

}