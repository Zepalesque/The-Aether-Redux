package net.zepalesque.redux.recipe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.redux.api.ItemStackConstructor;
import net.zepalesque.redux.recipe.serializer.ReduxRecipeSerializers;
import net.zepalesque.redux.recipe.serializer.StackingRecipeSerializer;

import java.util.Optional;

public class InfusionRecipe extends AbstractStackingRecipe {
    public static final String ADDED_INFUSION = "added_infusion";
    public InfusionRecipe(ResourceLocation id, Ingredient ingredient, ItemStackConstructor result, Optional<CompoundTag> additional, Optional<SoundEvent> sound) {
        super(ReduxRecipeTypes.INFUSION.get(), id, ingredient, result, additional, sound);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ReduxRecipeSerializers.INFUSION.get();
    }

    public static class Serializer extends StackingRecipeSerializer<InfusionRecipe> {
        public Serializer() {
            super(InfusionRecipe::new);
        }
    }
}