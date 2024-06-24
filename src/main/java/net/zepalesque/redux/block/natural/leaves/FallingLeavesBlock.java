package net.zepalesque.redux.block.natural.leaves;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.function.Supplier;

public class FallingLeavesBlock extends AetherDoubleDropsLeaves {
    private final Supplier<? extends ParticleOptions> particle;

    public FallingLeavesBlock(Supplier<? extends ParticleOptions> particle, BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
        this.particle = particle;
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        super.animateTick(state, level, pos, rand);

        if (ReduxConfig.CLIENT.leaf_particles.get() && rand.nextInt(15) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.isCollisionShapeFullBlock(level, blockpos)) {
                ParticleUtils.spawnParticleBelow(level, pos, rand, this.particle.get());
            }
        }
    }
}
