package net.zepalesque.redux.util.holder;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.Map;
import java.util.Optional;

public interface RegMap<K, V> {

    Map<Either<TagKey<K>, ResourceKey<K>>, V> getMap();

    V getOrThrow(Holder<K> key);

    boolean initialized();

    default Optional<V> get(Holder<K> key) {
       return this.initialized() ? Optional.ofNullable(getOrThrow(key)) : Optional.empty();
    }

    boolean initialize(RegistryAccess access);

    boolean initIf(RegistryAccess access);
}
