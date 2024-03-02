package net.zepalesque.redux.api.flag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Not<E extends DataFlag<?>> implements DataFlag<Not<?>> {

    public static final Codec<Not<?>> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(DataFlag.CODEC.fieldOf("inverted").forGetter((cond) -> cond.condition))
                    .apply(condition, Not::new));

    protected final E condition;




    public Not(E condition) {
        this.condition = condition;
    }

    @Override
    public boolean test() {
        return !this.condition.test();
    }

    @Override
    public Codec<Not<?>> codec() {
        return FlagSerializers.NOT.get();
    }

    @Override
    public String text() {
        return "Not{" +
                "condition='" + this.condition.text() + '\'' +
                '}';
    }
}
