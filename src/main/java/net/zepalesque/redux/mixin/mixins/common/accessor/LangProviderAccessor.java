package net.zepalesque.redux.mixin.mixins.common.accessor;

import net.neoforged.neoforge.common.data.LanguageProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LanguageProvider.class)
public interface LangProviderAccessor {
    @Accessor("data")
    Map<String, String> redux$getData();
}
