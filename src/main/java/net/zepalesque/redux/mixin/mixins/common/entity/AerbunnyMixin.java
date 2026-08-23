package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.passive.Aerbunny;
import net.zepalesque.redux.attachment.anim.AerbunnyAnimAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Aerbunny.class)
public class AerbunnyMixin {
	@Inject(method = "handleEntityEvent", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/aetherteam/aether/entity/passive/Aerbunny;spawnExplosionParticle()V"), cancellable = true, remap = false)
	protected void redux$handleEntityEvent(byte id, CallbackInfo ci) {
		var self = (Aerbunny) (Object) this;
		
		AerbunnyAnimAttachment.get(self).clientPuff(self);
	}
}
