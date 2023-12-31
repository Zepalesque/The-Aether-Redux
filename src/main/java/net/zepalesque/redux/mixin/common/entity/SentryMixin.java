package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import net.minecraft.sounds.SoundEvent;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sentry.class)
public abstract class SentryMixin extends SlimeMixin {

    @Inject(method = "getSquishSound", at = @At("RETURN"), cancellable = true)
    protected void getSquishSound(CallbackInfoReturnable<SoundEvent> cir) {
        cir.setReturnValue(ReduxSoundEvents.SENTRY_LAND_BASE.get());
    }

    @Override
    protected void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        Sentry mob = (Sentry) (Object) this;
        if (ReduxConfig.COMMON.improved_sentry_sounds.get() && mob.isAwake())
        {
            cir.setReturnValue(ReduxSoundEvents.SENTRY_AMBIENT.get());
        }
    }

    @Override
    protected void getSoundPitch(CallbackInfoReturnable<Float> cir) {
        Sentry mob = (Sentry) (Object) this;
        if (ReduxConfig.COMMON.improved_sentry_sounds.get())
        {
            cir.setReturnValue(((mob.getRandom().nextFloat() - mob.getRandom().nextFloat()) * 0.2F + 1.0F));
        }
    }
}
