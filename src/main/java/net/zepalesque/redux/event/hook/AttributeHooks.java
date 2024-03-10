package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;

public class AttributeHooks {

    public static void addAttributes(EntityAttributeModificationEvent event)
    {
        event.add(AetherEntityTypes.COCKATRICE.get(), Attributes.ATTACK_DAMAGE, 3.0D);
//        event.add(AetherEntityTypes.GOLDEN_SWET.get(), Attributes.ATTACK_DAMAGE, 3.0D);

    }


}
