package net.zepalesque.redux.mixin.common.registry;

import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.core.MappedRegistry$2")
public interface MappedGetterAccessor<T> {
    @Accessor("this$0")
    MappedRegistry<T> zenith$registry();
}
