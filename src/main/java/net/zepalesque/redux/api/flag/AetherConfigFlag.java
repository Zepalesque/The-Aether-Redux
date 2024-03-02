package net.zepalesque.redux.api.flag;

import com.aetherteam.aether.data.ConfigSerializationUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 *  Must use a {@link ForgeConfigSpec.ConfigValue}<{@link Boolean}> that is either a part of {@link com.aetherteam.aether.AetherConfig.Common} or {@link com.aetherteam.aether.AetherConfig.Common}
 */
public class AetherConfigFlag implements DataFlag<AetherConfigFlag> {

    public static final Codec<AetherConfigFlag> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("config_path").forGetter((config) -> ConfigSerializationUtil.serialize(config.config)))
                    .apply(condition, AetherConfigFlag::fromText));

    protected final ForgeConfigSpec.ConfigValue<Boolean> config;

    public static AetherConfigFlag fromText(String config) {
        return new AetherConfigFlag(ConfigSerializationUtil.deserialize(config));
    }
    
    public AetherConfigFlag(ForgeConfigSpec.ConfigValue<Boolean> config) {
        this.config = config;
    }

    @Override
    public boolean test() {
        return this.config.get();
    }

    @Override
    public Codec<AetherConfigFlag> codec() {
        return FlagSerializers.AETHER_CONFIG.get();
    }

    @Override
    public String text() {
        return "AetherConfigCondition{" +
                "config='" + config.toString() + '\'' +
                '}';
    }
}
