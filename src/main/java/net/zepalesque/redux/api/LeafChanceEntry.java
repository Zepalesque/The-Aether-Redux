package net.zepalesque.redux.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;

public record LeafChanceEntry(ParticleOptions particle, Either<Integer, Float> probability) {

    private static final MapCodec<Either<Integer, Float>> PROBABILITY_CODEC = Codec.mapEither(
            Codec.INT.fieldOf("rarity"),
            Codec.FLOAT.fieldOf("chance")
    );

    public static LeafChanceEntry of(ParticleOptions particle, int rarity) {
        return new LeafChanceEntry(particle, Either.left(rarity));
    }

    public static LeafChanceEntry of(ParticleOptions particle, float chance) {
        return new LeafChanceEntry(particle, Either.right(chance));
    }

    public static LeafChanceEntry of(ParticleOptions particle) {
        return new LeafChanceEntry(particle, Either.left(15));
    }

    public static final Codec<LeafChanceEntry> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ParticleTypes.CODEC.fieldOf("particle").forGetter(LeafChanceEntry::particle),
            PROBABILITY_CODEC.forGetter(LeafChanceEntry::probability)
    ).apply(builder, LeafChanceEntry::new));




    public boolean success(RandomSource random) {
        return this.probability().map(i -> random.nextInt(i) == 0, f -> random.nextFloat() < f);
    }
}
