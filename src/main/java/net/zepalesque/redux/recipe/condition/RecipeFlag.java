package net.zepalesque.redux.recipe.condition;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.flag.DataFlag;

import java.util.Optional;

public  class RecipeFlag implements ICondition {
    private static final ResourceLocation NAME = Redux.locate("recipe_flag");
    private final DataFlag<?> condition;

    public RecipeFlag(DataFlag<?> pCondition)
    {
        this.condition = pCondition;
    }

    @Override
    public ResourceLocation getID()
    {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        if (this.condition != null) {
            return this.condition.test();
        } else {
            return true;
        }
    }

    @Override
    public String toString()
    {
        return "recipe_flag(\"" + (this.condition != null ? this.condition.text() : "null") + "\")";
    }

    public static class Serializer implements IConditionSerializer<RecipeFlag>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, RecipeFlag value)
        {

            DataResult<JsonElement> result = DataFlag.CODEC.encodeStart(JsonOps.INSTANCE, value.condition);
            result.resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not write RecipeFlag with id {} - error: {}", this.getID(), errorMessage))
                    .ifPresent((jsonElement) -> json.add("flag", jsonElement));

        }

        @Override
        public RecipeFlag read(JsonObject json)
        {
            JsonElement element = json.get("flag");
            Optional<? extends DataFlag<?>> optional = DataFlag.CODEC.parse(JsonOps.INSTANCE, element).resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not parse RecipeFlag from json with id {} - error: {}", this.getID(), errorMessage));
            if (optional.isPresent())
            {
                return new RecipeFlag(optional.get());
            } else {
                throw new JsonSyntaxException("Failed to parse data flag json!");
            }
        }

        @Override
        public ResourceLocation getID()
        {
            return NAME;
        }
    }
}
