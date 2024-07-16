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
import net.minecraft.world.item.EggItem;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.Redux;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface RegistryMap<K, V> {
    static <K, V> Codec<RegistryMap<K, V>> codec(ResourceKey<? extends Registry<K>> registry, Codec<V> valueCodec) {
        return new RegistryMapCodec<>(registry, valueCodec);
    }

    Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap();

    LazyOptional<Map<ResourceKey<K>, V>> keyMap();

    LazyOptional<Map<Holder<K>, V>> holderMap();

    default Map<Holder<K>, V> holderMapOrThrow() {
        return this.holderMap().orElseThrow(RegistryMap::exception);
    }

    default V getOrThrow(Holder<K> key) {
        Preconditions.checkState(this.containsKey(key), "RegistryMap does not contain value {}!", key);
        return this.holderMap().orElseThrow(RegistryMap::exception).get(key);
    }
    default Optional<V> get(Holder<K> key) {
        return this.containsKey(key) ? Optional.of(this.getOrThrow(key)) : Optional.empty();
    }

    // wacko logic
    default boolean containsKey(Holder<K> holder) {
        return this.holderMap().map(map -> map.containsKey(holder)).orElse(false) ||
                this.keyMap().map(map -> holder.unwrapKey().map(map::containsKey).orElse(false)).orElse(false);
    }

    default boolean containsValue(V val) {
        return this.holderMap().map(map -> map.containsValue(val)).orElse(false) || this.encodeMap().containsValue(val);
    }

    Registered<K, V> asRegistered(HolderLookup<K> lookup);


    void initialize();

    class Keyed<K, V>  implements RegistryMap<K, V> {
        protected final Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap;
        protected final LazyOptional<Map<ResourceKey<K>, V>> keyMap;
        protected final HolderGetter<K> getter;

        private Keyed(Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap, HolderGetter<K> getter) {
            this.encodeMap = encodeMap;
            this.getter = getter;
            this.keyMap = LazyOptional.of(() -> RegistryMap.createKeyMap(getter, encodeMap));
        }

        @Override
        public Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap() {
            return this.encodeMap;
        }

        @Override
        public LazyOptional<Map<ResourceKey<K>, V>> keyMap() {
            this.initialize();
            return this.keyMap;
        }

        @Override
        public LazyOptional<Map<Holder<K>, V>> holderMap() {
            return LazyOptional.empty();
        }

        @Override
        public Registered<K, V> asRegistered(HolderLookup<K> lookup) {
            return new Registered<>(this.encodeMap, lookup);
        }

        @Override
        public void initialize() {
            if (this.keyMap == null) {
            }
        }
    }

    class Registered<K, V> extends Keyed<K, V> {

        private final LazyOptional<Map<Holder<K>, V>> holderMap;
        private final HolderLookup<K> getterAsLookup;
        private Registered(Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap, HolderLookup<K> lookup) {
            super(keyMap, lookup);
            this.getterAsLookup = lookup;
            this.holderMap = this.keyMap.lazyMap(keys -> keys.entrySet().stream()
                            .flatMap(entry -> Stream.ofNullable(getterAsLookup.get(entry.getKey()).map(holder -> Pair.of(holder, entry.getValue())).orElse(null)))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
        }
         @Override
         public LazyOptional<Map<Holder<K>, V>> holderMap() {
             return this.holderMap;
         }

         @Override
         public Map<Holder<K>, V> holderMapOrThrow() {
             return this.holderMap().orElseThrow(RegistryMap::exception);
         }

         @Override
         public Registered<K, V> asRegistered(HolderLookup<K> lookup) {
             return this;
         }

        @Override
        public void initialize() {
            super.initialize();
        }
    }

    static <K, V> Keyed<K, V> createPartial(Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap, HolderGetter<K> getter) {
        return new Keyed<>(encodeMap, getter);
    }

    static <K, V> Registered<K, V> createFull(Map<Either<TagKey<K>, ResourceKey<K>>, V> encodeMap, HolderLookup<K> lookup) {
        return new Registered<>(encodeMap, lookup);
    }

    private static RuntimeException exception() {
        return new NoSuchElementException("No value present");
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

    class Builder<K, V> {
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
            return createFull(this.builder.build(), lookup);
        }

        public RegistryMap<K, V> build(HolderGetter<K> getter) {
            return createPartial(this.builder.build(), getter);
        }

    }


}
