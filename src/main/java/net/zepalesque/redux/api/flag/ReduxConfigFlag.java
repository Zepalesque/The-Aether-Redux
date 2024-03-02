package net.zepalesque.redux.api.flag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.common.ForgeConfigSpec;
import net.zepalesque.redux.config.util.ReduxConfigSerialization;

/**
 *  Must use a {@link ForgeConfigSpec.ConfigValue}<{@link Boolean}> that is either a part of {@link com.aetherteam.aether.AetherConfig.Common} or {@link com.aetherteam.aether.AetherConfig.Common}
 */
public class ReduxConfigFlag implements DataFlag<ReduxConfigFlag> {

    public static final Codec<ReduxConfigFlag> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("config_path").forGetter((config) -> ReduxConfigSerialization.serialize(config.config)))
                    .apply(condition, ReduxConfigFlag::fromText));

    protected final ForgeConfigSpec.ConfigValue<Boolean> config;

    public static ReduxConfigFlag fromText(String config) {
        return new ReduxConfigFlag(ReduxConfigSerialization.deserialize(config));
    }
    public ReduxConfigFlag(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    public boolean test() {
        return this.config.get();
    }

    @Override
    public Codec<ReduxConfigFlag> codec() {
        return FlagSerializers.REDUX_CONFIG.get();
    }

    @Override
    public String text() {
        return "ReduxConfigCondition{" +
                "config='" + config.toString() + '\'' +
                '}';
    }
}
