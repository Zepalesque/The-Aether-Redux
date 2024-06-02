package net.zepalesque.redux.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.AbstractWoodSet;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Redux.MODID, bus = Bus.MOD)
public class ReduxTabs {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();

        Supplier<? extends ItemLike> sup = null;
        for (AbstractWoodSet set : Redux.WOOD_SETS) {
            sup = set.addToCreativeTab(event, sup);
        }

        sup = null;
        for (AbstractStoneSet set : Redux.STONE_SETS) {
            sup = set.addToCreativeTab(event, sup);
        }

    }
}
