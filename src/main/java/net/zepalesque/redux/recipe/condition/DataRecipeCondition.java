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
import net.zepalesque.redux.api.condition.AbstractCondition;

import java.util.Optional;

public  class DataRecipeCondition implements ICondition {
    private static final ResourceLocation NAME = Redux.locate("data_recipe_condition");
    private final AbstractCondition condition;

    public DataRecipeCondition(AbstractCondition pCondition)
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
            return this.condition.isConditionMet();
        } else {
            return true;
        }
    }

    @Override
    public String toString()
    {
        return "data_condition(\"" + (this.condition != null ? this.condition.text() : "null") + "\")";
    }

    public static class Serializer implements IConditionSerializer<DataRecipeCondition>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, DataRecipeCondition value)
        {

            DataResult<JsonElement> result = AbstractCondition.CODEC.encodeStart(JsonOps.INSTANCE, value.condition);
            result.resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not write DataRecipeCondition with id {} - error: {}", this.getID(), errorMessage))
                    .ifPresent((jsonElement) -> json.add("data_recipe_condition", jsonElement));

        }

        @Override
        public DataRecipeCondition read(JsonObject json)
        {
            JsonElement element = json.get("data_recipe_condition");
            Optional<? extends AbstractCondition<?>> optional = AbstractCondition.CODEC.parse(JsonOps.INSTANCE, element).resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not parse DataRecipeCondition from json with id {} - error: {}", this.getID(), errorMessage));
            if (optional.isPresent())
            {
                return new DataRecipeCondition(optional.get());
            } else {
                throw new JsonSyntaxException("Failed to parse data condition json!");
            }
        }

        @Override
        public ResourceLocation getID()
        {
            return NAME;
        }
    }
}
