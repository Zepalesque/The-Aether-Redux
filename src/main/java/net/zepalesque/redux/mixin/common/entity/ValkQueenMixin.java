package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
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


    @Redirect(method = "handleNpcInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;countItem(Lnet/minecraft/world/item/Item;)I"))
    public int initialize(Inventory instance, Item item) {
        return instance.countItem(ReduxItems.GRAND_VICTORY_MEDAL.get()) >= 1 ? 10 : instance.countItem(item);
    }

    @Redirect(method = "handleNpcInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    public void shrinkStack(ItemStack instance, int decrement, Player player) {
        if (player.getInventory().countItem(ReduxItems.GRAND_VICTORY_MEDAL.get()) < 1) {
            instance.shrink(decrement);
        }
    }

}
