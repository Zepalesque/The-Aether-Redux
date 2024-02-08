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

// Todo: Colder water color?
public class GlacialTaiga implements BiomeGenerator {
    @Override
    public MobSpawnSettings.Builder mobspawns(MobSpawnSettings.Builder builder) {
        return BiomeGenerator.defaultMobs(builder);
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

                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.GLACIAL_TREES)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.LUMINA_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.FROSTED_PURPLE_FLOWER_PATCH)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.DAGGERBLOOM_PATCH)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ReduxPlacedFeatures.AEROGEL_ORE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ReduxPlacedFeatures.SPLITFERN_PATCH);
    }

    @Override
    public float temperature() {
        return -1.6F;
    }
    @Override
    public float downfall() {
        return 0.4F;
    }

    @Override
    public boolean precipitation() {
        return true;
    }
}
