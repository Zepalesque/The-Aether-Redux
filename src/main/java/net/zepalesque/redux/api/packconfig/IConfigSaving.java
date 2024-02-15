package net.zepalesque.redux.api.packconfig;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;

/**
 * A configuration category or setting that can save
 */
public interface IConfigSaving {

    /** Return the serialization ID for this object */
    String id();

    /** Serializes this object to a {@link JsonElement} */
    JsonElement write();

    /** Deserializes this object from a {@link JsonElement} */
    void read(JsonElement json);

    /** Returns the parent category for this object. Can be null. */
    @Nullable Category parent();
}
