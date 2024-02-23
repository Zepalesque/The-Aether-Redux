package net.zepalesque.redux.util.compat;

import com.aetherteam.aether_genesis.block.GenesisBlocks;
import com.aetherteam.aether_genesis.client.particle.GenesisParticleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DABlocks;

import java.util.Map;
import java.util.function.Supplier;

public class AetherGenesisParticleUtil {

    private static final Map<Block, Supplier<? extends ParticleOptions>> PARTICLE_MAP = new ImmutableMap.Builder<Block, Supplier<? extends ParticleOptions>>()
            .put(GenesisBlocks.BLUE_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_BLUE_SKYROOT_LEAVES)
            .put(GenesisBlocks.DARK_BLUE_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_DARK_BLUE_SKYROOT_LEAVES).build();

    public static @Nullable Supplier<? extends ParticleOptions> getParticle(Block self) {
        return PARTICLE_MAP.get(self);
    }

    public static boolean isPurpleCrystal(Supplier<? extends ParticleOptions> particle) {
        return particle == GenesisParticleTypes.PURPLE_CRYSTAL_LEAVES;
    }
    public static boolean isPurpleCrystal(ParticleOptions particle) {
        return particle == GenesisParticleTypes.PURPLE_CRYSTAL_LEAVES.get();
    }
}
