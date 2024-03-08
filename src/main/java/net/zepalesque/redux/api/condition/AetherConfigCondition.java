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
            condition.group(Codec.STRING.fieldOf("config_path").forGetter((config) -> ConfigSerializationUtil.serialize(config.config)))
                    .apply(condition, AetherConfigCondition::fromText));

    protected final ForgeConfigSpec.ConfigValue<Boolean> config;

    public static AetherConfigCondition fromText(String config) {
        return new AetherConfigCondition(ConfigSerializationUtil.deserialize(config));
    }
    
    public AetherConfigCondition(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    public boolean isConditionMet() {
        return this.config != null && this.config.get();
    }

    @Override
    public Codec<AetherConfigCondition> codec() {
        return ConditionSerializers.AETHER_CONFIG.get();
    }

    @Override
    public String text() {
        return "AetherConfigCondition{" +
                "config='" + config.toString() + '\'' +
                '}';
    }
}
