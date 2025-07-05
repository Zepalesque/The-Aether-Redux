package net.zepalesque.redux.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

public class BackportUtil {
    
    // ParticleUtils.spawnParticleBelow
    public static void spawnParticleBelow(Level level, BlockPos pos, RandomSource random, ParticleOptions particle) {
        double d0 = (double)pos.getX() + random.nextDouble();
        double d1 = (double)pos.getY() - 0.05D;
        double d2 = (double)pos.getZ() + random.nextDouble();
        level.addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }
    
    // Mth.lerpInt
    public static int lerpInt(float delta, int start, int end) {
        return start + Mth.floor(delta * (float)(end - start));
    }
    
    public static <T extends Comparable<T>, V extends T, S> BlockState trySetValue(BlockState holder, Property<T> property, V value) {
        BlockState newState;
        try {
            newState = holder.setValue(property, value);
            return newState;
        } catch (IllegalArgumentException e) {
            return holder;
        }
    }
}
