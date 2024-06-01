package net.zepalesque.redux.config;

import com.google.gson.JsonSyntaxException;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

public class DataSerializableConfig {

    protected final ModConfigSpec spec;
    protected final String id;

    public DataSerializableConfig(ModConfigSpec spec, String id) {
        this.spec = spec;
        this.id = id;
    }

    public String serialize(ModConfigSpec.ConfigValue<Boolean> config) {
        try {
            return config.getPath().toString();
        } catch (NullPointerException e) {
            throw new JsonSyntaxException("Error loading config entry from JSON! Maybe the config key is incorrect?");
        }
    }

    public ModConfigSpec.ConfigValue<Boolean> deserialize(String string) {
        List<String> path = Arrays.asList(string.replace("[", "").replace("]", "").split(", "));

        return this.spec().getValues().get(path);
    }

    public ModConfigSpec spec() {
        return spec;
    }

    public String serializerID() {
        return this.id;
    }
}
