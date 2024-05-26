package net.zepalesque.redux.blockset.wood.type;

import com.aetherteam.aether.block.construction.BookshelfBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.DatagenUtil;

public abstract class AbstractBookshelfSet<B extends BookshelfBlock> extends LogWallWoodSet {

    protected final DeferredBlock<B> bookshelf;

    public AbstractBookshelfSet(String id, MapColor woodColor, MapColor barkColor, SoundType sound) {
        super(id, woodColor, barkColor, sound);
        DeferredRegister.Blocks blocks = ReduxBlocks.BLOCKS;
        DeferredRegister.Items items = ReduxItems.ITEMS;

        this.bookshelf = bookshelf(blocks, items, id, woodColor, sound);
    }


    protected abstract DeferredBlock<B> bookshelf(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);

    public DeferredBlock<B> bookshelf() {
        return this.bookshelf;
    }

    @Override
    protected void itemData(ReduxItemModelProvider data) {
        super.itemData(data);
        data.itemBlock(this.bookshelf().get());
    }

    @Override
    protected void langData(ReduxLanguageProvider data) {
        super.langData(data);
        String name = DatagenUtil.getNameLocalized(this.id);

        data.add(this.bookshelf());
        data.addLore(this.bookshelf(), "A nice bookshelf made of " + name + " wood. These are nice for decoration, and also will enhance the abilities of Enchanting Tables!");
    }

    @Override
    protected void lootData(ReduxBlockLootProvider data) {
        super.lootData(data);
        data.add(this.bookshelf().get(), (bookshelf) -> data.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3.0F)));
    }

    @Override
    protected void blockTagData(ReduxBlockTagsProvider data) {
        super.blockTagData(data);
        data.tag(BlockTags.MINEABLE_WITH_AXE).add(this.bookshelf().get());
        data.tag(Tags.Blocks.BOOKSHELVES).add(this.bookshelf().get());
    }

    @Override
    public void flammables(FireAccessor accessor) {
        super.flammables(accessor);
        accessor.callSetFlammable(this.bookshelf().get(), 30, 20);
    }
}
