package net.zepalesque.redux.mixin.mixins.common.accessor;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(ModConfigSpec.Builder.class)
public interface CfgBuilderAccessor {
	@Accessor("currentPath")
	List<String> redux$currentPath();
}
