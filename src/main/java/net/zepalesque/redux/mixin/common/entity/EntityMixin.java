package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    protected void redux$push(Entity entity, CallbackInfo ci) {}

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {}
}
