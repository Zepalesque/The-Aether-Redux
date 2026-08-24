package net.zepalesque.redux.mixin.mixins.common.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
@SuppressWarnings("CancellableInjectionUsage")
public abstract class LivingEntityMixin extends EntityMixin {
	@Shadow
	public abstract float getSpeed();
	
	@Shadow
	@Nullable
	public abstract AttributeInstance getAttribute(Holder<Attribute> attribute);
	
	@Shadow
	public abstract void setHealth(float health);
	
	@Shadow
	public abstract float getMaxHealth();
	
	@Inject(method = "makePoofParticles", at = @At("HEAD"), cancellable = true)
	protected void redux$poof(CallbackInfo ci) {}

	@Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
	protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {}

	@Inject(method = "doPush", at = @At("HEAD"), cancellable = true)
	protected void redux$doPush(Entity entity, CallbackInfo ci) {}

	@Inject(method = "pushEntities", at = @At("HEAD"), cancellable = true)
	protected void redux$pushEntities(CallbackInfo ci) {}

	@Inject(method = "getBoundingBoxForCulling", at = @At("HEAD"), cancellable = true)
	protected void redux$cullBox(CallbackInfoReturnable<AABB> cir) {}
	
	@Inject(method = "getRiddenSpeed", at = @At("HEAD"), cancellable = true)
	protected void redux$getRiddenSpeed(Player player, CallbackInfoReturnable<Float> cir) {}
}
