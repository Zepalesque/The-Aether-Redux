package net.zepalesque.redux.client.audio;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;

public class ReduxMusic {
    public static final Music AETHER_NIGHT = Musics.createGameMusic(ReduxSoundEvents.NIGHT_MUSIC.get());
    public static final Music GENESIS = new Music(ReduxSoundEvents.GENESIS_MUSIC.get(), 12000, 24000, true);
    public static final Music DEFAULT_AETHER_MUSIC = new Music(AetherSoundEvents.MUSIC_AETHER.get(), 12000, 24000, true);
    public static final Music REDUX_MENU = new Music(ReduxSoundEvents.REDUX_MENU.get(), 0, 0, true);
}
