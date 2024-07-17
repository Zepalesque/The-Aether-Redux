package net.zepalesque.redux.util.holder;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.Map;
import java.util.Optional;

public class RegistryMapCodec<K, V> implements Codec<RegistryMap<K, V>> {

    private final Codec<Map<Either<TagKey<K>, ResourceKey<K>>, V>> codec;
    private final ResourceKey<? extends Registry<K>> key;

    RegistryMapCodec(ResourceKey<? extends Registry<K>> registry, Codec<V> valueCodec) {
        Codec<Either<TagKey<K>, ResourceKey<K>>> eitherCodec = Codec.either(TagKey.hashedCodec(registry), ResourceKey.codec(registry));
        this.codec = Codec.unboundedMap(eitherCodec, valueCodec);
        this.key = registry;
    }

    @Override
    public <T> DataResult<Pair<RegistryMap<K, V>, T>> decode(DynamicOps<T> ops, T input) {
        if (ops instanceof RegistryOps<T> regOps) {
            Optional<HolderOwner<K>> optional = regOps.owner(this.key);
            if (optional.isEmpty()) {
                return DataResult.error(() -> "Unknown registry: " + this.key);
            }
            if (optional.get() instanceof HolderLookup.RegistryLookup<K> lookup) {
                DataResult<Pair<Map<Either<TagKey<K>, ResourceKey<K>>, V>, T>> mapResult = this.codec.decode(ops, input);
                return mapResult.map(pair -> pair.mapFirst(map -> RegistryMap.createFull(map, lookup)));
            }
        }
        return DataResult.error(() -> "Not a registry ops");
    }

    @Override
    public <T> DataResult<T> encode(RegistryMap<K, V> input, DynamicOps<T> ops, T prefix) {
        return this.codec.encode(input.encodeMap(), ops, prefix);
    }
}
