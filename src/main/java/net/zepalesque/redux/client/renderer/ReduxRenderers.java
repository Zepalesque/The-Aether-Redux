package net.zepalesque.redux.client.renderer;

import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockset.BaseWoodSet;
import net.zepalesque.redux.blockset.ReduxWoodSets;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReduxRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Redux.WOOD_SETS.forEach(set -> set.registerRenderers(event));
    }
}
