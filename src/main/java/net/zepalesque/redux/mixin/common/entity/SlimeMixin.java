package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public abstract class SlimeMixin extends MobMixin {
    @Inject(method = "getSoundPitch", at = @At("RETURN"))
    protected void getSoundPitch(CallbackInfoReturnable<Float> cir) {    }
}
