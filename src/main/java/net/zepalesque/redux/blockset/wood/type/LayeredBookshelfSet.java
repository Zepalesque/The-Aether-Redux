package net.zepalesque.redux.blockset.wood.type;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.block.construction.LayeredBookshelfBlock;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;

public class LayeredBookshelfSet extends AbstractBookshelfSet<LayeredBookshelfBlock> {

    public LayeredBookshelfSet(String id, MapColor woodColor, MapColor barkColor, SoundType sound) {
        super(id, woodColor, barkColor, sound);
    }

    protected DeferredBlock<LayeredBookshelfBlock> bookshelf(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_bookshelf", () -> new LayeredBookshelfBlock(BlockBehaviour.Properties.of()
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
    public void blockData(ReduxBlockStateProvider data) {
        super.blockData(data);
        data.layeredBookshelf(this.bookshelf().get(), this.planks().get());
    }
}
