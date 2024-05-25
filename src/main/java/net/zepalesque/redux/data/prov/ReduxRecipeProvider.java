package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherRecipeProvider;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class ReduxRecipeProvider extends AetherRecipeProvider {

    public ReduxRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String id) {
        super(output, lookupProvider, id);
    }

    public static void woodFromLogs(RecipeOutput recipeOutput, ItemLike wood, ItemLike log) {
        RecipeProvider.woodFromLogs(recipeOutput, wood, log);
    }

    public static void planksFromLog(RecipeOutput recipeOutput, ItemLike planks, TagKey<Item> logs, int resultCount) {
        RecipeProvider.planksFromLog(recipeOutput, planks, logs, resultCount);
    }

    public ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return super.fence(fence, material);
    }

    public ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return super.fenceGate(fenceGate, material);
    }

    public static RecipeBuilder doorBuilder(ItemLike door, Ingredient material) {
        return RecipeProvider.doorBuilder(door, material);
    }

    public static String getHasName(ItemLike itemLike) {
        return RecipeProvider.getHasName(itemLike);
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike itemLike) {
        return RecipeProvider.has(itemLike);
    }

    public static RecipeBuilder trapdoorBuilder(ItemLike trapdoor, Ingredient material) {
        return RecipeProvider.trapdoorBuilder(trapdoor, material);
    }

    public static void pressurePlate(RecipeOutput output, ItemLike pressurePlate, ItemLike material) {
        RecipeProvider.pressurePlate(output, pressurePlate, material);
    }

    public static RecipeBuilder buttonBuilder(ItemLike button, Ingredient material) {
        return RecipeProvider.buttonBuilder(button, material);
    }

    public static void woodenBoat(RecipeOutput output, ItemLike boat, ItemLike material) {
        RecipeProvider.woodenBoat(output, boat, material);
    }

    public static void chestBoat(RecipeOutput output, ItemLike boat, ItemLike material) {
        RecipeProvider.chestBoat(output, boat, material);
    }

    public static void slab(RecipeOutput output, RecipeCategory category, ItemLike slab, ItemLike material) {
        RecipeProvider.slab(output, category, slab, material);
    }

    public RecipeBuilder stairs(Supplier<? extends Block> stairs, Supplier<? extends Block> material) {
        return super.stairs(stairs, material);
    }

}
