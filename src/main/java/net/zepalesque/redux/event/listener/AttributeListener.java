package net.zepalesque.redux.event.listener;

import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.zepalesque.redux.event.hook.AttributeHooks;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AttributeListener {

    @SubscribeEvent
    public static void attributeModifications(EntityAttributeModificationEvent event)
    {
        AttributeHooks.addAttributes(event);
    }
}
