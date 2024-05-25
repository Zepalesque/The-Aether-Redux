package net.zepalesque.redux.data.prov.tags;

import com.aetherteam.aether.Aether;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public abstract class ReduxItemTagsProvider extends ItemTagsProvider {

    public ReduxItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, String id, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, id, helper);
    }

    @Override
    public IntrinsicTagAppender<Item> tag(TagKey<Item> tag) {
        return super.tag(tag);
    }

    @Override
    public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        super.copy(blockTag, itemTag);
    }
}
