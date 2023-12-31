package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.event.ItemUseConvertEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.trigger.BlockStateRecipeTrigger;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class RecipeListener {


    @SubscribeEvent
    public static void blockstateConversion(ItemUseConvertEvent event)
    {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer sp)
        {
            BlockStateRecipeTrigger.INSTANCE.trigger(sp, event.getOldBlockState(), event.getNewBlockState(), event.getRecipeType());
        }
    }
}
