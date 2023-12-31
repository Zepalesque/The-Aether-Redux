package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FallingLeafParticleLeavesBlock extends AetherDoubleDropsLeaves {
    private final Supplier<? extends ParticleOptions> particle;

    public FallingLeafParticleLeavesBlock(Supplier<? extends ParticleOptions> particle, Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
        this.particle = particle;

    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    public void animateTick(BlockState p_272714_, Level p_272837_, BlockPos p_273218_, RandomSource p_273360_) {
        super.animateTick(p_272714_, p_272837_, p_273218_, p_273360_);
        if (p_273360_.nextInt(15) == 0) {
            BlockPos blockpos = p_273218_.below();
            BlockState blockstate = p_272837_.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(p_272837_, blockpos, Direction.UP)) {
                ParticleUtils.spawnParticleBelow(p_272837_, p_273218_, p_273360_, this.particle.get());
            }
        }
    }
}