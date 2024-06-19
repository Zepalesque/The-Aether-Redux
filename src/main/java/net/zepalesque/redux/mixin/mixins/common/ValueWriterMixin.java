package net.zepalesque.redux.mixin.mixins.common;

import com.electronwill.nightconfig.core.io.CharacterOutput;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.zepalesque.redux.config.enums.NamedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: FIND A BETTER WAY PLEASE GOD
// TODO: FIND A BETTER WAY PLEASE GOD
// TODO: FIND A BETTER WAY PLEASE GOD
// TODO: FIND A BETTER WAY PLEASE GOD
// TODO: FIND A BETTER WAY PLEASE GOD
// TODO: FIND A BETTER WAY PLEASE GOD
@Mixin(targets = "com.electronwill.nightconfig.toml.ValueWriter")
public abstract class ValueWriterMixin {
    @Shadow
    private static void writeString(String string, CharacterOutput output, TomlWriter writer) {}

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    private static void redux$write(Object value, CharacterOutput output, TomlWriter writer, CallbackInfo ci) {
        if (value instanceof NamedConfig config) {
            writeString(config.serialize(), output, writer);
            ci.cancel();
        }
    }
}
