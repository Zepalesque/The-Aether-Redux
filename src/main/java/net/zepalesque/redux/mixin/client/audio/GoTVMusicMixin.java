package net.zepalesque.redux.mixin.client.audio;

import com.aetherteam.aether_genesis.client.GenesisMusic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.client.audio.ReduxMusic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GenesisMusic.class)
public class GoTVMusicMixin {

    @Unique
    private static final Music REDUX$NIGHT = new Music(GenesisMusic.AETHER_NIGHT.getEvent(), ReduxMusic.MUSIC_MIN, ReduxMusic.MUSIC_MAX, GenesisMusic.AETHER_NIGHT.replaceCurrentMusic());

    @Inject(method = "getNightMusicForBiome", at = @At("HEAD"), cancellable = true, remap = false)
    private static void redux$decreasedDelayMusic(Holder<Biome> biomeHolder, CallbackInfoReturnable<Music> cir) {
        cir.setReturnValue(REDUX$NIGHT);
    }
}
