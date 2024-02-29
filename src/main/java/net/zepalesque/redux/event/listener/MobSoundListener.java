package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether_genesis.client.GenesisSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class MobSoundListener {




    @SubscribeEvent
    public static void onPlaySound(PlayLevelSoundEvent event) {
        SoundEvent sound = event.getSound().get();
        RegistryObject<SoundEvent> newSound = null;

        // TODO: Move to built-in pack!!!
        if (ReduxConfig.COMMON.better_conversion_sounds.get()) {
            if (sound == AetherSoundEvents.ITEM_AMBROSIUM_SHARD.get()) {
                newSound = ReduxSoundEvents.CONVERT_AMBROSIUM;
            }
            if (sound == AetherSoundEvents.ITEM_SWET_BALL_USE.get()) {
                newSound = ReduxSoundEvents.CONVERT_SWET_BALL;
            }
        }
        if (newSound != null) {
            Optional<Holder<SoundEvent>> holder = ForgeRegistries.SOUND_EVENTS.getHolder(newSound.get());
            holder.ifPresent(event::setSound);
        }
    }
}
