package net.zepalesque.redux.blockset.leaf.type;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;

public class AetherLeafSet extends BaseReduxLeafSet<AetherDoubleDropsLeaves, SaplingBlock, AetherLeafSet> {
	public AetherLeafSet(
		String id,
		String saplTexFold,
		String leafTexFold,
		TreeGrower grower,
		MapColor leafColor,
		MapColor saplingColor,
		DeferredHolder<ParticleType<?>,
		SimpleParticleType> particle
	) {
		super(
			id,
			saplTexFold,
			leafTexFold,
			grower,
			() -> new AetherDoubleDropsLeaves(
				BlockBehaviour.Properties
					.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get())
					.mapColor(leafColor)
			),
			treeGrower -> new SaplingBlock(
				treeGrower,
				BlockBehaviour.Properties
					.ofFullCopy(AetherBlocks.SKYROOT_SAPLING.get())
					.mapColor(saplingColor)
			),
			particle
		);
	}

	@Override
	public void mainBlockData(ReduxBlockStateProvider data) {
		data.crossBlock(this.sapling().get(), this.saplTexFold);
		data.pottedPlant(this.pot().get(), this.sapling().get(), this.saplTexFold);
		data.block(this.leaves().get(), this.leafTexFold);
	}

	@Override
	public void mainItemData(ReduxItemModelProvider data) {
		data.itemBlockFlat(this.sapling().get(), this.saplTexFold);
		data.itemBlock(this.leaves().get());
	}
}
