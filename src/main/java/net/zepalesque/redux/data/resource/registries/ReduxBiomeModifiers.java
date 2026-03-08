package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.world.biome.modifier.ConditionalBiomeModifier;
import net.zepalesque.zenith.api.world.biome.modifier.MusicModifier;
import net.zepalesque.zenith.api.world.biome.modifier.SkiesModifier;
import net.zepalesque.zenith.api.world.biome.modifier.WaterModifier;
import net.zepalesque.zenith.core.Zenith;

public class ReduxBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CLOUDBED = createKey("add_cloudbed");
	public static final ResourceKey<BiomeModifier> ADD_LAKES = createKey("add_lakes");
	public static final ResourceKey<BiomeModifier> ADD_CAT_FISH = createKey("add_cat_fish");
	public static final ResourceKey<BiomeModifier> ADD_VERBENA = createKey("add_verbena");
	public static final ResourceKey<BiomeModifier> ADD_CAELGAE = createKey("add_caelgae");
	public static final ResourceKey<BiomeModifier> ADD_BLOOMTAIL = createKey("add_bloomtail");
    public static final ResourceKey<BiomeModifier> SKY_COLOR_AETHER = createKey("modify_sky_color");
    public static final ResourceKey<BiomeModifier> WATER_COLOR_AETHER = createKey("modify_water_color");
    public static final ResourceKey<BiomeModifier> MUSIC_MODIFY = createKey("modify_music");
    public static final ResourceKey<BiomeModifier> ADD_SENTRITE = createKey("add_sentrite");
    public static final ResourceKey<BiomeModifier> ADD_ANGILITE = createKey("add_angilite");
    public static final ResourceKey<BiomeModifier> ADD_WYNDSPROUTS = createKey("add_wyndsprouts");
    public static final ResourceKey<BiomeModifier> ADD_CAVES = createKey("add_caves");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Redux.loc(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Condition<?>> conditions = context.lookup(Zenith.Keys.CONDITION);

        BiomeModifier cloudbed = new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_CLOUDBED), HolderSet.direct(features.getOrThrow(ReduxPlacements.CLOUDBED)),
                GenerationStep.Decoration.RAW_GENERATION);
        context.register(ADD_CLOUDBED, new ConditionalBiomeModifier(Holder.direct(cloudbed), conditions.get(ReduxConditions.CLOUDBED).orElseThrow()));

        BiomeModifier lakes = new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_LAKES), HolderSet.direct(features.getOrThrow(ReduxPlacements.LAKES)),
                GenerationStep.Decoration.RAW_GENERATION);
        context.register(ADD_LAKES, new ConditionalBiomeModifier(Holder.direct(lakes), conditions.getOrThrow(ReduxConditions.LAKES)));

        var catfish = new BiomeModifiers.AddSpawnsBiomeModifier(biomes.getOrThrow(ReduxTags.Biomes.HAS_CAT_FISH), List.of(
            new MobSpawnSettings.SpawnerData(ReduxEntities.CAT_FISH.get(), 5, 1, 5)
        ));
        context.register(ADD_CAT_FISH, new ConditionalBiomeModifier(Holder.direct(catfish), conditions.getOrThrow(ReduxConditions.LAKES)));
        
        context.register(ADD_VERBENA, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VERBENA), HolderSet.direct(features.getOrThrow(ReduxPlacements.TURBO_VERBENA_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_CAELGAE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_CAELGAE), HolderSet.direct(features.getOrThrow(ReduxPlacements.CAELGAE_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_BLOOMTAIL, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_CAELGAE), HolderSet.direct(features.getOrThrow(ReduxPlacements.BLOOMTAIL)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        BiomeModifier sky = new SkiesModifier(
                Optional.of(new SkiesModifier.DefaultSkySettings(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_SKY_COLOR), Optional.of(0x9FA4DD), Optional.of(0xBEC4E5))),
                ImmutableMap.<Holder<Biome>, Integer>builder() // sky
                        .put(biomes.getOrThrow(ReduxBiomes.GILDED_GROVES), 0xC4BDAA)
                        .put(biomes.getOrThrow(ReduxBiomes.THE_BLIGHT), 0x7C7DA5)
                        .build(),
                ImmutableMap.<Holder<Biome>, Integer>builder() // fog
                        .put(biomes.getOrThrow(ReduxBiomes.GILDED_GROVES), 0xDDD9DA)
                        .put(biomes.getOrThrow(ReduxBiomes.THE_BLIGHT), 0xADAED3)
                        .build());
        context.register(SKY_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(sky), conditions.get(ReduxConditions.SKY_COLORS).orElseThrow()));

        BiomeModifier water = new WaterModifier(
                Optional.of(new WaterModifier.DefaultWaterSettings(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_WATER_COLOR), Optional.of(0x85BDD1), Optional.of(0x182226))),
                ImmutableMap.<Holder<Biome>, Integer>builder() // water
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_MEADOW), 0x91C8D8)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST), 0x79A8C4)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_WOODLAND), 0x6A94B5)
                        .put(biomes.getOrThrow(ReduxBiomes.GILDED_GROVES), 0x89C1C6)
                        .put(biomes.getOrThrow(ReduxBiomes.THE_BLIGHT), 0xA2ACD8)
                        .build(),
                ImmutableMap.<Holder<Biome>, Integer>builder() // fog
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_MEADOW), 0x1B2528)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST), 0x141C21)
                        .put(biomes.getOrThrow(AetherBiomes.SKYROOT_WOODLAND), 0x10171C)
                        .put(biomes.getOrThrow(ReduxBiomes.GILDED_GROVES), 0x1E2A2B)
                        .put(biomes.getOrThrow(ReduxBiomes.THE_BLIGHT), 0x1C1E26)
                        .build());
        context.register(WATER_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(water), conditions.get(ReduxConditions.WATER_COLORS).orElseThrow()));


        context.register(MUSIC_MODIFY, new MusicModifier(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_MUSIC),
                new MusicModifier.MusicOperator(Optional.empty(), Optional.of(ReduxMusic.MUSIC_MIN), Optional.of(ReduxMusic.MUSIC_MAX), Optional.empty()), Optional.empty()));

        context.register(ADD_SENTRITE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_SENTRITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.SENTRITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ANGILITE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_ANGILITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.ANGILITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_WYNDSPROUTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_WYNDSPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacements.WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_CAVES, new BiomeModifiers.AddCarversBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_CAVES), HolderSet.direct(carvers.getOrThrow(ReduxCarverConfig.AETHER_CAVES)), GenerationStep.Carving.AIR));
    }
}
