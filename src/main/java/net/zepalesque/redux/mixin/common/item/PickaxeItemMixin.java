package net.zepalesque.redux.mixin.common.item;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin extends DiggerItemMixin {

    @Override
    protected void hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker, CallbackInfoReturnable<Boolean> cir) {
        pStack.hurtAndBreak(pTarget instanceof Slider && pStack.is(AetherTags.Items.SLIDER_DAMAGING_ITEMS) ? 1 : 2, pAttacker, (consumer) -> consumer.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        cir.setReturnValue(true);
    }
}
