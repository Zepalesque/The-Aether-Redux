package net.zepalesque.redux.block.natural.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class InfectedLeavesBlock extends ShadedLeavesBlock {

    private final Supplier<? extends ParticleOptions> particle;

    public InfectedLeavesBlock(int lightBlock, Supplier<? extends ParticleOptions> particle, Properties properties) {
        super(lightBlock, properties);
        this.particle = particle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (level.getRandom().nextInt(10) == 0) {
            // TODO: Spore drip particles



        }

    }
}
