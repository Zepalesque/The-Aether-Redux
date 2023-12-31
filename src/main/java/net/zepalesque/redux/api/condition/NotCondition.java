package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class NotCondition<E extends AbstractCondition<?>> implements AbstractCondition<NotCondition<?>> {

    public static final Codec<NotCondition<?>> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(AbstractCondition.CODEC.fieldOf("inverted").forGetter((cond) -> cond.condition))
                    .apply(condition, NotCondition::new));

    protected final E condition;




    public NotCondition(E condition) {
        this.condition = condition;
    }

    @Override
    public boolean isConditionMet() {
        return !this.condition.isConditionMet();
    }

    @Override
    public Codec<NotCondition<?>> codec() {
        return ConditionSerializers.NOT_CONDITION.get();
    }

    @Override
    public String text() {
        return "NotCondition{" +
                "condition='" + this.condition.text() + '\'' +
                '}';
    }
}
