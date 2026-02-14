package net.zepalesque.redux.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;

public record WeightedParticleEntry(ParticleOptions particle, Either<Integer, Float> probability) {

    private static final int DEFAULT_RARITY = 15;
    private static final float DEFAULT_CHANCE = 0.05F;

    private static final MapCodec<Either<Integer, Float>>
        PROBABILITY_CODEC = Codec.mapEither(
            Codec.INT.optionalFieldOf("rarity", DEFAULT_RARITY),
            Codec.FLOAT.optionalFieldOf("chance", DEFAULT_CHANCE)
        );

    private static final Codec<ParticleOptions> SIMPLE_PARTICLE_CODEC = BuiltInRegistries.PARTICLE_TYPE
        .byNameCodec().flatXmap(
            WeightedParticleEntry::simple,
            options -> DataResult.success(options)
                .map(ParticleOptions::getType)
        );

    private static final Codec<ParticleOptions> PARTICLE_CODEC = Codec.withAlternative(
        SIMPLE_PARTICLE_CODEC,
        ParticleTypes.CODEC
    );

    public static final Codec<WeightedParticleEntry> CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            PARTICLE_CODEC.fieldOf("particle").forGetter(WeightedParticleEntry::particle),
            PROBABILITY_CODEC.forGetter(WeightedParticleEntry::probability)
        ).apply(builder, WeightedParticleEntry::new));

    public static final Codec<WeightedParticleEntry>
        COMPRESSABLE_CODEC = NeoForgeExtraCodecs.withAlternative(
            PARTICLE_CODEC.flatXmap(
                options -> DataResult.success(WeightedParticleEntry.of(options)),
                entry -> !entry.hasDefaultProbability()
                    ? DataResult.error(() -> "Skipping to main codec as entry has non-default values...")
                    : DataResult.success(entry.particle())
            ), CODEC);
    
    public boolean success(RandomSource random) {
        return this.probability().map(i -> random.nextInt(i) == 0, f -> random.nextFloat() < f);
    }

    public boolean hasDefaultProbability() {
        return this.probability().map(i -> i == DEFAULT_RARITY, f -> f == DEFAULT_CHANCE);
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

    public static DataResult<ParticleOptions> simple(ParticleType<?> type) {
        return type instanceof ParticleOptions simple ? DataResult.success(simple) :
                DataResult.error(() -> "Particle type %s does not implement ParticleOptions!"
                        .formatted(BuiltInRegistries.PARTICLE_TYPE.getKey(type)));
    }
}