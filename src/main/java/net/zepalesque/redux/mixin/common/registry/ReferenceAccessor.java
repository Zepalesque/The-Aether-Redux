package net.zepalesque.redux.mixin.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Holder.Reference.class)
public interface ReferenceAccessor<T> {
    @Accessor("value")
    T valueOrNull();
    
    @Accessor("key")
    ResourceKey<T> keyOrNull();
}
