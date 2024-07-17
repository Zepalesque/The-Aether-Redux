package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.util.codec.ReduxCodecs;
import net.zepalesque.redux.util.holder.RegistryMap;

import java.util.Optional;

public record SkiesModifier(Optional<DefaultSkySettings> settings, RegistryMap<Biome, Integer> skyMap, RegistryMap<Biome, Integer> fogMap) implements BiomeModifier {

    public static final Codec<SkiesModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            DefaultSkySettings.CODEC.optionalFieldOf("default_colors").forGetter(SkiesModifier::settings),
            ReduxCodecs.MAP_CODEC.fieldOf("sky_map").forGetter(SkiesModifier::skyMap),
            ReduxCodecs.MAP_CODEC.fieldOf("fog_map").forGetter(SkiesModifier::fogMap)).apply(builder, SkiesModifier::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING) {
            if (settings.isEmpty() || settings.get().biomes.contains(biome)) {
                if (settings.isPresent()) {
                    settings.get().sky.ifPresent(builder.getSpecialEffects()::skyColor);
                    settings.get().fog.ifPresent(builder.getSpecialEffects()::fogColor);
                }
                skyMap.get(biome).ifPresent(builder.getSpecialEffects()::skyColor);
                fogMap.get(biome).ifPresent(builder.getSpecialEffects()::fogColor);
            }
        }
    }


    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }


    public record DefaultSkySettings(HolderSet<Biome> biomes, Optional<Integer> sky, Optional<Integer> fog) {
        public static final Codec<DefaultSkySettings> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                Biome.LIST_CODEC.fieldOf("biomes").forGetter(DefaultSkySettings::biomes),
                Codec.INT.optionalFieldOf("sky").forGetter(DefaultSkySettings::sky),
                Codec.INT.optionalFieldOf("fog").forGetter(DefaultSkySettings::fog)
                ).apply(builder, DefaultSkySettings::new));
    }
}
