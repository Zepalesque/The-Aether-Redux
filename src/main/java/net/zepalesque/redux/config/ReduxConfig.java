package net.zepalesque.redux.config;

import com.google.gson.JsonSyntaxException;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class ReduxConfig {

    public static class Common {

        public final ModConfigSpec.ConfigValue<Boolean> placeholder;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("TODO");
            placeholder = builder
                    .comment("Temporary placeholder config, used")
                    .define("Placeholder Config", true);
            builder.pop();
        }

        public static String serialize(ModConfigSpec.ConfigValue<Boolean> config) {
            try {
                return config.getPath().toString();
            } catch (NullPointerException e) {
                throw new JsonSyntaxException("Error loading config entry from JSON! Maybe the config key is incorrect?");
            }
        }

        public static ModConfigSpec.ConfigValue<Boolean> deserialize(String string) {
            List<String> path = Arrays.asList(string.replace("[", "").replace("]", "").split(", "));
            ModConfigSpec.ConfigValue<Boolean> config =   COMMON_SPEC.getValues().get(path);

            return config;
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

}
