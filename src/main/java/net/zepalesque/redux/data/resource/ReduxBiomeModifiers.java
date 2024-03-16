package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.util.function.CodecPredicates;
import net.zepalesque.redux.world.biome.modifier.*;

import java.util.List;
import java.util.Optional;

public class ReduxBiomeModifiers {
    public static String FEATURE = "feature/";
    public static String MOB = "mob/";
    public static String MODIFY = "modify/";
    public static String AETHER_GRASS_COLOR = "aether_grass_colors/";
    public static String GRASS_COLOR_BASE = "grass_color_base/";
    public static String OVERWORLD_AETHER_GRASS = "overworld_aether_grass/";

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
    public static final ResourceKey<BiomeModifier> WATER_COLOR_AETHER = createKey(MODIFY + "water_color");
    public static final ResourceKey<BiomeModifier> AETHER_COLOR_OVERRIDE = createKey(MODIFY + "aether_color_override");
    public static final ResourceKey<BiomeModifier> MUSIC_MODIFY = createKey(MODIFY + "music_modify");

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

        context.register(ADD_FUNGAL_CAVES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_FUNGAL_CAVES), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.FUNGAL_SPARSE_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_MOSSY_HOLYSTONE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_HOLYSTONE_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.MOSSY_HOLYSTONE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_MOSSY_ROCKS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_MOSSY_ROCKS), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.MOSSY_ROCK)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_SNOW, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(AetherTags.Biomes.IS_AETHER), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.AETHER_SNOW_LAYER)),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION));

        context.register(ADD_WYNDSPROUTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_WYNDSPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_GENESIS_SPROUTS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_BOTH_SPROUTS), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.GENESIS_SKYSPROUTS_PATCH), features.getOrThrow(ReduxPlacedFeatures.GENESIS_WYNDSPROUTS_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_VANILLA_SWET, new ReduxSpawnsModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VANILLA_SWET), List.of(new MobSpawnSettings.SpawnerData(ReduxEntityTypes.VANILLA_SWET.get(), 5, 1, 2)), Conditions.VANILLA_SWETS, 0.5, 0.1));

        context.register(ADD_ENDERMAN, new ReduxSpawnsModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_ENDERMAN), List.of(new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 1, 4)), Conditions.ENDERMEN, 1.0, 0.12));

        context.register(ADD_VERIDIUM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_VERIDIUM_ORE), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.VERIDIUM_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_DIVINITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_DIVINITE), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.DIVINITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_SENTRITE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_SENTRITE), HolderSet.direct(features.getOrThrow(ReduxPlacedFeatures.SENTRITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(WATER_COLOR_AETHER, new WaterColorReplacementBiomeModifier(biomes.getOrThrow(ReduxTags.Biomes.HAS_REDUX_WATER_COLOR),
                CodecPredicates.DualInt.of(4159204, 329011), 0x5492A8, 0x0D2835, Conditions.WATER));

        context.register(AETHER_COLOR_OVERRIDE, new GrassAndFoliageColorModifier(biomes.getOrThrow(ReduxTags.Biomes.NO_GRASS_OVERRIDE),
                0x91BD59, 0x77AB2F));

        context.register(MUSIC_MODIFY, new MusicModifier(biomes.getOrThrow(ReduxTags.Biomes.MUSIC_MODIFY),
                Optional.of(ReduxSoundEvents.REDUX_MENU.getHolder().get()), Optional.of(CodecPredicates.DualInt.of(600, 2400)), Optional.of(false), Optional.of(CodecPredicates.Sound.of(AetherSoundEvents.MUSIC_AETHER.getHolder().get())), Optional.empty(), Optional.empty()));



        ReduxBiomes.AETHER_GRASS_COLORS.forEach((key, color) ->
                context.register(createKey(AETHER_GRASS_COLOR + key.location().getPath()),
                new AetherGrassColorModifier(HolderSet.direct(biomes.getOrThrow(key)), color)));
        ReduxBiomes.VANILLA_GRASS_COLORS.forEach((key, colors) ->
                context.register(createKey(GRASS_COLOR_BASE + key.location().getPath()),
                new GrassAndFoliageColorModifier(HolderSet.direct(biomes.getOrThrow(key)), colors.getFirst(), colors.getSecond())));
        ReduxBiomes.OVERWORLD_BIOME_AETHER_GRASS_COLORS.forEach((key, colors) ->
                context.register(createKey(OVERWORLD_AETHER_GRASS + key.location().getPath()),
                new AetherGrassColorModifier(biomes.getOrThrow(key), colors)));
    }
}
