package net.zepalesque.redux.world.biome.surfacerule;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.zepalesque.redux.api.condition.AbstractCondition;

public class ConditionCondition implements SurfaceRules.ConditionSource {
        public static final KeyDispatchDataCodec<ConditionCondition> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.create((condition) ->
            condition.group(AbstractCondition.CODEC.fieldOf("data_condition").forGetter((cond) -> cond.condition))
                    .apply(condition, ConditionCondition::new)));
    public final AbstractCondition<?> condition;

    public ConditionCondition(AbstractCondition condition) {
        this.condition = condition;
    }

    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.Condition apply(SurfaceRules.Context context) {
        return this.condition::isConditionMet;
    }
}