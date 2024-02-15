package net.zepalesque.redux.api.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.components.AbstractWidget;
import net.zepalesque.redux.api.packconfig.PackConfig;

import java.util.Optional;
import java.util.function.Function;

/** Some useful {@link Mapper}s */
public class Mappers {


    public static final Mapper<String> STRING = new Mapper<>() {
        @Override
        public Optional<String> decode(JsonElement json) {
            return primitiveOptional(json, JsonElement::getAsString);
        }

        @Override
        public JsonElement encode(String s) {
            return new JsonPrimitive(s);
        }

        @Override
        public Optional<String> read(String s) {
            return Optional.of(s);
        }

        @Override
        public String write(String s) {
            return s;
        }
    };

    public static final Mapper<Integer> INT = new Mapper<>() {
        @Override
        public Optional<Integer> decode(JsonElement data) {
            return primitiveOptional(data, JsonElement::getAsInt);
        }

        @Override
        public JsonElement encode(Integer value) {
            return new JsonPrimitive(value);
        }

        @Override
        public Optional<Integer> read(String data) {
            return emptyIfError(data, Integer::valueOf);
        }

        @Override
        public String write(Integer value) {
            return value.toString();
        }
    };

    public static final Mapper<Long> LONG = new Mapper<>() {
        @Override
        public Optional<Long> decode(JsonElement data) {
            return primitiveOptional(data, JsonElement::getAsLong);
        }

        @Override
        public JsonElement encode(Long value) {
            return new JsonPrimitive(value);
        }

        @Override
        public Optional<Long> read(String data) {
            return emptyIfError(data, Long::valueOf);
        }

        @Override
        public String write(Long value) {
            return value.toString();
        }
    };

    public static final Mapper<Float> FLOAT = new Mapper<>() {
        @Override
        public Optional<Float> decode(JsonElement data) {
            return primitiveOptional(data, JsonElement::getAsFloat);
        }

        @Override
        public JsonElement encode(Float value) {
            return new JsonPrimitive(value);
        }

        @Override
        public Optional<Float> read(String data) {
            return emptyIfError(data, Float::valueOf);
        }

        @Override
        public String write(Float value) {
            return value.toString();
        }
    };

    public static final Mapper<Double> DOUBLE = new Mapper<>() {
        @Override
        public Optional<Double> decode(JsonElement data) {
            return primitiveOptional(data, JsonElement::getAsDouble);
        }

        @Override
        public JsonElement encode(Double value) {
            return new JsonPrimitive(value);
        }

        @Override
        public Optional<Double> read(String data) {
            return emptyIfError(data, Double::valueOf);
        }

        @Override
        public String write(Double value) {
            return value.toString();
        }
    };








    public static final Mapper<Boolean> BOOL = new Mapper<>() {
        @Override
        public Optional<Boolean> decode(JsonElement data) {
            return primitiveOptional(data, JsonElement::getAsBoolean);
        }

        @Override
        public JsonElement encode(Boolean value) {
            return new JsonPrimitive(value);
        }

        @Override
        public Optional<Boolean> read(String data) {
            return emptyIfError(data, Boolean::valueOf);
        }

        @Override
        public String write(Boolean value) {
            return value.toString();
        }

    };

    public static <T> Optional<T> primitiveOptional(JsonElement json, Function<JsonElement, T> method) {
        try {
            return json.isJsonPrimitive() ? Optional.of(method.apply(json)) : Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    private static <T> Optional<T> emptyIfError(String data, Function<String, T> func) {
        try {
            T value = func.apply(data);
            return Optional.of(value);
        } catch (Exception e)
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
