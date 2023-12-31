package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigListener {

    @SubscribeEvent
    public static void configChanged(ModConfigEvent.Reloading event)
    {
       if (event.getConfig().getSpec() == ReduxConfig.COMMON_SPEC)
       {
           AetherEntityTypes.MIMIC.get().getDimensions().height = ReduxConfig.COMMON.better_mimics.get() ? 1.25f : 2F;
           if (Redux.aetherGenesisCompat())
           {
               GenesisEntityTypes.SKYROOT_MIMIC.get().getDimensions().height = ReduxConfig.COMMON.better_mimics.get() ? 1.25f : 2F;
           }
       }
    }
}
