package net.zepalesque.redux.recipe;

import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.recipe.recipes.InfusionRecipe;
import net.zepalesque.redux.recipe.recipes.WillowSporeRecipe;
import net.zepalesque.zenith.api.recipe.serializer.StackingRecipeSerializer;

public class ReduxRecipes {
    public static final DeferredRegister<RecipeType<?>>
        TYPES = Redux.reg(BuiltInRegistries.RECIPE_TYPE);
    public static final DeferredHolder<RecipeType<?>, RecipeType<InfusionRecipe>>
        INFUSION = TYPES.register("infusion",
        () -> RecipeType.simple(Redux.loc("infusion"))
    );
    public static final DeferredHolder<RecipeType<?>, RecipeType<WillowSporeRecipe>>
        WILLOW_SPORES = TYPES.register("willow_spore_blighting",
        () -> RecipeType.simple(Redux.loc("willow_spore_blighting"))
    );

    public static class Serializers {
        public static final DeferredRegister<RecipeSerializer<?>>
            SERIALIZERS = Redux.reg(BuiltInRegistries.RECIPE_SERIALIZER);
        
        public static final DeferredHolder<RecipeSerializer<?>, StackingRecipeSerializer<InfusionRecipe>>
            INFUSION = SERIALIZERS.register("infusion",
            InfusionRecipe.Serializer::new
        );
        
        public static final DeferredHolder<RecipeSerializer<?>, BlockStateRecipeSerializer<WillowSporeRecipe>>
            WILLOW_SPORES = SERIALIZERS.register("willow_spore_blighting",
            WillowSporeRecipe.Serializer::new
        );
    }
}
