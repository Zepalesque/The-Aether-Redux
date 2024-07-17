package net.zepalesque.redux.recipe.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.ItemStackConstructor;
import net.zepalesque.redux.recipe.AbstractStackingRecipe;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

public class StackingRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final ItemStackConstructor result;
    private Optional<CompoundTag> extra = Optional.empty();
    private Optional<SoundEvent> sound = Optional.empty();
    private final RecipeSerializer<? extends AbstractStackingRecipe> serializer;

    public StackingRecipeBuilder(Ingredient ingredient, ItemStackConstructor result,RecipeSerializer<? extends AbstractStackingRecipe> serializer) {
        this.ingredient = ingredient;
        this.result = result;
        this.serializer = serializer;

    }




    public static StackingRecipeBuilder recipe(Ingredient ingredient, ItemStackConstructor result, RecipeSerializer<? extends AbstractStackingRecipe> factory) {
        return new StackingRecipeBuilder(ingredient, result, factory);
    }

    public StackingRecipeBuilder withExtra(CompoundTag data) {
        this.extra = Optional.of(data);
        return this;
    }

    public StackingRecipeBuilder withSound(SoundEvent sound) {
        this.sound = Optional.of(sound);
        return this;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStackConstructor getResultStack() {
        return this.result;
    }

    public RecipeSerializer<? extends AbstractStackingRecipe> getSerializer() {
        return this.serializer;
    }


    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        return this;
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
        finishedRecipeConsumer.accept(new StackingRecipeBuilder.Result(id, this.ingredient, this.result, this.extra, this.sound, this.serializer));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final ItemStackConstructor result;
        private final Optional<CompoundTag> extra;
        private final Optional<SoundEvent> sound;
        private final RecipeSerializer<? extends AbstractStackingRecipe> serializer;

        public Result(ResourceLocation id, Ingredient ingredient, ItemStackConstructor result, Optional<CompoundTag> extra, Optional<SoundEvent> sound, RecipeSerializer<? extends AbstractStackingRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.extra = extra;
            this.sound = sound;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonOps ops = JsonOps.INSTANCE;

            json.add("ingredient", this.ingredient.toJson());

            JsonElement object = ItemStackConstructor.CODEC.encodeStart(ops, this.result).result().orElseThrow();
            json.add("result", object);

            this.extra.flatMap(tag -> CompoundTag.CODEC.encodeStart(ops, tag).resultOrPartial(Redux.LOGGER::error)).ifPresent(element -> json.add("additional", element));

            this.sound.map(ForgeRegistries.SOUND_EVENTS::getKey).ifPresent(loc -> json.addProperty("sound", loc.toString()));

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
