package net.zepalesque.redux.client.event.hook;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherMusicManager;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusicManager;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.mixin.client.audio.MusicManagerAccessor;
import net.zepalesque.redux.mixin.client.audio.SoundEngineAccessor;

import java.util.List;
import java.util.Optional;

public class ReduxAudioHooks {

    public static boolean shouldCancelMusic(SoundEngine soundEngine, SoundInstance sound) {
        if (shouldCancel(soundEngine, sound)) {
            return true;
        }
        if (sound.getSource() == SoundSource.MUSIC && ReduxConfig.CLIENT.night_track_music_manager.get()) {
            return ((ReduxMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(ReduxMusicManager.getSituationalMusic().getEvent()).getLocation())) && (ReduxMusicManager.getSituationalOppositeDaytimeMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(ReduxMusicManager.getSituationalOppositeDaytimeMusic().getEvent()).getLocation()))) || ReduxMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(ReduxMusicManager.getCurrentMusic().getLocation());

        } else {
            return false;
        }
    }

    public static boolean shouldCancel(SoundEngine engine, SoundInstance instance) {
        // If it's not music then we have no reason to cancel it
        if (instance.getSource() != SoundSource.MUSIC) {
            return false;
        }
        Minecraft mc = Minecraft.getInstance();
        // If the player is null, we won't be able to check the biome anyway, so it's highly unlikely to be an overlapping aether track. Do not cancel.
        if (mc.player == null) {
            return false;
        }
        Optional<Holder<SoundEvent>> optional = ForgeRegistries.SOUND_EVENTS.getHolder(instance.getLocation());
        if (optional.isPresent()) {
            Holder<SoundEvent> sound = optional.get();
            // Don't cancel lost content's boss music
            if (sound.is(ReduxTags.Sounds.ALWAYS_ALLOW)) {
                return false;
            }
            Holder<Biome> biome = mc.player.level.getBiome(mc.player.blockPosition());
            // If the biome ISN'T an aether biome (and the dimension isn't the aether), then we don't need to interfere
            if (!biome.is(AetherTags.Biomes.AETHER_MUSIC) && mc.player.level.dimension() != AetherDimensions.AETHER_LEVEL) {
                return false;
            } // If it IS however, and the music isn't a designated aether track, then it shouldn't be playing in the first place. Cancel.
            else if (!sound.is(ReduxTags.Sounds.AETHER_MUSIC)) {
                Redux.LOGGER.info("Caught music track that seems to not belong in the Aether trying to play! Sound ID: {}", instance.getLocation());
                return true;
            } else {
                // If the given sound (instance) is already playing, then we probably shouldn't cancel as this may cancel music partway through playing.
                // I believe this can even happen because of streaming sound events, although I'm not certain.
                // If it is the music manager's track, it may have slipped by. Do not cancel.
                if (((SoundEngineAccessor) engine).redux$getInstanceToChannel().keySet().stream()
                        .anyMatch(tested -> tested == instance)) {
                    return false;
                } // If it isn't, we want to check if it's the current music in the correct music manager. This is because the currentMusic is set before the sound begins playing.
                else if (isCurrentTrack(instance)) {
                    return false;
                }

            }
        }
        Redux.LOGGER.info("Caught potential overlapping music track attempting to play in the Aether! Sound ID: {}", instance.getLocation());
        Redux.LOGGER.info("If this was logged halfway through the music track playing and cancelled it, this is an issue, please report it to the Aether: Redux's issue tracker.");
        return true;

    }


    private static boolean isCurrentTrack(SoundInstance instance) {
        if (!AetherConfig.CLIENT.disable_music_manager.get()) {
            return instance == AetherMusicManager.getCurrentMusic();
        } else if (ReduxConfig.CLIENT.night_track_music_manager.get()) {
            return instance == ReduxMusicManager.getCurrentMusic();
        } else {
            return instance == ((MusicManagerAccessor)Minecraft.getInstance().getMusicManager()).redux$getCurrentMusic();
        }
    }

    public static boolean shouldCancelAercloudSound(SoundInstance sound) {
        if (sound.getSource() == SoundSource.BLOCKS && !ReduxConfig.CLIENT.aercloud_sfx.get()) {
            return (sound.getLocation().equals(ReduxSoundEvents.BLUE_AERCLOUD_BOUNCE.get().getLocation()) || sound.getLocation().equals(ReduxSoundEvents.GOLD_AERCLOUD_WHOOSH.get().getLocation()));
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
                    List<ResourceLocation> activeSounds = ((SoundEngineAccessor) soundEngine).redux$getInstanceToChannel().keySet().stream().map(SoundInstance::getLocation).toList();
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
