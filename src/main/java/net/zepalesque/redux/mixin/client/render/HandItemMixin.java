package net.zepalesque.redux.mixin.client.render;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public class HandItemMixin {
/*

    @Inject(at = @At("HEAD"), method = "evaluateWhichHandsToRender", cancellable = true)
    private static void evaluateWhichHandsToRender(LocalPlayer pPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemstack = pPlayer.getMainHandItem();
        ItemStack itemstack1 = pPlayer.getOffhandItem();
        boolean isCrossbow = itemstack.is(ReduxItems.SUBZERO_CROSSBOW.get()) || itemstack1.is(ReduxItems.SUBZERO_CROSSBOW.get());
        if (isCrossbow && pPlayer.isUsingItem()) {
            cir.setReturnValue(crossbowThingIdkAnymore(pPlayer));
        } else if (isCrossbow){
            cir.setReturnValue(subzeroCharged(itemstack) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS);
        }
    }


    @Unique
    private static ItemInHandRenderer.HandRenderSelection crossbowThingIdkAnymore(LocalPlayer pPlayer) {
        ItemStack itemstack = pPlayer.getUseItem();
        InteractionHand interactionhand = pPlayer.getUsedItemHand();
        if (!itemstack.is(ReduxItems.SUBZERO_CROSSBOW.get())) {
            return interactionhand == InteractionHand.MAIN_HAND && subzeroCharged(pPlayer.getOffhandItem()) ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS;
        } else {
            return ItemInHandRenderer.HandRenderSelection.onlyForHand(interactionhand);
        }
    }

    @Unique
    private static boolean subzeroCharged(ItemStack pStack) {
        return pStack.is(ReduxItems.SUBZERO_CROSSBOW.get()) && SubzeroCrossbowItem.isCharged(pStack);
    }*/
}
