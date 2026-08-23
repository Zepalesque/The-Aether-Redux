package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.passive.Aerbunny;
import net.zepalesque.redux.attachment.anim.AerbunnyAnimAttachment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Aerbunny.AerbunnyMoveControl.class)
public class AerbunnyMoveControlMixin {
	@Final
	@Shadow
	private Aerbunny aerbunny;
	
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/JumpControl;jump()V"), cancellable = true, remap = false)
	protected void redux$tick(CallbackInfo ci) {
		AerbunnyAnimAttachment.get(this.aerbunny).serverJump(this.aerbunny);
	}
}
