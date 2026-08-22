package net.zepalesque.redux.mixin.mixins.common.accessor;

import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModConfigSpec.Builder.class)
public interface CfgBuilderAccessor {
	@Accessor("currentPath")
	List<String> redux$currentPath();
}
