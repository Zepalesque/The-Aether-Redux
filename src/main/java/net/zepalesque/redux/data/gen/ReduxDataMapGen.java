package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.generators.AetherDataMapData;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.data.ReduxDataMaps;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.world.biome.tint.ReduxBiomeTints;

import java.util.concurrent.CompletableFuture;

public class ReduxDataMapGen extends ReduxDataMapProvider {

    public ReduxDataMapGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        Redux.WOOD_SETS.forEach(set -> set.mapData(this));
        Redux.STONE_SETS.forEach(set -> set.mapData(this));

        var biomeTints = this.builder(ReduxBiomeTints.AETHER_GRASS.get().getDataMap());
        biomeTints.add(AetherBiomes.SKYROOT_FOREST, 0xA2F2BC, false);
        biomeTints.add(AetherBiomes.SKYROOT_WOODLAND, 0x96E8B0, false);
        biomeTints.add(AetherBiomes.SKYROOT_MEADOW, 0xBAFFCB, false);

        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        this.addCompost(compostables, ReduxBlocks.CLOUDROOT_LEAVES, 0.3F);
        this.addCompost(compostables, ReduxBlocks.CLOUDROOT_SAPLING, 0.3F);
        this.addCompost(compostables, ReduxBlocks.SHORT_AETHER_GRASS, 0.3F);
        this.addCompost(compostables, ReduxItems.WYND_OATS, 0.3F);
        this.addCompost(compostables, ReduxBlocks.WYNDSPROUTS, 0.3F);
        this.addCompost(compostables, ReduxItems.WYND_OAT_PANICLE, 0.65F);
    }
}
