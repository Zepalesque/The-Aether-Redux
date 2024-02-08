package net.zepalesque.redux.data.resource.biome;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.zepalesque.redux.data.resource.ReduxPlacedFeatures;
import net.zepalesque.redux.data.resource.biome.registry.BiomeGenerator;

public class Blight implements BiomeGenerator {
    @Override
    public MobSpawnSettings.Builder mobspawns(MobSpawnSettings.Builder builder) {
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

                .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, ReduxPlacedFeatures.BLIGHT_ROCK)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHT_TREES)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GLOWSPROUTS_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SPIROLYCTIL_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHTSHADE_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.BLIGHTMOSS_VEGETATION)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ReduxPlacedFeatures.BLIGHTMOSS_VEGETATION)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.HOLYSILT_DISK)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.BLIGHTMOSS_HOLYSTONE_ORE);
    }

    @Override
    public int sky() {
        return 0xC6C1FF;
    }

    @Override
    public int skyFog() {
        return 0xA591BC;
    }

    @Override
    public int water() {
        return 4607385;
    }

    @Override
    public int waterFog() {
        return 723770;
    }
}
