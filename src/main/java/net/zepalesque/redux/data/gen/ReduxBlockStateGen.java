package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;

public class ReduxBlockStateGen extends ReduxBlockStateProvider {

    public ReduxBlockStateGen(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.tintableShortGrass(ReduxBlocks.SHORT_AETHER_GRASS.get(), "natural/");

    }
}
