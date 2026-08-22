package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttributeHooks {
    public static void addAttributes(net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent event) {
        event.add(AetherEntityTypes.COCKATRICE.get(), Attributes.ATTACK_DAMAGE, 3.0D);
    }
}
