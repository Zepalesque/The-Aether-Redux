package net.zepalesque.redux.util.holder;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.Map;
import java.util.Optional;

public interface RegMap<K, V> {
    Map<Either<TagKey<K>, ResourceKey<K>>, V> keyMap();

    Map<Holder<K>, V> holderMap();

    default V getOrThrow(Holder<K> key) {
        Preconditions.checkState(this.containsKey(key), "RegistryMap does not contain value {}!", key);
        return this.holderMap().get(key);
    }
    default Optional<V> get(Holder<K> key) {
        return this.containsKey(key) ? Optional.of(this.getOrThrow(key)) : Optional.empty();
    }

    default boolean containsKey(Holder<K> key) {
        return this.holderMap().containsKey(key);
    }

    default boolean containsValue(V key) {
        return this.keyMap().containsValue(key);
    }
}
