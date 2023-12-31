package net.zepalesque.redux.util.compat;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DABlocks;

import java.util.function.Supplier;

public class DeepAetherParticleUtil {


    public static @Nullable Supplier<? extends ParticleOptions> getParticle(Block self)
    {
        if (Redux.deepAetherCompat()) {
        return self == DABlocks.BLUE_ROSEROOT_LEAVES.get() || self == DABlocks.FLOWERING_BLUE_ROSEROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_BLUE_ROSEROOT_LEAVES :
                self == DABlocks.ROSEROOT_LEAVES.get() || self == DABlocks.FLOWERING_ROSEROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_ROSEROOT_LEAVES :
                        self == DABlocks.CONBERRY_LEAVES.get() ? ReduxParticleTypes.FALLING_CONBERRY_LEAVES :
                                self == DABlocks.CRUDEROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_CRUDEROOT_NEEDLES :
                                        self == DABlocks.YAGROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_YAGROOT_LEAVES :
                                                self == DABlocks.SUNROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_SUNROOT_LEAVES : null; }
        return null;
    }
}
