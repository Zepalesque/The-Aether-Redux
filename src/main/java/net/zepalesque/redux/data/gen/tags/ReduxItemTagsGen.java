package net.zepalesque.redux.data.gen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
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

    }
}
