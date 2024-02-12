package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.item.ReduxItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


// TODO: Find a better way PLEASE this is bad implementation
@Mixin(ValkyrieQueen.class)
public class ValkQueenMixin {


    // attempt to count 1 grand victory medal. if true, count it as 10 victory medals. otherwise, proceed as normal.
    @WrapOperation(method = "handleNpcInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;countItem(Lnet/minecraft/world/item/Item;)I"))
    public int initialize(Inventory instance, Item item, Operation<Integer> original) {
        return original.call(instance, ReduxItems.GRAND_VICTORY_MEDAL.get()) >= 1 ? 10 : original.call(instance, item);
    }

    // only shrink if player does not have grand victory medal
    @WrapWithCondition(method = "handleNpcInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    public boolean shrinkStack(ItemStack instance, int decrement, Player player, byte interactionID) {
        return player.getInventory().countItem(ReduxItems.GRAND_VICTORY_MEDAL.get()) < 1;
    }

}
