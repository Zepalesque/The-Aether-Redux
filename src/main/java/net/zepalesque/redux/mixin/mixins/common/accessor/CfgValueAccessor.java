package net.zepalesque.redux.mixin.mixins.common.accessor;

import javax.annotation.Nullable;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModConfigSpec.ConfigValue.class)
public interface CfgValueAccessor<T> {
	@Accessor("spec")
	@Nullable ModConfigSpec redux$spec();
}
