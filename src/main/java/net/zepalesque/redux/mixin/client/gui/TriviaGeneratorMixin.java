package net.zepalesque.redux.mixin.client.gui;

import com.aetherteam.aether.client.TriviaGenerator;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.zepalesque.redux.Redux;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = TriviaGenerator.class, remap = false)
public abstract class TriviaGeneratorMixin {

    @Shadow
    @Nullable
    protected abstract Component getTriviaComponent();

    @Shadow @Final private List<Component> trivia;

    @Inject(
        at = @At("HEAD"),
        method = "getTriviaLine",
        cancellable = true,
        remap = false
    )
    private void getTriviaLine(CallbackInfoReturnable<Component> cir) {
        if (this.getTriviaComponent() != null && this.getTriviaComponent().getString().contains("§0")) {
            cir.setReturnValue(Component.translatable("gui.aether.pro_tip").append(Component.literal(" ").append(this.getTriviaComponent())).withStyle(style -> style.withColor(Redux.REDUX_PURPLE)));
        }

    }
    @Inject(
        at = @At("TAIL"),
        method = "generateTriviaList",
        remap = false
    )
    private void list(CallbackInfo ci) {
        for (int i = 0; i < this.trivia.size(); i++) {
            Component component = this.trivia.get(i);
            if (component instanceof MutableComponent mutable && component.getString().contains("§0")) {
                this.trivia.set(i, mutable.withStyle(style -> style.withColor(Redux.REDUX_PURPLE)));
            }
        }

    }


}
