package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.SentryRenderer;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SentryRenderer.class)
public class SentryRendererMixin extends MobRendererMixin<Sentry, SlimeModel<Sentry>> {

    @Inject(at = @At("HEAD"), method = "scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", cancellable = true)
    public void scaleMob(LivingEntity par1, PoseStack par2, float par3, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.sentry_model_upgrade.get()) {
            ci.cancel();
        }
    }
    @Override
    public void getRenderType(Sentry livingEntity, boolean bodyVisible, boolean translucent, boolean glowing, CallbackInfoReturnable<RenderType> cir) {
        if (ReduxConfig.CLIENT.sentry_model_upgrade.get()) {
            cir.setReturnValue(null);
        }
        super.getRenderType(livingEntity, bodyVisible, translucent, glowing, cir);
    }

}
