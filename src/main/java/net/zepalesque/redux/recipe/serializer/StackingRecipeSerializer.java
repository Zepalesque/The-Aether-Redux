package net.zepalesque.redux.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.zepalesque.redux.recipe.StackingRecipe;
import net.zepalesque.redux.util.json.CommonJsonUtil;

import javax.annotation.Nullable;

public class StackingRecipeSerializer<T extends StackingRecipe> implements RecipeSerializer<T> {
    private final StackingRecipeSerializer.CookieBaker<T> factory;

    public StackingRecipeSerializer(StackingRecipeSerializer.CookieBaker<T> factory) {
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject json) {
        if (!json.has("ingredient")) throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
        JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonElement);


        ItemStack result;
        if (!json.has("result")) {
            throw new JsonSyntaxException("Missing result, expected to find a string or object");
        }
        if (json.get("result").isJsonObject()) {
            JsonObject resultObject = json.getAsJsonObject("result");
            result = ShapedRecipe.itemStackFromJson(resultObject);
        } else {
            throw new JsonSyntaxException("Expected result to be object");
        }

        int infusionAmount = CommonJsonUtil.getIntOr("infusion_increase", json, 0);


        return this.factory.create(id, ingredient, result, infusionAmount);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        int infusionAmount = buffer.readInt();
        return this.factory.create(id, ingredient, result, infusionAmount);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeItem(recipe.getResult());
        buffer.writeInt(recipe.infusionAmount());

    }

    public interface CookieBaker<T extends StackingRecipe> {
        T create(ResourceLocation id, Ingredient ingredient, ItemStack result, int infusionAmount);
    }
}
