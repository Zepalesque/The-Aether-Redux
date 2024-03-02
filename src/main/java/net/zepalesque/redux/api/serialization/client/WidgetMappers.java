package net.zepalesque.redux.api.serialization.client;

import com.google.gson.JsonElement;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.api.serialization.Mappers;
import net.zepalesque.redux.client.gui.component.config.SaveableBooleanButton;

import java.util.Optional;
import java.util.function.Function;

/** Some useful {@link WidgetMapper}s. See {@link Mappers} */
public class WidgetMappers {


    public static final WidgetMapper<String> STRING = WidgetMapper.fromMapper(Mappers.STRING);

    public static final WidgetMapper<Integer> INT = WidgetMapper.fromMapper(Mappers.INT);

    public static final WidgetMapper<Long> LONG = WidgetMapper.fromMapper(Mappers.LONG);


    public static final WidgetMapper<Float> FLOAT = WidgetMapper.fromMapper(Mappers.FLOAT);

    public static final WidgetMapper<Double> DOUBLE = WidgetMapper.fromMapper(Mappers.DOUBLE);

    public static final WidgetMapper<Boolean> BOOL = WidgetMapper.fromMapper(Mappers.BOOL, (config, page, screen, x, y, width, height, font) ->
{
        if (config.get() instanceof Boolean) {
            return new SaveableBooleanButton(x, y, width, height, (PackConfig<Boolean>) config, page, screen);
        } else {
            throw new ClassCastException("Cannot cast non-boolean to boolean!");
        }
    });

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
