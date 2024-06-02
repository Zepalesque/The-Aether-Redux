package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
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
        this.tag(ReduxTags.Blocks.COARSE_AETHER_DIRT).add(AetherBlocks.AETHER_GRASS_BLOCK.get());

    }
}
