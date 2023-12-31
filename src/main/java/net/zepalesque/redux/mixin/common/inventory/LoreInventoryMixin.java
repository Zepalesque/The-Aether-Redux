package net.zepalesque.redux.mixin.common.inventory;

import com.aetherteam.aether.inventory.container.LoreInventory;
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

    @Shadow @Final private Player player;

    @Inject(at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/advancement/LoreTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;)V"), method = "setItem", remap = false)
    public void triggerLore(int index, ItemStack stack, CallbackInfo ci) {
        Player plr = this.player;
        if (plr instanceof ServerPlayer serverPlayer) {
            ReduxPlayer.get(plr).ifPresent(reduxPlayer -> reduxPlayer.getLoreModule().unlock(stack.getItem()));
            ReduxPacketHandler.sendToPlayer(new LoreUnlockPacket(serverPlayer.getUUID(), stack.getItem()), serverPlayer);
        }
    }
}
