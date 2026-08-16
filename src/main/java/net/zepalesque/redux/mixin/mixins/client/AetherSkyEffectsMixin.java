package net.zepalesque.redux.mixin.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.aetherteam.aether.client.renderer.level.AetherSkyRenderEffects;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin(AetherSkyRenderEffects.class)
public abstract class AetherSkyEffectsMixin {
	@Expression("9.5")
	@ModifyExpressionValue(method = "<init>", at = @At("MIXINEXTRAS:EXPRESSION"))
	private static float redux$setCloudLevel(float og) {
		// TODO: config for this
		return 156f;
	}
}
