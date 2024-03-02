package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Not<E extends AbstractCondition<?>> implements AbstractCondition<Not<?>> {

    public static final Codec<Not<?>> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(AbstractCondition.CODEC.fieldOf("inverted").forGetter((cond) -> cond.condition))
                    .apply(condition, Not::new));

    protected final E condition;




    public Not(E condition) {
        this.condition = condition;
    }

    @Override
    public boolean isConditionMet() {
        return !this.condition.isConditionMet();
    }

    @Override
    public Codec<Not<?>> codec() {
        return ConditionSerializers.NOT.get();
    }

    @Override
    public String text() {
        return "Not{" +
                "condition='" + this.condition.text() + '\'' +
                '}';
    }
}
