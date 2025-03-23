package net.zepalesque.redux.mixin.client.audio;

import com.aetherteam.aether_genesis.client.GenesisMusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GenesisMusicManager.class)
public abstract class GenesisMusicManagerMixin {

    @Shadow @Final private static Minecraft minecraft;

    @Shadow
    public static boolean isVanillaWorldPreviewEnabled() {
        return false;
    }

    @Shadow
    public static boolean isAetherWorldPreviewEnabled() {
        return false;
    }

    @Inject(method = "getSituationalMusic", at = @At("TAIL"), cancellable = true, remap = false)
    private static void redux$situationalMusic(CallbackInfoReturnable<Music> cir) {
        if (!(minecraft.screen instanceof WinScreen) && !isVanillaWorldPreviewEnabled() && !isAetherWorldPreviewEnabled() && minecraft.player != null)
            cir.setReturnValue(Minecraft.getInstance().getSituationalMusic());
    }

    @Inject(method = "getSituationalOppositeDaytimeMusic", at = @At("TAIL"), cancellable = true, remap = false)
    private static void redux$situationalMusicOpposite(CallbackInfoReturnable<Music> cir) {
        if (!(minecraft.screen instanceof WinScreen) && !isVanillaWorldPreviewEnabled() && !isAetherWorldPreviewEnabled() && minecraft.player != null)
            cir.setReturnValue(Minecraft.getInstance().getSituationalMusic());
    }
}
