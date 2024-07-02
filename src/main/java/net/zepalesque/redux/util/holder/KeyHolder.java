package net.zepalesque.redux.util.holder;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A holder value encapsulating a ResourceKey and no value.
 * @param <E> The type of the key's registry key
 */
public class KeyHolder<E> implements Holder<E> {

    private final ResourceKey<E> key;
    private final Optional<HolderOwner<E>> owner;

    /**
     * The internal method to create a KeyHolder
     * @param key The {@link ResourceKey} that is used
     * @param owner The optional {@link HolderOwner} that can be checked against another
     */
    private KeyHolder(ResourceKey<E> key, Optional<HolderOwner<E>> owner) {
        this.key = key;
        this.owner = owner;
    }

    /**
     * Creates a KeyHolder without a {@link HolderOwner}, mainly used for if the {@link HolderOwner} is not accessible
     * @param key The {@link ResourceKey} for this holder
     */
    public static <T> KeyHolder<T> create(ResourceKey<T> key) {
        return new KeyHolder<>(key, Optional.empty());
    }

    /**
     * Creates a KeyHolder with a {@link HolderOwner}
     * @param key The {@link ResourceKey} for this holder
     * @param owner The {@link HolderOwner} for this holder
     */
    public static <V> KeyHolder<V> create(ResourceKey<V> key, HolderOwner<V> owner) {
        return new KeyHolder<>(key, Optional.of(owner));
    }

    @Override
    public E value() {
        throw new IllegalStateException("Tried to get value of key holder: " + this.key.location());
    }

    // TODO: Experiment, see if this should be true instead (luckily it doesn't seem to be used in places where it makes a difference so it should be fine)
    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    public boolean is(ResourceLocation loc) {
        return this.key.location().equals(loc);
    }

    @Override
    public boolean is(ResourceKey<E> key) {
        return this.key == key;
    }

    @Override
    public boolean is(Predicate<ResourceKey<E>> predicate) {
        return predicate.test(this.key);
    }

    @Override
    public boolean is(TagKey<E> tag) {
        return false;
    }

    @Override
    public Stream<TagKey<E>> tags() {
        return Stream.empty();
    }

    @Override
    public Either<ResourceKey<E>, E> unwrap() {
        return Either.left(this.key);
    }

    @Override
    public Optional<ResourceKey<E>> unwrapKey() {
        return Optional.of(this.key);
    }

    @Override
    public Kind kind() {
        return Kind.REFERENCE;
    }

    @Override
    public boolean canSerializeIn(HolderOwner<E> owner) {
        return this.owner.isEmpty() || this.owner.get().canSerializeIn(owner);
    }
}
