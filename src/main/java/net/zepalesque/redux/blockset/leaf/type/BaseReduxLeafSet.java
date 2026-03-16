package net.zepalesque.redux.blockset.leaf.type;

import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.data.ReduxDataMaps;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;

public abstract class BaseReduxLeafSet<L extends LeavesBlock, S extends SaplingBlock, Self extends BaseReduxLeafSet<L, S, Self>> extends BaseLeafPileSet<L, S, Self> {
	private final DeferredHolder<ParticleType<?>, SimpleParticleType> particle;
	
	
	public BaseReduxLeafSet(String id, String saplTexFold, String leafTexFold, TreeGrower grower, Supplier<L> leaves, Function<TreeGrower, S> sapling, DeferredHolder<ParticleType<?>, SimpleParticleType> particle) {
		super(id, saplTexFold, leafTexFold, grower, leaves, sapling);
		this.particle = particle;
	}
	
	@Override
	public void mapData(ReduxDataMapProvider data) {
		super.mapData(data);
		var leaves = data.builder(ReduxDataMaps.LEAF_PARTICLES);
		data.addLeafParticle(leaves, this.leaves(), this.particle, 18);
	}
}
