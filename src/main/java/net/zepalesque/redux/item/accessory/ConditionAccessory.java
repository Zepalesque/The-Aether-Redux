package net.zepalesque.redux.item.accessory;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.BiPredicate;

public class ConditionAccessory extends AbilityTooltipMiscItem {


    private final BiPredicate<SlotContext, ItemStack> allowEquipPredicate;

    public ConditionAccessory(Properties properties, BiPredicate<SlotContext, ItemStack> allowEquipPredicate, String... pAbilities) {
        super(properties, pAbilities);
        this.allowEquipPredicate = allowEquipPredicate;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return this.allowEquipPredicate.test(slotContext, stack) && super.canEquip(slotContext, stack);
    }
}
