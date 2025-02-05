package net.zepalesque.redux.block.natural.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.api.LeafChanceEntry;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.ReduxDataMaps;
import org.jetbrains.annotations.Nullable;

public class LeavesParticleUtil {

    @Nullable
    @SuppressWarnings("deprecation")
    public static LeafChanceEntry findEntry(Block b) {
        @Nullable LeafChanceEntry entry = b.builtInRegistryHolder().getData(ReduxDataMaps.LEAF_PARTICLES);

        return ReduxConfig.CLIENT.leaf_particles.get() ? entry : null;
    }

    public static void createParticle(BlockState state, Level level, BlockPos pos, RandomSource rand, LeafChanceEntry entry) {
        if (entry.success(rand)) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.isCollisionShapeFullBlock(level, blockpos)) {
                ParticleUtils.spawnParticleBelow(level, pos, rand, entry.particle());
            }
        }
    }

}
