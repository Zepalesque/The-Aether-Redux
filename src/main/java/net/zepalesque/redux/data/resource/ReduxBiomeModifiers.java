package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.aetherteam.aether_genesis.data.resources.registries.GenesisBiomes;
import net.builderdog.ancient_aether.data.resources.registries.AncientAetherBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.api.predicate.MusicPredicate;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.util.holder.RegistryMap;
import net.zepalesque.redux.world.biome.modifier.AetherGrassColors;
import net.zepalesque.redux.world.biome.modifier.CarverModifier;
import net.zepalesque.redux.world.biome.modifier.ConditionalBiomeModifier;
import net.zepalesque.redux.world.biome.modifier.FoliageModifier;
import net.zepalesque.redux.world.biome.modifier.MusicModifier;
import net.zepalesque.redux.world.biome.modifier.ReduxSpawnsModifier;
import net.zepalesque.redux.world.biome.modifier.SkiesModifier;
import net.zepalesque.redux.world.biome.modifier.WaterModifier;
import teamrazor.deepaether.world.biomes.DABiomes;

import java.util.List;
import java.util.Optional;

public class ReduxBiomeModifiers {
    public static String FEATURE = "feature/";
    public static String MOB = "mob/";
    public static String MODIFY = "modify/";

    public static final ResourceKey<BiomeModifier> ADD_AETHER_CAVES = createKey(FEATURE + "aether_caves");
    public static final ResourceKey<BiomeModifier> ADD_BLIGHTED_CAVES = createKey(FEATURE + "blighted_caves");
    public static final ResourceKey<BiomeModifier> ADD_FUNGAL_CAVES = createKey(FEATURE + "fungal_caves");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_HOLYSTONE_ORE = createKey(FEATURE + "mossy_holystone_ore");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_ROCKS = createKey(FEATURE + "mossy_rocks");
    public static final ResourceKey<BiomeModifier> ADD_WYNDSPROUTS = createKey(FEATURE + "wyndsprouts");
    public static final ResourceKey<BiomeModifier> ADD_GENESIS_SPROUTS = createKey(FEATURE + "genesis_sprouts");
    public static final ResourceKey<BiomeModifier> ADD_VANILLA_SWET = createKey(MOB + "vanilla_swet");
    public static final ResourceKey<BiomeModifier> ADD_ENDERMAN = createKey(MOB + "enderman");
    public static final ResourceKey<BiomeModifier> ADD_VERIDIUM = createKey(FEATURE + "veridium_ore");
    public static final ResourceKey<BiomeModifier> ADD_DIVINITE = createKey(FEATURE + "divinite");
    public static final ResourceKey<BiomeModifier> ADD_SENTRITE = createKey(FEATURE + "sentrite");
    public static final ResourceKey<BiomeModifier> ADD_SNOW = createKey(FEATURE + "snow");
    public static final ResourceKey<BiomeModifier> MODIFY_MUSIC = createKey(MODIFY + "modify_music");
    public static final ResourceKey<BiomeModifier> WATER_COLOR_AETHER = createKey(MODIFY + "redux_water_colors");
    public static final ResourceKey<BiomeModifier> SKY_COLOR_AETHER = createKey(MODIFY + "redux_sky_colors");
    public static final ResourceKey<BiomeModifier> VANILLA_GRASS_OVERRIDE = createKey(MODIFY + "redux_vanilla_grasses");
    public static final ResourceKey<BiomeModifier> AETHER_GRASS_COLORS = createKey(MODIFY + "redux_aether_grasses");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Redux.locate(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        context.register(ADD_AETHER_CAVES, new CarverModifier(biomes.getOrThrow(ReduxTags.Biomes.HAS_AETHER_CAVES), carvers.getOrThrow(ReduxConfiguredCarvers.AETHER_CAVES)));

        context.register(ADD_BLIGHTED_CAVES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_BLIGHTED_CAVES), HolderSet.direct(features.getOrThrow(ReduxPlacements.BLIGHTMOSS_SPARSE_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_FUNGAL_CAVES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_FUNGAL_CAVES), HolderSet.direct(features.getOrThrow(ReduxPlacements.FUNGAL_SPARSE_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_MOSSY_HOLYSTONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_HOLYSTONE_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacements.MOSSY_HOLYSTONE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_MOSSY_ROCKS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_ROCKS), HolderSet.direct(features.getOrThrow(ReduxPlacements.MOSSY_ROCK)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_SNOW, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AetherTags.Biomes.IS_AETHER), HolderSet.direct(features.getOrThrow(ReduxPlacements.AETHER_SNOW_LAYER)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_WYNDSPROUTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_WYNDSPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacements.WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_GENESIS_SPROUTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_BOTH_SPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacements.GENESIS_SKYSPROUTS_PATCH), features.getOrThrow(ReduxPlacements.GENESIS_WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_VANILLA_SWET, new ReduxSpawnsModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VANILLA_SWET), List.of(new MobSpawnSettings.SpawnerData(ReduxEntityTypes.VANILLA_SWET.get(), 5, 1, 2)), Conditions.VANILLA_SWETS, 0.5, 0.1));

        context.register(ADD_ENDERMAN, new ReduxSpawnsModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_ENDERMAN), List.of(new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 1, 4)), Conditions.ENDERMEN, 1.0, 0.12));

        context.register(ADD_VERIDIUM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VERIDIUM_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacements.VERIDIUM_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DIVINITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_DIVINITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.DIVINITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SENTRITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_SENTRITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.SENTRITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        BiomeModifier water = new WaterModifier(
                Optional.of(new WaterModifier.DefaultWaterSettings(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_WATER_COLOR), Optional.of(0x85BDD1), Optional.of(0x182226))),
                RegistryMap.Builder.<Biome, Integer>create() // water
                        .put(AetherBiomes.SKYROOT_MEADOW, 0x91C8D8)
                        .put(AetherBiomes.SKYROOT_FOREST, 0x79A8C4)
                        .put(AetherBiomes.SKYROOT_WOODLAND, 0x6A94B5)
                        .put(ReduxBiomes.THE_BLIGHT, 0xA2ACD8)
                        .put(ReduxBiomes.SKYFIELDS, 0x94D2EF)
                        .put(ReduxBiomes.CLOUDCAPS, 0x9ECCED)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0x94C4CE)
                        .put(ReduxTags.Biomes.IS_GILDED, 0x8FABC1)
                        .put(ReduxTags.Biomes.IS_FROSTED, 0xA2C3D8)
                        .build(biomes),
                RegistryMap.Builder.<Biome, Integer>create() // fog
                        .put(AetherBiomes.SKYROOT_MEADOW, 0x1B2528)
                        .put(AetherBiomes.SKYROOT_FOREST, 0x141C21)
                        .put(AetherBiomes.SKYROOT_WOODLAND, 0x10171C)
                        .put(ReduxBiomes.THE_BLIGHT, 0x1C1E26)
                        .put(ReduxBiomes.SKYFIELDS, 0x192328)
                        .put(ReduxBiomes.CLOUDCAPS, 0x171E23)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0x181F21)
                        .put(ReduxTags.Biomes.IS_GILDED, 0x181D21)
                        .put(ReduxTags.Biomes.IS_FROSTED, 0x1A2023)
                        .build(biomes));
        context.register(WATER_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(water), Conditions.WATER));

        BiomeModifier sky = new SkiesModifier(
                Optional.of(new SkiesModifier.DefaultSkySettings(biomes.getOrThrow(ReduxTags.Biomes.HAS_REDUX_SKY_COLOR), Optional.of(0x9FA4DD), Optional.of(0xBEC4E5))),
                RegistryMap.Builder.<Biome, Integer>create() // sky
                        .put(ReduxBiomes.THE_BLIGHT, 0x9994D1)
                        .put(ReduxBiomes.SKYFIELDS, 0xACBAE6)
                        .put(ReduxBiomes.CLOUDCAPS, 0x97A4F4)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0xACC9E6)
                        .put(ReduxTags.Biomes.IS_GILDED, 0xC4BDAA)
                        .put(ReduxTags.Biomes.IS_FROSTED, 0xB3B3E5)
                        .build(biomes),
                RegistryMap.Builder.<Biome, Integer>create() // fog
                        .put(ReduxBiomes.THE_BLIGHT, 0xC0B1DB)
                        .put(ReduxBiomes.SKYFIELDS, 0xCED5EB)
                        .put(ReduxBiomes.CLOUDCAPS, 0xBFC4FF)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0xCEDDEB)
                        .put(ReduxTags.Biomes.IS_GILDED, 0xDDD9DA)
                        .put(ReduxTags.Biomes.IS_FROSTED, 0xD0D2E5)
                        .build(biomes));
        context.register(SKY_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(sky), Conditions.SKY));

        BiomeModifier vanillaGrass = new FoliageModifier(
                Optional.of(new FoliageModifier.DefaultFoliageSettings(biomes.getOrThrow(ReduxTags.Biomes.CHANGE_VANILLA_GRASS_COLORS), Optional.of(0x91BD59), Optional.of(0x77AB2F))),
                RegistryMap.Builder.<Biome, Integer>create() // grass
                        .put(AetherBiomes.SKYROOT_FOREST, 0x79C05A)
                        .put(AetherBiomes.SKYROOT_WOODLAND, 0x79C05A)
                        .put(ReduxBiomes.THE_BLIGHT, 0x97B276)
                        .put(ReduxBiomes.SKYFIELDS, 0x59C93C)
                        .put(ReduxBiomes.CLOUDCAPS, 0x55C93F)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0x9ABE4B)
                        .put(ReduxTags.Biomes.IS_GILDED, 0xACBA4F)
                        .put(ReduxTags.Biomes.IS_FROSTED, 0x86B783)
                        .build(biomes),
                RegistryMap.Builder.<Biome, Integer>create() // foliage
                        .put(AetherBiomes.SKYROOT_FOREST, 0x59AE30)
                        .put(AetherBiomes.SKYROOT_WOODLAND, 0x59AE30)
                        .put(ReduxBiomes.THE_BLIGHT, 0x819D5D)
                        .put(ReduxBiomes.SKYFIELDS, 0x30BB0B)
                        .put(ReduxBiomes.CLOUDCAPS, 0x2BBB0F)
                        .put(ReduxBiomes.SKYROOT_SHRUBLANDS, 0x82AC1E)
                        .put(ReduxTags.Biomes.IS_GILDED, 0x97A823)
                        .put(ReduxTags.Biomes.IS_FROSTED, 0x68A464)
                        .build(biomes));
        context.register(VANILLA_GRASS_OVERRIDE, vanillaGrass);

        context.register(MODIFY_MUSIC, new MusicModifier(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_MUSIC),
                new MusicModifier.MusicOperator(Optional.empty(), Optional.of(ReduxMusic.MUSIC_MIN), Optional.of(ReduxMusic.MUSIC_MAX), Optional.empty()),
                Optional.of(new MusicPredicate(Optional.empty(), Optional.of(List.of(12000)), Optional.of(List.of(24000)), Optional.empty()))));



        BiomeModifier aetherGrass = new AetherGrassColors(RegistryMap.Builder.<Biome, Integer>create()
                .put(AetherBiomes.SKYROOT_FOREST, 0xA2F2BC)
                .put(AetherBiomes.SKYROOT_WOODLAND, 0x96E8B0)
                .put(AetherBiomes.SKYROOT_MEADOW, 0xBAFFCB)
                .put(ReduxBiomes.THE_BLIGHT, ReduxBiomes.BLIGHT_GRASS_COLOR)
                .put(ReduxBiomes.GILDED_GROVES, ReduxBiomes.GILDED_GRASS_COLOR)
                .put(ReduxBiomes.GILDED_GRASSLANDS, ReduxBiomes.GILDED_GRASSLANDS_COLOR)
                .put(ReduxBiomes.SKYFIELDS, ReduxBiomes.SKYFIELDS_GRASS_COLOR)
                .put(ReduxBiomes.CLOUDCAPS, ReduxBiomes.CLOUDCAP_GRASS_COLOR)
                .put(ReduxBiomes.SKYROOT_SHRUBLANDS, ReduxBiomes.SHRUBLANDS_GRASS_COLOR)
                .put(ReduxTags.Biomes.IS_FROSTED, ReduxBiomes.FROSTED_GRASS_COLOR)
                .put(AncientAetherBiomes.WYNDCAP_HIGHLAND, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.WYNDCAP_TAIGA, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.FESTIVE_WYNDCAP_TAIGA, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.WYNDCAP_PEAKS, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.FROZEN_CAVERNS, ReduxBiomes.FROZEN_GRASS_COLOR)
                .put(AncientAetherBiomes.ELEVATED_FOREST, ReduxBiomes.PALE_GRASS_COLOR)
                .put(AncientAetherBiomes.ELEVATED_CLEARING, ReduxBiomes.PALE_GRASS_COLOR)
                .put(AncientAetherBiomes.ELEVATED_CAVERNS, ReduxBiomes.PALE_GRASS_COLOR)
                .put(DABiomes.AERGLOW_FOREST, ReduxBiomes.AERGLOW_GRASS_COLOR)
                .put(DABiomes.AERLAVENDER_FIELDS, ReduxBiomes.AERLAVENDER_GRASS_COLOR)
                .put(DABiomes.BLUE_AERGLOW_FOREST, ReduxBiomes.BLUE_AERGLOW_GRASS_COLOR)
                .put(DABiomes.GOLDEN_GROVE, ReduxBiomes.GOLDEN_GRASS_COLOR)
                .put(DABiomes.GOLDEN_HEIGHTS, ReduxBiomes.GOLDEN_GRASS_COLOR)
                .put(DABiomes.MYSTIC_AERGLOW_FOREST, ReduxBiomes.MYSTIC_AERGLOW_GRASS_COLOR)
                .put(GenesisBiomes.VIBRANT_FOREST, 0xA2F2BC)
                .put(GenesisBiomes.VIBRANT_WOODLAND, 0x96E8B0)
                .put(GenesisBiomes.VIBRANT_MEADOW, 0xBAFFCB)
                .put(Tags.Biomes.IS_COLD, ReduxBiomes.FROSTED_GRASS_COLOR)
                .put(Tags.Biomes.IS_DESERT, ReduxBiomes.OASIS_GRASS_COLOR)
                .put(Tags.Biomes.IS_LUSH, ReduxBiomes.SKYFIELDS_GRASS_COLOR)
                .put(Tags.Biomes.IS_MUSHROOM, ReduxBiomes.CLOUDCAP_GRASS_COLOR)
                .put(Tags.Biomes.IS_MAGICAL, ReduxBiomes.SHIMMERING_GRASS_COLOR)
                .put(Tags.Biomes.IS_PLATEAU, ReduxBiomes.GILDED_GRASS_COLOR)
                .put(Tags.Biomes.IS_SPARSE, ReduxBiomes.GILDED_GRASSLANDS_COLOR)
                .put(BiomeTags.IS_NETHER, ReduxBiomes.DUNES_GRASS_COLOR)
                .put(BiomeTags.IS_END, ReduxBiomes.BLIGHT_GRASS_COLOR)
                .build(biomes));
        context.register(AETHER_GRASS_COLORS, aetherGrass);
    }
}
