package net.zepalesque.redux.data.resource.biome;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.zepalesque.redux.data.resource.ReduxPlacedFeatures;
import net.zepalesque.redux.data.resource.biome.registry.BiomeGenerator;
import net.zepalesque.redux.entity.ReduxEntityTypes;

public class Cloudcaps implements BiomeGenerator {
    @Override
    public MobSpawnSettings.Builder mobspawns(MobSpawnSettings.Builder builder) {
        return builder
                .addMobCharge(AetherEntityTypes.COCKATRICE.get(), 0.5, 0.15)
                .addMobCharge(AetherEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                .addMobCharge(AetherEntityTypes.BLUE_SWET.get(), 0.5, 0.1)
                .addMobCharge(AetherEntityTypes.GOLDEN_SWET.get(), 0.5, 0.1)
                .addMobCharge(AetherEntityTypes.WHIRLWIND.get(), 0.4, 0.1)
                .addMobCharge(AetherEntityTypes.EVIL_WHIRLWIND.get(), 0.4, 0.1)

                .addSpawn(AetherMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 8, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR.get(), 20, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.BLUE_SWET.get(), 6, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.GOLDEN_SWET.get(), 6, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.WHIRLWIND.get(), 3, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.EVIL_WHIRLWIND.get(), 1, 1, 1))

                .creatureGenerationProbability(0.25F)
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ReduxEntityTypes.GLIMMERCOW.get(), 12, 2, 5))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ReduxEntityTypes.MYKAPOD.get(), 5, 1, 3));
    }

    @Override
    public BiomeGenerationSettings.Builder features(BiomeGenerationSettings.Builder builder) {
        return builder
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherPlacedFeatures.QUICKSOIL_SHELF_PLACEMENT)
                .addFeature(GenerationStep.Decoration.LAKES, ReduxPlacedFeatures.SURFACE_RULE_WATER_LAKE)
                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, AetherPlacedFeatures.WATER_SPRING_PLACEMENT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.HOLIDAY_TREE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.CRYSTAL_ISLAND_PLACEMENT)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.COLD_AERCLOUD_PLACEMENT)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.BLUE_AERCLOUD_PLACEMENT)
                .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.GOLDEN_AERCLOUD_PLACEMENT)

                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AETHER_DIRT_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ICESTONE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AMBROSIUM_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ZANITE_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_BURIED_PLACEMENT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_PLACEMENT)

                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.LARGE_MUSHROOMS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.AEVELIUM_GRASSES_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.CLOUDCAP_MUSHLING_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.JELLYSHROOM_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GLIMMERSTOOL_PATCH)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.LIGHTROOTS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.FUNGAL_VEGETATION)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ReduxPlacedFeatures.GLIMMERSTOOL_ROCK);
    }
}
