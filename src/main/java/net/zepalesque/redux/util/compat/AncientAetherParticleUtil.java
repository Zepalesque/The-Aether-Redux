package net.zepalesque.redux.util.compat;

import com.aetherteam.aether_genesis.block.GenesisBlocks;
import com.aetherteam.aether_genesis.client.particle.GenesisParticleTypes;
import com.google.common.collect.ImmutableMap;
import net.builderdog.ancient_aether.block.AncientAetherBlocks;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class AncientAetherParticleUtil {

    private static final Map<Block, Supplier<? extends ParticleOptions>> PARTICLE_MAP = new ImmutableMap.Builder<Block, Supplier<? extends ParticleOptions>>()
            .put(AncientAetherBlocks.CRYSTAL_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_CRYSTAL_SKYROOT_LEAVES)
            .put(AncientAetherBlocks.ENCHANTED_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_ENCHANTED_SKYROOT_LEAVES)
            .put(AncientAetherBlocks.HIGHSPROOT_LEAVES.get(), ReduxParticleTypes.FALLING_HIGHSPROOT_NEEDLES)
            .put(AncientAetherBlocks.SKYROOT_PINE_LEAVES.get(), ReduxParticleTypes.FALLING_SKYROOT_PINE_NEEDLES)
            .put(AncientAetherBlocks.SAKURA_LEAVES.get(), ReduxParticleTypes.SAKURA_PETALS)
            .build();

    public static @Nullable Supplier<? extends ParticleOptions> getParticle(Block self) {
        return PARTICLE_MAP.get(self);
    }
}
