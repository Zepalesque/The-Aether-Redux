package net.zepalesque.redux.client.event.listener;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
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
            return;
        }
        if (ReduxAudioHooks.shouldCancelPortalSound(soundEngine, sound)) {
            event.setSound(null);
            return;
        }
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            RandomSource random = level.getRandom();
            if (ReduxAudioHooks.shouldReplacePortalHum(sound)) {
                event.setSound(new SimpleSoundInstance(ReduxSoundEvents.PORTAL_HUM.get(), SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, RandomSource.create(random.nextLong()), sound.getX(), sound.getY(), sound.getZ()));
                return;
            }
            if (ReduxAudioHooks.shouldReplacePortalTrigger(sound)) {
                event.setSound(new SimpleSoundInstance(ReduxSoundEvents.PORTAL_TRIGGER.get(), SoundSource.BLOCKS, 0.25F, random.nextFloat() * 0.4F + 0.8F, RandomSource.create(random.nextLong()), sound.getX(), sound.getY(), sound.getZ()));
                return;
            }
            if (ReduxAudioHooks.shouldReplacePortalTravel(sound)) {
                event.setSound(new SimpleSoundInstance(ReduxSoundEvents.PORTAL_TRAVEL.get(), SoundSource.BLOCKS, 0.25F, random.nextFloat() * 0.4F + 0.8F, RandomSource.create(random.nextLong()), sound.getX(), sound.getY(), sound.getZ()));
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ReduxAudioHooks.tick();
        }

    }

    @SubscribeEvent
    public static void onPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
        ReduxAudioHooks.stop();
    }

}
