package net.zepalesque.redux.world.biome.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.zepalesque.redux.api.condition.AbstractCondition;
import net.zepalesque.redux.api.condition.ConditionSerializers;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.api.condition.NotCondition;

public class ConditionCondition<E extends AbstractCondition<E>> implements SurfaceRules.ConditionSource {
        public static final KeyDispatchDataCodec<ConditionCondition<?>> CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.create((condition) ->
            condition.group(AbstractCondition.CODEC.fieldOf("inverted").forGetter((cond) -> cond.condition))
                    .apply(condition, ConditionCondition::new)));
    public final AbstractCondition<E> condition;

    ConditionCondition(AbstractCondition<E> condition) {
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