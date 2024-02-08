package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.misc.ReduxTags;

public class EquipmentHooks {


    public static boolean isImmuneToBlightPlants(LivingEntity mob) {
        return EquipmentUtil.findFirstCurio(mob, stack -> stack.is(ReduxTags.Items.BLIGHTWARDING_ACCESSORIES)).isPresent();
    }
}
