package net.zepalesque.redux.data.resource;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.biome.modifier.CarverModifier;
import net.zepalesque.redux.world.biome.modifier.WaterColorBiomeModifier;
import net.zepalesque.redux.world.biome.modifier.WaterColorReplacementBiomeModifier;

import java.util.List;

public class ReduxBiomeModifiers {
    public static String FEATURE = "feature/";
    public static String MOB = "mob/";
    public static String MODIFY = "modify/";

    public static final ResourceKey<BiomeModifier> ADD_AETHER_CAVES = createKey(FEATURE + "aether_caves");
    public static final ResourceKey<BiomeModifier> ADD_BLIGHTED_CAVES = createKey(FEATURE + "blighted_caves");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_HOLYSTONE_ORE = createKey(FEATURE + "mossy_holystone_ore");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_ROCKS = createKey(FEATURE + "mossy_rocks");
    public static final ResourceKey<BiomeModifier> ADD_SKYSPROUTS = createKey(FEATURE + "skysprouts");
    public static final ResourceKey<BiomeModifier> ADD_VANILLA_SWET = createKey(MOB + "vanilla_swet");
    public static final ResourceKey<BiomeModifier> ADD_VERIDIUM = createKey(FEATURE + "veridium_ore");
    public static final ResourceKey<BiomeModifier> ADD_DIVINITE = createKey(FEATURE + "divinite");
    public static final ResourceKey<BiomeModifier> WATER_COLOR_AETHER = createKey(MODIFY + "water_color");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Redux.locate(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        context.register(ADD_AETHER_CAVES, new CarverModifier(biomes.getOrThrow(ReduxTags.Biomes.HAS_AETHER_CAVES), carvers.getOrThrow(ReduxConfiguredCarvers.AETHER_CAVES)));

        context.register(ADD_BLIGHTED_CAVES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_BLIGHTED_CAVES), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.BLIGHTMOSS_SPARSE_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_MOSSY_HOLYSTONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_HOLYSTONE_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.MOSSY_HOLYSTONE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_MOSSY_ROCKS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_ROCKS), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.MOSSY_ROCK)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_SKYSPROUTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_SKYSPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_VANILLA_SWET, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VANILLA_SWET), List.of(new MobSpawnSettings.SpawnerData(ReduxEntityTypes.VANILLA_SWET.get(), 5, 1, 2))));

        context.register(ADD_VERIDIUM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VERIDIUM_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.VERIDIUM_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DIVINITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_DIVINITE), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.DIVINITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(WATER_COLOR_AETHER, new WaterColorReplacementBiomeModifier(biomes.getOrThrow(ReduxTags.Biomes.HAS_REDUX_WATER_COLOR), WaterColorReplacementBiomeModifier.WaterColorPredicate.of(4159204, 329011), 5403045, 791347));

    }
}
