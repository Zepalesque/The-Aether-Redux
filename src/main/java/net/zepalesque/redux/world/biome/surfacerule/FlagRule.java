package net.zepalesque.redux.world.biome.surfacerule;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.zepalesque.redux.api.flag.DataFlag;

public class FlagRule implements SurfaceRules.ConditionSource {
        public static final KeyDispatchDataCodec<FlagRule> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.create((condition) ->
            condition.group(DataFlag.CODEC.fieldOf("flag").forGetter((cond) -> cond.condition))
                    .apply(condition, FlagRule::new)));
    public final DataFlag<?> condition;

    public FlagRule(DataFlag condition) {
        this.condition = condition;
    }

    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.Condition apply(SurfaceRules.Context context) {
        return this.condition::test;
    }
}