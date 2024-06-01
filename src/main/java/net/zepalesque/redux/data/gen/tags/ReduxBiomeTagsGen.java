package net.zepalesque.redux.data.gen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ReduxBiomeTagsGen extends BiomeTagsProvider {

    public ReduxBiomeTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, Redux.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {

    }
}
