package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ReduxRecipeGen extends ReduxRecipeProvider {

    public ReduxRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Redux.MODID);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        Redux.WOOD_SETS.forEach(set -> set.recipeData(this, output));
        Redux.STONE_SETS.forEach(set -> set.recipeData(this, output));

        enchantingRecipe(RecipeCategory.DECORATIONS, ReduxBlocks.CLOUDROOT_SAPLING.get(), AetherBlocks.SKYROOT_SAPLING.get(), 0.1F, 1000).save(output);
        ambrosiumEnchanting(ReduxBlocks.CLOUDROOT_LEAVES.get(), AetherBlocks.SKYROOT_LEAVES.get()).save(output);

        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_STONE_BASE.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_STONE_PILLAR.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_STONE_BASE.get(), AetherBlocks.SENTRY_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_STONE_PILLAR.get(), AetherBlocks.SENTRY_STONE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_STONE_BASE.get(), 4)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.CARVED_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_STONE_PILLAR.get(), 6)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.CARVED_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_STONE_BASE.get(), 4)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.SENTRY_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_STONE_PILLAR.get(), 6)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.SENTRY_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);


    }
}
