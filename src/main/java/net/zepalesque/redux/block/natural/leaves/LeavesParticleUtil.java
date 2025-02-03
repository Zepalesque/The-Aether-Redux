package net.zepalesque.redux.block.natural.leaves;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.ReduxDataMaps;
import org.jetbrains.annotations.Nullable;

public class LeavesParticleUtil {

    @Nullable
    @SuppressWarnings("deprecation")
    public static Pair<ParticleOptions, Integer> particleFor(Block b) {
        @Nullable Pair<ParticleOptions, Integer> pair = b.builtInRegistryHolder().getData(ReduxDataMaps.LEAF_PARTICLES);

        return ReduxConfig.CLIENT.leaf_particles.get() ? pair : null;
    }

    public static void createParticle(BlockState state, Level level, BlockPos pos, RandomSource rand, ParticleOptions particle, int chance) {
        if (ReduxConfig.CLIENT.leaf_particles.get() && rand.nextInt(chance) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.isCollisionShapeFullBlock(level, blockpos)) {
                ParticleUtils.spawnParticleBelow(level, pos, rand, particle);
            }
        }
    }

}
