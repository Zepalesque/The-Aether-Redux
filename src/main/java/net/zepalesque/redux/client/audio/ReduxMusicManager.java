package net.zepalesque.redux.client.audio;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherMenuUtil;
import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

public class ReduxMusicManager {
    private static final RandomSource random = RandomSource.create();
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final MusicManager musicManager = Minecraft.getInstance().getMusicManager();
    @Nullable
    private static SoundInstance currentMusic;
    private static int nextSongDelay = 100;

    public ReduxMusicManager() {
    }

    public static void tick() {
        Music music = getSituationalMusic();
        Music musicOpposite = getSituationalOppositeDaytimeMusic();
        if (music != null && musicOpposite != null) {
            if (currentMusic != null) {
                if (!music.getEvent().getLocation().equals(currentMusic.getLocation()) && !musicOpposite.getEvent().getLocation().equals(currentMusic.getLocation()) && music.replaceCurrentMusic()) {
                    minecraft.getSoundManager().stop(currentMusic);
                    nextSongDelay = Mth.nextInt(random, 0, music.getMinDelay() / 2);
                }

                if (!minecraft.getSoundManager().isActive(currentMusic)) {
                    currentMusic = null;
                    nextSongDelay = Math.min(nextSongDelay, Mth.nextInt(random, music.getMinDelay(), music.getMaxDelay()));
                }
            }

            nextSongDelay = Math.min(nextSongDelay, music.getMaxDelay());
            if (currentMusic == null && nextSongDelay-- <= 0) {
                startPlaying(music);
            }
        } else {
            currentMusic = null;
            if (nextSongDelay-- <= 0) {
                nextSongDelay = Math.min(Integer.MAX_VALUE, Mth.nextInt(random, (Integer)AetherConfig.CLIENT.music_backup_min_delay.get(), (Integer)AetherConfig.CLIENT.music_backup_max_delay.get()));
            }
        }

    }

    public static void startPlaying(Music pSelector) {
        musicManager.stopPlaying();
        currentMusic = SimpleSoundInstance.forMusic(pSelector.getEvent());
        if (currentMusic.getSound() == null) {
            currentMusic.resolve(minecraft.getSoundManager());
        }
        if (currentMusic.getSound() != null){
            minecraft.getSoundManager().play(currentMusic);
        }

        nextSongDelay = Integer.MAX_VALUE;
    }

    public static void stopPlaying() {
        if (currentMusic != null) {
            minecraft.getSoundManager().stop(currentMusic);
            currentMusic = null;
        }

        nextSongDelay += 100;
    }

    @Nullable
    public static SoundInstance getCurrentMusic() {
        return currentMusic;
    }

    public static boolean isAetherWorldPreviewEnabled() {
        return AetherMenuUtil.isAetherMenu() && isWorldPreviewEnabled() && !AetherConfig.CLIENT.disable_aether_world_preview_menu_music.get();
    }

    public static boolean isVanillaWorldPreviewEnabled() {
        return AetherMenuUtil.isMinecraftMenu() && isWorldPreviewEnabled() && !AetherConfig.CLIENT.disable_vanilla_world_preview_menu_music.get();
    }

    public static boolean isWorldPreviewEnabled() {
        return minecraft.player != null && WorldDisplayHelper.isActive();
    }

    public static boolean isCreative(Holder<Biome> holder, Player player) {
        return player.level.dimension() != Level.END && player.level.dimension() != Level.NETHER && holder.is(AetherTags.Biomes.AETHER_MUSIC) && !musicManager.isPlayingMusic(Musics.UNDER_WATER) && (!player.isUnderWater() || !holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC)) && player.getAbilities().instabuild && player.getAbilities().mayfly;
    }

    public static Music getSituationalMusic() {
        if (!(minecraft.screen instanceof WinScreen)) {
            if (!isVanillaWorldPreviewEnabled() && !isAetherWorldPreviewEnabled() && minecraft.player != null) {
                Holder<Biome> holder = minecraft.player.level.getBiome(minecraft.player.blockPosition());
                long time = minecraft.player.clientLevel.getLevelData().getDayTime() % 72000L;
                boolean night = time >= 39000 && time < 69000;

                if (night) {
                    return ReduxMusic.getNightMusicForBiome(holder);
                }
                if (isCreative(holder, minecraft.player)) {
                    return holder.value().getBackgroundMusic().orElse(Musics.GAME);
                }
            }
        }

        return null;
    }

    public static Music getSituationalOppositeDaytimeMusic()
    {
        if (!(minecraft.screen instanceof WinScreen)) {

            if (!isVanillaWorldPreviewEnabled() && !isAetherWorldPreviewEnabled() && minecraft.player != null) {
                Holder<Biome> holder = minecraft.player.level.getBiome(minecraft.player.blockPosition());
                long time = minecraft.player.clientLevel.getLevelData().getDayTime() % 72000L;
                boolean night = time >= 39000 && time < 69000;

                if (!night)
                {
                    return ReduxMusic.getNightMusicForBiome(holder);
                }
                if (isCreative(holder, minecraft.player)) {
                    return holder.value().getBackgroundMusic().orElse(Musics.GAME);
                }
            }
        }

        return null;
    }
}
