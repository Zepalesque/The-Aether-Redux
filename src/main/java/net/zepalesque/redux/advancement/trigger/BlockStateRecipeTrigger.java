package net.zepalesque.redux.advancement.trigger;

import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.predicate.RecipeTypePredicate;
import net.zepalesque.redux.advancement.predicate.StatePredicate;

/**
 * Criterion trigger used for checking a blockstate conversion recipe.
 */
public class BlockStateRecipeTrigger extends SimpleCriterionTrigger<BlockStateRecipeTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(Redux.MODID, "block_state_recipe");
    public static final BlockStateRecipeTrigger INSTANCE = new BlockStateRecipeTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite entity, DeserializationContext context) {
        StatePredicate ingredient = json.has("original_block") ? StatePredicate.fromJson(json.get("original_block")) : StatePredicate.ANY;
        StatePredicate result = json.has("result_block") ? StatePredicate.fromJson(json.get("result_block")) : StatePredicate.ANY;
        RecipeTypePredicate recipe =json.has("recipe_type") ? RecipeTypePredicate.fromJson(json.get("recipe_type")) : RecipeTypePredicate.ANY;
        return new TriggerInstance(entity, ingredient, result, recipe);
    }

    public void trigger(ServerPlayer player, BlockState ingredient, BlockState result, RecipeType recipe) {
        this.trigger(player, (instance) -> instance.test(ingredient, result, recipe));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final StatePredicate original;
        private final StatePredicate result;
        private final RecipeTypePredicate recipe;

        public TriggerInstance(EntityPredicate.Composite entity, StatePredicate original, StatePredicate result, RecipeTypePredicate recipe) {
            super(BlockStateRecipeTrigger.ID, entity);
            this.original = original;
            this.result = result;
            this.recipe = recipe;
        }


        public boolean test(BlockState ingredient, BlockState result, RecipeType<? extends AbstractBlockStateRecipe> recipe) {
            return this.original.matches(ingredient) && this.result.matches(result) && this.recipe.matches(recipe);
        }

        public static TriggerInstance forOriginal(Block original) {
            return forOriginal(StatePredicate.Builder.block().of(original).build());
        }

        public static TriggerInstance forOriginal(StatePredicate original) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, original, StatePredicate.ANY, RecipeTypePredicate.ANY);
        }


        public static TriggerInstance forResult(Block result) {
            return forResult(StatePredicate.Builder.block().of(result).build());
        }

        public static TriggerInstance forResult(StatePredicate result) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, StatePredicate.ANY, result, RecipeTypePredicate.ANY);
        }


        public static TriggerInstance forRecipeFrom(Block original, RecipeType<? extends AbstractBlockStateRecipe> recipe) {
            return forRecipeFrom(StatePredicate.Builder.block().of(original).build(), RecipeTypePredicate.of(recipe));
        }

        public static TriggerInstance forRecipeFrom(StatePredicate original, RecipeTypePredicate recipe) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, original, StatePredicate.ANY, recipe);
        }

        public static TriggerInstance forRecipeTo(Block result, RecipeType<? extends AbstractBlockStateRecipe> recipe) {
            return forRecipeTo(StatePredicate.Builder.block().of(result).build(), RecipeTypePredicate.of(recipe));
        }

        public static TriggerInstance forRecipeTo(StatePredicate result, RecipeTypePredicate recipe) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, StatePredicate.ANY, result, recipe);
        }

        public static TriggerInstance forRecipe(RecipeType<? extends AbstractBlockStateRecipe> recipe) {
            return forRecipe(RecipeTypePredicate.of(recipe));
        }

        public static TriggerInstance forRecipe(RecipeTypePredicate recipe) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, StatePredicate.ANY, StatePredicate.ANY, recipe);
        }

        public static TriggerInstance forConversion(Block ingredient, Block result) {
            return forConversion(StatePredicate.Builder.block().of(ingredient).build(), StatePredicate.Builder.block().of(result).build());
        }

        public static TriggerInstance forConversion(StatePredicate original, StatePredicate result) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, original, result, RecipeTypePredicate.ANY);
        }

        public static TriggerInstance forExact(Block ingredient, Block result, RecipeType<? extends AbstractBlockStateRecipe> recipe) {
            return forExact(StatePredicate.Builder.block().of(ingredient).build(), StatePredicate.Builder.block().of(result).build(), RecipeTypePredicate.of(recipe));
        }

        public static TriggerInstance forExact(StatePredicate original, StatePredicate result, RecipeTypePredicate recipe) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, original, result, recipe);
        }

        public static TriggerInstance any() {
            return forExact(StatePredicate.ANY, StatePredicate.ANY, RecipeTypePredicate.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            if (this.original != null && this.original != StatePredicate.ANY)
            {
                jsonObject.add("original_block", this.original.serializeToJson());
            }
            if (this.result != null && this.result != StatePredicate.ANY)
            {
                jsonObject.add("result_block", this.result.serializeToJson());
            }
            if (this.recipe != null && this.recipe != RecipeTypePredicate.ANY)
            {
                jsonObject.add("recipe_type", this.recipe.serializeToJson());
            }
            return jsonObject;
        }


    }
}
