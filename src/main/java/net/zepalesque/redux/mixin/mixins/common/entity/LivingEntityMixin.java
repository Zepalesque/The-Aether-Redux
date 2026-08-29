package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.item.combat.abilities.weapon.GravititeWeapon;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
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

@Mixin(LivingEntity.class)
@SuppressWarnings("CancellableInjectionUsage")
public abstract class LivingEntityMixin extends EntityMixin {
	@Shadow
	public abstract float getSpeed();
	
	@Shadow
	@Nullable public abstract AttributeInstance getAttribute(Holder<Attribute> attribute);
	
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

	@WrapMethod(method = "getKnockback")
	protected float getKnockback(Entity attacker, DamageSource src, Operation<Float> og) {
		var gravKnockback = src.getWeaponItem().getItem() instanceof GravititeWeapon ? 1 : 0;
		return og.call(attacker, src) + gravKnockback;
	}
}
