package net.zepalesque.redux.util.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.mixin.common.registry.MappedGetterAccessor;
import net.zepalesque.redux.util.holder.HolderUtil;
import net.zepalesque.redux.util.holder.KeyHolder;

import java.util.Optional;
public class OptionalHolderCodec<E> implements Codec<Holder<E>> {
    private final ResourceKey<? extends Registry<E>> registryKey;

    public static <E> OptionalHolderCodec<E> create(ResourceKey<? extends Registry<E>> registryKey) {
        return new OptionalHolderCodec<>(registryKey);
    }

    private OptionalHolderCodec(ResourceKey<? extends Registry<E>> registryKey) {
        this.registryKey = registryKey;
    }

    @Override
    public <T> DataResult<T> encode(Holder<E> input, DynamicOps<T> ops, T prefix) {
        if (ops instanceof RegistryOps<T> registryops) {
            Optional<HolderOwner<E>> optOwn = registryops.owner(this.registryKey);
            if (optOwn.isPresent()) {
                if (!input.canSerializeIn(optOwn.get())) {
                    return DataResult.error(() -> "Element " + input + " is not valid in current registry set");
                }

                if (input.kind() != Holder.Kind.DIRECT) {
                    Optional<DataResult<T>> result = HolderUtil.unwrapKey(input).map(key -> ResourceLocation.CODEC.encode(key.location(), ops, prefix));
                    if (result.isPresent()) {
                        return result.get();
                    }
                } else {
                    return DataResult.error(() -> "Cannot encode direct holder: " + input);
                }

            }
            return DataResult.error(() -> "Failed to encode key for element " + input);
        }
        return DataResult.error(() -> "Cannot encode holder with non-registry ops!");
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> ops, T value) {
        if (ops instanceof RegistryOps<T> registryops) {
            Optional<HolderGetter<E>> optGet = registryops.getter(this.registryKey);
            Optional<HolderOwner<E>> optOwn = registryops.owner(this.registryKey);
            return ResourceLocation.CODEC.decode(ops, value).flatMap(pair -> {
                Holder<E> holder;
                ResourceLocation loc = pair.getFirst();
                ResourceKey<E> key = ResourceKey.create(this.registryKey, loc);
                if (optGet.isPresent()) {
                    HolderGetter<E> getter = optGet.get();
                    if (getter.getClass().getName().equals("net.minecraft.core.MappedRegistry$2") && ((MappedGetterAccessor<E>) getter).zenith$registry().containsKey(key)) {
                        holder = getter.getOrThrow(key);
                        return DataResult.success(Pair.of(holder, pair.getSecond()));
                    }
                }
                holder = optOwn.map(owner -> KeyHolder.create(key, owner)).orElseGet(() -> KeyHolder.create(key));
                return DataResult.success(Pair.of(holder, pair.getSecond()));
            });
        }
        return DataResult.error(() -> "Failed to decode holder from value " + value);
    }
}
