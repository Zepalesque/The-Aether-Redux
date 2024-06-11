package net.zepalesque.redux.recipe.recipes;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.redux.recipe.ReduxRecipes;
import net.zepalesque.zenith.recipe.recipes.AbstractStackingRecipe;
import net.zepalesque.zenith.recipe.serializer.StackingRecipeSerializer;

import java.util.Optional;

public class InfusionRecipe extends AbstractStackingRecipe {
    public InfusionRecipe(Ingredient ingredient, ItemStack result, Optional<CompoundTag> additional, Optional<Holder<SoundEvent>> sound) {
        super(ReduxRecipes.INFUSION.get(), ingredient, result, additional, sound);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ReduxRecipes.Serializers.INFUSION.get();
    }

    public static class Serializer extends StackingRecipeSerializer<InfusionRecipe> {
        public Serializer() {
            super(InfusionRecipe::new);
        }
    }
}
