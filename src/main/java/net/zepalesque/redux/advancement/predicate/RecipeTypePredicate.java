package net.zepalesque.redux.advancement.predicate;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;
import java.util.Set;

public class RecipeTypePredicate {
   public static final RecipeTypePredicate ANY = new RecipeTypePredicate(null);

   @Nullable
   private final Set<RecipeType> recipes;

   public RecipeTypePredicate(@Nullable Set<RecipeType> recipes) {
      this.recipes = recipes;
   }

   public static RecipeTypePredicate of(@Nullable Set<RecipeType> recipes) {
      return new RecipeTypePredicate(recipes);
   }
   public static RecipeTypePredicate of(RecipeType recipe) {
      return new RecipeTypePredicate(ImmutableSet.of(recipe));
   }

   public boolean matches(RecipeType recipe) {
      return this.recipes == null || this.recipes.contains(recipe);
   }

   public JsonElement serializeToJson() {
      JsonObject jsonobject = new JsonObject();
      if (this.recipes != null && !this.recipes.isEmpty()) {
         JsonArray jsonarray = new JsonArray();

         for (RecipeType recipe : this.recipes) {
            jsonarray.add(BuiltInRegistries.RECIPE_TYPE.getKey(recipe).toString());
         }
         jsonobject.add("recipes", jsonarray);
      }
      return jsonobject;
   }

   public static RecipeTypePredicate fromJson(@Nullable JsonElement json) {
      if (json == null || !json.isJsonObject()) {
      return RecipeTypePredicate.ANY;
      } else {
         JsonObject jsonobject = GsonHelper.convertToJsonObject(json, "RecipeTypePredicate");
         JsonArray array = GsonHelper.getAsJsonArray(jsonobject, "recipes", null);
         if (array == null || array.isEmpty())
         {
            return RecipeTypePredicate.ANY;
         } else {
            Set<RecipeType> set = null;
            ImmutableSet.Builder<RecipeType> builder = ImmutableSet.builder();

            for(JsonElement jsonelement : array) {
               ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.convertToString(jsonelement, "recipe type"));
               builder.add(BuiltInRegistries.RECIPE_TYPE.getOptional(resourcelocation).orElseThrow(() -> new JsonSyntaxException("Unknown recipe type '" + resourcelocation + "'")));
            }

            set = builder.build();
            return new RecipeTypePredicate(set);
         }

      }
   }
}