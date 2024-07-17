package net.zepalesque.redux.item.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;
import java.util.Optional;

// Will be used for Veridium tools in redux in order to change their infusion value
public interface CustomStackingBehavior {

    @Nullable
    ItemStack transformStack(Ingredient ingredient, ItemStack original, RecipeType<?> type, Optional<CompoundTag> additionalData);
}
