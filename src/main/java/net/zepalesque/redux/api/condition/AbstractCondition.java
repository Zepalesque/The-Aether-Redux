package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface AbstractCondition<T extends AbstractCondition<?>> {

    Codec<AbstractCondition<?>> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ConditionSerializers.CONDITION_SERIALIZER_REGISTRY.get().getCodec().dispatch("condition", AbstractCondition::codec, Function.identity()));


    boolean isConditionMet();

    Codec<T> codec();

    String text();

}
