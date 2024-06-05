package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;

public class ReduxBlockStateGen extends ReduxBlockStateProvider {

    public ReduxBlockStateGen(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        Redux.WOOD_SETS.forEach(set -> set.blockData(this));
        Redux.STONE_SETS.forEach(set -> set.blockData(this));

        this.tintableShortGrass(ReduxBlocks.SHORT_AETHER_GRASS.get(), "natural/");
        this.block(ReduxBlocks.CLOUDROOT_LEAVES.get(), "natural/");
        this.crossBlock(ReduxBlocks.CLOUDROOT_SAPLING.get(), "natural/");

        this.pillar(ReduxBlocks.CARVED_STONE_PILLAR.get(), "dungeon/");
        this.pillar(ReduxBlocks.SENTRY_STONE_PILLAR.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.CARVED_STONE_BASE.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.SENTRY_STONE_BASE.get(), "dungeon/");

        this.pillar(ReduxBlocks.LOCKED_CARVED_STONE_PILLAR.get(), ReduxBlocks.CARVED_STONE_PILLAR.get(), "dungeon/");
        this.pillar(ReduxBlocks.LOCKED_SENTRY_STONE_PILLAR.get(), ReduxBlocks.SENTRY_STONE_PILLAR.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.LOCKED_CARVED_STONE_BASE.get(), ReduxBlocks.CARVED_STONE_BASE.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.LOCKED_SENTRY_STONE_BASE.get(), ReduxBlocks.SENTRY_STONE_BASE.get(), "dungeon/");

        this.pillar(ReduxBlocks.TRAPPED_CARVED_STONE_PILLAR.get(), ReduxBlocks.CARVED_STONE_PILLAR.get(), "dungeon/");
        this.pillar(ReduxBlocks.TRAPPED_SENTRY_STONE_PILLAR.get(), ReduxBlocks.SENTRY_STONE_PILLAR.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.TRAPPED_CARVED_STONE_BASE.get(), ReduxBlocks.CARVED_STONE_BASE.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.TRAPPED_SENTRY_STONE_BASE.get(), ReduxBlocks.SENTRY_STONE_BASE.get(), "dungeon/");

        this.invisiblePillar(ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_PILLAR.get(), ReduxBlocks.CARVED_STONE_PILLAR.get(), "dungeon/");
        this.invisiblePillar(ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_PILLAR.get(), ReduxBlocks.SENTRY_STONE_PILLAR.get(), "dungeon/");
        this.invisibleBaseBrick(ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_BASE.get(), ReduxBlocks.CARVED_STONE_BASE.get(), "dungeon/");
        this.invisibleBaseBrick(ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_BASE.get(), ReduxBlocks.SENTRY_STONE_BASE.get(), "dungeon/");

        this.cubeActivatable(ReduxBlocks.RUNELIGHT.get(), "dungeon/");
        this.cubeActivatable(ReduxBlocks.LOCKED_RUNELIGHT.get(), ReduxBlocks.RUNELIGHT.get(), "dungeon/");

    }
}
