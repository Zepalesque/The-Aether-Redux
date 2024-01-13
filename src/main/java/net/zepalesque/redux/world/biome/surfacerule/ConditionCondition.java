package net.zepalesque.redux.world.biome.surfacerule;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.zepalesque.redux.api.condition.AbstractCondition;

public class ConditionCondition<E extends AbstractCondition<E>> implements SurfaceRules.ConditionSource {
        public static final KeyDispatchDataCodec<ConditionCondition<?>> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.create((condition) ->
            condition.group(AbstractCondition.CODEC.fieldOf("data_condition").forGetter((cond) -> cond.condition))
                    .apply(condition, ConditionCondition::new)));
    public final AbstractCondition<E> condition;

    public ConditionCondition(AbstractCondition<E> condition) {
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