package net.zepalesque.redux.mixin.common.world;

import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(NoiseRouter.class)
public interface NoiseRouterAccessor {
    @Accessor("depth")
    void setDepth(DensityFunction d);
}
