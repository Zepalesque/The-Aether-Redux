package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraftforge.fml.ModList;

public class ModLoaded implements AbstractCondition<ModLoaded> {

    public static final Codec<ModLoaded> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(Codec.STRING.fieldOf("mod_id").forGetter((config) -> config.modid))
                    .apply(condition, ModLoaded::new));

    protected final String modid;

    public ModLoaded(String modid) {
        this.modid = modid;
    }

    @Override
    public boolean isConditionMet() {
        return ModList.get().isLoaded(this.modid);
    }

    @Override
    public Codec<ModLoaded> codec() {
        return ConditionSerializers.MOD_LOADED.get();
    }

    @Override
    public String text() {
        return "ModLoaded{" +
                "modid='" + modid + '\'' +
                '}';
    }
}
