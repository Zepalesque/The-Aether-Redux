package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.AetherTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.ReduxTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ReduxBiomeTagsGen extends BiomeTagsProvider {

    public ReduxBiomeTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, Redux.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        
        this.tag(ReduxTags.Biomes.HAS_CLOUDBED).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.MODIFY_MUSIC).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.MODIFY_SKY_COLOR).addTag(AetherTags.Biomes.IS_AETHER);
    }
}
