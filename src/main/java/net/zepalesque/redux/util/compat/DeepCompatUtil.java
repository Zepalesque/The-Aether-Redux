package net.zepalesque.redux.util.compat;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DABlocks;

import java.util.Map;
import java.util.function.Supplier;

public class DeepCompatUtil {

    private static final Lazy<Map<Block, Supplier<? extends ParticleOptions>>> PARTICLE_MAP = Lazy.of(() -> new ImmutableMap.Builder<Block, Supplier<? extends ParticleOptions>>()
            .put(DABlocks.CONBERRY_LEAVES.get(), ReduxParticleTypes.FALLING_CONBERRY_LEAVES)
            .put(DABlocks.YAGROOT_LEAVES.get(), ReduxParticleTypes.FALLING_YAGROOT_LEAVES)
            .put(DABlocks.SUNROOT_LEAVES.get(), ReduxParticleTypes.FALLING_SUNROOT_LEAVES).build());

    public static @Nullable Supplier<? extends ParticleOptions> getParticle(Block self) {
        return PARTICLE_MAP.get().get(self);
    }
}
