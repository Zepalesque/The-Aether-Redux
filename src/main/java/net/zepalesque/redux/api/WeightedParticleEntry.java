package net.zepalesque.redux.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;

public record WeightedParticleEntry(ParticleOptions particle, Either<Integer, Float> probability) {

    private static final MapCodec<Either<Integer, Float>> PROBABILITY_CODEC = Codec.mapEither(
            Codec.INT.optionalFieldOf("rarity", 15),
            Codec.FLOAT.optionalFieldOf("chance", 0.05F)
    );

    private static final Codec<SimpleParticleType> SIMPLE_PARTICLE_CODEC = BuiltInRegistries.PARTICLE_TYPE.byNameCodec().flatXmap(WeightedParticleEntry::simple, WeightedParticleEntry::simple);

    private static final Codec<ParticleOptions> PARTICLE_CODEC = withAlternativeSwitched(SIMPLE_PARTICLE_CODEC, ParticleTypes.CODEC);

    public static final Codec<WeightedParticleEntry> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            PARTICLE_CODEC.fieldOf("particle").forGetter(WeightedParticleEntry::particle),
            PROBABILITY_CODEC.forGetter(WeightedParticleEntry::probability)
    ).apply(builder, WeightedParticleEntry::new));

    public static final Codec<WeightedParticleEntry> COMPRESSABLE_CODEC = Codec.withAlternative(CODEC, PARTICLE_CODEC.xmap(WeightedParticleEntry::of, WeightedParticleEntry::particle));



    public boolean success(RandomSource random) {
        return this.probability().map(i -> random.nextInt(i) == 0, f -> random.nextFloat() < f);
    }


    public static WeightedParticleEntry of(ParticleOptions particle, int rarity) {
        return new WeightedParticleEntry(particle, Either.left(rarity));
    }

    public static WeightedParticleEntry of(ParticleOptions particle, float chance) {
        return new WeightedParticleEntry(particle, Either.right(chance));
    }

    public static WeightedParticleEntry of(ParticleOptions particle) {
        return new WeightedParticleEntry(particle, Either.left(15));
    }

    public static DataResult<SimpleParticleType> simple(ParticleType<?> options) {
        return options instanceof SimpleParticleType simple ? DataResult.success(simple) :
                DataResult.error(() -> "Particle type %s was not instanceof SimpleParticleType!"
                        .formatted(BuiltInRegistries.PARTICLE_TYPE.getKey(options)));
    }

    private static <T> Codec<T> withAlternativeSwitched(final Codec<? extends T> primary, final Codec<T> alternative) {
        return Codec.either(
                primary,
                alternative
        ).xmap(
                Either::unwrap,
                Either::right
        );
    }
}