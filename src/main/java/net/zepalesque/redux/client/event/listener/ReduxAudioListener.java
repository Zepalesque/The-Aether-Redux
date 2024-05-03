package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.client.event.hook.ReduxAudioHooks;
import net.zepalesque.redux.mixin.client.audio.SoundEngineAccessor;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ReduxAudioListener {

    @Nullable
    private static SoundEngine se = null;
    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        SoundEngine soundEngine = event.getEngine();
        if (se != soundEngine) {
            se = soundEngine;
        }
        SoundInstance sound = event.getOriginalSound();
        if (ReduxAudioHooks.shouldCancel(soundEngine, sound)) {
            event.setSound(null);
            return;
        }
        if (ReduxAudioHooks.shouldCancelAercloudSound(sound)) {
            event.setSound(null);
        }
    }
}
