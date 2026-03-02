package net.zepalesque.redux.mixin.mixins.common.accessor;

import java.util.Map;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LanguageProvider.class)
public interface LangProviderAccessor {
    @Accessor("data")
    Map<String, String> redux$getData();
}
