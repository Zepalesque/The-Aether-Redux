package net.zepalesque.redux.world.biome.modifier;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.util.codec.ReduxCodecs;
import net.zepalesque.redux.util.holder.HolderUtil;

import java.util.Map;
import java.util.Optional;

public record SkiesModifier(Optional<DefaultSkySettings> settings, Map<ResourceKey<Biome>, Integer> skyMap, Map<ResourceKey<Biome>, Integer> fogMap) implements BiomeModifier {

    public static final Codec<SkiesModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            DefaultSkySettings.CODEC.optionalFieldOf("default_colors").forGetter(SkiesModifier::settings),
            ReduxCodecs.MAP_CODEC.fieldOf("sky_map").forGetter(SkiesModifier::skyMap),
            ReduxCodecs.MAP_CODEC.fieldOf("fog_map").forGetter(SkiesModifier::fogMap)).apply(builder, SkiesModifier::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        Optional<ResourceKey<Biome>> unwrapped = HolderUtil.unwrapKey(biome);
        if (phase == Phase.AFTER_EVERYTHING && unwrapped.isPresent()) {
            ResourceKey<Biome> key = unwrapped.get();
            if (settings.isEmpty() || settings.get().biomes.contains(biome)) {
                if (settings.isPresent()) {
                    settings.get().sky.ifPresent(builder.getSpecialEffects()::skyColor);
                    settings.get().fog.ifPresent(builder.getSpecialEffects()::fogColor);
                }

                if (skyMap.containsKey(key)) {
                    builder.getSpecialEffects().skyColor(skyMap.get(key));
                }

                if (fogMap.containsKey(key)) {
                    builder.getSpecialEffects().fogColor(fogMap.get(key));
                }
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
