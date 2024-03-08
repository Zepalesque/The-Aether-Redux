package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public abstract class SlimeMixin extends MobMixin {
    @Shadow public abstract void refreshDimensions();

    @Shadow @Final protected static EntityDataAccessor<Integer> ID_SIZE;

    @Inject(method = "getSoundPitch", at = @At("RETURN"), cancellable = true)
    protected void getSoundPitch(CallbackInfoReturnable<Float> cir) {
    }

    @Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    protected void redux$push(Entity entity, CallbackInfo ci) {}

}
