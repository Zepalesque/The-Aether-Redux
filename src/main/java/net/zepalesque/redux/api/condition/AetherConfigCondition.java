package net.zepalesque.redux.api.condition;

import com.aetherteam.aether.data.ConfigSerializationUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 *  Must use a {@link ForgeConfigSpec.ConfigValue}<{@link Boolean}> that is either a part of {@link com.aetherteam.aether.AetherConfig.Common} or {@link com.aetherteam.aether.AetherConfig.Common}
 */
public class AetherConfigCondition implements AbstractCondition<AetherConfigCondition> {

    public static final Codec<AetherConfigCondition> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("config_path").forGetter((config) -> config.configPath))
                    .apply(condition, AetherConfigCondition::new));

    protected final String configPath;



    public static AetherConfigCondition fromConfig(ForgeConfigSpec.ConfigValue<Boolean> config)
    {
        return new AetherConfigCondition(ConfigSerializationUtil.serialize(config));
    }


    public AetherConfigCondition(String configPath) {
        this.configPath = configPath;
    }

    @Override
    public boolean isConditionMet() {
        return ConfigSerializationUtil.deserialize(this.configPath).get();
    }

    @Override
    public Codec<AetherConfigCondition> codec() {
        return ConditionSerializers.AETHER_CONFIG.get();
    }

    @Override
    public String text() {
        return "AetherConfigCondition{" +
                "configPath='" + configPath + '\'' +
                '}';
    }
}
