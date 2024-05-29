package net.zepalesque.redux.mixin.mixins.client.color;


import com.aetherteam.aether.client.AetherColorResolvers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AetherColorResolvers.class)
public class AetherColorResolversMixin {

    @Inject(at = @At("HEAD"), method = "registerBlockColor", cancellable = true, remap = false)
    private static void stupidMixinBlackMagic(RegisterColorHandlersEvent.Block event, CallbackInfo ci) {
        // Cancel the aether's override of the grass color stuff
        ci.cancel();
    }
}