package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record CarverModifier(HolderSet<Biome> biomes, Holder<ConfiguredWorldCarver<?>> carver) implements BiomeModifier {
    public static final Codec<CarverModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(CarverModifier::biomes),
            ConfiguredWorldCarver.CODEC.fieldOf("carver").forGetter(CarverModifier::carver)
    ).apply(builder, CarverModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome))
        { builder.getGenerationSettings().addCarver(GenerationStep.Carving.AIR, carver);}
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierCodecs.CARVER.get();
    }
}
