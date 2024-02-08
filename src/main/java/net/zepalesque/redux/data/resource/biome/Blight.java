package net.zepalesque.redux.data.resource.biome;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.data.resource.ReduxPlacedFeatures;

public class Blight {

    public static Biome generate(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8F)
                .downfall(0.0F)
                .specialEffects(
        new BiomeSpecialEffects.Builder()
                .fogColor(0xA591BC)
                .skyColor(0xC6C1FF)
                .waterColor(4607385)
                .waterFogColor(723770)
                .grassColorOverride(0xb1_ff_cb)
                .foliageColorOverride(0xb1_ff_cb)
                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                .backgroundMusic(ReduxMusic.DEFAULT_AETHER_MUSIC)
                .build())
                .mobSpawnSettings(
                new MobSpawnSettings.Builder()
                        .addMobCharge(AetherEntityTypes.COCKATRICE.get(), 0.3, 0.1)
                        .addMobCharge(AetherEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                        .addMobCharge(AetherEntityTypes.AECHOR_PLANT.get(), 0.4,0.11)
                        .addMobCharge(AetherEntityTypes.BLUE_SWET.get(), 0.5, 0.1)
                        .addMobCharge(AetherEntityTypes.GOLDEN_SWET.get(), 0.5, 0.1)
                        .addMobCharge(AetherEntityTypes.EVIL_WHIRLWIND.get(), 0.4, 0.1)

                        .addSpawn(AetherMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 8, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR.get(), 20, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AECHOR_PLANT.get(), 7, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.BLUE_SWET.get(), 6, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.GOLDEN_SWET.get(), 6, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.EVIL_WHIRLWIND.get(), 1, 1, 1))

                        .creatureGenerationProbability(0.25F)
                        // TODO: Blightbunny
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY.get(), 11, 3, 3))
                        .build())
                .generationSettings(
        new BiomeGenerationSettings.Builder(placed, carvers)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, ReduxPlacedFeatures.HOLYSILT_DISK)

                .addFeature(GenerationStep.Decoration.LAKES, ReduxPlacedFeatures.SURFACE_RULE_WATER_LAKE)

                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ReduxPlacedFeatures.BLIGHT_ROCK)

                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AETHER_DIRT_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ICESTONE_PLACEMENT)
                // TODO: Increased zanite and decreased ambrosium
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AMBROSIUM_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ZANITE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_BURIED_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.BLIGHTMOSS_HOLYSTONE_ORE)

                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.BLIGHTMOSS_VEGETATION)

                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, AetherPlacedFeatures.WATER_SPRING_PLACEMENT)

                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHT_TREES)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.HOLIDAY_TREE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GLOWSPROUTS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SPIROLYCTIL_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHTSHADE_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHTMOSS_VEGETATION)

                // TODO: bring back Blighted Aerclouds?
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.COLD_AERCLOUD_PLACEMENT)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.BLUE_AERCLOUD_PLACEMENT)

                .build())
                .build();
    }
}
