package net.zepalesque.redux.client.keys;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.zepalesque.redux.Redux;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(
    modid = Redux.MODID,
    value = {Dist.CLIENT},
    bus = Bus.MOD
)
public class ReduxKeys {
    public static final KeyMapping SHOOT_FIREBALL = new KeyMapping("key.aether_redux.shoot_fireball.desc", GLFW.GLFW_KEY_H, "key.aether_redux.category");

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(SHOOT_FIREBALL);
    }
}
