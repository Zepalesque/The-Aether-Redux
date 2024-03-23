package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @Shadow @Nullable public abstract AttributeInstance getAttribute(Attribute attribute);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract float getMaxHealth();

    @Shadow public abstract boolean isBaby();


    @Shadow public abstract float getSpeed();

    @Inject(method = "doPush", at = @At("HEAD"), cancellable = true)
    protected void redux$doPush(Entity entity, CallbackInfo ci) {}


    @Inject(method = "getRiddenSpeed", at = @At("HEAD"), cancellable = true)
    protected void redux$getRiddenSpeed(Player player, CallbackInfoReturnable<Float> cir) {}

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {}
}
