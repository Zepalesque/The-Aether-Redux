package net.zepalesque.redux.client.event.hook;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;

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

}
