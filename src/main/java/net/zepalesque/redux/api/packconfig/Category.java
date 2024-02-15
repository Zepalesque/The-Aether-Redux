package net.zepalesque.redux.api.packconfig;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/** A category that can contain {@link IConfigSaving}s such as other categories or {@link PackConfig}s */
public class Category implements IConfigSaving {

    /** This category's ID */
    private final String id;

    /** The parent category. Can be null, but should only be null if this is the root directory. */
    private final @Nullable Category parent;

    /** The members of this category, such as other categories or configuration. */
    public Map<String, IConfigSaving> members = Maps.newHashMap();


    Category(String id, @Nullable Category parent)
    {
        this.id = id;
        this.parent = parent;
    }

    /** Adds a new member to this category */
    public void addMember(IConfigSaving member) {
        this.members.put(member.id(), member);
    }

    @Override
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

    /** Saves this category to the disk */
    public void save()
    {
        PackConfigBootstrap.write(this.getRoot().id(), this.getRoot().write());
    }

    /** Returns the root category for this object */
    public Category getRoot()
    {
        return Objects.requireNonNullElse(this.parent, this);
    }

    /** Creates a new sub-category, or returns one if one with the given ID exists. */
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

    /** Returns a new {@link PackConfig.Builder} for this category. Should only be used in a root category. */
    public PackConfig.Builder builder()
    {
        if (this.parent() != null) {
            Redux.LOGGER.warn("Attempting to create builder for non-root config collection!");
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
