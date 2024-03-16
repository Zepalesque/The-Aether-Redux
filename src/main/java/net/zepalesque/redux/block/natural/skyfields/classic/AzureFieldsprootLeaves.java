package net.zepalesque.redux.block.natural.skyfields.classic;

import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.util.level.ParticlePlacementUtil;

public class AzureFieldsprootLeaves extends AetherDoubleDropsLeaves {

    public AzureFieldsprootLeaves(Properties properties) {
        super(properties);
    }

    public ParticleOptions getParticle(RandomSource rand, BlockState state) {
        return rand.nextFloat() < 0.2F ?
                ReduxParticleTypes.FALLING_PRISMATIC_LEAVES.get() :
                ReduxParticleTypes.FIELDSPROUT_PETALS_3.get();
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    public void animateTick(BlockState block, Level world, BlockPos position, RandomSource rand) {
        super.animateTick(block, world, position, rand);
        if (rand.nextInt(15) == 0) {
            BlockPos blockpos = position.below();
            BlockState blockstate = world.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(world, blockpos, Direction.UP)) {
                ParticlePlacementUtil.spawnParticleBelow(world, position, rand, getParticle(rand, blockstate));
            }
        }
    }
}
