package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.world.biome.modifier.ConditionalBiomeModifier;
import net.zepalesque.zenith.world.biome.modifier.MusicModifier;
import net.zepalesque.zenith.world.biome.modifier.SkiesModifier;
import net.zepalesque.zenith.world.biome.modifier.WaterModifier;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

public class ReduxBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CLOUDBED = createKey("add_cloudbed");
    public static final ResourceKey<BiomeModifier> SKY_COLOR_AETHER = createKey("modify_sky_color");
    public static final ResourceKey<BiomeModifier> WATER_COLOR_AETHER = createKey("modify_water_color");
    public static final ResourceKey<BiomeModifier> MUSIC_MODIFY = createKey("modify_music");
    public static final ResourceKey<BiomeModifier> ADD_SENTRITE = createKey("add_sentrite");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Redux.loc(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Condition<?>> conditions = context.lookup(Zenith.Keys.CONDITION);


        BiomeModifier cloudbed = new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_CLOUDBED), HolderSet.direct(features.getOrThrow(ReduxPlacements.CLOUDBED)),
                GenerationStep.Decoration.RAW_GENERATION);
        context.register(ADD_CLOUDBED, new ConditionalBiomeModifier(Holder.direct(cloudbed), conditions.get(ReduxConditions.CLOUDBED).orElseThrow()));

        BiomeModifier sky = new SkiesModifier(
                Optional.of(new SkiesModifier.DefaultSkySettings(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_SKY_COLOR), Optional.of(0x9FA4DD), Optional.of(0xBEC4E5))),
                ImmutableMap.<Holder<Biome>, Integer>builder().build(),
                ImmutableMap.<Holder<Biome>, Integer>builder().build());
        context.register(SKY_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(sky), conditions.get(ReduxConditions.SKY_COLORS).orElseThrow()));

        BiomeModifier water = new WaterModifier(
                Optional.of(new WaterModifier.DefaultWaterSettings(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_WATER_COLOR), Optional.of(0x74C5E2), Optional.of(0x132126))),
                ImmutableMap.<Holder<Biome>, Integer>builder()
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_MEADOW), 0x81D1E8)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST), 0x68ACD5)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_WOODLAND), 0x5996C6)
                        .build(),
                ImmutableMap.<Holder<Biome>, Integer>builder()
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_MEADOW), 0x1B2528)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST), 0x111D23)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_WOODLAND), 0x0E171E)
                        .build());
        context.register(WATER_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(water), conditions.get(ReduxConditions.WATER_COLORS).orElseThrow()));


        context.register(MUSIC_MODIFY, new MusicModifier(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_MUSIC),
                new MusicModifier.MusicOperator(Optional.empty(), Optional.of(ReduxMusic.MUSIC_MIN), Optional.of(ReduxMusic.MUSIC_MAX), Optional.empty()), Optional.empty()));

        context.register(ADD_SENTRITE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_SENTRITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.SENTRITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));


    }
}
