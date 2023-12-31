package net.zepalesque.redux.recipe;

import com.aetherteam.aether.recipe.recipes.block.MatchEventRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.recipe.serializer.ReduxRecipeSerializers;

public class BlightedSporeRecipe extends AbstractBlockStateRecipe implements MatchEventRecipe {
    public BlightedSporeRecipe(ResourceLocation id,     BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function) {
        super(ReduxRecipeTypes.SPORE_BLIGHTING.get(), id, ingredient, result, function);
    }

    @Override
    public boolean matches(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState, RecipeType recipeType) {
        return this.matches(level, pos, oldState) && MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState, recipeType);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ReduxRecipeSerializers.SPORE_BLIGHTING.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<BlightedSporeRecipe> {
        public Serializer() {
            super(BlightedSporeRecipe::new);
        }
    }
}