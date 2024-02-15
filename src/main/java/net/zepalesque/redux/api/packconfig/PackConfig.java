package net.zepalesque.redux.api.packconfig;

import com.google.gson.JsonElement;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;
import net.zepalesque.redux.api.serialization.client.WidgetMappers;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * A configuration setting, these can be used for various things
 * The main usage being the resource pack config, as Forge/NeoForge's config system does not support loading the config file early enough for my needs.
 * @param <T>
 */
public class PackConfig<T> implements IConfigSaving, Supplier<T> {

    /** The id of the config */
    private final String id;

    /** Whether or not the config should display a comment */
    private boolean hasComment = false;

    /** The parent category of this config */
    private final @Nullable Category parent;

    /** The {@link WidgetMapper} that should be used to serialize and deserialize this config */
    private final WidgetMapper<T> mapper;

    /** The value assigned to this config */
    private T value;

    /** The default value for this config */
    private final T defVal;



    PackConfig(String id, WidgetMapper<T> mapper, T defVal, @Nullable Category parent)
    {
        this.id = id;
        this.parent = parent;
        this.mapper = mapper;
        this.value = defVal;
        this.defVal = defVal;
    }

    /** Returns whether or not the config should display a comment */
    public boolean hasComment() {
        return this.hasComment;
    }

    /** Marks the config as one with a comment */
    public void markComment() {
        this.hasComment = true;
    }

    /** Returns the config's value */
    public T get() {
        return value;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public JsonElement write() {
        return this.mapper().encode(this.value);
    }

    @Override
    public void read(JsonElement json) {
        this.mapper().decode(json).ifPresent(this::setVal);
    }

    /** Sets the value of this config. Only used internally. */
    private void setVal(T val)
    {
        this.value = val;
    }

    /** Sets the value of this config, then recursively saves it. */
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

    /** Returns the {@link WidgetMapper} that should be used to serialize or deserialize this config. */
    public WidgetMapper<T> mapper()
    {
        return this.mapper;
    }

    @Override
    public @Nullable Category parent() {
        return this.parent;
    }

    /** Attempts to set this config from a {@link String} */
    public boolean parseString(String s) {
        Optional<T> optional = this.mapper().read(s);
        if (optional.isPresent()) {
            T obj = optional.get();
            return this.set(obj);
        }
        return false;
    }

    /** Ensures the given {@link String} can be deserialized. */
    public boolean validate(String s)
    {
        return this.mapper().read(s).isPresent();
    }

    /** Returns the value, serialized to a {@link String} */
    public String asText()
    {
        return this.mapper().write(this.value);
    }

    /** Builder class. Allows the easy construction of a config with easily-tweakable values */
    public static class Builder {

        /** Current category the builder is in */
        Category current;

        /** The uppermost category or root of the current category structure */
        final Category base;

        /** Whether or not the config should have a comment */
        boolean hasComment;

        Builder(Category base) {
            this.current = base;
            this.base = base;
        }

        public Builder comment() {
            this.hasComment = true;
            return this;
        }

        /** Makes the builder go up a level, to the above category */
        public Builder pop() {
            Category popTo = this.current.parent();
            if (popTo != null) {
                this.current = popTo;
            } else {
                Redux.LOGGER.warn("Already at top level, cannot pop config builder!");
            }
            return this;
        }

        /** Makes the builder go up a specified number of levels */
        public Builder pop(int amount) {
            if (amount < 0) { Redux.LOGGER.warn("Cannot pop a negative amount! Use the push method instead!"); return this; }
            if (amount == 0) { Redux.LOGGER.warn("Popping zero times does nothing!"); return this; }
            for (int i = 0; i < amount; i++)
            {
                this.pop();
            }
            return this;
        }

        /** Creates a new category, or enters an existing one */
        public Builder push(String id) {
            this.current = this.current.getOrCreate(id);
            return this;
        }

        /** Creates new categories, or enters existing ones */
        public Builder push(String... ids) {
            for (String id : ids)
            {
                this.push(id);
            }
            return this;
        }

        /** Creates a {@link PackConfig}. This may be removed in a future update, as the {@code possibleValues} parameter does nothing. */
        public <T> PackConfig<T> cfg(String id, T defVal, WidgetMapper<T> codec, @Nullable String possibleValues) {
            PackConfig<T> config = new PackConfig<>(id, codec, defVal, this.current);
            if (this.hasComment) {
                config.markComment();
            }
            this.hasComment = false;
            this.current.addMember(config);
            return config;
        }
        /** Creates a {@link PackConfig} */
        public <T> PackConfig<T> cfg(String id, T defVal, WidgetMapper<T> codec) {
            return this.cfg(id, defVal, codec, null);
        }

        /** Creates a {@link PackConfig} using a boolean value */
        public  PackConfig<Boolean> cfg(String id, boolean defVal) {
            return this.cfg(id, defVal, WidgetMappers.BOOL);
        }
    }
}
