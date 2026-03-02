package net.zepalesque.redux.recipe.recipes;

import com.aetherteam.aether.recipe.recipes.block.MatchEventRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.recipe.ReduxRecipes;

public class WillowSporeRecipe extends AbstractBlockStateRecipe implements MatchEventRecipe {
    public WillowSporeRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function) {
        super(ReduxRecipes.WILLOW_SPORES.get(), ingredient, result, function);
    }
    
    @Override
    public boolean matches(@Nullable Player player, Level level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        return this.matches(level, pos, oldState) && MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState, recipeType);
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ReduxRecipes.Serializers.WILLOW_SPORES.get();
    }
    
    public static class Serializer extends BlockStateRecipeSerializer<WillowSporeRecipe> {
        public Serializer() {
            super(WillowSporeRecipe::new);
        }
    }
}
