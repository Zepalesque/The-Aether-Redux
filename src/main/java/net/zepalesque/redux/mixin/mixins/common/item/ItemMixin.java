package net.zepalesque.redux.mixin.mixins.common.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin {
	@Inject(method = "onDestroyed", at = @At("HEAD"))
	protected void redux$OnDestroyed(ItemEntity itemEntity, CallbackInfo ci) {}
}
