package net.zepalesque.redux.client.event.hook;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusicManager;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.mixin.client.audio.SoundEngineAccessor;

import java.util.List;
import java.util.Optional;

public class ReduxAudioHooks {

    public static boolean shouldCancelMusic(SoundEngine soundEngine, SoundInstance sound) {
        if (alreadyPlaying(soundEngine, sound)) {
            return true;
        }
        if (sound.getSource() == SoundSource.MUSIC && ReduxConfig.CLIENT.night_track_music_manager.get()) {
            return ((ReduxMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(ReduxMusicManager.getSituationalMusic().getEvent()).getLocation())) && (ReduxMusicManager.getSituationalOppositeDaytimeMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(ReduxMusicManager.getSituationalOppositeDaytimeMusic().getEvent()).getLocation()))) || ReduxMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(ReduxMusicManager.getCurrentMusic().getLocation());
        } else {
            return false;
        }
    }

    private static boolean alreadyPlaying(SoundEngine soundEngine, SoundInstance sound) {
        if (sound.getSource() == SoundSource.MUSIC) {
            Optional<Holder<SoundEvent>> optional = ForgeRegistries.SOUND_EVENTS.getHolder(sound.getLocation());
            if (optional.isPresent()) {
                Holder<SoundEvent> holder = optional.get();
                if (holder.is(ReduxTags.Sounds.AETHER_MUSIC)) {
                    boolean shouldCancel = ((SoundEngineAccessor) soundEngine).genesis$getInstanceToChannel().keySet().stream().map(SoundInstance::getLocation).map(ForgeRegistries.SOUND_EVENTS::getHolder).anyMatch(holder1 -> holder1.isPresent() && holder1.get().is(ReduxTags.Sounds.AETHER_MUSIC));
                    if (shouldCancel) {
                        Redux.LOGGER.warn("Caught additional music track attempting to play! Cancelling to avoid overlap...");
                    }
                    return shouldCancel;
                }
            }
        }
        return false;
    }

    public static boolean shouldCancelAercloudBounceSound(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && !ReduxConfig.CLIENT.blue_aercloud_bounce_sfx.get()) {
            return (sound.getLocation().equals(ReduxSoundEvents.BLUE_AERCLOUD_BOUNCE.get().getLocation()));
        } else {
            return false;
        }
    }

    public static boolean shouldReplacePortalHum(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && ReduxConfig.CLIENT.aether_ii_portal_sounds.get()) {
            return sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get().getLocation());
        } else {
            return false;
        }
    }
    public static boolean shouldReplacePortalTrigger(SoundInstance sound) {
        if (sound.getSource() == SoundSource.AMBIENT && ReduxConfig.CLIENT.aether_ii_portal_sounds.get()) {
            return sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRIGGER.get().getLocation());
        } else {
            return false;
        }
    }
    public static boolean shouldReplacePortalTravel(SoundInstance sound) {
        if (sound.getSource() == SoundSource.AMBIENT && ReduxConfig.CLIENT.aether_ii_portal_sounds.get()) {
            return sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_TRAVEL.get().getLocation());
        } else {
            return false;
        }
    }
    public static boolean shouldCancelPortalSound(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null && ReduxConfig.CLIENT.aether_ii_portal_sounds.get()) {
            if (sound.getSource() == SoundSource.BLOCKS) {
                if (sound.getLocation().equals(AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get().getLocation())) {
                    List<ResourceLocation> activeSounds = ((SoundEngineAccessor) soundEngine).genesis$getInstanceToChannel().keySet().stream().map(SoundInstance::getLocation).toList();
                    return activeSounds.contains(ReduxSoundEvents.PORTAL_HUM.get().getLocation()) || activeSounds.contains(ReduxSoundEvents.PORTAL_TRAVEL.get().getLocation()) || activeSounds.contains(ReduxSoundEvents.PORTAL_TRIGGER.get().getLocation());
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
