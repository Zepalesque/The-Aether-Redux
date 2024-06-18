package net.zepalesque.redux.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.AbstractWoodSet;
import net.zepalesque.zenith.util.TabUtil;

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

        if (tab == AetherCreativeTabs.AETHER_NATURAL_BLOCKS.get()) {
            TabUtil.putAfter(AetherBlocks.SKYROOT_LEAVES, ReduxBlocks.CLOUDROOT_LEAVES, event);
            TabUtil.putAfter(AetherBlocks.SKYROOT_SAPLING, ReduxBlocks.CLOUDROOT_SAPLING, event);
        }

        if (tab == AetherCreativeTabs.AETHER_DUNGEON_BLOCKS.get()) {
            TabUtil.putAfter(AetherBlocks.CARVED_STONE, ReduxBlocks.CARVED_BASE, event);
            TabUtil.putAfter(ReduxBlocks.CARVED_BASE, ReduxBlocks.CARVED_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.LOCKED_CARVED_STONE, ReduxBlocks.LOCKED_CARVED_BASE, event);
            TabUtil.putAfter(ReduxBlocks.LOCKED_CARVED_BASE, ReduxBlocks.LOCKED_CARVED_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.TRAPPED_CARVED_STONE, ReduxBlocks.TRAPPED_CARVED_BASE, event);
            TabUtil.putAfter(ReduxBlocks.TRAPPED_CARVED_BASE, ReduxBlocks.TRAPPED_CARVED_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.BOSS_DOORWAY_CARVED_STONE, ReduxBlocks.BOSS_DOORWAY_CARVED_BASE, event);
            TabUtil.putAfter(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE, ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR, event);


            TabUtil.putAfter(AetherBlocks.SENTRY_STONE, ReduxBlocks.SENTRY_BASE, event);
            TabUtil.putAfter(ReduxBlocks.SENTRY_BASE, ReduxBlocks.SENTRY_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.LOCKED_SENTRY_STONE, ReduxBlocks.LOCKED_SENTRY_BASE, event);
            TabUtil.putAfter(ReduxBlocks.LOCKED_SENTRY_BASE, ReduxBlocks.LOCKED_SENTRY_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.TRAPPED_SENTRY_STONE, ReduxBlocks.TRAPPED_SENTRY_BASE, event);
            TabUtil.putAfter(ReduxBlocks.TRAPPED_SENTRY_BASE, ReduxBlocks.TRAPPED_SENTRY_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE, ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE, event);

            TabUtil.putAfter(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR, ReduxStoneSets.SENTRITE_BRICKS.block(), event);
            TabUtil.putAfter(ReduxStoneSets.SENTRITE_BRICKS.block(), ReduxBlocks.LOCKED_SENTRITE_BRICKS, event);

            TabUtil.putAfter(ReduxBlocks.LOCKED_SENTRITE_BRICKS, ReduxBlocks.RUNELIGHT, event);
            TabUtil.putAfter(ReduxBlocks.RUNELIGHT, ReduxBlocks.LOCKED_RUNELIGHT, event);
        }

        if (tab == AetherCreativeTabs.AETHER_BUILDING_BLOCKS.get()) {
            TabUtil.putAfter(AetherBlocks.ZANITE_BLOCK, ReduxBlocks.RAW_VERIDIUM_BLOCK, event);
            TabUtil.putAfter(ReduxBlocks.RAW_VERIDIUM_BLOCK, ReduxBlocks.VERIDIUM_BLOCK, event);
            TabUtil.putAfter(ReduxBlocks.VERIDIUM_BLOCK, ReduxBlocks.REFINED_SENTRITE_BLOCK, event);

            TabUtil.add(ReduxBlocks.SENTRITE_LANTERN, event);
            TabUtil.add(ReduxBlocks.SENTRITE_CHAIN, event);
        }

        if (tab == AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES.get()) {
            TabUtil.putBefore(AetherItems.GRAVITITE_SWORD, ReduxItems.INFUSED_VERIDIUM_HOE, event);
            TabUtil.putBefore(ReduxItems.INFUSED_VERIDIUM_HOE, ReduxItems.INFUSED_VERIDIUM_AXE, event);
            TabUtil.putBefore(ReduxItems.INFUSED_VERIDIUM_AXE, ReduxItems.INFUSED_VERIDIUM_PICKAXE, event);
            TabUtil.putBefore(ReduxItems.INFUSED_VERIDIUM_PICKAXE, ReduxItems.INFUSED_VERIDIUM_SHOVEL, event);
            TabUtil.putBefore(ReduxItems.INFUSED_VERIDIUM_SHOVEL, /*ReduxItems.INFUSED_VERIDIUM_SWORD, event);
            TabUtil.putBefore(ReduxItems.INFUSED_VERIDIUM_SWORD,*/ ReduxItems.VERIDIUM_HOE, event);
            TabUtil.putBefore(ReduxItems.VERIDIUM_HOE, ReduxItems.VERIDIUM_AXE, event);
            TabUtil.putBefore(ReduxItems.VERIDIUM_AXE, ReduxItems.VERIDIUM_PICKAXE, event);
            TabUtil.putBefore(ReduxItems.VERIDIUM_PICKAXE, ReduxItems.VERIDIUM_SHOVEL, event);
//            TabUtil.putBefore(ReduxItems.VERIDIUM_SHOVEL, ReduxItems.VERIDIUM_SWORD, event);
        }

        if (tab == AetherCreativeTabs.AETHER_INGREDIENTS.get()) {
            TabUtil.putAfter(AetherItems.ZANITE_GEMSTONE, ReduxItems.RAW_VERIDIUM, event);
            TabUtil.putAfter(ReduxItems.RAW_VERIDIUM, ReduxItems.VERIDIUM_INGOT, event);
            TabUtil.putAfter(ReduxItems.VERIDIUM_INGOT, ReduxItems.VERIDIUM_NUGGET, event);

            TabUtil.putAfter(AetherItems.AECHOR_PETAL, ReduxItems.WYND_OATS, event);
            TabUtil.putAfter(ReduxItems.WYND_OATS, ReduxItems.WYND_OAT_PANICLE, event);
        }
    }
}
