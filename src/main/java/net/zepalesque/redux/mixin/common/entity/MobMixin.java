package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntityMixin {
    @Inject(method = "getAmbientSound", at = @At("RETURN"), cancellable = true)
    protected void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {    }
}
