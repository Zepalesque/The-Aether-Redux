package net.zepalesque.redux.client.event.hook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.world.entity.LivingEntity;

public class ClientQuicksoilHooks {

    public static void cancelSneak(final LivingEntity entity) {
        if (entity == Minecraft.getInstance().player) {
            ToggleKeyMapping.set(Minecraft.getInstance().options.keyShift.getKey(), false);
        }
    }
}
