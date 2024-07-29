package net.zepalesque.redux.item.tools;

import net.minecraft.core.Holder;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.recipe.recipes.InfusionRecipe;
import net.zepalesque.zenith.item.CustomStackingBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface VeridiumItem extends CustomStackingBehavior {

    String NBT_KEY = "infusion_level";
    String INFUSION_AMOUNT = "infusion_increase";
    int DURABILITY_DMG_MULTIPLIER = 4;

    Item getUninfusedItem(ItemStack stack);

    default ItemStack getUninfusedStack(ItemStack stack) {
        ItemStack i = new ItemStack(this.getUninfusedItem(stack));
        CompoundTag tag = stack.getOrCreateTag().copy();
        tag.remove(NBT_KEY);
        i.setTag(tag);
        return i;
    }

    @Nullable
    @Override
    default ItemStack transformStack(Ingredient ingredient, ItemStack original, RecipeType<?> type, Optional<CompoundTag> additionalData) {
        if (additionalData.isEmpty()) {
            return original;
        }
        CompoundTag additional = additionalData.get();
        int increase = additional.getByte(InfusionRecipe.ADDED_INFUSION);
        if (increase <= 0) {
            return original;
        }
        int max = ReduxConfig.SERVER.max_veridium_tool_infusion.get();
        CompoundTag tag = original.getOrCreateTag();
        // CompoundTags already return 0 if they do not contain the given byte key, and as the config has a minimum value of 1, it will skip this and add infusion if the item doesn't have the tag
        if (tag.getByte(NBT_KEY) >= max) {
            return null;
        } else {
            // As the above comment mentions, CompoundTags will return 0 when the byte key isn't present. This will still result in the correct value, as it will add the amount to zero.
            byte infusion = (byte) Math.min(tag.getByte(NBT_KEY) + additional.getByte(InfusionRecipe.ADDED_INFUSION), max);
            original.addTagElement(NBT_KEY, ByteTag.valueOf(infusion));
            return original;
        }
    }

    // If null is returned, do not change the item in the slot
    @Nullable
    default ItemStack deplete(ItemStack stack, @Nullable LivingEntity user, int amount) {
        if (user != null && user.level().isClientSide()) {
            return null;
        }
        if (user instanceof Player p && p.isCreative()) {
            return null;
        }

        CompoundTag tag = stack.getOrCreateTag();
        if (tag.getByte(NBT_KEY) > amount) {
            byte infusion = (byte) (tag.getByte(NBT_KEY) - amount);
            stack.addTagElement(NBT_KEY, ByteTag.valueOf(infusion));
        } else {
            return this.getUninfusedStack(stack);
        }
        return null;
    }

    default Holder<SoundEvent> getUninfuseSound() {
        return ReduxSounds.INFUSION_EXPIRE;
    }

    default void sendSound(ServerPlayer sp) {
        sp.connection.send(new ClientboundSoundPacket(getUninfuseSound(), SoundSource.PLAYERS, sp.getX(), sp.getY(), sp.getZ(), 0.8F, 0.8F + sp.level().getRandom().nextFloat() * 0.4F, sp.level().getRandom().nextLong()));
    }
}
