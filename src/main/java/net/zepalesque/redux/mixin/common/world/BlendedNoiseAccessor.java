package net.zepalesque.redux.mixin.common.world;

import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlendedNoise.class)
public interface BlendedNoiseAccessor {

    @Accessor("xzScale")
    double xzScale();
    @Accessor("xzScale")
    void xzScale(double val);
    @Accessor("yScale")
    double yScale();
    @Accessor("yScale")
    void yScale(double val);
    @Accessor("xzFactor")
    double xzFactor();
    @Accessor("xzFactor")
    void xzFactor(double val);
    @Accessor("yFactor")
    double yFactor();
    @Accessor("yFactor")
    void yFactor(double val);
    @Accessor("smearScaleMultiplier")
    double smearScaleMultiplier();
    @Accessor("smearScaleMultiplier")
    void smearScaleMultiplier(double val);
}
