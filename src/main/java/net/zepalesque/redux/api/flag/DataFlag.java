package net.zepalesque.redux.api.flag;

import com.mojang.serialization.Codec;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface DataFlag<T extends DataFlag<?>> {

    Codec<DataFlag<?>> CODEC = ExtraCodecs.lazyInitializedCodec(() -> FlagSerializers.REGISTRY.get().getCodec().dispatch("data_flag", DataFlag::codec, Function.identity()));


    boolean test();

    Codec<T> codec();

    String text();

}
