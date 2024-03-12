package net.zepalesque.redux.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.redux.recipe.serializer.ReduxRecipeSerializers;
import net.zepalesque.redux.recipe.serializer.StackingRecipeSerializer;

public class InfusionRecipe extends AbstractStackingRecipe{

    public InfusionRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int infusionAmount) {
        super(ReduxRecipeTypes.INFUSION.get(), id, ingredient, result, infusionAmount);
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