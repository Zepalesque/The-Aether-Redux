package net.zepalesque.redux.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

public class CommonJsonUtil {

    public static <T> T getRequired(String key, JsonObject json, Function<JsonObject, T> output) {
        JsonElement jsonelement = json.get(key);
        if (jsonelement != null && !jsonelement.isJsonNull()) {
            if (!jsonelement.isJsonObject()) {
                throw new IllegalStateException("Required property " + key + " was not a JsonObject as espected");
            } else {
                return output.apply(jsonelement.getAsJsonObject());
            }
        } else {
            throw new IllegalStateException("Missing required property: " + key);
        }
    }

    public static String getRequiredString(String key, JsonObject json) {
        String s = getStringOr(key, json, null);
        if (s == null) {
            throw new IllegalStateException("Missing required property: " + key);
        } else {
            return s;
        }
    }

    @Nullable
    public static String getStringOr(String key, JsonObject json, @Nullable String defaultValue) {
        JsonElement jsonelement = json.get(key);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? defaultValue : jsonelement.getAsString();
        } else {
            return defaultValue;
        }
    }

    @Nullable
    public static UUID getUuidOr(String key, JsonObject json, @Nullable UUID defaultValue) {
        String s = getStringOr(key, json, null);
        return s == null ? defaultValue : UUID.fromString(s);
    }

    public static int getIntOr(String key, JsonObject json, int defaultValue) {
        JsonElement jsonelement = json.get(key);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? defaultValue : jsonelement.getAsInt();
        } else {
            return defaultValue;
        }
    }

    public static long getLongOr(String key, JsonObject json, long defaultValue) {
        JsonElement jsonelement = json.get(key);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? defaultValue : jsonelement.getAsLong();
        } else {
            return defaultValue;
        }
    }

    public static boolean getBooleanOr(String key, JsonObject json, boolean defaultValue) {
        JsonElement jsonelement = json.get(key);
        if (jsonelement != null) {
            return jsonelement.isJsonNull() ? defaultValue : jsonelement.getAsBoolean();
        } else {
            return defaultValue;
        }
    }

    public static Date getDateOr(String key, JsonObject json) {
        JsonElement jsonelement = json.get(key);
        return jsonelement != null ? new Date(Long.parseLong(jsonelement.getAsString())) : new Date();
    }


}
