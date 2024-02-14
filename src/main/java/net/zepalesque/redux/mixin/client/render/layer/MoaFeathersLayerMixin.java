package net.zepalesque.redux.mixin.client.render.layer;

import com.legacy.lost_aether.client.render.layer.MoaHeadFeatherLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoaHeadFeatherLayer.class)
public class MoaFeathersLayerMixin {

    @Inject(at = @At("HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/Entity;FFFFFF)V", cancellable = true)
    public void renderMob(PoseStack par1, MultiBufferSource par2, int par3, Entity par4, float par5, float par6, float par7, float par8, float par9, float par10, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.moa_model_upgrade.get()) {
            ci.cancel();
        }
    }
}