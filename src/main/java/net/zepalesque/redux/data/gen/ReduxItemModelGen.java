package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.item.ReduxItems;

public class ReduxItemModelGen extends ReduxItemModelProvider {

    public ReduxItemModelGen(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    protected void registerModels() {
        Redux.WOOD_SETS.forEach(set -> set.itemData(this));
        Redux.STONE_SETS.forEach(set -> set.itemData(this));

        itemBlockFlatCustomTexture(ReduxBlocks.SHORT_AETHER_GRASS.get(), "natural/aether_medium_grass");
        this.itemBlock(ReduxBlocks.CLOUDROOT_LEAVES.get());
        this.itemBlockFlat(ReduxBlocks.CLOUDROOT_SAPLING.get(), "natural/");

        this.itemBlock(ReduxBlocks.CARVED_BASE.get());
        this.itemBlock(ReduxBlocks.CARVED_PILLAR.get());
        this.itemBlock(ReduxBlocks.SENTRY_BASE.get());
        this.itemBlock(ReduxBlocks.SENTRY_PILLAR.get());

        this.itemOverlayColumn(ReduxBlocks.LOCKED_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/lock", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.LOCKED_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/lock", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.LOCKED_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/lock", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.LOCKED_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/lock", "dungeon/");

        this.itemOverlayColumn(ReduxBlocks.TRAPPED_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/exclamation", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.TRAPPED_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/exclamation", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.TRAPPED_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/exclamation", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.TRAPPED_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/exclamation", "dungeon/");

        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/door", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/door", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/door", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/door", "dungeon/");

        this.itemBlock(ReduxBlocks.RUNELIGHT.get(), "_on");
        this.itemOverlayDungeonBlock(ReduxBlocks.LOCKED_RUNELIGHT.get(), ReduxBlocks.RUNELIGHT.get(), "lock", "dungeon/", "_on");
        this.itemOverlayDungeonBlock(ReduxBlocks.LOCKED_SENTRITE_BRICKS.get(), ReduxStoneSets.SENTRITE_BRICKS.block().get(), "construction/", "lock");

        this.item(ReduxItems.WYND_OAT_PANICLE.get(), "materials/");
        this.item(ReduxItems.WYND_OATS.get(), "food/");

        itemBlockFlatTintOverlay(ReduxBlocks.WYNDSPROUTS.get(), "natural/");
        itemBlockFlatTintOverlay(ReduxBlocks.SKYSPROUTS.get(), "natural/");

        this.item(ReduxItems.RAW_VERIDIUM.get(), "materials/");
        this.item(ReduxItems.VERIDIUM_INGOT.get(), "materials/");
        this.item(ReduxItems.VERIDIUM_NUGGET.get(), "materials/");

        this.item(ReduxItems.REFINED_SENTRITE.get(), "materials/");
        this.item(ReduxItems.SENTRITE_CHUNK.get(), "materials/");

        this.item(ReduxBlocks.SENTRITE_LANTERN.get().asItem(), "misc/");
        this.item(ReduxBlocks.SENTRITE_CHAIN.get().asItem(), "misc/");

        this.handheldItem(ReduxItems.VERIDIUM_PICKAXE.get(), "tools/");
        this.handheldItem(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(), "tools/");

    }

}
