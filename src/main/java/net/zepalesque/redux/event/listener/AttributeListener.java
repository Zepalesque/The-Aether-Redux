package net.zepalesque.redux.event.listener;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.zepalesque.redux.event.hook.AttributeHooks;

@EventBusSubscriber
public class AttributeListener {

    @SubscribeEvent
    public static void attributeModifications(EntityAttributeModificationEvent event) {
        AttributeHooks.addAttributes(event);
    }
}
