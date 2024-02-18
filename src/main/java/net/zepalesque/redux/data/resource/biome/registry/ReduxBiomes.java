package net.zepalesque.redux.data.resource.biome.registry;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether_genesis.data.resources.registries.GenesisBiomes;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.biome.*;
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

    // TODO: Add to 2.1
    public static final ResourceKey<Biome> SHIMMERING_HILLS = createKey("shimmering_hills");
    public static final ResourceKey<Biome> QUICKSOIL_DUNES = createKey("quicksoil_dunes");
    public static final ResourceKey<Biome> QUICKSOIL_OASIS = createKey("quicksoil_oasis");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, Redux.locate(name));
    }

    private static ResourceKey<Biome> genesisKey(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation("aether_genesis", name));
    }

    public static final int AETHER_GRASS_COLOR = 0xB1FFCB;
    public static final int GILDED_GRASS_COLOR = 0xFFED96;
    public static final int GILDED_GRASSLANDS_COLOR = 0xFFF5A3;
    public static final int BLIGHT_GRASS_COLOR = 0xD5BAFF;
    public static final int FROSTED_GRASS_COLOR = 0xCCF7FF;
    public static final int HIGHFIELDS_GRASS_COLOR = 0xBFFFEC;
    public static final int SHRUBLANDS_GRASS_COLOR = 0xD7FFCC;
    public static final int CLOUDCAP_GRASS_COLOR = 0xD6FFF7;

    public static final int SHIMMERING_GRASS_COLOR = 0xBAF0FF;
    public static final int DUNES_GRASS_COLOR = 0xEEFFE0;
    public static final int OASIS_GRASS_COLOR = 0xD6FFD6;

    // Compat colors for grass tints
    public static final int FROZEN_GRASS_COLOR = 0xCBECFE;
    public static final int PALE_GRASS_COLOR = 0xC1E6CA;

    public static final int AERGLOW_GRASS_COLOR = 0xCFFFF1;
    public static final int AERLAVENDER_GRASS_COLOR = 0xBDEDCC;
    public static final int BLUE_AERGLOW_GRASS_COLOR = 0xCEFFFD;
    public static final int MYSTIC_AERGLOW_GRASS_COLOR = 0xD1FFE7;


    public static final ImmutableMap<ResourceKey<Biome>, Integer> AETHER_GRASS_COLORS = new ImmutableMap.Builder<ResourceKey<Biome>, Integer>()
            .put(THE_BLIGHT, BLIGHT_GRASS_COLOR)
            .put(GLACIAL_TAIGA, FROSTED_GRASS_COLOR)
            .put(FROSTED_TUNDRA, FROSTED_GRASS_COLOR)
            .put(GILDED_GROVES, GILDED_GRASS_COLOR)
            .put(GILDED_GRASSLANDS, GILDED_GRASSLANDS_COLOR)
            .put(HIGHFIELDS, HIGHFIELDS_GRASS_COLOR)
            .put(CLOUDCAPS, CLOUDCAP_GRASS_COLOR)
            .put(SKYROOT_SHRUBLANDS, SHRUBLANDS_GRASS_COLOR)
            .build();


    public static final ImmutableMap<ResourceKey<Biome>, Pair<Integer, Integer>> VANILLA_GRASS_COLORS = new ImmutableMap.Builder<ResourceKey<Biome>, Pair<Integer, Integer>>()
            .put(THE_BLIGHT, Pair.of(0x97B276, 0x819D5D))
            .put(GLACIAL_TAIGA, Pair.of(0x86B783, 0x68A464))
            .put(FROSTED_TUNDRA, Pair.of(0x86B783, 0x68A464))
            .put(GILDED_GROVES, Pair.of(0xACBA4F, 0x97A823))
            .put(GILDED_GRASSLANDS, Pair.of(0xACBA4F, 0x97A823))
            .put(HIGHFIELDS, Pair.of(0x59C93C, 0x30BB0B))
            .put(CLOUDCAPS, Pair.of(0x55C93F, 0x2BBB0F))
            .put(SKYROOT_SHRUBLANDS, Pair.of(0x9ABE4B, 0x82AC1E))
            .put(AetherBiomes.SKYROOT_FOREST, Pair.of(0x79C05A, 0x59AE30))
            .put(AetherBiomes.SKYROOT_WOODLAND, Pair.of(0x79C05A, 0x59AE30))
            .build();


    public static final ImmutableMap<TagKey<Biome>, Integer> OVERWORLD_BIOME_AETHER_GRASS_COLORS = new ImmutableMap.Builder<TagKey<Biome>, Integer>()
            .put(Tags.Biomes.IS_COLD, FROSTED_GRASS_COLOR)
            .put(Tags.Biomes.IS_DESERT, OASIS_GRASS_COLOR)
            .put(Tags.Biomes.IS_LUSH, HIGHFIELDS_GRASS_COLOR)
            .put(Tags.Biomes.IS_MUSHROOM, CLOUDCAP_GRASS_COLOR)
            .put(Tags.Biomes.IS_MAGICAL, SHIMMERING_GRASS_COLOR)
            .put(Tags.Biomes.IS_PLATEAU, GILDED_GRASS_COLOR)
            .put(Tags.Biomes.IS_SPARSE, GILDED_GRASSLANDS_COLOR)
            .put(BiomeTags.IS_NETHER, DUNES_GRASS_COLOR)
            .put(BiomeTags.IS_END, BLIGHT_GRASS_COLOR)
            .build();


    public static final int WATER = 5403045;
    public static final int WATER_FOG = 791347;

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(THE_BLIGHT, Blight.generate(context));
        context.register(CLOUDCAPS, Cloudcaps.generate(context));
        context.register(GLACIAL_TAIGA, GlacialTaiga.generate(context));
        context.register(FROSTED_TUNDRA, FrostedTundra.generate(context));
        context.register(GILDED_GROVES, GildedGroves.generate(context));
        context.register(GILDED_GRASSLANDS, GildedGrasslands.generate(context));
        context.register(HIGHFIELDS, Highfields.generate(context));
        context.register(SKYROOT_SHRUBLANDS, SkyrootShrublands.generate(context));
    }


    public static final int CRYSTAL_GRASS = 0xBFE5FF;


    public static Biome simpleBiome(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor) {
        return BiomeGenerator.biomeBase(gen, mobSpawns, music, waterColor, waterFogColor, grassColor, 0xc0_c0_ff, 0x93_93_bc, false, 0.8F, 0.0F);
    }

    public static Biome climateBiome(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor, boolean precip, float temp, float downfall) {
        return BiomeGenerator.biomeBase(gen, mobSpawns, music, waterColor, waterFogColor, grassColor, 0xc0_c0_ff, 0x93_93_bc, precip, temp, downfall);
    }

    public static Biome biomeSky(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor, int skyColor, int skyFogColor) {
        return BiomeGenerator.biomeBase(gen, mobSpawns, music, waterColor, waterFogColor, grassColor, skyColor, skyFogColor, false, 0.8F, 0.0F);
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
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ReduxEntityTypes.SHIMMERCOW.get(), 12, 2, 5))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ReduxEntityTypes.MYKAPOD.get(), 5, 1, 3));

    }
    public static MobSpawnSettings.Builder increasedMobSpawns(MobSpawnSettings.Builder builder) {
        return defaultMobSpawnsNoPassive(builder)
                .creatureGenerationProbability(0.25F)
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.PHYG.get(), 10, 5, 7))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SHEEPUFF.get(), 12, 5, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.FLYING_COW.get(), 12, 4, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY.get(), 18, 3, 9))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.MOA.get(), 8, 1, 4));
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