package net.zepalesque.redux.data.prov;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.zepalesque.redux.api.WeightedParticleEntry;
import net.zepalesque.unity.data.prov.UnityDataMapProvider;

public abstract class ReduxDataMapProvider extends UnityDataMapProvider {
	protected ReduxDataMapProvider(
		PackOutput packOutput,
		CompletableFuture<HolderLookup.Provider> lookupProvider
	) {
		super(packOutput, lookupProvider);
	}

	@SuppressWarnings("deprecation")
	public void addLeafParticle(
		DataMapProvider.Builder<WeightedParticleEntry, Block> map,
		Supplier<? extends Block> block,
		Supplier<? extends ParticleOptions> particle
	) {
		map.add(
			block.get().builtInRegistryHolder().unwrapKey().orElseThrow(),
			WeightedParticleEntry.of(particle.get()),
			false
		);
	}

	@SuppressWarnings("deprecation")
	public void addLeafParticle(
		DataMapProvider.Builder<WeightedParticleEntry, Block> map,
		Supplier<? extends Block> block,
		Supplier<? extends ParticleOptions> particle,
		int rarity
	) {
		map.add(
			block.get().builtInRegistryHolder().unwrapKey().orElseThrow(),
			WeightedParticleEntry.of(particle.get(), rarity),
			false
		);
	}

	@SuppressWarnings("deprecation")
	public void addLeafParticle(
		DataMapProvider.Builder<WeightedParticleEntry, Block> map,
		Supplier<? extends Block> block,
		Supplier<? extends ParticleOptions> particle,
		float chance
	) {
		map.add(
			block.get().builtInRegistryHolder().unwrapKey().orElseThrow(),
			WeightedParticleEntry.of(particle.get(), chance),
			false
		);
	}
}
