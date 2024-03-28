package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Chicken;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chicken.class)
public abstract class ChickenMixin extends MobMixin {

    @Inject(method = "getAmbientSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.hasCustomName() && "Quail".equals(this.getName().getString())) {
            cir.setReturnValue(ReduxSoundEvents.QUAIL_AMBIENT.get());
        }
    }

    @Inject(method = "getHurtSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getHurtSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.hasCustomName() && "Quail".equals(this.getName().getString())) {
            cir.setReturnValue(ReduxSoundEvents.QUAIL_HURT.get());
        }
    }

    @Inject(method = "getDeathSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.hasCustomName() && "Quail".equals(this.getName().getString())) {
            cir.setReturnValue(ReduxSoundEvents.QUAIL_DEATH.get());
        }
    }
}
