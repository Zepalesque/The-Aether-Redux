package net.zepalesque.redux.recipe.serializer;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.ItemStackConstructor;
import net.zepalesque.redux.recipe.StackingRecipe;
import net.zepalesque.redux.util.AssertionUtil;
import net.zepalesque.redux.util.json.CommonJsonUtil;

import javax.annotation.Nullable;
import java.util.Optional;

public class StackingRecipeSerializer<T extends StackingRecipe> implements RecipeSerializer<T> {
    private final StackingRecipeSerializer.CookieBaker<T> factory;

    public StackingRecipeSerializer(StackingRecipeSerializer.CookieBaker<T> factory) {
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject json) {
        JsonOps ops = JsonOps.INSTANCE;
        if (!json.has("ingredient")) throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
        JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonElement);


        AssertionUtil.checkState(json.has("result"), () -> new JsonSyntaxException("Missing result, expected to find a string or object"));
        AssertionUtil.checkState(json.get("result").isJsonObject(), () -> new JsonSyntaxException("Expected result to be object"));
        JsonObject resultObject = json.getAsJsonObject("result");
        ItemStackConstructor result = ItemStackConstructor.CODEC.parse(ops, resultObject).resultOrPartial(Redux.LOGGER::error).orElseThrow();

        Optional<CompoundTag> additional;
        if (json.has("additional") && json.get("additional").isJsonObject()) {
            JsonObject additionalObject = json.getAsJsonObject("additional");
            additional = CompoundTag.CODEC.parse(ops, additionalObject).resultOrPartial(Redux.LOGGER::error);
        } else {
            additional = Optional.empty();
        }

        Optional<SoundEvent> sound;
        if (json.has("sound")) {
            JsonElement soundObject = json.get("sound");
            sound = BuiltInRegistries.SOUND_EVENT.getOptional(new ResourceLocation(soundObject.getAsString()));
        } else {
            sound = Optional.empty();
        }

        return this.factory.create(id, ingredient, result, additional, sound);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStackConstructor result = buffer.readJsonWithCodec(ItemStackConstructor.CODEC);
        Optional<CompoundTag> additional = buffer.readOptional(FriendlyByteBuf::readNbt);
        Optional<SoundEvent> sound = buffer.readOptional(FriendlyByteBuf::readResourceLocation).flatMap(BuiltInRegistries.SOUND_EVENT::getOptional);
        return this.factory.create(id, ingredient, result, additional,sound);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeJsonWithCodec(ItemStackConstructor.CODEC, recipe.getResult());
        buffer.writeOptional(recipe.getAdditionalData(), FriendlyByteBuf::writeNbt);
        buffer.writeOptional(recipe.getSound().map(BuiltInRegistries.SOUND_EVENT::getKey), FriendlyByteBuf::writeResourceLocation);
    }

    public interface CookieBaker<T extends StackingRecipe> {
        T create(ResourceLocation id, Ingredient ingredient, ItemStackConstructor result, Optional<CompoundTag> additional, Optional<SoundEvent> sound);
    }
}
