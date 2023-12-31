package net.zepalesque.redux.client.audio;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.sounds.Music;

public class ReduxMusic {
    public static final Music DEFAULT_AETHER_MUSIC = new Music(AetherSoundEvents.MUSIC_AETHER.getHolder().orElseThrow(), 12000, 24000, true);
}
