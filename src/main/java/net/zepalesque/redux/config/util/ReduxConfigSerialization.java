package net.zepalesque.redux.config.util;

import com.google.gson.JsonSyntaxException;
import net.minecraftforge.common.ForgeConfigSpec;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.Arrays;
import java.util.List;

public class ReduxConfigSerialization {

    public static String serialize(ForgeConfigSpec.ConfigValue<Boolean> config) {
        try {
            return config.getPath().toString();
        } catch (NullPointerException e) {
            throw new JsonSyntaxException("Error loading config entry from JSON! Maybe the config key is incorrect?");
        }
    }

    public static ForgeConfigSpec.ConfigValue<Boolean> deserialize(String string) {
        List<String> path = Arrays.asList(string.replace("[", "").replace("]", "").split(", "));
        ForgeConfigSpec.ConfigValue<Boolean> config = ReduxConfig.COMMON_SPEC.getValues().get(path);

        return config;
    }
}
