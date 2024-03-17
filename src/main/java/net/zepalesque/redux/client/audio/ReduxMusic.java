package net.zepalesque.redux.client.audio;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;

public class ReduxMusic {
    public static final Music AETHER_NIGHT = Musics.createGameMusic(ReduxSoundEvents.NIGHT_MUSIC.get());
    public static final Music GENESIS = new Music(ReduxSoundEvents.GENESIS_MUSIC.get(), 12000, 24000, true);
    public static final Music DEFAULT_AETHER_MUSIC = new Music(AetherSoundEvents.MUSIC_AETHER.get(), 12000, 24000, true);
    public static final Music REDUX_MENU = new Music(ReduxSoundEvents.REDUX_MENU.get(), 0, 0, true);

    // TODO: make this driven by a resource-pack-changeable thing maybe? if not just do it data-driven
    public static Music getNightMusicForBiome(Holder<Biome> biomeHolder) {
        return AETHER_NIGHT;
    }

}
