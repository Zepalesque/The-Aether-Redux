package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;

public class ReduxItemModelGen extends ReduxItemModelProvider {

    public ReduxItemModelGen(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    protected void registerModels() {
        Redux.WOOD_SETS.forEach(set -> set.itemData(this));

        itemBlockFlatCustomTexture(ReduxBlocks.SHORT_AETHER_GRASS, "natural/aether_medium_grass");
    }
}
