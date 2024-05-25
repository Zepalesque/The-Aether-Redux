package net.zepalesque.redux.data.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ReduxRecipeGen extends ReduxRecipeProvider {

    public ReduxRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Redux.MODID);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {

    }
}
