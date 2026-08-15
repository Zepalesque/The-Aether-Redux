package net.zepalesque.redux.block.natural.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.api.WeightedParticleEntry;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.ReduxDataMaps;
import org.jetbrains.annotations.Nullable;

public class LeafParticleUtil {
	@Nullable
	@SuppressWarnings("deprecation")
	public static WeightedParticleEntry findEntry(Block b) {
		@Nullable
		WeightedParticleEntry entry = b.builtInRegistryHolder().getData(ReduxDataMaps.LEAF_PARTICLES);

		return ReduxConfig.CLIENT.leaf_particles.get() ? entry : null;
	}

	public static void createParticle(
		BlockState state,
		Level level,
		BlockPos pos,
		RandomSource rand,
		WeightedParticleEntry entry
	) {
		if (entry.success(rand)) {
			var below = pos.below();
			var blockstate = level.getBlockState(below);
			if (!blockstate.isCollisionShapeFullBlock(level, below)) ParticleUtils.spawnParticleBelow(
				level,
				pos,
				rand,
				entry.particle()
			);
		}
	}
}
