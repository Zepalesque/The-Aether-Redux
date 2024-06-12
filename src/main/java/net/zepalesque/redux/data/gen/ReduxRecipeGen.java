package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.item.ReduxItems;
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

        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.VERIDIUM_NUGGET.get(), RecipeCategory.MISC, ReduxItems.VERIDIUM_INGOT.get(), "veridium_nugget", "veridium_nugget_to_veridium_ingot");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.SENTRITE_CHUNK.get(), RecipeCategory.MISC, ReduxItems.REFINED_SENTRITE.get(), "sentrite_chunk", "sentrite_chunk_to_refined_sentrite");
//        smeltingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxBlocks.VERIDIUM_ORE.get(), 0.8F).save(output, name("smelt_veridium"));
//        blastingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxBlocks.VERIDIUM_ORE.get(), 0.8F).save(output, name("blast_veridium"));
        smeltingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxItems.RAW_VERIDIUM.get(), 0.8F).save(output, name("smelt_raw_veridium"));
        blastingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxItems.RAW_VERIDIUM.get(), 0.8F).save(output, name("blast_raw_veridium"));
        smeltingOreRecipe(ReduxItems.REFINED_SENTRITE.get(), ReduxStoneSets.SENTRITE.block().get(), 0.8F).save(output, name("refine_sentrite_smelt"));
        blastingOreRecipe(ReduxItems.REFINED_SENTRITE.get(), ReduxStoneSets.SENTRITE.block().get(), 0.8F).save(output, name("refine_sentrite_blast"));

        enchantingRecipe(RecipeCategory.DECORATIONS, ReduxBlocks.CLOUDROOT_SAPLING.get(), AetherBlocks.SKYROOT_SAPLING.get(), 0.1F, 1000).save(output);
        ambrosiumEnchanting(ReduxBlocks.CLOUDROOT_LEAVES.get(), AetherBlocks.SKYROOT_LEAVES.get()).save(output);

        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_BASE.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_PILLAR.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_BASE.get(), AetherBlocks.SENTRY_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_PILLAR.get(), AetherBlocks.SENTRY_STONE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_BASE.get(), 4)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.CARVED_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_PILLAR.get(), 6)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.CARVED_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_BASE.get(), 4)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.SENTRY_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_PILLAR.get(), 6)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(ReduxRecipeProvider.getHasName(AetherBlocks.SENTRY_STONE.get()), ReduxRecipeProvider.has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);

        // Temporary test recipe
        infuse(ReduxItems.VERIDIUM_INGOT.get(), Items.IRON_INGOT).withSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ENCHANTMENT_TABLE_USE))
                .save(output, Redux.loc("infusion_test"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReduxBlocks.SENTRITE_LANTERN.get(), 1)
                .define('C', ReduxItems.SENTRITE_CHUNK.get())
                .define('A', AetherBlocks.AMBROSIUM_TORCH.get())
                .pattern("CCC")
                .pattern("CAC")
                .pattern("CCC")
                .unlockedBy(getHasName(ReduxItems.SENTRITE_CHUNK.get()), has(ReduxItems.SENTRITE_CHUNK.get()))
                .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE.get()), has(ReduxItems.REFINED_SENTRITE.get()))
                .unlockedBy(getHasName(AetherBlocks.AMBROSIUM_TORCH.get()), has(AetherBlocks.AMBROSIUM_TORCH.get()))
                .unlockedBy(getHasName(AetherItems.AMBROSIUM_SHARD.get()), has(AetherItems.AMBROSIUM_SHARD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReduxBlocks.SENTRITE_CHAIN.get(), 3)
                .define('I', ReduxItems.REFINED_SENTRITE.get())
                .define('N', ReduxItems.SENTRITE_CHUNK.get())
                .pattern("N")
                .pattern("I")
                .pattern("N")
                .unlockedBy(getHasName(ReduxItems.SENTRITE_CHUNK.get()), has(ReduxItems.SENTRITE_CHUNK.get()))
                .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE.get()), has(ReduxItems.REFINED_SENTRITE.get()))
                .save(output);


    }
}
