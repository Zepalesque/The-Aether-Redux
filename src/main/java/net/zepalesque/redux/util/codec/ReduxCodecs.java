package net.zepalesque.redux.util.codec;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.biome.Biome;
import net.zepalesque.redux.world.tree.decorator.EnchantedVineDecorator;

import java.util.Map;

public class ReduxCodecs {

    public static final Codec<Holder<Biome>> OPTIONAL_BIOME = OptionalHolderCodec.create(Registries.BIOME);

    public static final Codec<Map<Holder<Biome>, Integer>> MAP_CODEC = Codec.unboundedMap(OPTIONAL_BIOME, Codec.INT);

    public static <T> MapCodec<T> mapAlternative(final MapCodec<T> primary, final MapCodec<? extends T> alternative) {
        return Codec.mapEither(
                primary,
                alternative
        ).xmap(
                EnchantedVineDecorator::unwrap,
                Either::left
        );
    }
}
