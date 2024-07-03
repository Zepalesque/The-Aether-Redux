package net.zepalesque.redux.util.holder;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.zepalesque.redux.Redux;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class RegistryMap<K, V> implements RegMap<K, V> {

    public static <K, V> Codec<RegistryMap<K, V>> codec(ResourceKey<? extends Registry<K>> registry, Codec<V> valueCodec) {
        return new RegistryMapCodec<>(registry, valueCodec);
    }

    private final Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap;
    private final Map<Holder<K>, V> holderMap;

    RegistryMap(HolderLookup<K> lookup, Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap) {
        this.keyMap = keyMap;
        this.holderMap = createMap(lookup, keyMap);
    }


    @Override
    public Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap() {
        return this.keyMap;
    }

    @Override
    public Map<Holder<K>, V> holderMap() {
        return this.holderMap;
    }

    private static <K, V> Map<Holder<K>, V> createMap(HolderLookup<K> registry, Map<Either<TagKey<K>, ResourceKey<K>>, V> keys) {
        Map<Holder<K>, V> map = new HashMap<>();
        var entrySet = keys.entrySet();
        var tagValues = entrySet.stream().filter(entry -> entry.getKey().map(tag -> true, rk -> false)).map(entry -> Pair.of(entry.getKey().left().orElseThrow(), entry.getValue())).collect(Collectors.toSet());
        var keyValues = entrySet.stream().filter(entry -> entry.getKey().map(tag -> false, rk -> true)).map(entry -> Pair.of(entry.getKey().right().orElseThrow(), entry.getValue())).collect(Collectors.toSet());
        tagValues.forEach(pair ->
                registry.get(pair.getFirst()).ifPresent(set -> set.stream().forEach(holder -> {
                    if (map.containsKey(holder)) {
                        logExistingFromTag(holder.unwrapKey(), pair.getFirst(), map.get(holder), pair.getSecond());
                    }
                    map.put(holder, pair.getSecond());
                })));
        keyValues.forEach(pair ->
                registry.get(pair.getFirst()).ifPresent(holder -> {
                    if (map.containsKey(holder)) {
                        logExisting(holder.unwrapKey(), map.get(holder), pair.getSecond());
                    }
                    map.put(holder, pair.getSecond());
                }));
        return ImmutableMap.copyOf(map);
    }

    private static <K, V> void logExistingFromTag(Optional<ResourceKey<K>> loc, TagKey<K> tag, V oldVal, V newVal) {
        Redux.LOGGER.warn("Found already existing holder {} from tag {}! Replacing value anyway! Old value was {}, new is {}",
                loc.map(key -> key.location().toString()).orElse("null"),
                tag.location(),
                oldVal, newVal);
    }

    private static <K, V> void logExisting(Optional<ResourceKey<K>> loc, V oldVal, V newVal) {
        Redux.LOGGER.warn("Found already existing holder {}! Replacing value anyway! Old value was {}, new is {}",
                loc.map(key -> key.location().toString()).orElse("null"),
                oldVal, newVal);
    }

    public static class Builder<K, V> {
        private final ImmutableMap.Builder<Either<TagKey<K>, ResourceKey<K>>, V> builder;

        private Builder(ImmutableMap.Builder<Either<TagKey<K>, ResourceKey<K>>, V> builder) {
            this.builder = builder;
        }

        public static <K, V> Builder<K, V> create() {
            return new Builder<>(new ImmutableMap.Builder<>());
        }

        public Builder<K, V> put(TagKey<K> key, V value) {
            this.builder.put(Either.left(key), value);
            return this;
        }

        public Builder<K, V> put(ResourceKey<K> key, V value) {
            this.builder.put(Either.right(key), value);
            return this;
        }

        public RegistryMap<K, V> build(HolderLookup<K> lookup) {
            return new RegistryMap<>(lookup, this.builder.build());
        }

    }
}
