package net.zepalesque.redux.client.render;

import com.aetherteam.aether.client.renderer.accessory.PendantRenderer;
import com.aetherteam.aether.client.renderer.entity.*;
import com.aetherteam.aether_genesis.client.renderer.entity.BattleSentryRenderer;
import com.aetherteam.aether_genesis.client.renderer.entity.SkyrootMimicRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.client.render.bewlr.ReduxBEWLR;
import net.zepalesque.redux.client.render.entity.*;
import net.zepalesque.redux.client.render.entity.layer.entity.*;
import net.zepalesque.redux.client.render.entity.misc.ReduxBoatRenderer;
import net.zepalesque.redux.client.render.entity.model.PinModel;
import net.zepalesque.redux.client.render.entity.model.RebuxModel;
import net.zepalesque.redux.client.render.entity.model.RebuxRenderer;
import net.zepalesque.redux.client.render.entity.model.SpearModel;
import net.zepalesque.redux.client.render.entity.model.entity.*;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(
        modid = Redux.MODID,
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxRenderers {


    public static final Lazy<BlockEntityWithoutLevelRenderer> bewlr = Lazy.of(ReduxBEWLR::new);

    public static final IClientItemExtensions itemProp = new IClientItemExtensions() {
        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return ReduxRenderers.bewlr.get();
        }
    };

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
        event.registerEntityRenderer(ReduxEntityTypes.INFUSED_VERIDIUM_DART.get(), InfusedVeridiumDartRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.VERIDIUM_DART.get(), VeridiumArrowRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.VOLATILE_FIRE_CRYSTAL.get(), VolatileFireCrystalRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.EMBER.get(), EmberRenderer::new);
        event.registerEntityRenderer(ReduxEntityTypes.THROWN_SPEAR.get(), ThrownSpearRenderer::new);

        event.registerEntityRenderer(ReduxEntityTypes.SHIMMERCOW.get(), ShimmercowRenderer::new);

        event.registerEntityRenderer(ReduxEntityTypes.BLIGHTBUNNY.get(), BlightbunnyRenderer::new);

        event.registerEntityRenderer(ReduxEntityTypes.REBUX.get(), RebuxRenderer::new);

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
        CuriosRendererRegistry.register(ReduxItems.GRAND_VICTORY_MEDAL.get(), PendantRenderer::new);
        CuriosRendererRegistry.register(ReduxItems.SOLAR_EMBLEM.get(), SolarEmblemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ReduxModelLayers.MOA, MoaReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.COCKATRICE, CockatriceReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.GLIMMERCOW, ShimmercowModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.MIMIC, MimicReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.SENTRY, SentryReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.BLIGHTBUNNY, BlightbunnyModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.SPEAR, SpearModel::createLayer);
        event.registerLayerDefinition(ReduxModelLayers.REBUX, RebuxModel::createLayer);
        event.registerLayerDefinition(ReduxModelLayers.SHEEPUFF, SheepuffReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.PHYG, PhygReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.FLYING_COW, FlyingCowReduxModel::createBodyLayer);
        event.registerLayerDefinition(ReduxModelLayers.PIN, PinModel::createLayer);

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
            if (renderer instanceof SheepuffRenderer sheepuff) {
                sheepuff.addLayer(new SheepuffReduxLayer(sheepuff, new SheepuffReduxModel(mc.getEntityModels().bakeLayer(ReduxModelLayers.SHEEPUFF))));
            }
            if (renderer instanceof PhygRenderer phyg) {
                phyg.addLayer(new PhygReduxLayer(phyg, new PhygReduxModel<>(mc.getEntityModels().bakeLayer(ReduxModelLayers.PHYG))));
            }
            if (renderer instanceof FlyingCowRenderer cow) {
                cow.addLayer(new FlyingCowReduxLayer(cow, new FlyingCowReduxModel<>(mc.getEntityModels().bakeLayer(ReduxModelLayers.FLYING_COW))));
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