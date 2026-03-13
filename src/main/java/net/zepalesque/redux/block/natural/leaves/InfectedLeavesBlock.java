package net.zepalesque.redux.block.natural.leaves;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

// TODO: harvesting
public class InfectedLeavesBlock extends BlightwillowLeavesBlock {
	@SuppressWarnings("unused")
	private final Supplier<? extends ParticleOptions> particle;

	public InfectedLeavesBlock(
		Supplier<? extends ParticleOptions> particle,
		int lightBlock,
		Properties properties
	) {
		super(lightBlock, properties);
		this.particle = particle;
		//this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.HAS_SPORES, false));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);

		if (random.nextInt(10) == 0) {
			var below = pos.below();
			var blockstate = level.getBlockState(below);
			if (!blockstate.isCollisionShapeFullBlock(level, below)) {
			}
		}
	}
}
