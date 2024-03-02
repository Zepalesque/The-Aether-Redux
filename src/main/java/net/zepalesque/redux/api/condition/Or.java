package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Or<E extends AbstractCondition<?>, T extends AbstractCondition<?>> implements AbstractCondition<Or<?, ?>> {

    public static final Codec<Or<?, ?>> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(AbstractCondition.CODEC.fieldOf("arg1").forGetter((cond) -> cond.arg1),
                            AbstractCondition.CODEC.fieldOf("arg2").forGetter((cond) -> cond.arg2))
                    .apply(condition, Or::new));

    protected final E arg1;
    protected final T arg2;




    public Or(E arg1, T arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public boolean isConditionMet() {
        return this.arg1.isConditionMet() || this.arg2.isConditionMet();
    }

    @Override
    public Codec<Or<?, ?>> codec() {
        return ConditionSerializers.OR.get();
    }

    @Override
    public String text() {
        return "Or{" +
                "arg1='" + this.arg1.text() + "'," +
                "arg2='" + this.arg2.text() + "'" +
                "}";
    }
}
