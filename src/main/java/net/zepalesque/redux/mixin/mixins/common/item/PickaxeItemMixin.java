package net.zepalesque.redux.mixin.mixins.common.item;

import com.aetherteam.aether.AetherTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.zepalesque.redux.data.ReduxTags.Entities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin extends DiggerItemMixin {

    @Override
    protected void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfo ci) {
        if (target.getType().is(Entities.VALID_PICKAXE_TARGETS) && stack.is(AetherTags.Items.SLIDER_DAMAGING_ITEMS)) {
            stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
            ci.cancel();
        }
    }
}
