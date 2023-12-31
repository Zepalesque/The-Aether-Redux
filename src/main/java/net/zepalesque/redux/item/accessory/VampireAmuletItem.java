package net.zepalesque.redux.item.accessory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.Redux;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Supplier;

public class VampireAmuletItem extends AbilityTooltipPendantItem {


    public VampireAmuletItem(Supplier<? extends SoundEvent> ringSound, Properties properties, String... pAbilities) {
        super(Redux.locate("irrelevant_lol"), ringSound, properties, pAbilities);
    }

    @Override
    public ResourceLocation getPendantTexture() {
        return super.getPendantTexture();
    }

    public static boolean validForActivation(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getBoolean("valid_for_activation");
    }
    public static void setValidForActivation(ItemStack stack, boolean val) {
        CompoundTag tag = stack.getOrCreateTag().copy();
        tag.putBoolean("valid_for_activation", val);
        stack.setTag(tag);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        setValidForActivation(stack, false);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        setValidForActivation(stack, true);
    }
}
