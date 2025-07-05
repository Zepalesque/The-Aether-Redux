package net.zepalesque.redux.client.audio;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;
import net.zepalesque.redux.config.ReduxConfig;

public class ReduxMusic {
    public static final int MUSIC_MIN = 2400;
    public static final int MUSIC_MAX = 4800;
    
    public static final Music DEFAULT_AETHER_MUSIC = new Music(AetherSoundEvents.MUSIC_AETHER.get(), 12000, 24000, true);
//    public static final Music AETHER_NIGHT = new Music(ReduxSoundEvents.AETHER_NIGHT.get(), 12000, 24000, true);
    public static final Music REDUX_MENU = new Music(ReduxSoundEvents.REDUX_MENU.get(), 0, 0, true);
    
    public static final Music AETHER_NIGHT_SHORTER_DELAY = new Music(ReduxSoundEvents.AETHER_NIGHT.get(), MUSIC_MIN, MUSIC_MAX, true);
    

    
    public static Music getNightMusicForBiome(Holder<Biome> biomeHolder) {
        return AETHER_NIGHT_SHORTER_DELAY;
    }
}
