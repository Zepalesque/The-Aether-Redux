package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import net.minecraft.core.particles.ParticleOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Mixin({LeavesWithParticlesBlock.class})
public interface ParticleLeavesAccessor {
    @Accessor
    Supplier<? extends ParticleOptions> getParticle();

}
