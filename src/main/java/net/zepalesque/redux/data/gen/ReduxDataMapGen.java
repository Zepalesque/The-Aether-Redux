package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.data.generators.AetherDataMapData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.data.ReduxDataMaps;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ReduxDataMapGen extends ReduxDataMapProvider {

    protected ReduxDataMapGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        Redux.WOOD_SETS.forEach(set -> set.mapData(this));
        Redux.STONE_SETS.forEach(set -> set.mapData(this));
    }
}
