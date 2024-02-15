package net.zepalesque.redux.api.packconfig;

import com.google.gson.JsonElement;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;
import net.zepalesque.redux.api.serialization.client.WidgetMappers;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class PackConfig<T> implements IConfigSaving, Supplier<T> {

    private final String id;
    private boolean hasComment = false;
    private final @Nullable Category parent;

    private final WidgetMapper<T> converter;
    private T value;
    private final T defVal;



    PackConfig(String id, WidgetMapper<T> converter, T defVal, @Nullable Category parent)
    {
        this.id = id;
        this.parent = parent;
        this.converter = converter;
        this.value = defVal;
        this.defVal = defVal;
    }

    public boolean hasComment() {
        return this.hasComment;
    }

    public void markComment() {
        this.hasComment = true;
    }

    public T get() {
        return value;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public JsonElement write() {
        return this.converter().encode(this.value);
    }

    @Override
    public void read(JsonElement json) {
        this.converter().decode(json).ifPresent(this::setVal);
    }

    private void setVal(T val)
    {
        this.value = val;
    }

    public boolean set(T val)
    {
        if (this.value != val) {
            this.setVal(val);
            if (this.parent != null) {
                this.parent.save();
            }
            return true;
        }
        return false;
    }



    public WidgetMapper<T> converter()
    {
        return this.converter;
    }

    @Override
    public @Nullable Category parent() {
        return this.parent;
    }

    public boolean parseString(String s) {
        Optional<T> optional = this.converter().read(s);
        if (optional.isPresent()) {
            T obj = optional.get();
            return this.set(obj);
        }
        return false;
    }

    public boolean validate(String s)
    {
        return this.converter().read(s).isPresent();
    }

    public String asText()
    {
        return this.converter().write(this.value);
    }

    public static class Builder {


        Category current;
        final Category base;
        boolean hasComment;

        Builder(Category base) {
            this.current = base;
            this.base = base;
        }

        public Builder comment() {
            this.hasComment = true;
            return this;
        }

        public Builder pop() {
            Category popTo = this.current.parent();
            if (popTo != null) {
                this.current = popTo;
            } else {
                Redux.LOGGER.warn("Already at top level, cannot pop config builder!");
            }
            return this;
        }

        public Builder pop(int amount) {
            if (amount < 0) { Redux.LOGGER.warn("Cannot pop a negative amount! Use the push method instead!"); return this; }
            if (amount == 0) { Redux.LOGGER.warn("Popping zero times does nothing!"); return this; }
            for (int i = 0; i < amount; i++)
            {
                this.pop();
            }
            return this;
        }

        public Builder push(String id) {
            this.current = this.current.getOrCreate(id);
            return this;
        }

        public Builder push(String... ids) {
            for (String id : ids)
            {
                this.push(id);
            }
            return this;
        }

        public <T> PackConfig<T> cfg(String id, T defVal, WidgetMapper<T> codec, @Nullable String possibleValues) {
            PackConfig<T> config = new PackConfig<>(id, codec, defVal, this.current);
            if (this.hasComment) {
                config.markComment();
            }
            this.hasComment = false;
            this.current.addMember(config);
            return config;
        }
        public <T> PackConfig<T> cfg(String id, T defVal, WidgetMapper<T> codec) {
            return this.cfg(id, defVal, codec, null);
        }

        public  PackConfig<Boolean> cfg(String id, boolean defVal) {
            return this.cfg(id, defVal, WidgetMappers.BOOL);
        }
    }
}
