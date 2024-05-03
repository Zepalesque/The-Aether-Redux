package net.zepalesque.redux.client.audio;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;

public class ReduxMusic {
    public static final int MUSIC_MIN = 1200;
    public static final int MUSIC_MAX = 3600;
    public static final Music AETHER_NIGHT = new Music(ReduxSoundEvents.NIGHT_MUSIC.get(), MUSIC_MIN, MUSIC_MAX, false);
    public static final Music REDUX_MENU = new Music(ReduxSoundEvents.REDUX_MENU.get(), 0, 0, true);

    // TODO: make this driven by a resource-pack-changeable thing maybe? if not just do it data-driven
    public static Music getNightMusicForBiome(Holder<Biome> biomeHolder) {
        return AETHER_NIGHT;
    }


}
