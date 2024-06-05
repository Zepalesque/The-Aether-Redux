package net.zepalesque.redux.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.world.item.CreativeModeTab;
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
            TabUtil.putAfter(AetherBlocks.CARVED_STONE, ReduxBlocks.CARVED_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.CARVED_STONE_BASE, ReduxBlocks.CARVED_STONE_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.LOCKED_CARVED_STONE, ReduxBlocks.LOCKED_CARVED_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.LOCKED_CARVED_STONE_BASE, ReduxBlocks.LOCKED_CARVED_STONE_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.TRAPPED_CARVED_STONE, ReduxBlocks.TRAPPED_CARVED_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.TRAPPED_CARVED_STONE_BASE, ReduxBlocks.TRAPPED_CARVED_STONE_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.BOSS_DOORWAY_CARVED_STONE, ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_BASE, ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_PILLAR, event);


            TabUtil.putAfter(AetherBlocks.SENTRY_STONE, ReduxBlocks.SENTRY_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.SENTRY_STONE_BASE, ReduxBlocks.SENTRY_STONE_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.LOCKED_SENTRY_STONE, ReduxBlocks.LOCKED_SENTRY_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.LOCKED_SENTRY_STONE_BASE, ReduxBlocks.LOCKED_SENTRY_STONE_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.TRAPPED_SENTRY_STONE, ReduxBlocks.TRAPPED_SENTRY_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.TRAPPED_SENTRY_STONE_BASE, ReduxBlocks.TRAPPED_SENTRY_STONE_PILLAR, event);

            TabUtil.putAfter(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE, ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_BASE, event);
            TabUtil.putAfter(ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_BASE, ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_PILLAR, event);

            TabUtil.putAfter(ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_PILLAR, ReduxStoneSets.SENTRITE_BRICKS.block(), event);
            TabUtil.putAfter(ReduxStoneSets.SENTRITE_BRICKS.block(), ReduxBlocks.LOCKED_SENTRITE_BRICKS, event);

            TabUtil.putAfter(ReduxBlocks.LOCKED_SENTRITE_BRICKS, ReduxBlocks.RUNELIGHT, event);
            TabUtil.putAfter(ReduxBlocks.RUNELIGHT, ReduxBlocks.LOCKED_RUNELIGHT, event);
        }
    }
}
