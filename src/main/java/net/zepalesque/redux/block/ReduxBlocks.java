package net.zepalesque.redux.block;

import com.google.common.base.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.natural.AetherShortGrassBlock;
import net.zepalesque.redux.item.ReduxItems;

import java.util.function.Function;

public class ReduxBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Redux.MODID);

    public static DeferredBlock<AetherShortGrassBlock> SHORT_AETHER_GRASS = register("short_aether_grass",
            () -> new AetherShortGrassBlock(
                    Properties.ofFullCopy(Blocks.SHORT_GRASS)
                            // TODO: Check if this is necessary
                            .hasPostProcess((state, level, pos) -> true)
            ));


    private static <T extends Block> DeferredBlock<T> register(final String name, final Supplier<? extends T> block, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> obj = ReduxBlocks.BLOCKS.register(name, block);
        ReduxItems.ITEMS.register(name, item.apply(obj));
        return obj;
    }

    public static <T extends Block> DeferredBlock<T> register(final String name, final Supplier<? extends T> block) {
        return register(name, block, object -> () -> new BlockItem(object.get(), new Item.Properties()));
    }
}
