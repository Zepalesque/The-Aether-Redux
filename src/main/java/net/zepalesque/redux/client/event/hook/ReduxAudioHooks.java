package net.zepalesque.redux.client.event.hook;

import com.aetherteam.aether.client.AetherMusicManager;
import com.aetherteam.aether_genesis.client.GenesisMusicManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.mixin.client.audio.SoundEngineAccessor;

import java.util.Optional;

public class ReduxAudioHooks {

    public static boolean shouldCancelAercloudSound(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && !ReduxConfig.CLIENT.aercloud_sfx.get()) {
            return sound.getLocation().equals(ReduxSoundEvents.GOLD_AERCLOUD_WHOOSH.get().getLocation()) ||
                   sound.getLocation().equals(ReduxSoundEvents.PURPLE_AERCLOUD_ZOOM.get().getLocation()) ||
                   sound.getLocation().equals(ReduxSoundEvents.GREEN_AERCLOUD_WUBBLE.get().getLocation());
        } else {
            return false;
        }
    }

    public static boolean shouldCancelMusic(SoundEngine soundEngine, SoundInstance sound) {
        return alreadyPlaying(soundEngine, sound);
    }

    private static boolean alreadyPlaying(SoundEngine soundEngine, SoundInstance sound) {
        if (sound.getSource() == SoundSource.MUSIC) {
            Optional<Holder<SoundEvent>> optional = ForgeRegistries.SOUND_EVENTS.getHolder(sound.getLocation());
            if (optional.isPresent()) {
                Holder<SoundEvent> holder = optional.get();
                if (holder.is(ReduxTags.Sounds.AETHER_MUSIC)) {
                    boolean shouldCancel = ((SoundEngineAccessor) soundEngine).genesis$getInstanceToChannel().keySet().stream()
                            .anyMatch(instance -> {
                                Optional<Holder<SoundEvent>> instanceHolder = ForgeRegistries.SOUND_EVENTS.getHolder(instance.getLocation());
                                if (instanceHolder.isPresent()) {
                                    Holder<SoundEvent> h1 = instanceHolder.get();
                                    if (h1.is(ReduxTags.Sounds.AETHER_MUSIC)) {
                                        return instance != AetherMusicManager.getCurrentMusic() && (!Redux.aetherGenesisCompat() || instance != GenesisMusicManager.getCurrentMusic());
                                    }
                                }
                                return false;
                            });
                    if (shouldCancel) {
                        Redux.LOGGER.info("Caught additional music track attempting to play! Cancelling to avoid overlap...");
                    }
                    return shouldCancel;
                }
            }
        }
        return false;
    }

}
