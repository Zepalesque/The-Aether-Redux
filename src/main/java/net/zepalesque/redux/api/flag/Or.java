package net.zepalesque.redux.api.flag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Or<E extends DataFlag<?>, T extends DataFlag<?>> implements DataFlag<Or<?, ?>> {

    public static final Codec<Or<?, ?>> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(DataFlag.CODEC.fieldOf("arg1").forGetter((cond) -> cond.arg1),
                            DataFlag.CODEC.fieldOf("arg2").forGetter((cond) -> cond.arg2))
                    .apply(condition, Or::new));

    protected final E arg1;
    protected final T arg2;




    public Or(E arg1, T arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public boolean test() {
        return this.arg1.test() || this.arg2.test();
    }

    @Override
    public Codec<Or<?, ?>> codec() {
        return FlagSerializers.OR.get();
    }

    @Override
    public String text() {
        return "Or{" +
                "arg1='" + this.arg1.text() + "'," +
                "arg2='" + this.arg2.text() + "'" +
                "}";
    }
}
