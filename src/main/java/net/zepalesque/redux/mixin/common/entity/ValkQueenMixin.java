package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.zepalesque.redux.item.ReduxItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


// TODO: Find a better way PLEASE
@Mixin(ValkyrieQueen.class)
public class ValkQueenMixin {


    @Redirect(method = "handleNpcInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;countItem(Lnet/minecraft/world/item/Item;)I"))
    public int initialize(Inventory instance, Item item) {
        return EquipmentUtil.hasCurio(instance.player, ReduxItems.GRAND_VICTORY_MEDAL.get()) ? 10 : instance.countItem(item);
    }

}
