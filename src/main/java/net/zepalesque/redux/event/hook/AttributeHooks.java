package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;

public class AttributeHooks {

    public static void addAttributes(EntityAttributeModificationEvent event)
    {
        event.add(AetherEntityTypes.COCKATRICE.get(), Attributes.ATTACK_DAMAGE, 3.0D);
        event.add(AetherEntityTypes.FLYING_COW.get(), ForgeMod.ENTITY_GRAVITY.get(), 0.0D);
        event.add(AetherEntityTypes.PHYG.get(), ForgeMod.ENTITY_GRAVITY.get(), 0.0D);
//        event.add(AetherEntityTypes.GOLDEN_SWET.get(), Attributes.ATTACK_DAMAGE, 3.0D);

    }


}
