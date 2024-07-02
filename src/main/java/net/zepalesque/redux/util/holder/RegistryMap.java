/*
package net.zepalesque.redux.util.holder;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


public class RegistryMap<K, V> implements RegMap<K, V> {

    public static <K, V> Codec<RegistryMap<K, V>> codec(ResourceKey<? extends Registry<K>> registry, Codec<V> valueCodec) {
        Codec<TagKey<K>> tagCodec = TagKey.hashedCodec(registry);
        Codec<ResourceKey<K>> keyCodec = ResourceKey.codec(registry);
        Codec<Either<TagKey<K>, ResourceKey<K>>> eitherCodec = Codec.either(tagCodec, keyCodec);
        return Codec.unboundedMap(eitherCodec, valueCodec).xmap(map -> new RegistryMap<>(map, registry), RegistryMap::keys);
    }

    // Treat as if is immutable
    private final Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap;
    private final ResourceKey<? extends Registry<K>> registryKey;

    private @Nullable Map<Holder<K>, V> holderMap;

    private RegistryMap(Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap, ResourceKey<? extends Registry<K>> registryKey, HolderLookup.RegistryLookup<T> registryLookup) {
        this.keyMap = keyMap;
        this.registryKey = registryKey;
    }


    @Override
    public Map<Either<TagKey<K>, ResourceKey<K>>, V> getMap() {
        return this.keyMap;
    }

    @Override
    public V getOrThrow(Holder<K> key) {
        return Objects.requireNonNull(this.holderMap, "Cannot get value from uninitialized RegistryMap!").get(key);
    }

    @Override
    public boolean initialized() {
        return this.holderMap != null;
    }

    public boolean initialize(RegistryAccess access) {
        if (this.initialized()) {
            Redux.LOGGER.warn("Tried to initialize RegistryMap which had already been initialized!");
            return false;
        } else {
            this.holderMap = createMap(access, this.registryKey, this.keyMap);
            return true;
        }
    }

    private static <K, V> Map<Holder<K>, V> createMap(RegistryAccess access, ResourceKey<? extends Registry<K>> regKey, Map<Either<TagKey<K>, ResourceKey<K>>, V> keys) {
        Optional<Registry<K>> optional = access.registry(regKey);
        if (optional.isPresent()) {
            Registry<K> registry = optional.get();
            Map<Holder<K>, V> map = new HashMap<>();
            var entrySet = keys.entrySet();
            var tagValues = entrySet.stream().filter(entry -> entry.getKey().map(tag -> true, rk -> false)).map(entry -> Pair.of(entry.getKey().left().orElseThrow(), entry.getValue())).collect(Collectors.toSet());
            var keyValues = entrySet.stream().filter(entry -> entry.getKey().map(tag -> false, rk -> true)).map(entry -> Pair.of(entry.getKey().right().orElseThrow(), entry.getValue())).collect(Collectors.toSet());
            tagValues.forEach(pair ->
                    registry.getTag(pair.getFirst()).ifPresent(set -> set.stream().forEach(holder -> {
                        if (map.containsKey(holder)) {
                            logExistingFromTag(holder.unwrapKey(), pair.getFirst(), map.get(holder), pair.getSecond());
                        }
                        map.put(holder, pair.getSecond());
                    })));
            keyValues.forEach(pair ->
                    registry.getHolder(pair.getFirst()).ifPresent(holder -> {
                        if (map.containsKey(holder)) {
                            logExisting(holder.unwrapKey(), map.get(holder), pair.getSecond());
                        }
                        map.put(holder, pair.getSecond());
                    }));
            return ImmutableMap.copyOf(map);
        }
        return ImmutableMap.of();
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

    public Map<Either<TagKey<K>, ResourceKey<K>>, V> keys() {
        return keyMap;
    }

    public static class Builder<K, V> {
        private final ResourceKey<? extends Registry<K>> registry;
        private final ImmutableMap.Builder<Either<TagKey<K>, ResourceKey<K>>, V> builder;

        private Builder(ImmutableMap.Builder<Either<TagKey<K>, ResourceKey<K>>, V> builder, ResourceKey<? extends Registry<K>> registry) {
            this.registry = registry;
            this.builder = builder;
        }

        public static <K, V> Builder<K, V> create(ResourceKey<? extends Registry<K>> registry) {
            return new Builder<>(new ImmutableMap.Builder<>(), registry);
        }

        public Builder<K, V> put(TagKey<K> key, V value) {
            this.builder.put(Either.left(key), value);
            return this;
        }

        public Builder<K, V> put(ResourceKey<K> key, V value) {
            this.builder.put(Either.right(key), value);
            return this;
        }

        public RegistryMap<K, V> build() {
            return new RegistryMap<>(this.builder.build(), this.registry);
        }

    }
}
*/
