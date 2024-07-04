package net.zepalesque.redux.util.holder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.zepalesque.redux.Redux;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface RegistryMap<K, V> {
    static <K, V> Codec<RegistryMap<K, V>> codec(ResourceKey<? extends Registry<K>> registry, Codec<V> valueCodec) {
        return new RegistryMapCodec<>(registry, valueCodec);
    }

    Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap();

    Map<ResourceKey<K>, V> keyMap();

    Optional<Map<Holder<K>, V>> holderMap();

    default Map<Holder<K>, V> holderMapOrThrow() {
        return this.holderMap().orElseThrow();
    }

    default V getOrThrow(Holder<K> key) {
        Preconditions.checkState(this.containsKey(key), "RegistryMap does not contain value {}!", key);
        return this.holderMap().orElseThrow().get(key);
    }
    default Optional<V> get(Holder<K> key) {
        return this.containsKey(key) ? Optional.of(this.getOrThrow(key)) : Optional.empty();
    }

    default boolean containsKey(Holder<K> holder) {
        return this.holderMap().isPresent() && this.holderMap().get().containsKey(holder) || holder.unwrapKey().map(this.keyMap()::containsKey).orElse(false);
    }

    default boolean containsValue(V val) {
        return this.holderMap().isPresent() && this.holderMap().get().containsValue(val) || this.encodeMap().containsValue(val);
    }

    public Registered<K, V> asRegistered(HolderLookup<K> lookup);

    class Keyed<K, V>  implements RegistryMap<K, V> {
        protected final Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap;
        protected final Map<ResourceKey<K>, V> keyMap;

        Keyed(Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap, HolderGetter<K> getter) {
            this.encodeMap = encodeMap;
            this.keyMap = RegistryMap.createKeyMap(getter, encodeMap);
        }

        @Override
        public Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap() {
            return this.encodeMap;
        }

        @Override
        public Map<ResourceKey<K>, V> keyMap() {
            return this.keyMap;
        }

        @Override
        public Optional<Map<Holder<K>, V>> holderMap() {
            return Optional.empty();
        }

        @Override
        public Registered<K, V> asRegistered(HolderLookup<K> lookup) {
            return new Registered<>(this.encodeMap, lookup);
        }
    }

    class Registered<K, V> extends Keyed<K, V> {

        private final Map<Holder<K>, V> holderMap;
        Registered(Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap, HolderLookup<K> lookup) {
            super(keyMap, lookup);
            this.holderMap = this.keyMap.entrySet().stream().flatMap(entry -> Stream.ofNullable(lookup.get(entry.getKey()).map(holder -> Pair.of(holder, entry.getValue())).orElse(null))).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        }
         @Override
         public Optional<Map<Holder<K>, V>> holderMap() {
             return Optional.of(this.holderMap);
         }

         @Override
         public Map<Holder<K>, V> holderMapOrThrow() {
             return this.holderMap;
         }

         @Override
         public Registered<K, V> asRegistered(HolderLookup<K> lookup) {
             return this;
         }
     }



    static <K, V> Map<ResourceKey<K>, V> createKeyMap(HolderGetter<K> registry, Map<Either<TagKey<K>, ResourceKey<K>>, V> keys) {
        Map<ResourceKey<K>, V> map = new HashMap<>();
        var entrySet = keys.entrySet();
        var tagValues = entrySet.stream().filter(entry -> entry.getKey().map(tag -> true, rk -> false)).map(entry -> Pair.of(entry.getKey().left().orElseThrow(), entry.getValue())).collect(Collectors.toSet());
        var keyValues = entrySet.stream().filter(entry -> entry.getKey().map(tag -> false, rk -> true)).map(entry -> Pair.of(entry.getKey().right().orElseThrow(), entry.getValue())).collect(Collectors.toSet());
        tagValues.forEach(pair ->
                registry.get(pair.getFirst()).ifPresent(set -> set.stream().forEach(holder -> holder.unwrapKey().ifPresent(key -> {
                if (map.containsKey(key)) {
                    logExistingFromTag(key, pair.getFirst(), map.get(key), pair.getSecond());
                }
                map.put(key, pair.getSecond());
                }))));
        keyValues.forEach(pair -> {
            ResourceKey<K> key = pair.getFirst();
            if (map.containsKey(key)) {
                logExisting(key, map.get(key), pair.getSecond());
            }
            map.put(key, pair.getSecond());
        });
        return ImmutableMap.copyOf(map);
    }

    private static <K, V> void logExistingFromTag(ResourceKey<K> key, TagKey<K> tag, V oldVal, V newVal) {
        Redux.LOGGER.warn("Found already existing holder {} from tag {}! Replacing value anyway! Old value was {}, new is {}",
                key.location(),
                tag.location(),
                oldVal, newVal);
    }

    private static <K, V> void logExisting(ResourceKey<K> key, V oldVal, V newVal) {
        Redux.LOGGER.warn("Found already existing holder {}! Replacing value anyway! Old value was {}, new is {}",
                key.location(),
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

        public RegistryMap<K, V> full(HolderLookup<K> lookup) {
            return new RegistryMap.Registered<>(this.builder.build(), lookup);
        }

        public RegistryMap<K, V> build(HolderGetter<K> getter) {
            return new RegistryMap.Keyed<>(this.builder.build(), getter);
        }

    }
}
