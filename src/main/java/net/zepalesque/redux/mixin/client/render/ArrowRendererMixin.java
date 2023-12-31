package net.zepalesque.redux.mixin.client.render;

import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TippableArrowRenderer.class)
public class ArrowRendererMixin
{
    @Unique
    private static final ResourceLocation SUBZERO_ARROW_LOCATION = new ResourceLocation(Redux.MODID, "textures/entity/projectile/subzero_arrow.png");

    @Inject(at = @At("HEAD"), method = "getTextureLocation*", cancellable = true)
    private void getTextureLocation(Arrow entity, CallbackInfoReturnable<ResourceLocation> cir) {
        SubzeroArrow.get(entity).ifPresent(subzeroArrow -> {
            if (subzeroArrow.isSubzeroArrow()) {
                if (entity.getColor() <= 0) {
                    cir.setReturnValue(SUBZERO_ARROW_LOCATION);
                }
            }
        });
    }
}
