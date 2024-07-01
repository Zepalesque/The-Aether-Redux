package net.zepalesque.redux.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public class HolderUtil {
    public static <T> Optional<ResourceKey<T>> unwrapKey(Holder<T> input) {
        try {
            return input.unwrapKey();
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }
}
