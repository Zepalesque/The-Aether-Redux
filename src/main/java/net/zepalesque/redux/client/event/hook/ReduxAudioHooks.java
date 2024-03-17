package net.zepalesque.redux.client.event.hook;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusicManager;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.mixin.client.audio.SoundEngineAccessor;

import java.util.List;

public class ReduxAudioHooks {



    public static boolean shouldCancelMusic(SoundInstance sound) {
        if (!Redux.aetherGenesisCompat() && sound.getSource() == SoundSource.MUSIC && ReduxConfig.CLIENT.night_track_music_manager.get()) {
            return ((ReduxMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(ReduxMusicManager.getSituationalMusic().getEvent()).getLocation())) && (ReduxMusicManager.getSituationalOppositeDaytimeMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(ReduxMusicManager.getSituationalOppositeDaytimeMusic().getEvent()).getLocation()))) || ReduxMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(ReduxMusicManager.getCurrentMusic().getLocation());
        } else {
            return false;
        }
    }

    public static boolean shouldCancelAercloudBounceSound(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && !ReduxConfig.CLIENT.blue_aercloud_bounce_sfx.get()) {
            return (sound.getLocation().equals(ReduxSoundEvents.BLUE_AERCLOUD_BOUNCE.get().getLocation()));
        } else {
            return false;
        }
    }

    public static boolean shouldReplacePortalHum(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && ReduxConfig.COMMON.aether_ii_portal_sounds.get()) {
            return sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get().getLocation());
        } else {
            return false;
        }
    }
    public static boolean shouldReplacePortalTrigger(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && ReduxConfig.COMMON.aether_ii_portal_sounds.get()) {
            return sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get().getLocation());
        } else {
            return false;
        }
    }
    public static boolean shouldReplacePortalTravel(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && ReduxConfig.COMMON.aether_ii_portal_sounds.get()) {
            return sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get().getLocation());
        } else {
            return false;
        }
    }
    public static boolean shouldCancelPortalSound(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null && ReduxConfig.COMMON.aether_ii_portal_sounds.get()) {
            if (sound.getSource() == SoundSource.BLOCKS) {
                if (sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get().getLocation())) {
                    List<ResourceLocation> activeSounds = ((SoundEngineAccessor) soundEngine).genesis$getInstanceToChannel().keySet().stream().map(SoundInstance::getLocation).toList();
                    return activeSounds.contains(sound.getLocation());
                }
            }
        }
        return false;
    }

    public static void tick() {
        if (!Minecraft.getInstance().isPaused() && ReduxConfig.CLIENT.night_track_music_manager.get()) {
            ReduxMusicManager.tick();
        }
    }
    public static void stop() {
        ReduxMusicManager.stopPlaying();

    }
}
