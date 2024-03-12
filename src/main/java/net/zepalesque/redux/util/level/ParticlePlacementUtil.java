package net.zepalesque.redux.util.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ParticlePlacementUtil {


    /** Duplicate of 1.19.4+'s ParticleUtils#spawnParticleBelow. Exists purely since it does not in 1.19.2.
     */
    public static void spawnParticleBelow(Level level, BlockPos pos, RandomSource random, ParticleOptions particle) {
        double d0 = (double)pos.getX() + random.nextDouble();
        double d1 = (double)pos.getY() - 0.05D;
        double d2 = (double)pos.getZ() + random.nextDouble();
        level.addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}