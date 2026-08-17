package net.zepalesque.redux.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.level.AetherSkyRenderEffects;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AetherSkyRenderEffects.class)
public abstract class AetherSkyEffectsMixin {
	@Expression("9.5")
	@ModifyExpressionValue(method = "<init>", at = @At("MIXINEXTRAS:EXPRESSION"))
	private static float redux$setCloudLevel(float og) {
		return ReduxConfig.getOrDefault(ReduxConfig.CLIENT.move_clouds)
			? 156f
			: og;
	}
}
