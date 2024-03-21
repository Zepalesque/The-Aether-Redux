package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.client.event.hook.ReduxAudioHooks;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ReduxAudioListener {
    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        SoundEngine soundEngine = event.getEngine();
        SoundInstance sound = event.getOriginalSound();
        if (ReduxAudioHooks.shouldCancelMusic(soundEngine, sound)) {
            event.setSound(null);
            return;
        }
        if (ReduxAudioHooks.shouldCancelAercloudSound(sound)) {
            event.setSound(null);
        }
    }
}
