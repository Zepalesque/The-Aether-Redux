package net.zepalesque.redux.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;

import java.util.Optional;

public record WeightedParticleEntry(ParticleOptions particle, Optional<Either<Integer, Float>> probability) {

    private static final MapCodec<Optional<Either<Integer, Float>>> PROBABILITY_CODEC = Codec.mapEither(
            Codec.INT.optionalFieldOf("rarity"),
            Codec.FLOAT.optionalFieldOf("chance")
    ).xmap(WeightedParticleEntry::mapToOptional, WeightedParticleEntry::mapFromOptional);

    public static WeightedParticleEntry of(ParticleOptions particle, int rarity) {
        return new WeightedParticleEntry(particle, Either.left(rarity));
    }

    public static WeightedParticleEntry of(ParticleOptions particle, float chance) {
        return new WeightedParticleEntry(particle, Either.right(chance));
    }

    public static WeightedParticleEntry of(ParticleOptions particle) {
        return new WeightedParticleEntry(particle, Either.left(15));
    }

    WeightedParticleEntry(ParticleOptions particle, Either<Integer, Float> either) {
        this(particle, Optional.of(either));
    }

    public static final Codec<WeightedParticleEntry> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ParticleTypes.CODEC.fieldOf("particle").forGetter(WeightedParticleEntry::particle),
            PROBABILITY_CODEC.forGetter(WeightedParticleEntry::probability)
    ).apply(builder, WeightedParticleEntry::new));




    public boolean success(RandomSource random) {
        return this.probability().map(e -> e.map(i -> random.nextInt(i) == 0, f -> random.nextFloat() < f)).orElse(random.nextInt(15) == 0);
    }




    private static Either<Optional<Integer>, Optional<Float>> mapFromOptional(Optional<Either<Integer, Float>> optional) {
        return optional.map(e -> e.mapBoth(Optional::ofNullable, Optional::ofNullable)).orElse(Either.left(Optional.empty()));
    }

    private static Optional<Either<Integer, Float>> mapToOptional(Either<Optional<Integer>, Optional<Float>> either) {
        var wrapped = Optional.of(either);

        boolean valuePresent = either.map(Optional::isPresent, Optional::isPresent);

        return valuePresent ? wrapped.map(e -> e.mapBoth(Optional::get, Optional::get)) : Optional.empty();
    }
}
