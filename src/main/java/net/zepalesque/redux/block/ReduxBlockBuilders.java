package net.zepalesque.redux.block;

import com.google.common.base.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.zepalesque.redux.item.ReduxItems;

import javax.swing.text.html.Option;
import java.util.function.Function;

public class ReduxBlockBuilders {
    
    protected static <T extends Block> DeferredBlock<T> register(
        final String name,
        final Supplier<? extends T> block,
        Function<DeferredBlock<T>, Supplier<? extends Item>> item) { // i dont like java function "pointer" syntax, boxing smh
        DeferredBlock<T> obj = ReduxBlocks.BLOCKS.register(name, block);
        
        ReduxItems.ITEMS.register(name, item.apply(obj));
        return obj;
    }

    public static <T extends Block> DeferredBlock<T> register(
        final String name,
        final Supplier<? extends T> block) {
        return register(name, block,
            object -> () -> new BlockItem( // sily,,
                object.get(),
                new Item.Properties()
            ));
    }
}
