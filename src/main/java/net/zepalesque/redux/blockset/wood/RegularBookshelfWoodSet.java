package net.zepalesque.redux.blockset.wood;

import com.aetherteam.aether.block.construction.BookshelfBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.item.ReduxItems;

public class RegularBookshelfWoodSet extends BookshelfSet<BookshelfBlock> {

    public RegularBookshelfWoodSet(String id, MapColor woodColor, MapColor barkColor, SoundType sound) {
        super(id, woodColor, barkColor, sound);
    }


    protected DeferredBlock<BookshelfBlock> bookshelf(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_bookshelf", () -> new BookshelfBlock(BlockBehaviour.Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .ignitedByLava()
                .strength(1.5F)
                .sound(soundType)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    protected void blockData(ReduxBlockStateProvider data) {
        super.blockData(data);
        data.bookshelf(this.bookshelf().get(), this.planks().get());
    }
}
