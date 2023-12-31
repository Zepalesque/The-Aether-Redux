package net.zepalesque.redux.api.packconfig;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;

public interface IConfigSaving {

    String id();

    JsonElement write();

    void read(JsonElement json);

    @Nullable Category parent();
}
