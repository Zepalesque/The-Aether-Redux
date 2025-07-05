package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.misc.ReduxTags;
import top.theillusivec4.curios.api.CuriosApi;

public class EquipmentHooks {


    public static boolean isImmuneToBlightPlants(LivingEntity mob) {
        return CuriosApi.getCuriosHelper().findFirstCurio(mob, stack -> stack.is(ReduxTags.Items.BLIGHTWARDING_ACCESSORIES)).isPresent();
    }
}
