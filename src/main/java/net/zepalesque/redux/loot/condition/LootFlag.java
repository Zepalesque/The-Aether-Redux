package net.zepalesque.redux.loot.condition;

import com.google.gson.*;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.flag.DataFlag;

import java.util.Optional;

public class LootFlag implements LootItemCondition {
    private final DataFlag<?> condition;

    public LootFlag(DataFlag<?> condition)
    {
        this.condition = condition;
    }

    public LootItemConditionType getType() {
        return ReduxLootConditions.FLAG.get();
    }


    public boolean test(LootContext lootContext) {
        if (this.condition != null) {
            return this.condition.test();
        } else {
            return true;
        }
    }

    public static LootItemCondition.Builder of(DataFlag<?> condition) {
        return () -> new LootFlag(condition);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootFlag> {
        public Serializer() {
        }

        public void serialize(JsonObject json, LootFlag instance, JsonSerializationContext context) {

            DataResult<JsonElement> result = DataFlag.CODEC.encodeStart(JsonOps.INSTANCE, instance.condition);
            result.resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not write LootFlag with id {} - error: {}", "loot_flag", errorMessage))
                    .ifPresent((jsonElement) -> json.add("flag", jsonElement));
        }

        public LootFlag deserialize(JsonObject json, JsonDeserializationContext context) {
            JsonElement element = json.get("flag");
            Optional<? extends DataFlag<?>> optional = DataFlag.CODEC.parse(JsonOps.INSTANCE, element).resultOrPartial(errorMessage -> Redux.LOGGER.warn("Could not parse LootFlag from json with id {} - error: {}", "flag", errorMessage));
            if (optional.isPresent())
            {
                return new LootFlag(optional.get());
            } else {
                throw new JsonSyntaxException("Failed to parse data flag json!");
            }
        }
    }
}
