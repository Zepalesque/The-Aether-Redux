package net.zepalesque.redux.item.util;

import com.aetherteam.aether.item.AetherItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.item.ReduxItems;

public interface VeridiumItem {

    byte MAXIUMUM_VERIDIUM_INFUSION = Byte.valueOf((byte) 64);
    byte AMBROSIUM_CHARGING_AMOUNT = Byte.valueOf((byte) 8);
    int CHARGED_DAMAGE_MULTIPLIER = 4;

    static CompoundTag createCompoundFor(ItemStack stack)
    {
        CompoundTag compound = stack.hasTag() ? stack.getOrCreateTag().copy() : new CompoundTag();

        return compound;
    }
    static ItemStack decreaseCharge(ItemStack stack)
    {
        Item item = stack.getItem();
        if (item instanceof VeridiumItem virydium) {
            Item itemForStack = stack.getItem();
            CompoundTag compound = createCompoundFor(stack);
            byte ambrosiumCharge = Byte.valueOf(compound.getByte("ambrosium_charge"));
            byte newCharge;
            if (ambrosiumCharge > 1) {
                newCharge = (byte) (Byte.valueOf(ambrosiumCharge) - 1);
            } else {
                newCharge = (byte) 0;
                itemForStack = virydium.getReplacementItem(stack);
            }
            compound.putByte("ambrosium_charge", newCharge);
            ItemStack newStack = new ItemStack(itemForStack, 1);
            newStack.setTag(compound);

            return newStack;
        }
        return stack;
    }
    static boolean increaseCharge(ItemStack stack, Player player, Slot slot, ItemStack other, ClickAction action)
    {
        boolean changedValues = false;
        ItemStack newStack;
        Item item = stack.getItem();
        if (item instanceof VeridiumItem virydium) {
            if (action == ClickAction.SECONDARY && slot.allowModification(player) && !other.isEmpty()) {
                if (other.getItem() == AetherItems.AMBROSIUM_SHARD.get()) {
                    CompoundTag compound = createCompoundFor(stack);
                    byte ambrosiumCharge = getCharge(stack);
                    byte newCharge;
                    if (ambrosiumCharge <= MAXIUMUM_VERIDIUM_INFUSION - AMBROSIUM_CHARGING_AMOUNT || !virydium.isCharged(stack)) {
                        newCharge = virydium.isCharged(stack) ? Byte.valueOf((byte) (ambrosiumCharge + AMBROSIUM_CHARGING_AMOUNT))
                                : Byte.valueOf(AMBROSIUM_CHARGING_AMOUNT);
                        compound.putByte("ambrosium_charge", newCharge);
                        if (!virydium.isCharged(stack)) {
                            newStack = new ItemStack(virydium.getReplacementItem(stack), 1);
                            newStack.setTag(compound);
                            newStack.setDamageValue(stack.getDamageValue());
                            slot.set(newStack);
                        } else { stack.setTag(compound);}
                        virydium.playChargeSound(player);
                        other.shrink(1);
                        changedValues = true;
                    }
                }
            }
        }
        return changedValues;
    }

    static ItemStack increaseCharge(ItemStack stack)
    {
        ItemStack newStack = null;
        Item item = stack.getItem();
        if (item instanceof VeridiumItem virydium) {
            CompoundTag compound = createCompoundFor(stack);
            byte ambrosiumCharge = getCharge(stack);
            byte newCharge;
            if (ambrosiumCharge <= MAXIUMUM_VERIDIUM_INFUSION - AMBROSIUM_CHARGING_AMOUNT || !virydium.isCharged(stack)) {
                newCharge = virydium.isCharged(stack) ? Byte.valueOf((byte) (ambrosiumCharge + AMBROSIUM_CHARGING_AMOUNT))
                        : Byte.valueOf(AMBROSIUM_CHARGING_AMOUNT);
                compound.putByte("ambrosium_charge", newCharge);
                if (!virydium.isCharged(stack)) {
                    newStack = new ItemStack(item, 1);
                    newStack.setTag(compound);
                    newStack.setDamageValue(stack.getDamageValue());
                } else { stack.setTag(compound);}
            }

        }
        return newStack == null ? stack : newStack;
    }


    /**
     * Returns a new {@link ItemStack} that copies the given stack. Returns null if no change is made
     * @param stack the {@link ItemStack} to copy the NBT data from
     * @return a modified {@link ItemStack} with the new data, or null if nothing is done.
     */
    static ItemStack infuse(ItemStack stack, int amount)
    {
        if (amount == 0)
        {
            return stack;
        }
        Item item = stack.getItem();
        CompoundTag compound = createCompoundFor(stack);
        byte ambrosiumCharge = getCharge(stack);
        byte newCharge;
        if (ambrosiumCharge <= MAXIUMUM_VERIDIUM_INFUSION - amount) {
            newCharge = (byte) (ambrosiumCharge + amount);
            compound.putByte("ambrosium_charge", newCharge);
            ItemStack i = new ItemStack(item);
            i.setTag(compound);
            return i;
        }
        return null;
    }
    static byte getCharge(ItemStack stack)
    {
        CompoundTag compound = stack.hasTag() ? stack.getOrCreateTag().copy() : new CompoundTag();
        if (!(stack.getItem() instanceof VeridiumItem)) { Redux.LOGGER.warn("Attempting to get infusion level of non-veridium item! Reading NBT data anyway"); }
        return compound.getByte("ambrosium_charge");
    }

    Item getReplacementItem(ItemStack stack);
    default boolean isCharged(ItemStack stack) {
        return stack.getItem() instanceof TieredItem tier && tier.getTier() == ReduxItemTiers.INFUSED_VERIDIUM;
    }

    default boolean isCharged(Item item) {
        return item == ReduxItems.INFUSED_VERIDIUM_AXE.get() || item == ReduxItems.INFUSED_VERIDIUM_HOE.get() || item == ReduxItems.INFUSED_VERIDIUM_SHOVEL.get() || item == ReduxItems.INFUSED_VERIDIUM_SWORD.get() || item == ReduxItems.INFUSED_VERIDIUM_PICKAXE.get();
    }


    static void infuseSound(Entity pEntity) {
        pEntity.playSound(ReduxSoundEvents.INFUSE_ITEM.get(), 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    default void playChargeSound(Entity pEntity) {
        pEntity.playSound(ReduxSoundEvents.INFUSE_ITEM.get(), 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

}
