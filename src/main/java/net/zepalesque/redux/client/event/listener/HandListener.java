package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;

@Mod.EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class HandListener {


    @SubscribeEvent
    public static void renderHand(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack itemstack = player.getMainHandItem();
            ItemStack itemstack1 = player.getOffhandItem();
            boolean isCrossbow = itemstack.is(ReduxItems.SUBZERO_CROSSBOW.get()) || itemstack1.is(ReduxItems.SUBZERO_CROSSBOW.get());
            if (isCrossbow && player.isUsingItem()) {
                crossbowThingIdkAnymore(player, event);
            } else if (isCrossbow) {
                if (subzeroCharged(itemstack) && event.getHand() == InteractionHand.OFF_HAND) {
                    event.setCanceled(true);
                }
            }
        }
    }

    // :skull:
    private static void crossbowThingIdkAnymore(LocalPlayer player, RenderHandEvent event) {
        ItemStack itemstack = player.getUseItem();
        InteractionHand interactionhand = player.getUsedItemHand();
        if (!itemstack.is(ReduxItems.SUBZERO_CROSSBOW.get())) {
            if (interactionhand == InteractionHand.MAIN_HAND && subzeroCharged(player.getOffhandItem()) && event.getHand() == InteractionHand.OFF_HAND)  {
                event.setCanceled(true);
            }
        } else {
           if (interactionhand != event.getHand()) {
               event.setCanceled(true);
           }
        }
    }

    private static boolean subzeroCharged(ItemStack pStack) {
        return pStack.is(ReduxItems.SUBZERO_CROSSBOW.get()) && SubzeroCrossbowItem.isCharged(pStack);
    }
}
