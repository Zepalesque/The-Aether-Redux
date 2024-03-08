package net.zepalesque.redux.api.condition;

import com.aetherteam.aether.data.ConfigSerializationUtil;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.common.ForgeConfigSpec;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.util.ReduxConfigSerialization;

import java.util.Map;

/**
 *  Must use a {@link ForgeConfigSpec.ConfigValue}<{@link Boolean}> that is either a part of {@link com.aetherteam.aether.AetherConfig.Common} or {@link com.aetherteam.aether.AetherConfig.Common}
 */
public class ReduxConfigCondition implements AbstractCondition<ReduxConfigCondition> {

    public static final Codec<ReduxConfigCondition> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("config_path").forGetter((config) -> ReduxConfigSerialization.serialize(config.config)))
                    .apply(condition, ReduxConfigCondition::fromText));

//    private static final Map<String, ForgeConfigSpec.ConfigValue<Boolean>> MAP = new ImmutableMap.Builder<String, ForgeConfigSpec.ConfigValue<Boolean>>()
//            .put()

    protected final ForgeConfigSpec.ConfigValue<Boolean> config;

    public static ReduxConfigCondition fromText(String config) {
        return new ReduxConfigCondition(ReduxConfigSerialization.deserialize(config));
    }
    public ReduxConfigCondition(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    public boolean isConditionMet() {
        if (this.config == null) {
            Redux.LOGGER.warn("Config Condition value was null! Printing stack trace...");
            Thread.dumpStack();
        }
        return this.config != null && this.config.get();
    }

    @Override
    public Codec<ReduxConfigCondition> codec() {
        return ConditionSerializers.REDUX_CONFIG.get();
    }

    @Override
    public String text() {
        return "ReduxConfigCondition{" +
                "config='" + config.toString() + '\'' +
                '}';
    }
}
