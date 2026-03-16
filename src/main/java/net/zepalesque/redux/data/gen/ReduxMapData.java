package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.data.ReduxDataMaps;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import net.zepalesque.redux.data.resource.registries.ReduxStateListEntries;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.unity.extstate.UnityStateLists;
import net.zepalesque.unity.world.biome.tint.UnityBiomeTints;
import net.zepalesque.zenith.core.Zenith;
import net.zepalesque.zenith.core.registry.StateLists;

public class ReduxMapData extends ReduxDataMapProvider {
    public ReduxMapData(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        Redux.BLOCK_SETS.forEach(set -> set.mapData(this));

        var biomeTints = this.builder(UnityBiomeTints.AETHER_GRASS.get().getDataMap());
        biomeTints.add(ReduxBiomes.GILDED_GROVES, ReduxColors.Tints.GILDED_GRASS_COLOR, false);
        biomeTints.add(ReduxBiomes.THE_BLIGHT, ReduxColors.Tints.BLIGHT_GRASS_COLOR, false);
        biomeTints.add(ReduxBiomes.FROSTED_FORESTS, ReduxColors.Tints.FROSTED_GRASS_COLOR, false);

        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        this.addCompost(compostables, ReduxBlocks.SILVEROOT_LEAVES, 0.3F);
        this.addCompost(compostables, ReduxBlocks.SILVEROOT_LEAF_PILE, 0.3F);
        this.addCompost(compostables, ReduxBlocks.STORMFIR_LEAVES, 0.3F);
        this.addCompost(compostables, ReduxBlocks.STORMFIR_LEAF_PILE, 0.3F);
        this.addCompost(compostables, ReduxBlocks.BLIGHTWILLOW_LEAVES, 0.3F);
        this.addCompost(compostables, ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES, 0.5F);
        this.addCompost(compostables, ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, 0.3F);
        this.addCompost(compostables, ReduxItems.WYND_OATS, 0.3F);
        this.addCompost(compostables, ReduxBlocks.WYNDSPROUTS, 0.3F);
        this.addCompost(compostables, ReduxItems.WYND_OAT_PANICLE, 0.65F);
        this.addCompost(compostables, ReduxBlocks.LUXWEED, 0.3F);
        this.addCompost(compostables, ReduxBlocks.CAELGAE_PATCH, 0.65F);
        this.addCompost(compostables, ReduxItems.CAELGAE_CLUMP, 0.65F);
        this.addCompost(compostables, ReduxBlocks.TURBO_VERBENA, 0.3F);
        this.addCompost(compostables, ReduxBlocks.BLOOMTAIL, 0.3F);
        this.addCompost(compostables, ReduxBlocks.ECHYSIA, 0.65F);

        var leaves = this.builder(ReduxDataMaps.LEAF_PARTICLES);
        final int crystal;
        this.addLeafParticle(leaves, AetherBlocks.SKYROOT_LEAVES, ReduxParticles.SKYROOT_LEAF, 16);
        this.addLeafParticle(leaves, AetherBlocks.CRYSTAL_LEAVES, ReduxParticles.CRYSTAL_LEAF, crystal = 18);
        this.addLeafParticle(leaves, AetherBlocks.CRYSTAL_FRUIT_LEAVES, ReduxParticles.CRYSTAL_LEAF, crystal);
        this.addLeafParticle(leaves, AetherBlocks.GOLDEN_OAK_LEAVES, ReduxParticles.GOLDEN_OAK_LEAF, 14);
        this.addLeafParticle(leaves, ReduxBlocks.SILVEROOT_LEAVES, ReduxParticles.SILVEROOT_LEAF);
        this.addLeafParticle(leaves, ReduxBlocks.STORMFIR_LEAVES, ReduxParticles.STORMFIR_LEAF, 18);
        this.addLeafParticle(leaves, ReduxBlocks.BLIGHTWILLOW_LEAVES, ReduxParticles.BLIGHTWILLOW_LEAF, 20);
        this.addLeafParticle(leaves, ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES, ReduxParticles.INFECTED_BLIGHTWILLOW_LEAF, 25);
        
        var entries = provider.asGetterLookup().lookupOrThrow(Zenith.Keys.EXTENDABLE_STATE_LIST_ENTRY);
        var statelistModifs = this.builder(StateLists.STATE_LIST_MODIFIERS);
        statelistModifs.add(UnityStateLists.FLUTEMOSS.getKey(), HolderSet.direct(entries.getOrThrow(ReduxStateListEntries.ECHYSIA)), false);
    }
}
