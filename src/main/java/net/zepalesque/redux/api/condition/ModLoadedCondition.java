package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.fml.ModList;

public class ModLoadedCondition implements AbstractCondition<ModLoadedCondition> {

    public static final Codec<ModLoadedCondition> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("mod_id").forGetter((config) -> config.modid))
                    .apply(condition, ModLoadedCondition::new));

    protected final String modid;

    public ModLoadedCondition(String modid) {
        this.modid = modid;
    }

    @Override
    public boolean isConditionMet() {
        return ModList.get().isLoaded(this.modid);
    }

    @Override
    public Codec<ModLoadedCondition> codec() {
        return ConditionSerializers.MOD_LOADED.get();
    }

    @Override
    public String text() {
        return "ModLoadedCondition{" +
                "modid='" + modid + '\'' +
                '}';
    }
}
