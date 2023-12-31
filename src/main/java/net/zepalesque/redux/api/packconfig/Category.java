package net.zepalesque.redux.api.packconfig;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class Category implements IConfigSaving {

    private final String id;
    private final @Nullable Category parent;

    public Map<String, IConfigSaving> members = Maps.newHashMap();


    Category(String id, @Nullable Category parent)
    {
        this.id = id;
        this.parent = parent;
    }

    public void addMember(IConfigSaving member) {
        this.members.put(member.id(), member);
    }

    public @Nullable Category parent() {
        return parent;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public JsonElement write() {
        JsonObject json = new JsonObject();
        for (Map.Entry<String, IConfigSaving> entry : this.members.entrySet()) {
            json.add(entry.getKey(), entry.getValue().write());
        }
        return json;
    }
    public void save()
    {
        PackConfigBootstrap.write(this.getHighest().id(), this.getHighest().write());
    }

    public Category getHighest()
    {
        return Objects.requireNonNullElse(this.parent, this);
    }

    public Category getOrCreate(String id)
    {
        if (this.members.containsKey(id))
        {
            IConfigSaving val = this.members.get(id);
            if (val instanceof Category coll) {
                return coll;
            } else {
                throw new ClassCastException("Cannot convert a PackCfg to a CfgCollection!");
            }
        } else {
            Category collection = new Category(id, this);
            this.members.put(id, collection);
            return collection;
        }
    }

    public PackConfig.Builder builder()
    {
        if (this.parent() != null) {
            Redux.LOGGER.warn("Attempting to create builder for non-base config collection!");
        }
        return new PackConfig.Builder(this);
    }

    @Override
    public void read(JsonElement json) {
        if (json instanceof JsonObject obj) {
            for (Map.Entry<String, IConfigSaving> entry : this.members.entrySet()) {
                if (obj.has(entry.getKey())) {
                    JsonElement val = obj.get(entry.getKey());
                    entry.getValue().read(val);
                }
            }
        }
    }
}
