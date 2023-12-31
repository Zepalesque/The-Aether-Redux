package net.zepalesque.redux.api.serialization.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.api.serialization.Mapper;
import net.zepalesque.redux.client.gui.component.SaveableEditBox;
import net.zepalesque.redux.client.gui.component.SaveableEnumButton;
import net.zepalesque.redux.client.gui.screen.PackConfigMenu;
import net.zepalesque.redux.util.function.OctoFunction;

import java.util.Optional;
import java.util.function.Function;


@OnlyIn(Dist.CLIENT)
public interface WidgetMapper<T> extends Mapper<T> {

    static <A extends Enum<A>> WidgetMapper<A> fromEnum(Class<A> clazz) {
        return new WidgetMapper<>() {
            @Override
            public Optional<A> decode(JsonElement json) {
                try {
                    return json.isJsonPrimitive() ?
                            Optional.of(Enum.valueOf(clazz,
                                    json.getAsString())) : Optional.empty();
                } catch (Exception e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }

            @Override
            public JsonElement encode(A a) {
                return new JsonPrimitive(a.name());
            }

            @Override
            public Optional<A> read(String s) {
                try {
                    return Optional.of(Enum.valueOf(clazz, s));
                } catch (Exception e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }

            @Override
            public String write(A a) {
                return a.name();
            }

            @Override
            public AbstractWidget createWidget(PackConfig<?> config, int pg, PackConfigMenu screen, int x, int y, int width, int height, Font font) {
                return new SaveableEnumButton<>(x, y, width, height, (PackConfig<A>) config, pg, screen);
            }

        };


    }



    default AbstractWidget createWidget(PackConfig<?> config, int pg, PackConfigMenu screen, int x, int y, int width, int height, Font font) {
        return new SaveableEditBox(font, x, y, width, height, config, pg, screen);
    }

    default <Q> WidgetMapper<Q> xmap(Function<T, Q> to, Function<Q, T> from, OctoFunction<PackConfig<?>, Integer, PackConfigMenu, Integer, Integer, Integer, Integer, Font, AbstractWidget> widgetConstructor) {
        Function<Optional<T>, Optional<Q>> optionalFunc = ogVal -> ogVal.isEmpty() ? Optional.empty() : Optional.of(to.apply(ogVal.get()));
        final WidgetMapper<T> og = this;
        return new WidgetMapper<>() {
            @Override
            public Optional<Q> decode(JsonElement json) {
                return optionalFunc.apply(og.decode(json));
            }

            @Override
            public JsonElement encode(Q q) {
                return og.encode(from.apply(q));
            }

            @Override
            public Optional<Q> read(String s) {
                return optionalFunc.apply(og.read(s));
            }

            @Override
            public String write(Q q) {
                return og.write(from.apply(q));
            }

            @Override
            public AbstractWidget createWidget(PackConfig<?> config, int pg, PackConfigMenu screen, int x, int y, int width, int height, Font font) {
                return widgetConstructor.apply(config, pg, screen, x, y, width, height, font);
            }
        };
    }

    static <T> WidgetMapper<T> fromMapper(final Mapper<T> mapper)
    {
        return new WidgetMapper<>() {
            @Override
            public Optional<T> decode(JsonElement data) {
                return mapper.decode(data);
            }

            @Override
            public JsonElement encode(T value) {
                return mapper.encode(value);
            }

            @Override
            public Optional<T> read(String data) {
                return mapper.read(data);
            }

            @Override
            public String write(T value) {
                return mapper.write(value);
            }
        };
    }

    static <T> WidgetMapper<T> fromMapper(final Mapper<T> mapper, OctoFunction<PackConfig<?>, Integer, PackConfigMenu, Integer, Integer, Integer, Integer, Font, AbstractWidget> widgetConstructor)
    {
        return new WidgetMapper<>() {
            @Override
            public Optional<T> decode(JsonElement data) {
                return mapper.decode(data);
            }

            @Override
            public JsonElement encode(T value) {
                return mapper.encode(value);
            }

            @Override
            public Optional<T> read(String data) {
                return mapper.read(data);
            }

            @Override
            public String write(T value) {
                return mapper.write(value);
            }

            @Override
            public AbstractWidget createWidget(PackConfig<?> config, int pg, PackConfigMenu screen, int x, int y, int width, int height, Font font) {
                return widgetConstructor.apply(config, pg, screen, x, y, width, height, font);
            }
        };
    }

}
