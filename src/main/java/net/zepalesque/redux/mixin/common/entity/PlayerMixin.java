package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    // Only necessary because 1.19.2 :|
    @Inject(method = "getHurtSound", at = @At("RETURN"), cancellable = true)
    protected void getHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
        if (damageSource == ReduxDamageTypes.ZANBERRY_BUSH || damageSource == ReduxDamageTypes.CORRUPTED_VINES) {
            cir.setReturnValue(SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH);
        }
    }
}
