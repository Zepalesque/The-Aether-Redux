package net.zepalesque.redux.mixin.client.audio;

import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.mixin.common.entity.SlimeMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sentry.class)
public abstract class SentryClientMixin extends SlimeMixin {
    @Override
    protected void getSoundPitch(CallbackInfoReturnable<Float> cir) {
        Sentry mob = (Sentry) (Object) this;
        if (Redux.packConfig != null && Redux.packConfig.better_sentry_sounds.get()) {
            cir.setReturnValue(((mob.getRandom().nextFloat() - mob.getRandom().nextFloat()) * 0.2F + 1.0F));
        }
    }
}
