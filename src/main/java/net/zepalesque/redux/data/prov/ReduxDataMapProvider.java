package net.zepalesque.redux.data.prov;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.zepalesque.unity.data.prov.UnityDataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class ReduxDataMapProvider extends UnityDataMapProvider {

    protected ReduxDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @SuppressWarnings("deprecation")
    public void addLeafParticle(DataMapProvider.Builder<Pair<ParticleOptions, Integer>, Block> map, Supplier<? extends Block> block, Supplier<? extends ParticleOptions> particle) {
        map.add(block.get().builtInRegistryHolder().unwrapKey().orElseThrow(), Pair.of(particle.get(), 15), false);
    }

    @SuppressWarnings("deprecation")
    public void addLeafParticle(DataMapProvider.Builder<Pair<ParticleOptions, Integer>, Block> map, Supplier<? extends Block> block, Supplier<? extends ParticleOptions> particle, int rarity) {
        map.add(block.get().builtInRegistryHolder().unwrapKey().orElseThrow(), Pair.of(particle.get(), rarity), false);
    }
}
