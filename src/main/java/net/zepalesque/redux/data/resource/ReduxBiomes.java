package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.entity.ReduxEntityTypes;

public class ReduxBiomes {
    public static final ResourceKey<Biome> THE_BLIGHT = createKey("the_blight");
    public static final ResourceKey<Biome> GLACIAL_TAIGA = createKey("glacial_taiga");
    public static final ResourceKey<Biome> FROSTED_TUNDRA = createKey("frosted_tundra");
    public static final ResourceKey<Biome> GILDED_GROVES = createKey("gilded_groves");
    public static final ResourceKey<Biome> GILDED_GRASSLANDS = createKey("gilded_grasslands");
    public static final ResourceKey<Biome> HIGHFIELDS = createKey("highfields");
    public static final ResourceKey<Biome> CLOUDCAPS = createKey("cloudcaps");
    public static final ResourceKey<Biome> SKYROOT_SHRUBLANDS = createKey("skyroot_shrublands");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, Redux.locate(name));

    }

    public static final int WATER = 5403045;
    public static final int WATER_FOG = 791347;

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(THE_BLIGHT, biomeSky(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, false))
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, ReduxPlacedFeatures.BLIGHT_ROCK)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHT_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GLOWSPROUTS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SPIROLYCTIL_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHTSHADE_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHTMOSS_VEGETATION)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.BLIGHTMOSS_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.HOLYSILT_DISK)
                , defaultMobSpawnsNoPassive(new MobSpawnSettings.Builder())
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 8, 2, 5)),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                4607385,
                723770,
                BLIGHT_GRASS_COLOR,
                0xC6C1FF,
                0xA591BC
                ));

        context.register(CLOUDCAPS, simpleBiome(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, true))
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.LARGE_MUSHROOMS)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.AEVELIUM_GRASSES_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.CLOUDCAP_MUSHLING_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.JELLYSHROOM_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SHIMMERSTOOL_PATCH)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.LIGHTROOTS)
                        .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ReduxPlacedFeatures.SHIMMERSTOOL_ROCK)
                , cloudcapMobs(new MobSpawnSettings.Builder()),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                WATER,
                WATER_FOG,
                AETHER_GRASS_COLOR
                ));

        context.register(GLACIAL_TAIGA, climateBiome(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, false))
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GLACIAL_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.LUMINA_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.FROSTED_PURPLE_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.DAGGERBLOOM_PATCH)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.AEROGEL_ORE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SPLITFERN_PATCH)
                , defaultMobSpawns(new MobSpawnSettings.Builder()),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                WATER,
                WATER_FOG,
                FROSTED_GRASS_COLOR,
                true,
                -1.6F,
                0.4F
        ));


        context.register(FROSTED_TUNDRA, climateBiome(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, false))
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.FROSTED_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.FROSTED_PURPLE_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.DAGGERBLOOM_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SPLITFERN_PATCH)
                , defaultMobSpawns(new MobSpawnSettings.Builder()),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                WATER,
                WATER_FOG,
                FROSTED_GRASS_COLOR,
                true,
                -1.1F,
                0.5F
        ));

        context.register(GILDED_GROVES, simpleBiome(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, true))
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GROVE_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GILDED_WHITE_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GOLDEN_CLOVER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.AURUM_PATCH)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.GILDED_HOLYSTONE_ORE)
                , defaultMobSpawns(new MobSpawnSettings.Builder()),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                WATER,
                WATER_FOG,
                GILDED_GRASS_COLOR
                ));
        context.register(GILDED_GRASSLANDS, simpleBiome(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, true))
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GRASSLAND_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GILDED_WHITE_FLOWER_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.AURUM_PATCH)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.GILDED_HOLYSTONE_ORE)
                , defaultMobSpawns(new MobSpawnSettings.Builder()),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                WATER,
                WATER_FOG,
                GILDED_GRASS_COLOR
                ));

        context.register(HIGHFIELDS, simpleBiome(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, false))
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.HIGHFIELDS_TREES)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, ReduxPlacedFeatures.HIGHFIELDS_ROCK)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.TALL_GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SKYSPROUTS_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.IRIDIA_PATCH)
                , increasedMobSpawns(new MobSpawnSettings.Builder()),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                WATER,
                WATER_FOG,
                HIGHFIELDS_GRASS_COLOR
        ));

        context.register(SKYROOT_SHRUBLANDS, simpleBiome(
                ores(baseFeatures(new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers), false, false, true, true, false))
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.SHRUBLANDS_TREES)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.TALL_GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SHRUBLANDS_PURPLE_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SHRUBLANDS_WHITE_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.AURUM_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.ZANBERRY_BUSH_PATCH)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SPLITFERN_PATCH)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, ReduxPlacedFeatures.SHRUBLANDS_ROCK)
                , defaultMobSpawns(new MobSpawnSettings.Builder()),
                ReduxMusic.DEFAULT_AETHER_MUSIC,
                WATER,
                WATER_FOG,
                SHRUBLANDS_GRASS_COLOR
        ));
    }
    public static final int AETHER_GRASS_COLOR = 0xB1FFCB;
    public static final int GILDED_GRASS_COLOR = 0xF8FFBF;
    public static final int BLIGHT_GRASS_COLOR = 0xD5BAFF;
    public static final int FROSTED_GRASS_COLOR = 0xCCF7FF;
    public static final int HIGHFIELDS_GRASS_COLOR = 0xCCFFED;
    public static final int SHRUBLANDS_GRASS_COLOR = 0xC1FFB1;



    public static Biome biomeBase(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor, int skyColor, int skyFogColor, boolean precip, float temp, float downfall) {
        return fullDefinition(
                precip,
                temp,
                downfall,
                new BiomeSpecialEffects.Builder()
                        .fogColor(skyFogColor)
                        .skyColor(skyColor)
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .grassColorOverride(grassColor)
                        .foliageColorOverride(0xb1_ff_cb)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .backgroundMusic(music)
                        .build(),
                mobSpawns.build(),
                gen.build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome simpleBiome(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor) {
        return biomeBase(gen, mobSpawns, music, waterColor, waterFogColor, grassColor, 0xc0_c0_ff, 0x93_93_bc, false, 0.8F, 0.0F);
    }

    public static Biome climateBiome(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor, boolean precip, float temp, float downfall) {
        return biomeBase(gen, mobSpawns, music, waterColor, waterFogColor, grassColor, 0xc0_c0_ff, 0x93_93_bc, precip, temp, downfall);
    }

    public static Biome biomeSky(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor, int skyColor, int skyFogColor) {
        return biomeBase(gen, mobSpawns, music, waterColor, waterFogColor, grassColor, skyColor, skyFogColor, false, 0.8F, 0.0F);
    }


    public static MobSpawnSettings.Builder defaultMobSpawnsNoPassive(MobSpawnSettings.Builder builder)
    {
        return builder
                .addMobCharge(AetherEntityTypes.COCKATRICE.get(), 0.5, 0.15)
                .addMobCharge(AetherEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                .addMobCharge(AetherEntityTypes.AECHOR_PLANT.get(), 0.4,0.11)
                .addMobCharge(AetherEntityTypes.BLUE_SWET.get(), 0.5, 0.1)
                .addMobCharge(AetherEntityTypes.GOLDEN_SWET.get(), 0.5, 0.1)
                .addMobCharge(AetherEntityTypes.WHIRLWIND.get(), 0.4, 0.1)
                .addMobCharge(AetherEntityTypes.EVIL_WHIRLWIND.get(), 0.4, 0.1)
                .addMobCharge(AetherEntityTypes.AERWHALE.get(), 0.5, 0.11)

                .addSpawn(AetherMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 8, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR.get(), 20, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AECHOR_PLANT.get(), 7, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.BLUE_SWET.get(), 6, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.GOLDEN_SWET.get(), 6, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.WHIRLWIND.get(), 3, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.EVIL_WHIRLWIND.get(), 1, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_AERWHALE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERWHALE.get(), 10, 1, 1));
    }

    public static MobSpawnSettings.Builder defaultMobSpawns(MobSpawnSettings.Builder builder)
    {
        return defaultMobSpawnsNoPassive(builder)
                .creatureGenerationProbability(0.25F)
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.PHYG.get(), 10, 3, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SHEEPUFF.get(), 12, 3, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.FLYING_COW.get(), 12, 2, 5))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY.get(), 11, 3, 3))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.MOA.get(), 8, 1, 3));
    }

    public static MobSpawnSettings.Builder cloudcapMobs(MobSpawnSettings.Builder builder)
    {
        return defaultMobSpawnsNoPassive(builder)
                .creatureGenerationProbability(0.25F)
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ReduxEntityTypes.SHIMMERCOW.get(), 12, 2, 5));

    }
    public static MobSpawnSettings.Builder increasedMobSpawns(MobSpawnSettings.Builder builder)
    {
        return defaultMobSpawnsNoPassive(builder)
                .creatureGenerationProbability(0.25F)
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.PHYG.get(), 10, 5, 7))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SHEEPUFF.get(), 12, 5, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.FLYING_COW.get(), 12, 4, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY.get(), 18, 3, 9))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.MOA.get(), 8, 1, 4));
    }


    public static BiomeGenerationSettings.Builder baseFeatures(BiomeGenerationSettings.Builder builder, boolean holidayTrees, boolean crystalTrees, boolean coldClouds, boolean blueClouds, boolean goldClouds)
    {
        builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherPlacedFeatures.QUICKSOIL_SHELF_PLACEMENT)
                .addFeature(GenerationStep.Decoration.LAKES, ReduxPlacedFeatures.SURFACE_RULE_WATER_LAKE)
                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, AetherPlacedFeatures.WATER_SPRING_PLACEMENT);
        if (holidayTrees) { builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.HOLIDAY_TREE_PLACEMENT); }
        if (crystalTrees) { builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.CRYSTAL_ISLAND_PLACEMENT); }
        if (coldClouds) { builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.COLD_AERCLOUD_PLACEMENT); }
        if (blueClouds) { builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.BLUE_AERCLOUD_PLACEMENT); }
        if (goldClouds) { builder.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.GOLDEN_AERCLOUD_PLACEMENT); }

        return builder;
    }
    public static BiomeGenerationSettings.Builder ores(BiomeGenerationSettings.Builder builder, boolean dirt, boolean icestone, boolean ambrosium, boolean zanite, boolean gravititeBuried, boolean gravitite)
    {
        if (dirt) { builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AETHER_DIRT_PLACEMENT); }
        if (icestone) { builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ICESTONE_PLACEMENT); }
        if (ambrosium) { builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AMBROSIUM_PLACEMENT); }
        if (zanite) { builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ZANITE_PLACEMENT); }
        if (gravititeBuried) { builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_BURIED_PLACEMENT); }
        if (gravitite) { builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_PLACEMENT); }
        return builder;
    }
    public static BiomeGenerationSettings.Builder ores(BiomeGenerationSettings.Builder builder)
    {
        return ores(builder, true, true, true, true, true, true);
    }


    public static BiomeGenerationSettings.Builder defaultFeatures(BiomeGenerationSettings.Builder builder)
    {
                                builder.addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherPlacedFeatures.QUICKSOIL_SHELF_PLACEMENT)
            .addFeature(GenerationStep.Decoration.LAKES, AetherPlacedFeatures.WATER_LAKE_PLACEMENT)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AETHER_DIRT_PLACEMENT)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ICESTONE_PLACEMENT)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AMBROSIUM_PLACEMENT)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ZANITE_PLACEMENT)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_BURIED_PLACEMENT)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_PLACEMENT)
            .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, AetherPlacedFeatures.WATER_SPRING_PLACEMENT)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.HOLIDAY_TREE_PLACEMENT)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.TALL_GRASS_PATCH_PLACEMENT)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.WHITE_FLOWER_PATCH_PLACEMENT)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.PURPLE_FLOWER_PATCH_PLACEMENT)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.BERRY_BUSH_PATCH_PLACEMENT)
            .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.CRYSTAL_ISLAND_PLACEMENT)
            .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.COLD_AERCLOUD_PLACEMENT)
            .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.BLUE_AERCLOUD_PLACEMENT)
            .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.GOLDEN_AERCLOUD_PLACEMENT);
                                return builder;
    }

    public static Biome fullDefinition(boolean precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }
}