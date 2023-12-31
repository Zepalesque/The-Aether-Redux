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
        if (ReduxConfig.COMMON.improved_moa_sounds.get()) {
            if (sound == AetherSoundEvents.ENTITY_MOA_AMBIENT.get()) {
                newSound = ReduxSoundEvents.MOA_AMBIENT;
            }
            if (sound == AetherSoundEvents.ENTITY_MOA_HURT.get()) {
                newSound = ReduxSoundEvents.MOA_HURT;
            }
            if (sound == AetherSoundEvents.ENTITY_MOA_DEATH.get()) {
                newSound = ReduxSoundEvents.MOA_DEATH;
            }
        }
        if (ReduxConfig.COMMON.improved_cockatrice_sounds.get()) {
            if (sound == AetherSoundEvents.ENTITY_COCKATRICE_AMBIENT.get()) {
                newSound = ReduxSoundEvents.COCKATRICE_AMBIENT;
            }
            if (sound == AetherSoundEvents.ENTITY_COCKATRICE_HURT.get()) {
                newSound = ReduxSoundEvents.COCKATRICE_HURT;
            }
            if (sound == AetherSoundEvents.ENTITY_COCKATRICE_DEATH.get()) {
                newSound = ReduxSoundEvents.COCKATRICE_DEATH;
            }
        }
        if (ReduxConfig.COMMON.better_conversion_sounds.get()) {
            if (sound == AetherSoundEvents.ITEM_AMBROSIUM_SHARD.get()) {
                newSound = ReduxSoundEvents.CONVERT_AMBROSIUM;
            }
            if (sound == AetherSoundEvents.ITEM_SWET_BALL_USE.get()) {
                newSound = ReduxSoundEvents.CONVERT_SWET_BALL;
            }
        }
        if (ReduxConfig.COMMON.improved_sentry_sounds.get())
        {
            if (sound == AetherSoundEvents.ENTITY_SENTRY_DEATH.get()) {
                newSound = ReduxSoundEvents.SENTRY_DEATH;
            }
            if (sound == AetherSoundEvents.ENTITY_SENTRY_HURT.get()) {
                newSound = ReduxSoundEvents.SENTRY_HURT;
            }
            if (sound == AetherSoundEvents.ENTITY_SENTRY_JUMP.get()) {
                newSound = ReduxSoundEvents.SENTRY_POUNCE;
            }
            if (sound == ReduxSoundEvents.SENTRY_LAND_BASE.get()) {
                newSound = ReduxSoundEvents.SENTRY_LAND;
            }
        }
        if (ReduxConfig.COMMON.improved_slider_hurt_sounds.get()) {
            if (sound == AetherSoundEvents.ENTITY_SLIDER_HURT.get()) {
                newSound = ReduxSoundEvents.SLIDER_HURT;
            }
            if (sound == AetherSoundEvents.ENTITY_SLIDER_AMBIENT.get()) {
                newSound = ReduxSoundEvents.SLIDER_AMBIENT;
            }
        }

        if (ReduxConfig.COMMON.improved_aechor_plant_sounds.get()) {
            if (sound == AetherSoundEvents.ENTITY_AECHOR_PLANT_DEATH.get()) {
                newSound = ReduxSoundEvents.AECHOR_PLANT_DEATH;
            }
            if (sound == AetherSoundEvents.ENTITY_AECHOR_PLANT_HURT.get()) {
                newSound = ReduxSoundEvents.AECHOR_PLANT_HURT;
            }
        }

        if (ReduxConfig.COMMON.improved_aerwhale_sounds.get()) {
            if (sound == AetherSoundEvents.ENTITY_AERWHALE_AMBIENT.get()) {
                newSound = ReduxSoundEvents.AERWHALE_AMBIENT;
            }
        }
        if (Redux.aetherGenesisCompat() && ReduxConfig.COMMON.improved_tempest_sounds.get()) {
            if (sound == GenesisSoundEvents.ENTITY_TEMPEST_AMBIENT.get()) {
                newSound = ReduxSoundEvents.TEMPEST_AMBIENT;
            }
            if (sound == GenesisSoundEvents.ENTITY_TEMPEST_HURT.get()) {
                newSound = ReduxSoundEvents.TEMPEST_HURT;
            }
            if (sound == GenesisSoundEvents.ENTITY_TEMPEST_DEATH.get()) {
                newSound = ReduxSoundEvents.TEMPEST_DEATH;
            }
            if (sound == GenesisSoundEvents.ENTITY_TEMPEST_SHOOT.get()) {
                newSound = ReduxSoundEvents.TEMPEST_ZAP;
            }
        }
        if (ReduxConfig.COMMON.improved_mimic_awaken_sound.get())
        {
            if (sound == AetherSoundEvents.BLOCK_CHEST_MIMIC_OPEN.get())
            {
                newSound = ReduxSoundEvents.MIMIC_AWAKEN;
            }
        }

        if (newSound != null) {
            Optional<Holder<SoundEvent>> holder = ForgeRegistries.SOUND_EVENTS.getHolder(newSound.get());
            holder.ifPresent(event::setSound);
        }
    }
}
