package net.zepalesque.redux.item.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.InfusionExpirePacket;

import javax.annotation.Nullable;

public interface VeridiumItem {

    byte MAXIUMUM_VERIDIUM_INFUSION = (byte) 64;
    int CHARGED_DAMAGE_MULTIPLIER = 4;
    String nbt_tag = "ambrosium_charge";

    static CompoundTag createCompoundFor(ItemStack stack) {
        return stack.hasTag() ? stack.getOrCreateTag().copy() : new CompoundTag();
    }

    static ItemStack depleteInfusion(ItemStack stack, @Nullable LivingEntity user) {
        Item item = stack.getItem();
        if (item instanceof VeridiumItem virydium) {
            Item itemForStack = stack.getItem();
            CompoundTag compound = createCompoundFor(stack);
            byte ambrosiumCharge = compound.getByte(nbt_tag);
            byte newCharge;
            if (ambrosiumCharge > 1) {
                newCharge = (byte) (ambrosiumCharge - 1);
            } else {
                newCharge = (byte) 0;
                itemForStack = virydium.getReplacementItem(stack);
                if (user != null) {
                    if (user instanceof ServerPlayer player) {
                        // TODO: figure out better way to do this maybe
                        ReduxPacketHandler.sendToPlayer(new InfusionExpirePacket(), player);
                    }
                }
            }
            compound.putByte(nbt_tag, newCharge);
            ItemStack newStack = new ItemStack(itemForStack, 1);
            newStack.setTag(compound);

            return newStack;
        }
        return stack;
    }
    /**
     * Returns a new {@link ItemStack} that copies the given stack. Returns null if no change is made
     * @param stack the {@link ItemStack} to copy the NBT data from
     * @return a modified {@link ItemStack} with the new data, or null if nothing is done.
     */
    static ItemStack infuse(ItemStack stack, int amount) {
        if (amount == 0)
        {
            return stack;
        }
        Item item = stack.getItem();
        CompoundTag compound = createCompoundFor(stack);
        byte ambrosiumCharge = getInfusion(stack);
        byte newCharge;
        if (ambrosiumCharge <= MAXIUMUM_VERIDIUM_INFUSION - amount) {
            newCharge = (byte) (ambrosiumCharge + amount);
            compound.putByte(nbt_tag, newCharge);
            ItemStack i = new ItemStack(item);
            i.setTag(compound);
            return i;
        }
        return null;
    }

    static byte getInfusion(ItemStack stack) {
        CompoundTag compound = stack.hasTag() ? stack.getOrCreateTag().copy() : new CompoundTag();
        if (!(stack.getItem() instanceof VeridiumItem)) { Redux.LOGGER.warn("Attempting to get infusion level of non-veridium item! Reading NBT data anyway"); }
        return compound.getByte(nbt_tag);
    }

    Item getReplacementItem(ItemStack stack);

    default boolean isInfused(ItemStack stack) {
        return stack.is(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS);
    }

    default boolean isInfused(Item item) {
        return item.builtInRegistryHolder().is(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS);
    }
}
