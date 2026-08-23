package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.passive.Aerbunny;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.DamageSource;
import net.zepalesque.redux.attachment.anim.AerbunnyAnimAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Aerbunny.class)
public class AerbunnyMixin {
	@Inject(method = "spawnExplosionParticle",
		at = @At("HEAD"),
		remap = false
	)
	protected void redux$spawnExplosionParticle(CallbackInfo ci) {
		var self = (Aerbunny) (Object) this;
		
		AerbunnyAnimAttachment.get(self).clientPuff(self);
	}
}
