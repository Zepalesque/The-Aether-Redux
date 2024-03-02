package net.zepalesque.redux.loot.condition;

import com.google.gson.*;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.AbstractCondition;

import java.util.Optional;

public class DataLootCondition implements LootItemCondition {
    private final AbstractCondition condition;

    public DataLootCondition(AbstractCondition condition)
    {
        this.condition = condition;
    }

    public LootItemConditionType getType() {
        return ReduxLootConditions.DATA_CONDITION.get();
    }


    public boolean test(LootContext lootContext) {
        if (this.condition != null) {
            return this.condition.isConditionMet();
        } else {
            return true;
        }
    }

    public static LootItemCondition.Builder conditionOf(AbstractCondition condition) {
        return () -> new DataLootCondition(condition);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<DataLootCondition> {
        public Serializer() {
        }

        public void serialize(JsonObject json, DataLootCondition instance, JsonSerializationContext context) {

            DataResult<JsonElement> result = AbstractCondition.CODEC.encodeStart(JsonOps.INSTANCE, instance.condition);
            result.resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not write DataLootCondition with id {} - error: {}", "data_loot_condition", errorMessage))
                    .ifPresent((jsonElement) -> json.add("data_loot_condition", jsonElement));
        }

        public DataLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            JsonElement element = json.get("data_loot_condition");
            Optional<? extends AbstractCondition<?>> optional = AbstractCondition.CODEC.parse(JsonOps.INSTANCE, element).resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not parse DataRecipeCondition from json with id {} - error: {}", "data_loot_condition", errorMessage));
            if (optional.isPresent())
            {
                return new DataLootCondition(optional.get());
            } else {
                throw new JsonSyntaxException("Failed to parse data condition json!");
            }
        }
    }
}
