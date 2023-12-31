package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.common.ForgeConfigSpec;
import net.zepalesque.redux.config.util.ReduxConfigSerialization;

public class ReduxConfigCondition implements AbstractCondition<ReduxConfigCondition> {

    public static final Codec<ReduxConfigCondition> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("config_path").forGetter((config) -> config.configPath))
                    .apply(condition, ReduxConfigCondition::new));

    protected final String configPath;



    public static ReduxConfigCondition of(ForgeConfigSpec.ConfigValue<Boolean> config)
    {
        return new ReduxConfigCondition(ReduxConfigSerialization.serialize(config));
    }


    public ReduxConfigCondition(String configPath) {
        this.configPath = configPath;
    }

    @Override
    public boolean isConditionMet() {
        return ReduxConfigSerialization.deserialize(this.configPath).get();
    }

    @Override
    public Codec<ReduxConfigCondition> codec() {
        return ConditionSerializers.REDUX_CONFIG.get();
    }

    @Override
    public String text() {
        return "ReduxConfigCondition{" +
                "configPath='" + configPath + '\'' +
                '}';
    }
}
