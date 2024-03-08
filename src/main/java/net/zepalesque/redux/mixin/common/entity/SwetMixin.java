package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Swet.class)
public class SwetMixin extends SlimeMixin {

    @Override
    protected void redux$push(Entity entity, CallbackInfo ci) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            ci.cancel();
        } else {
            super.redux$push(entity, ci);
        }
    }

    @Override
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            cir.setReturnValue(false);
        } else {
            super.redux$isPushable(cir);
        }
    }
}
