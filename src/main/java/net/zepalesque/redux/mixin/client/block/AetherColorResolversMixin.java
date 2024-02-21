package net.zepalesque.redux.mixin.client.block;


import com.aetherteam.aether.client.AetherColorResolvers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AetherColorResolvers.class)
public class AetherColorResolversMixin {

    @Inject(at = @At("HEAD"), method = "registerBlockColor", cancellable = true)
    private static void stupidJavaBlackMagic(RegisterColorHandlersEvent.Block event, CallbackInfo ci) {
        // Cancel the aether's override of the grass color stuff
        ci.cancel();
    }
}