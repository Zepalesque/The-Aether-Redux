package net.zepalesque.redux.util.compat;

import com.aetherteam.aether_genesis.block.GenesisBlocks;
import com.aetherteam.aether_genesis.client.particle.GenesisParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DABlocks;

import java.util.function.Supplier;

public class AetherGenesisParticleUtil {


    public static @Nullable Supplier<? extends ParticleOptions> getParticle(Block self)
    {
        if (Redux.deepAetherCompat()) {
        return self == GenesisBlocks.BLUE_SKYROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_BLUE_SKYROOT_LEAVES :
               self == GenesisBlocks.DARK_BLUE_SKYROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_DARK_BLUE_SKYROOT_LEAVES :
               null; }
        return null;
    }

    public static boolean isPurpleCrystal(Supplier<? extends ParticleOptions> particle) {
        return particle == GenesisParticleTypes.PURPLE_CRYSTAL_LEAVES;
    }
    public static boolean isPurpleCrystal(ParticleOptions particle) {
        return particle == GenesisParticleTypes.PURPLE_CRYSTAL_LEAVES.get();
    }
}
