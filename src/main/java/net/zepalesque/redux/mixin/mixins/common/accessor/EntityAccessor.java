package net.zepalesque.redux.mixin.mixins.common.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Invoker
    BlockPos callGetBlockPosBelowThatAffectsMyMovement();
}
