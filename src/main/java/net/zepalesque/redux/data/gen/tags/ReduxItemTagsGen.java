package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.AetherTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ReduxItemTagsGen extends ReduxItemTagsProvider {

    public ReduxItemTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, Redux.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Redux.WOOD_SETS.forEach(set -> set.itemTagData(this));
        Redux.STONE_SETS.forEach(set -> set.itemTagData(this));


        this.tag(AetherTags.Items.SENTRY_BLOCKS).add(
                ReduxBlocks.CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.SENTRY_STONE_BASE.get().asItem(),
                ReduxBlocks.LOCKED_CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_STONE_BASE.get().asItem(),
                ReduxBlocks.TRAPPED_CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_STONE_BASE.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_BASE.get().asItem(),
                ReduxBlocks.RUNELIGHT.get().asItem(),
                ReduxBlocks.LOCKED_RUNELIGHT.get().asItem()
        );
        this.tag(AetherTags.Items.LOCKED_DUNGEON_BLOCKS).add(
                ReduxBlocks.LOCKED_CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_STONE_BASE.get().asItem(),
                ReduxBlocks.LOCKED_RUNELIGHT.get().asItem()
        );
        this.tag(AetherTags.Items.DUNGEON_BLOCKS).add(
                ReduxBlocks.CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.SENTRY_STONE_BASE.get().asItem(),
                ReduxBlocks.RUNELIGHT.get().asItem()
        );
        this.tag(AetherTags.Items.TRAPPED_DUNGEON_BLOCKS).add(
                ReduxBlocks.TRAPPED_CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_STONE_BASE.get().asItem()
        );
        this.tag(AetherTags.Items.BOSS_DOORWAY_DUNGEON_BLOCKS).add(
                ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_STONE_BASE.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_STONE_BASE.get().asItem()
        );
    }
}
