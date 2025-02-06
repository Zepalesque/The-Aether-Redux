package net.zepalesque.redux.block.natural.leaves;

import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

// TODO: harvesting
public class InfectedLeavesBlock extends AetherDoubleDropsLeaves {

    private final Supplier<? extends ParticleOptions> particle;

    public InfectedLeavesBlock(Supplier<? extends ParticleOptions> particle, Properties properties) {
        super(properties);
        this.particle = particle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (random.nextInt(10) == 0) {
            BlockPos below = pos.below();
            BlockState blockstate = level.getBlockState(below);
            if (!blockstate.isCollisionShapeFullBlock(level, below)) {
//                ParticleUtils.spawnParticleBelow(level, pos, random, this.particle.get());
            }
        }
    }
}
