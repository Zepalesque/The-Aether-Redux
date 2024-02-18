package net.zepalesque.redux.mixin.common.inventory;

import com.aetherteam.aether.advancement.LoreTrigger;
import com.aetherteam.aether.inventory.container.LoreInventory;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.advancement.trigger.FallFromAetherTrigger;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.LoreUnlockPacket;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LoreInventory.class)
public class LoreInventoryMixin {
    // WrapOperation is useful in this case since it gives us the casted ServerPlayer and stack. make sure to always call original!
    @WrapOperation(at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/advancement/LoreTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;)V"), method = "setItem")
    public void triggerLore(LoreTrigger instance, ServerPlayer player, ItemStack stack, Operation<Void> original) {
        // first, call the original method (this is the trigger lore)
        original.call(instance, player, stack);

        // then, do all of redux's stuff
        ReduxPlayer.get(player).ifPresent(reduxPlayer -> reduxPlayer.getLoreModule().unlock(stack.getItem()));
        ReduxPacketHandler.sendToPlayer(new LoreUnlockPacket(player.getUUID(), stack.getItem()), player);
    }
}
