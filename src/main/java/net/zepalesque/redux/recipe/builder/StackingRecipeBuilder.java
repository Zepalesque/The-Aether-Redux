package net.zepalesque.redux.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.recipe.AbstractStackingRecipe;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class StackingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int infusionAmount;
    private final RecipeSerializer<? extends AbstractStackingRecipe> serializer;
    @Nullable
    private ResourceLocation function;

    public StackingRecipeBuilder(Item result, Ingredient ingredient, int infusionAmount, RecipeSerializer<? extends AbstractStackingRecipe> serializer) {
        this.result = result;
        this.ingredient = ingredient;
        this.infusionAmount = infusionAmount;
        this.serializer = serializer;

    }



    public static StackingRecipeBuilder recipe(Ingredient ingredient, Item result, int infusionAmount, RecipeSerializer<? extends AbstractStackingRecipe> serializer) {
        return new StackingRecipeBuilder(result, ingredient, infusionAmount, serializer);
    }
    public static StackingRecipeBuilder recipe(Ingredient ingredient, Item result, RecipeSerializer<? extends AbstractStackingRecipe> serializer) {
        return recipe(ingredient, result, 0, serializer);
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    public RecipeBuilder function(@Nullable ResourceLocation function) {
        this.function = function;
        return this;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public RecipeSerializer<? extends AbstractStackingRecipe> getSerializer() {
        return this.serializer;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        return this;
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
        finishedRecipeConsumer.accept(new StackingRecipeBuilder.Result(id, this.ingredient, this.result, this.infusionAmount, this.serializer));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Item result;
        private final int infusionAmount;
        private final RecipeSerializer<? extends AbstractStackingRecipe> serializer;

        public Result(ResourceLocation id, Ingredient ingredient, Item result, int infusionAmount, RecipeSerializer<? extends AbstractStackingRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.infusionAmount = infusionAmount;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());

            JsonObject result = new JsonObject();
            result.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());

            json.add("result", result);
            if (this.infusionAmount != 0) {
                json.addProperty("infusion_increase", this.infusionAmount);
            }
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
