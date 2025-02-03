package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.data.ReduxDataMaps;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.unity.world.biome.tint.UnityBiomeTints;

import java.util.concurrent.CompletableFuture;

public class ReduxMapData extends ReduxDataMapProvider {

    public ReduxMapData(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        Redux.BLOCK_SETS.forEach(set -> set.mapData(this));

        var biomeTints = this.builder(UnityBiomeTints.AETHER_GRASS.get().getDataMap());
        biomeTints.add(ReduxBiomes.GILDED_GROVES, ReduxColors.Tints.GILDED_GRASS_COLOR, false);
        biomeTints.add(ReduxBiomes.THE_BLIGHT, ReduxColors.Tints.BLIGHT_GRASS_COLOR, false);

        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        this.addCompost(compostables, ReduxBlocks.GILDENROOT_LEAVES, 0.3F);
        this.addCompost(compostables, ReduxBlocks.GILDENROOT_LEAF_PILE, 0.05F);
        this.addCompost(compostables, ReduxBlocks.SHADEROOT_LEAVES, 0.3F);
        this.addCompost(compostables, ReduxBlocks.SHADEROOT_LEAF_PILE, 0.05F);
        this.addCompost(compostables, ReduxItems.WYND_OATS, 0.3F);
        this.addCompost(compostables, ReduxBlocks.WYNDSPROUTS, 0.3F);
        this.addCompost(compostables, ReduxItems.WYND_OAT_PANICLE, 0.65F);

        var leaves = this.builder(ReduxDataMaps.LEAF_PARTICLES);
        this.addLeafParticle(leaves, AetherBlocks.SKYROOT_LEAVES, ReduxParticles.SKYROOT_LEAF, 20);
        this.addLeafParticle(leaves, AetherBlocks.CRYSTAL_LEAVES, ReduxParticles.CRYSTAL_LEAF, 30);
        this.addLeafParticle(leaves, AetherBlocks.CRYSTAL_FRUIT_LEAVES, ReduxParticles.CRYSTAL_LEAF, 30);
        this.addLeafParticle(leaves, AetherBlocks.GOLDEN_OAK_LEAVES, ReduxParticles.GOLDEN_OAK_LEAF);
        this.addLeafParticle(leaves, ReduxBlocks.GILDENROOT_LEAVES, ReduxParticles.GILDENROOT_LEAF, 20);
        this.addLeafParticle(leaves, ReduxBlocks.SHADEROOT_LEAVES, ReduxParticles.SHADEROOT_LEAF, 13);
        this.addLeafParticle(leaves, ReduxBlocks.BLIGHTWILLOW_LEAVES, ReduxParticles.BLIGHTWILLOW_LEAF, 12);
    }
}
