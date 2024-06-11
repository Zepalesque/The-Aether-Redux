package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ReduxBlockTagsGen extends ReduxBlockTagsProvider {

    public ReduxBlockTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Redux.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Redux.WOOD_SETS.forEach(set -> set.blockTagData(this));
        Redux.STONE_SETS.forEach(set -> set.blockTagData(this));

        this.tag(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR).add(AetherBlocks.QUICKSOIL.get());

        this.tag(BlockTags.LEAVES).add(
                ReduxBlocks.CLOUDROOT_LEAVES.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                ReduxBlocks.CLOUDROOT_LEAVES.get()
        );

        this.tag(AetherTags.Blocks.SENTRY_BLOCKS).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.LOCKED_CARVED_PILLAR.get(),
                ReduxBlocks.LOCKED_SENTRY_PILLAR.get(),
                ReduxBlocks.LOCKED_CARVED_BASE.get(),
                ReduxBlocks.LOCKED_SENTRY_BASE.get(),
                ReduxBlocks.TRAPPED_CARVED_PILLAR.get(),
                ReduxBlocks.TRAPPED_SENTRY_PILLAR.get(),
                ReduxBlocks.TRAPPED_CARVED_BASE.get(),
                ReduxBlocks.TRAPPED_SENTRY_BASE.get(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get(),
                ReduxBlocks.LOCKED_RUNELIGHT.get(),
                ReduxBlocks.LOCKED_SENTRITE_BRICKS.get()
        );
        this.tag(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS).add(
                ReduxBlocks.LOCKED_CARVED_PILLAR.get(),
                ReduxBlocks.LOCKED_SENTRY_PILLAR.get(),
                ReduxBlocks.LOCKED_CARVED_BASE.get(),
                ReduxBlocks.LOCKED_SENTRY_BASE.get(),
                ReduxBlocks.LOCKED_RUNELIGHT.get(),
                ReduxBlocks.LOCKED_SENTRITE_BRICKS.get()
        );
        this.tag(AetherTags.Blocks.DUNGEON_BLOCKS).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get()
        );
        this.tag(AetherTags.Blocks.TRAPPED_DUNGEON_BLOCKS).add(
                ReduxBlocks.TRAPPED_CARVED_PILLAR.get(),
                ReduxBlocks.TRAPPED_SENTRY_PILLAR.get(),
                ReduxBlocks.TRAPPED_CARVED_BASE.get(),
                ReduxBlocks.TRAPPED_SENTRY_BASE.get()
        );
        this.tag(AetherTags.Blocks.BOSS_DOORWAY_DUNGEON_BLOCKS).add(
                ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get()
        );


        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get()
        );

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get()
        );

        this.tag(BlockTags.FLOWER_POTS).add(
                ReduxBlocks.POTTED_WYNDSPROUTS.get(),
                ReduxBlocks.POTTED_SKYSPROUTS.get()
        );

        this.tag(BlockTags.CROPS).add(
                ReduxBlocks.WYNDOATS.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                ReduxBlocks.WYNDSPROUTS.get(),
                ReduxBlocks.SKYSPROUTS.get(),
                ReduxBlocks.WYNDOATS.get()
        );

    }
}
