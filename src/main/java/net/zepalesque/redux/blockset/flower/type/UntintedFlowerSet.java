package net.zepalesque.redux.blockset.flower.type;

import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.zenith.util.function.Consumers;

public class UntintedFlowerSet<B extends Block> extends BaseFlowerSet<B> {
    public UntintedFlowerSet(String id, String textureFolder, Supplier<B> constructor) {
        super(id, textureFolder, constructor);
    }

    @Override
    public void blockData(ReduxBlockStateProvider data) {
        data.crossBlock(this.flower().get(), this.textureFolder);
        Consumers.C3<Block, Block, String> pot = this.usePottedPrefix
            ? data::potAlt
            : data::pottedPlant;
        pot.accept(this.pot().get(), this.flower().get(), this.textureFolder);
    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        data.itemBlockFlat(this.flower().get(), this.textureFolder);
    }
}
