package net.zepalesque.redux.client.event.listener;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ClientMobListener {

    @SubscribeEvent
    public static void tickMimic(LivingEvent.LivingTickEvent event)
    {
        if (event.getEntity() instanceof Mimic mimic && mimic.level().isClientSide() && ReduxConfig.COMMON.better_mimics.get() && ReduxConfig.CLIENT.mimic_slam_sound.get())
        {
            int age = mimic.tickCount;
            if (age % 25 == 13) {
                mimic.level().playSound(Minecraft.getInstance().player, mimic.getX(), mimic.getY(), mimic.getZ(), ReduxSoundEvents.MIMIC_SLAM.get(), SoundSource.HOSTILE, 0.5F, mimic.level().random.nextFloat() * 0.1F + 0.9F);
            }
        }
    }
}
