package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.world.placementmodifier.ConfigFilter;
import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.aetherteam.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.AbstractCondition;
import net.zepalesque.redux.api.condition.ReduxConfigCondition;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.world.placement.ConditionFilter;

import java.util.List;

import static net.zepalesque.redux.data.resource.Folders.ORE;

public class ReduxPlacedFeatures {

    public static final DungeonBlacklistFilter DUNGEON_BLACKLIST = new DungeonBlacklistFilter();
    public static final NoiseThresholdCountPlacement NOISE_THRESHOLD = NoiseThresholdCountPlacement.of(-0.8D, 5, 10);

    public static final ResourceKey<PlacedFeature> AEVELIUM_GRASSES_PATCH = copyKey(ReduxConfiguredFeatures.AEVELIUM_GRASSES_PATCH);
    public static final ResourceKey<PlacedFeature> AURUM_PATCH = copyKey(ReduxConfiguredFeatures.AURUM_PATCH);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_PURPLE_PATCH = createKey(Folders.PATCH + "shrublands_purple_patch");
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_WHITE_PATCH = createKey(Folders.PATCH + "shrublands_white_patch");
    public static final ResourceKey<PlacedFeature> ZANBERRY_BUSH_PATCH = copyKey(ReduxConfiguredFeatures.ZANBERRY_BUSH_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_SPARSE_VEGETATION = createKey(Folders.CAVE + "blightmoss_sparse_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_VEGETATION = createKey(Folders.CAVE + "blightmoss_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTSHADE_PATCH = copyKey(ReduxConfiguredFeatures.BLIGHTSHADE_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_ROCK = copyKey(ReduxConfiguredFeatures.BLIGHT_ROCK);
    public static final ResourceKey<PlacedFeature> SHIMMERSTOOL_ROCK = copyKey(ReduxConfiguredFeatures.SHIMMERSTOOL_ROCK);
    public static final ResourceKey<PlacedFeature> SHIMMERSTOOL_PATCH = copyKey(ReduxConfiguredFeatures.SHIMMERSTOOL_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_TREES = copyKey(ReduxConfiguredFeatures.BLIGHT_TREES);
    public static final ResourceKey<PlacedFeature> CLOUDCAP_MUSHLING_PATCH = copyKey(ReduxConfiguredFeatures.CLOUDCAP_MUSHLING_PATCH);
    public static final ResourceKey<PlacedFeature> DAGGERBLOOM_PATCH = copyKey(ReduxConfiguredFeatures.DAGGERBLOOM_PATCH);
    public static final ResourceKey<PlacedFeature> SPLITFERN_PATCH = copyKey(ReduxConfiguredFeatures.SPLITFERN_PATCH);
    public static final ResourceKey<PlacedFeature> AEROGEL_ORE = copyKey(ReduxConfiguredFeatures.AEROGEL_ORE);
    public static final ResourceKey<PlacedFeature> FROSTED_PURPLE_FLOWER_PATCH = copyKey(ReduxConfiguredFeatures.FROSTED_PURPLE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> GLACIAL_TREES = copyKey(ReduxConfiguredFeatures.GLACIAL_TREES);
    public static final ResourceKey<PlacedFeature> FROSTED_TREES = copyKey(ReduxConfiguredFeatures.FROSTED_TREES);
    public static final ResourceKey<PlacedFeature> GILDED_HOLYSTONE_ORE = copyKey(ReduxConfiguredFeatures.GILDED_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> GILDED_ROCK  = copyKey(ReduxConfiguredFeatures.GILDED_ROCK);
    public static final ResourceKey<PlacedFeature> GILDED_WHITE_FLOWER_PATCH = copyKey(ReduxConfiguredFeatures.GILDED_WHITE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> GLOWSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.GLOWSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_CLOVER_PATCH = copyKey(ReduxConfiguredFeatures.GOLDEN_CLOVER_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_HEIGHTS_GILDED_HOLYSTONE_ORE = createKey(ORE + "golden_heights_" + name(ReduxBlocks.GILDED_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> GROVE_TREES = copyKey(ReduxConfiguredFeatures.GROVE_TREES);
    public static final ResourceKey<PlacedFeature> GRASSLAND_TREES = copyKey(ReduxConfiguredFeatures.GRASSLAND_TREES);
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_ROCK  = copyKey(ReduxConfiguredFeatures.HIGHFIELDS_ROCK);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_ROCK  = copyKey(ReduxConfiguredFeatures.SHRUBLANDS_ROCK);
    public static final ResourceKey<PlacedFeature> SKYSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_TREES = copyKey(ReduxConfiguredFeatures.HIGHFIELDS_TREES);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_TREES = copyKey(ReduxConfiguredFeatures.SHRUBLANDS_TREES);
    public static final ResourceKey<PlacedFeature> IRIDIA_PATCH  = copyKey(ReduxConfiguredFeatures.IRIDIA_PATCH);
    public static final ResourceKey<PlacedFeature> LARGE_MUSHROOMS = copyKey(ReduxConfiguredFeatures.LARGE_MUSHROOMS);
    public static final ResourceKey<PlacedFeature> LUMINA_PATCH = copyKey(ReduxConfiguredFeatures.LUMINA_PATCH);
    public static final ResourceKey<PlacedFeature> LIGHTROOTS = copyKey(ReduxConfiguredFeatures.LIGHTROOTS);
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_ORE  = createKey(ORE + name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> MOSSY_ROCK  = copyKey(ReduxConfiguredFeatures.MOSSY_ROCK);
    public static final ResourceKey<PlacedFeature> WYNDSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_WYNDSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.GENESIS_WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_SKYSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.GENESIS_SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> SPIROLYCTIL_PATCH  = copyKey(ReduxConfiguredFeatures.SPIROLYCTIL_PATCH);
    public static final ResourceKey<PlacedFeature> JELLYSHROOM_PATCH = copyKey(ReduxConfiguredFeatures.JELLYSHROOM_PATCH);
    public static final ResourceKey<PlacedFeature> SURFACE_RULE_WATER_LAKE = copyKey(ReduxConfiguredFeatures.SURFACE_RULE_WATER_LAKE);
    public static final ResourceKey<PlacedFeature> VERIDIUM_ORE = copyKey(ReduxConfiguredFeatures.VERIDIUM_ORE);
    public static final ResourceKey<PlacedFeature> DIVINITE_ORE = copyKey(ReduxConfiguredFeatures.DIVINITE_ORE);
    public static final ResourceKey<PlacedFeature> HOLYSILT_DISK = copyKey(ReduxConfiguredFeatures.HOLYSILT_DISK);
    public static final ResourceKey<PlacedFeature> CLOUD_LAYER = copyKey(ReduxConfiguredFeatures.CLOUD_LAYER);

    public static final ResourceKey<PlacedFeature> AETHER_SNOW_LAYER = copyKey(ReduxConfiguredFeatures.AETHER_SNOW_LAYER);

    public static final ResourceKey<PlacedFeature> QUICKSOIL_SHELF_OVERRIDE = aetherKey("quicksoil_shelf");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {

        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, AEVELIUM_GRASSES_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.AEVELIUM_GRASSES_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                new ConfigFilter(AetherConfig.SERVER.generate_tall_grass),
                BiomeFilter.biome()
        );
        register(context, CLOUD_LAYER, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.CLOUD_LAYER)
        );
        register(context, AETHER_SNOW_LAYER, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.AETHER_SNOW_LAYER)
        );


        register(context, AURUM_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.AURUM_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, BiasedToBottomInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(12),
                BiomeFilter.biome());


        register(context, SHRUBLANDS_WHITE_PATCH, configuredFeatures.getOrThrow(AetherConfiguredFeatures.WHITE_FLOWER_PATCH_CONFIGURATION),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(7),
                BiomeFilter.biome());

        register(context, SHRUBLANDS_PURPLE_PATCH, configuredFeatures.getOrThrow(AetherConfiguredFeatures.PURPLE_FLOWER_PATCH_CONFIGURATION),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(16),
                BiomeFilter.biome());


        register(context, ZANBERRY_BUSH_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.AURUM_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, BiasedToBottomInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(16),
                BiomeFilter.biome());

        register(context, GILDED_WHITE_FLOWER_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GILDED_WHITE_FLOWER_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(12),
                BiomeFilter.biome()
        );

        register(context, BLIGHTMOSS_SPARSE_VEGETATION, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.BLIGHTMOSS_PATCH),
                CountPlacement.of(35),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(120))),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(Blocks.CAVE_AIR), BlockPredicate.solid(), 12),
                RandomOffsetPlacement.of(ConstantInt.ZERO, ConstantInt.of(1)),
                BiomeFilter.biome());

        register(context, BLIGHTMOSS_VEGETATION, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.BLIGHTMOSS_PATCH),
                CountPlacement.of(250),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(120))),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(Blocks.CAVE_AIR), BlockPredicate.solid(), 12),
                RandomOffsetPlacement.of(ConstantInt.ZERO, ConstantInt.of(1)),
                BiomeFilter.biome());

        register(context, BLIGHT_ROCK, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.BLIGHT_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BiomeFilter.biome()
        );
        register(context, SHIMMERSTOOL_ROCK, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SHIMMERSTOOL_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(12),
                InSquarePlacement.spread(),
                BiomeFilter.biome()
        );

        register(context, SHIMMERSTOOL_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SHIMMERSTOOL_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 3), 4),
                RarityFilter.onAverageOnceEvery(28),
                BiomeFilter.biome()
        );


        register(context, BLIGHTSHADE_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.BLIGHTSHADE_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1  , 3), 4),
                RarityFilter.onAverageOnceEvery(4),
                BiomeFilter.biome()
        );



        register(context, LARGE_MUSHROOMS, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.LARGE_MUSHROOMS),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(10), 9)
                        .add(ConstantInt.of(14), 1)
                        .build())),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST
        );

        register(context, CLOUDCAP_MUSHLING_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.CLOUDCAP_MUSHLING_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                BiomeFilter.biome(),
                RarityFilter.onAverageOnceEvery(12),
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.CLOUDCAP_MUSHLING.get()));



        register(context, DAGGERBLOOM_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.DAGGERBLOOM_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(10),
                BiomeFilter.biome()
        );

        register(context, SPLITFERN_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SPLITFERN_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 3), 4),
                RarityFilter.onAverageOnceEvery(7),
                BiomeFilter.biome());

        register(context, GLACIAL_TREES, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GLACIAL_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(24), 9)
                        .add(ConstantInt.of(16), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GLACIA_SAPLING.get())
        );

        register(context, FROSTED_TREES, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.FROSTED_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(15), 9)
                        .add(ConstantInt.of(12), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(/*TODO*/ReduxBlocks.GLACIA_SAPLING.get())
        );

        register(context, AEROGEL_ORE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.AEROGEL_ORE),
                CountPlacement.of(32),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                condition(ReduxConfigCondition.of(ReduxConfig.COMMON.mossy_holystone_ores)),
                BiomeFilter.biome()
        );

        register(context, FROSTED_PURPLE_FLOWER_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.FROSTED_PURPLE_FLOWER_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(13  ),
                BiomeFilter.biome()
        );

        register(context, GILDED_HOLYSTONE_ORE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GILDED_HOLYSTONE_ORE),
                CountPlacement.of(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                condition(ReduxConfigCondition.of(ReduxConfig.COMMON.mossy_holystone_ores)),
                BiomeFilter.biome()
        );

        register(context, GROVE_TREES, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GROVE_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(12), 9)
                        .add(ConstantInt.of(7), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())
        );
        register(context, GRASSLAND_TREES, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GRASSLAND_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(1), 9)
                        .add(ConstantInt.of(2), 1)
                        .add(ConstantInt.of(0), 4)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())
        );

        register(context, GOLDEN_HEIGHTS_GILDED_HOLYSTONE_ORE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GILDED_HOLYSTONE_ORE),
                CountPlacement.of(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                condition(ReduxConfigCondition.of(ReduxConfig.COMMON.mossy_holystone_ores)),
                BiomeFilter.biome()
        );


        register(context, GILDED_ROCK, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GILDED_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BiomeFilter.biome()
        );



        register(context, GLOWSPROUTS_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GLOWSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 2), 4),
                RarityFilter.onAverageOnceEvery(2),
                BiomeFilter.biome()
        );

        register(context, GOLDEN_CLOVER_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GOLDEN_CLOVER_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(4),
                BiomeFilter.biome()
        );


        register(context, HIGHFIELDS_ROCK, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.HIGHFIELDS_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 2)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BiomeFilter.biome()
        );

        register(context, SHRUBLANDS_ROCK, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SHRUBLANDS_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 6)
                                .add(UniformInt.of(1, 2), 3)
                                .add(UniformInt.of(1, 5), 1)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(12),
                InSquarePlacement.spread(),
                BiomeFilter.biome()
        );



        register(context, HIGHFIELDS_TREES, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.HIGHFIELDS_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(1), 9)
                        .add(ConstantInt.of(2), 3)
                        .add(ConstantInt.of(0), 5)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, ConstantInt.of(2), 4),
                BiomeFilter.biome(),
                RarityFilter.onAverageOnceEvery(2),
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.FLOWERING_FIELDSPROUT_SAPLING.get()),
                DUNGEON_BLACKLIST
        );

        register(context, SHRUBLANDS_TREES, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SHRUBLANDS_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(2), 9)
                        .add(ConstantInt.of(3), 3)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, ConstantInt.of(2), 4),
                BiomeFilter.biome(),
                RarityFilter.onAverageOnceEvery(2),
                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()),
                DUNGEON_BLACKLIST
        );

        register(context, IRIDIA_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.IRIDIA_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 3), 4),
                RarityFilter.onAverageOnceEvery(3),
                BiomeFilter.biome());

        register(context, LUMINA_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.LUMINA_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 2), 4),
                RarityFilter.onAverageOnceEvery(7),
                BiomeFilter.biome());

        register(context, LIGHTROOTS, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.LIGHTROOTS),
                CountPlacement.of(UniformInt.of(104, 157)),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, new VerticalAnchor.Absolute(256))),
                InSquarePlacement.spread(),
                BiomeFilter.biome());

        register(context, MOSSY_HOLYSTONE_ORE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.MOSSY_HOLYSTONE_ORE),
                CountPlacement.of(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                condition(ReduxConfigCondition.of(ReduxConfig.COMMON.mossy_holystone_ores)),
                BiomeFilter.biome()
        );

        register(context, MOSSY_ROCK, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.MOSSY_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BiomeFilter.biome()
        );
        register(context, HOLYSILT_DISK, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.HOLYSILT_DISK),
                RarityFilter.onAverageOnceEvery(5),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST
        );

        register(context, SURFACE_RULE_WATER_LAKE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SURFACE_RULE_WATER_LAKE),
                RarityFilter.onAverageOnceEvery(15),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome()
        );

        register(context, WYNDSPROUTS_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.WYNDSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(2),
                BiomeFilter.biome()
        );
        register(context, GENESIS_WYNDSPROUTS_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GENESIS_WYNDSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(4),
                BiomeFilter.biome()
        );

        register(context, GENESIS_SKYSPROUTS_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.GENESIS_SKYSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(4),
                BiomeFilter.biome()
        );

        register(context, SKYSPROUTS_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SKYSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, BiasedToBottomInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(3),
                BiomeFilter.biome()
        );

        register(context, SPIROLYCTIL_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.SPIROLYCTIL_PATCH),
                NOISE_THRESHOLD,
                RarityFilter.onAverageOnceEvery(5),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 3), 4),
                BiomeFilter.biome());

        register(context, JELLYSHROOM_PATCH, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.JELLYSHROOM_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(5),
                BiomeFilter.biome());

        register(context, BLIGHT_TREES, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.BLIGHT_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(UniformInt.of(10, 12), 5)
                        .add(ConstantInt.of(19), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.BLIGHTWILLOW_SAPLING.get()),
                DUNGEON_BLACKLIST
        );

        register(context, VERIDIUM_ORE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.VERIDIUM_ORE),
                CountPlacement.of(6),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                BiomeFilter.biome()
        );

        register(context, DIVINITE_ORE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.DIVINITE_ORE),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(128))),
                BiomeFilter.biome()
        );

        register(context, QUICKSOIL_SHELF_OVERRIDE, configuredFeatures.getOrThrow(ReduxConfiguredFeatures.ROOTED_QUICKSOIL_SHELF),
                RarityFilter.onAverageOnceEvery(5),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST
        );


    }



    private static ConditionFilter condition(AbstractCondition<?> condition)
    {
        return new ConditionFilter(condition);
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Redux.MODID, name));
    }
    private static ResourceKey<PlacedFeature> aetherKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Aether.MODID, name));
    }
    private static String name(RegistryObject<?> reg)
    {
        return reg.getId().getPath();
    }

    private static ResourceKey<PlacedFeature> copyKey(ResourceKey<ConfiguredFeature<?, ?>> configFeat)
    {
        return createKey(configFeat.location().getPath());
    }

}
