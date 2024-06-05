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

public class GildedGroves {


    public static Biome generate(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8F)
                .downfall(0.0F)
                .specialEffects(
        new BiomeSpecialEffects.Builder()
                .skyColor(0xC4BDAA)
                .fogColor(0xDDD9DA)
                .waterColor(0x5271A5)
                .waterFogColor(0x0C1333)
                .grassColorOverride(0xb1_ff_cb)
                .foliageColorOverride(0xb1_ff_cb)
                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                .backgroundMusic(ReduxMusic.DEFAULT_AETHER_MUSIC)
                .build())
                .mobSpawnSettings(
                new MobSpawnSettings.Builder()
                        .addMobCharge(AetherEntityTypes.COCKATRICE.get(), 0.5, 0.15)
                        .addMobCharge(AetherEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                        .addMobCharge(AetherEntityTypes.AECHOR_PLANT.get(), 0.4,0.11)
                        .addMobCharge(AetherEntityTypes.GOLDEN_SWET.get(), 0.5, 0.1)
                        .addMobCharge(AetherEntityTypes.WHIRLWIND.get(), 0.4, 0.1)
                        .addMobCharge(AetherEntityTypes.EVIL_WHIRLWIND.get(), 0.4, 0.1)
                        .addMobCharge(AetherEntityTypes.AERWHALE.get(), 0.5, 0.11)

                        .addSpawn(AetherMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 8, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR.get(), 20, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AECHOR_PLANT.get(), 7, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.GOLDEN_SWET.get(), 6, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.WHIRLWIND.get(), 3, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.EVIL_WHIRLWIND.get(), 1, 1, 1))
                        .addSpawn(AetherMobCategory.AETHER_AERWHALE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERWHALE.get(), 10, 1, 1))

                        .creatureGenerationProbability(0.25F)
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.PHYG.get(), 12, 3, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SHEEPUFF.get(), 8, 3, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.FLYING_COW.get(), 17, 2, 5))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY.get(), 8, 3, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.MOA.get(), 7, 1, 3))
                        .build())
                .generationSettings(
        new BiomeGenerationSettings.Builder(placed, carvers)

                .addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherPlacedFeatures.QUICKSOIL_SHELF_PLACEMENT)

                .addFeature(GenerationStep.Decoration.LAKES, ReduxPlacedFeatures.SURFACE_RULE_WATER_LAKE)

                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AETHER_DIRT_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ICESTONE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.DENSE_AMBROSIUM_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.SPARSE_ZANITE_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_BURIED_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.GILDED_HOLYSTONE_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.DIVINITE_ORE_INCREASED)

                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, AetherPlacedFeatures.WATER_SPRING_PLACEMENT)

                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GROVE_TREES)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GROVE_TREES_ALT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.HOLIDAY_TREE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.ANCIENT_ENCHANTED_GRASS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GILDED_WHITE_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GOLDEN_CLOVER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.AURUM_PATCH)

                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.COLD_AERCLOUD_PLACEMENT)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ReduxPlacedFeatures.SPARSE_BLUE_AERCLOUD)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.GOLDEN_AERCLOUD_PLACEMENT)

                .build())
                .build();
    }
}
