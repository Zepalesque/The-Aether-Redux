package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether_genesis.client.renderer.entity.BattleSentryRenderer;
import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BattleSentryRenderer.class)
public abstract class BattleSentryRendererMixin extends MobRendererMixin<BattleSentry, SlimeModel<BattleSentry>> {

    @Inject(at = @At("HEAD"), method = "scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", cancellable = true)
    public void scaleMob(LivingEntity par1, PoseStack par2, float par3, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.sentry_model_upgrade.get()) {
            ci.cancel();
        }
    }



    @WrapOperation(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"), method = "render(Lcom/aetherteam/aether_genesis/entity/monster/BattleSentry;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void redux$wrapRender(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        if (!ReduxConfig.CLIENT.sentry_model_upgrade.get()) {
            original.call(instance, x, y, z);
        }
    }

    @Override
    public void getRenderType(BattleSentry livingEntity, boolean bodyVisible, boolean translucent, boolean glowing, CallbackInfoReturnable<RenderType> cir) {
        if (ReduxConfig.CLIENT.sentry_model_upgrade.get()) {
            cir.setReturnValue(null);
        }
        super.getRenderType(livingEntity, bodyVisible, translucent, glowing, cir);
    }
}
