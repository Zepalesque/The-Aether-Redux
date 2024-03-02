package net.zepalesque.redux.api.flag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class And<E extends DataFlag<?>, T extends DataFlag<?>> implements DataFlag<And<?, ?>> {

    public static final Codec<And<?, ?>> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(DataFlag.CODEC.fieldOf("arg1").forGetter((cond) -> cond.arg1),
                            DataFlag.CODEC.fieldOf("arg2").forGetter((cond) -> cond.arg2))
                    .apply(condition, And::new));

    protected final E arg1;
    protected final T arg2;




    public And(E arg1, T arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public boolean test() {
        return this.arg1.test() && this.arg2.test();
    }

    @Override
    public Codec<And<?, ?>> codec() {
        return FlagSerializers.AND.get();
    }

    @Override
    public String text() {
        return "And{" +
                "arg1='" + this.arg1.text() + "'," +
                "arg2='" + this.arg2.text() + "'" +
                "}";
    }
}
