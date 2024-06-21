package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.builders.AetherPlacedFeatureBuilders;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.world.placementmodifier.ConfigFilter;
import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.aetherteam.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
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
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.world.placement.ConditionFilter;

import java.util.List;

import static net.zepalesque.redux.data.resource.Folders.ORE;

public class ReduxPlacements {

    public static final DungeonBlacklistFilter DUNGEON_BLACKLIST = new DungeonBlacklistFilter();
    public static final NoiseThresholdCountPlacement NOISE_THRESHOLD = NoiseThresholdCountPlacement.of(-0.8D, 5, 10);

    public static final ResourceKey<PlacedFeature> AEVELIUM_GRASSES_PATCH = copyKey(ReduxFeatureConfig.AEVELIUM_GRASSES_PATCH);
    public static final ResourceKey<PlacedFeature> AURUM_PATCH = copyKey(ReduxFeatureConfig.AURUM_PATCH);
    public static final ResourceKey<PlacedFeature> ZYATRIX_PATCH = copyKey(ReduxFeatureConfig.ZYATRIX_PATCH);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_PURPLE_PATCH = createKey(Folders.PATCH + "shrublands_purple_patch");
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_WHITE_PATCH = createKey(Folders.PATCH + "shrublands_white_patch");
    public static final ResourceKey<PlacedFeature> ZANBERRY_BUSH_PATCH = copyKey(ReduxFeatureConfig.ZANBERRY_BUSH_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_SPARSE_VEGETATION = createKey(Folders.CAVE + "blightmoss_sparse_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_VEGETATION = createKey(Folders.CAVE + "blightmoss_vegetation");
    public static final ResourceKey<PlacedFeature> FUNGAL_SPARSE_VEGETATION = createKey(Folders.CAVE + "fungal_sparse_vegetation");
    public static final ResourceKey<PlacedFeature> FUNGAL_VEGETATION = createKey(Folders.CAVE + "fungal_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTSHADE_PATCH = copyKey(ReduxFeatureConfig.BLIGHTSHADE_PATCH);
    public static final ResourceKey<PlacedFeature> CORRUPTED_VINES_PATCH = copyKey(ReduxFeatureConfig.CORRUPTED_VINES_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_ROCK = copyKey(ReduxFeatureConfig.BLIGHT_ROCK);
    public static final ResourceKey<PlacedFeature> GLIMMERSTOOL_ROCK = copyKey(ReduxFeatureConfig.GLIMMERSTOOL_ROCK);
    public static final ResourceKey<PlacedFeature> DENSE_GRASS = createKey(Folders.PATCH + "dense_grass");
    public static final ResourceKey<PlacedFeature> GLIMMERSTOOL_PATCH = copyKey(ReduxFeatureConfig.GLIMMERSTOOL_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_TREES = copyKey(ReduxFeatureConfig.BLIGHT_TREES);
    public static final ResourceKey<PlacedFeature> CLOUDCAP_MUSHLING_PATCH = copyKey(ReduxFeatureConfig.CLOUDCAP_MUSHLING_PATCH);
    public static final ResourceKey<PlacedFeature> DAGGERBLOOM_PATCH = copyKey(ReduxFeatureConfig.DAGGERBLOOM_PATCH);
    public static final ResourceKey<PlacedFeature> THERATIP_PATCH = copyKey(ReduxFeatureConfig.THERATIP_PATCH);
    public static final ResourceKey<PlacedFeature> SPLITFERN_PATCH = copyKey(ReduxFeatureConfig.SPLITFERN_PATCH);
    public static final ResourceKey<PlacedFeature> AEROGEL_ORE = copyKey(ReduxFeatureConfig.AEROGEL_ORE);
    public static final ResourceKey<PlacedFeature> SPARSE_AEROGEL_ORE = createKey(Folders.ORE + "sparse_aerogel_ore");
    public static final ResourceKey<PlacedFeature> BLIGHTED_AERCLOUD = createKey(Folders.MISC + "blighted_aercloud");
    public static final ResourceKey<PlacedFeature> SPARSE_BLUE_AERCLOUD = createKey(Folders.MISC + "sparse_blue_aercloud");
    public static final ResourceKey<PlacedFeature> SPARSE_ZANITE_ORE = createKey(Folders.ORE + "sparse_zanite_ore");
    public static final ResourceKey<PlacedFeature> SPARSE_AMBROSIUM_ORE = createKey(Folders.ORE + "sparse_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> DENSE_ZANITE_ORE = createKey(Folders.ORE + "dense_zanite_ore");
    public static final ResourceKey<PlacedFeature> DENSE_AMBROSIUM_ORE = createKey(Folders.ORE + "dense_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> DENSE_BLUE_AERCLOUD = createKey(Folders.MISC + "dense_blue_aercloud");
    public static final ResourceKey<PlacedFeature> LARGE_ICESTONE_CHUNK = copyKey(ReduxFeatureConfig.LARGE_ICESTONE_CHUNK);
    public static final ResourceKey<PlacedFeature> FROSTED_PURPLE_FLOWER_PATCH = copyKey(ReduxFeatureConfig.FROSTED_PURPLE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> FROSTED_TREES = copyKey(ReduxFeatureConfig.FROSTED_TREES);
    public static final ResourceKey<PlacedFeature> GLACIAL_TREES = copyKey(ReduxFeatureConfig.GLACIAL_TREES);
    public static final ResourceKey<PlacedFeature> GILDED_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.BLIGHTMOSS_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> GILDED_ROCK  = copyKey(ReduxFeatureConfig.GILDED_ROCK);
    public static final ResourceKey<PlacedFeature> GILDED_WHITE_FLOWER_PATCH = copyKey(ReduxFeatureConfig.GILDED_WHITE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> GLOWSPROUTS_PATCH = copyKey(ReduxFeatureConfig.GLOWSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_CLOVER_PATCH = copyKey(ReduxFeatureConfig.GOLDEN_CLOVER_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_HEIGHTS_GILDED_HOLYSTONE_ORE = createKey(ORE + "golden_heights_" + name(ReduxBlocks.GILDED_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> GROVE_TREES = copyKey(ReduxFeatureConfig.GROVE_TREES);
    public static final ResourceKey<PlacedFeature> GRASSLAND_TREES = copyKey(ReduxFeatureConfig.GRASSLAND_TREES);
    public static final ResourceKey<PlacedFeature> GROVE_TREES_ALT = copyKey(ReduxFeatureConfig.GROVE_TREES_ALT);
    public static final ResourceKey<PlacedFeature> GRASSLAND_TREES_ALT = copyKey(ReduxFeatureConfig.GRASSLAND_TREES_ALT);
    public static final ResourceKey<PlacedFeature> SKYFIELDS_ROCK = copyKey(ReduxFeatureConfig.SKYFIELDS_ROCK);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_ROCK  = copyKey(ReduxFeatureConfig.SHRUBLANDS_ROCK);
    public static final ResourceKey<PlacedFeature> SKYSPROUTS_PATCH = copyKey(ReduxFeatureConfig.SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> SKYFIELDS_TREES = copyKey(ReduxFeatureConfig.SKYFIELDS_TREES);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_TREES = copyKey(ReduxFeatureConfig.SHRUBLANDS_TREES);
    public static final ResourceKey<PlacedFeature> SHIMMERING_TREES = createKey(Folders.TREE + "shimmering_trees");
    public static final ResourceKey<PlacedFeature> IRIDIA_PATCH  = copyKey(ReduxFeatureConfig.IRIDIA_PATCH);
    public static final ResourceKey<PlacedFeature> XAELIA_PATCH  = copyKey(ReduxFeatureConfig.XAELIA_PATCH);
    public static final ResourceKey<PlacedFeature> LARGE_MUSHROOMS = copyKey(ReduxFeatureConfig.LARGE_MUSHROOMS);
    public static final ResourceKey<PlacedFeature> LUMINA_PATCH = copyKey(ReduxFeatureConfig.LUMINA_PATCH);
    public static final ResourceKey<PlacedFeature> LIGHTROOTS = copyKey(ReduxFeatureConfig.LIGHTROOTS);
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_ORE  = createKey(ORE + name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> MOSSY_ROCK  = copyKey(ReduxFeatureConfig.MOSSY_ROCK);
    public static final ResourceKey<PlacedFeature> ICESTONE_ROCK  = copyKey(ReduxFeatureConfig.ICESTONE_ROCK);
    public static final ResourceKey<PlacedFeature> WYNDSPROUTS_PATCH = copyKey(ReduxFeatureConfig.WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_WYNDSPROUTS_PATCH = copyKey(ReduxFeatureConfig.GENESIS_WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_SKYSPROUTS_PATCH = copyKey(ReduxFeatureConfig.GENESIS_SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> SPIROLYCTIL_PATCH  = copyKey(ReduxFeatureConfig.SPIROLYCTIL_PATCH);
    public static final ResourceKey<PlacedFeature> JELLYSHROOM_PATCH = copyKey(ReduxFeatureConfig.JELLYSHROOM_PATCH);
    public static final ResourceKey<PlacedFeature> SURFACE_RULE_WATER_LAKE = copyKey(ReduxFeatureConfig.SURFACE_RULE_WATER_LAKE);
    public static final ResourceKey<PlacedFeature> BLIGHT_LAKE = copyKey(ReduxFeatureConfig.BLIGHT_LAKE);
    public static final ResourceKey<PlacedFeature> BLIGHT_SPRING = copyKey(ReduxFeatureConfig.BLIGHT_SPRING);
    public static final ResourceKey<PlacedFeature> OASIS_LAKE = createKey(Folders.SURFACE + "oasis_lake");
    public static final ResourceKey<PlacedFeature> VERIDIUM_ORE = copyKey(ReduxFeatureConfig.VERIDIUM_ORE);
    public static final ResourceKey<PlacedFeature> DIVINITE_ORE = copyKey(ReduxFeatureConfig.DIVINITE_ORE);
    public static final ResourceKey<PlacedFeature> SENTRITE_ORE = copyKey(ReduxFeatureConfig.SENTRITE_ORE);
    public static final ResourceKey<PlacedFeature> DIVINITE_ORE_INCREASED = createKey(Folders.ORE + "divinite_ore_increased");
    public static final ResourceKey<PlacedFeature> HOLYSILT_DISK = copyKey(ReduxFeatureConfig.HOLYSILT_DISK);
    public static final ResourceKey<PlacedFeature> AEROGEL_DISK = copyKey(ReduxFeatureConfig.AEROGEL_DISK);
    public static final ResourceKey<PlacedFeature> CLOUD_LAYER = copyKey(ReduxFeatureConfig.CLOUD_LAYER);

    public static final ResourceKey<PlacedFeature> AETHER_SNOW_LAYER = copyKey(ReduxFeatureConfig.AETHER_SNOW_LAYER);

    public static final ResourceKey<PlacedFeature> ANCIENT_ENCHANTED_GRASS = copyKey(ReduxFeatureConfig.ANCIENT_ENCHANTED_GRASS);

    public static final ResourceKey<PlacedFeature> BONEMEAL_OVERRIDE = AetherPlacedFeatures.AETHER_GRASS_BONEMEAL;


    public static void bootstrap(BootstapContext<PlacedFeature> context) {

        HolderGetter<ConfiguredFeature<?, ?>> configs = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, AEVELIUM_GRASSES_PATCH, configs.getOrThrow(ReduxFeatureConfig.AEVELIUM_GRASSES_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                new ConfigFilter(AetherConfig.SERVER.generate_tall_grass),
                BiomeFilter.biome()
        );
        register(context, ANCIENT_ENCHANTED_GRASS, configs.getOrThrow(ReduxFeatureConfig.ANCIENT_ENCHANTED_GRASS),
                NoiseThresholdCountPlacement.of(-0.8, 10, 20),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                new ConfigFilter(AetherConfig.SERVER.generate_tall_grass),
                ConditionFilter.whenTrue(Conditions.ANCIENT),
                BiomeFilter.biome()
        );
        register(context, CLOUD_LAYER, configs.getOrThrow(ReduxFeatureConfig.CLOUD_LAYER));
        register(context, AETHER_SNOW_LAYER, configs.getOrThrow(ReduxFeatureConfig.AETHER_SNOW_LAYER)
        );


        register(context, AURUM_PATCH, configs.getOrThrow(ReduxFeatureConfig.AURUM_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, BiasedToBottomInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(12),
                BiomeFilter.biome());

        register(context, SPARSE_BLUE_AERCLOUD,
                configs.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION),
                AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 48));

        register(context, DENSE_BLUE_AERCLOUD,
                configs.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION),
                AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 14));


        register(context, BLIGHTED_AERCLOUD,
                configs.getOrThrow(ReduxFeatureConfig.BLIGHTED_AERCLOUD),
                AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 24));


        register(context, SPARSE_AMBROSIUM_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));

        register(context, SPARSE_ZANITE_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));

        register(context, DENSE_AMBROSIUM_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));

        register(context, DENSE_ZANITE_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(21, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));


        register(context, ZYATRIX_PATCH, configs.getOrThrow(ReduxFeatureConfig.ZYATRIX_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, BiasedToBottomInt.of(0, 3), 4),
                RarityFilter.onAverageOnceEvery(13),
                BiomeFilter.biome());


        register(context, SHRUBLANDS_WHITE_PATCH, configs.getOrThrow(AetherConfiguredFeatures.WHITE_FLOWER_PATCH_CONFIGURATION),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(15),
                BiomeFilter.biome());

        register(context, SHRUBLANDS_PURPLE_PATCH, configs.getOrThrow(AetherConfiguredFeatures.PURPLE_FLOWER_PATCH_CONFIGURATION),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(25),
                BiomeFilter.biome());


        register(context, ZANBERRY_BUSH_PATCH, configs.getOrThrow(ReduxFeatureConfig.ZANBERRY_BUSH_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, BiasedToBottomInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(9),
                BiomeFilter.biome());

        register(context, GILDED_WHITE_FLOWER_PATCH, configs.getOrThrow(ReduxFeatureConfig.GILDED_WHITE_FLOWER_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(12),
                BiomeFilter.biome()
        );

        register(context, BLIGHTMOSS_SPARSE_VEGETATION, configs.getOrThrow(ReduxFeatureConfig.BLIGHTMOSS_PATCH),
                CountPlacement.of(10),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(120))),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(Blocks.CAVE_AIR), BlockPredicate.solid(), 12),
                RandomOffsetPlacement.of(ConstantInt.ZERO, ConstantInt.of(1)),
                BiomeFilter.biome());

        register(context, BLIGHTMOSS_VEGETATION, configs.getOrThrow(ReduxFeatureConfig.BLIGHTMOSS_PATCH),
                CountPlacement.of(250),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(120))),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(Blocks.CAVE_AIR), BlockPredicate.solid(), 12),
                RandomOffsetPlacement.of(ConstantInt.ZERO, ConstantInt.of(4)),
                BiomeFilter.biome());

        register(context, FUNGAL_SPARSE_VEGETATION, configs.getOrThrow(ReduxFeatureConfig.FUNGAL_PATCH),
                CountPlacement.of(10),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(120))),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(Blocks.CAVE_AIR), BlockPredicate.solid(), 14),
                RandomOffsetPlacement.of(ConstantInt.ZERO, ConstantInt.of(1)),
                BiomeFilter.biome());

        register(context, FUNGAL_VEGETATION, configs.getOrThrow(ReduxFeatureConfig.FUNGAL_PATCH),
                CountPlacement.of(225),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(120))),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(Blocks.CAVE_AIR), BlockPredicate.solid(), 14),
                RandomOffsetPlacement.of(ConstantInt.ZERO, ConstantInt.of(4)),
                BiomeFilter.biome());

        register(context, BLIGHT_ROCK, configs.getOrThrow(ReduxFeatureConfig.BLIGHT_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );
        register(context, GLIMMERSTOOL_ROCK, configs.getOrThrow(ReduxFeatureConfig.GLIMMERSTOOL_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(12),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );

        register(context, GLIMMERSTOOL_PATCH, configs.getOrThrow(ReduxFeatureConfig.GLIMMERSTOOL_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 3), 4),
                RarityFilter.onAverageOnceEvery(28),
                BiomeFilter.biome()
        );

        register(context, DENSE_GRASS, configs.getOrThrow(AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10), ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 7),
                BiomeFilter.biome(), new ConfigFilter(AetherConfig.SERVER.generate_tall_grass));



        register(context, BLIGHTSHADE_PATCH, configs.getOrThrow(ReduxFeatureConfig.BLIGHTSHADE_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 3), 4),
                RarityFilter.onAverageOnceEvery(16),
                BiomeFilter.biome()
        );

        register(context, CORRUPTED_VINES_PATCH, configs.getOrThrow(ReduxFeatureConfig.CORRUPTED_VINES_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, ConstantInt.of(1), 4),
                RarityFilter.onAverageOnceEvery(21),
                BiomeFilter.biome()
        );



        register(context, LARGE_MUSHROOMS, configs.getOrThrow(ReduxFeatureConfig.LARGE_MUSHROOMS),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(10), 9)
                        .add(ConstantInt.of(14), 1)
                        .build())),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST
        );

        register(context, CLOUDCAP_MUSHLING_PATCH, configs.getOrThrow(ReduxFeatureConfig.CLOUDCAP_MUSHLING_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                BiomeFilter.biome(),
                RarityFilter.onAverageOnceEvery(12),
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.CLOUDCAP_MUSHLING.get()));



        register(context, DAGGERBLOOM_PATCH, configs.getOrThrow(ReduxFeatureConfig.DAGGERBLOOM_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(10),
                BiomeFilter.biome()
        );
        register(context, THERATIP_PATCH, configs.getOrThrow(ReduxFeatureConfig.THERATIP_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(5),
                BiomeFilter.biome()
        );

        register(context, SPLITFERN_PATCH, configs.getOrThrow(ReduxFeatureConfig.SPLITFERN_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 3), 4),
                RarityFilter.onAverageOnceEvery(7),
                BiomeFilter.biome());

        register(context, FROSTED_TREES, configs.getOrThrow(ReduxFeatureConfig.FROSTED_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(24), 9)
                        .add(ConstantInt.of(16), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GLACIA_SAPLING.get())
        );

        register(context, GLACIAL_TREES, configs.getOrThrow(ReduxFeatureConfig.GLACIAL_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(8), 9)
                        .add(ConstantInt.of(6), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GLACIA_SAPLING.get())
        );


        register(context, AEROGEL_ORE, configs.getOrThrow(ReduxFeatureConfig.AEROGEL_ORE),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                BiomeFilter.biome()
        );
        register(context, SPARSE_AEROGEL_ORE, configs.getOrThrow(ReduxFeatureConfig.AEROGEL_ORE),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                BiomeFilter.biome()
        );

        register(context, LARGE_ICESTONE_CHUNK, configs.getOrThrow(ReduxFeatureConfig.AEROGEL_ORE),
                CountPlacement.of(12),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                BiomeFilter.biome()
        );

        register(context, FROSTED_PURPLE_FLOWER_PATCH, configs.getOrThrow(ReduxFeatureConfig.FROSTED_PURPLE_FLOWER_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(13),
                BiomeFilter.biome()
        );

        register(context, GILDED_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE),
                CountPlacement.of(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                ConditionFilter.whenTrue(Conditions.MOSSY_ORE),
                BiomeFilter.biome()
        );
        register(context, BLIGHTMOSS_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.BLIGHTMOSS_HOLYSTONE_ORE),
                CountPlacement.of(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                ConditionFilter.whenTrue(Conditions.MOSSY_ORE),
                BiomeFilter.biome()
        );

        register(context, SHIMMERING_TREES, configs.getOrThrow(ReduxFeatureConfig.CRYSTAL_LEAF_TREE),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(12), 9)
                        .add(ConstantInt.of(7), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())
        );
        register(context, GROVE_TREES, configs.getOrThrow(ReduxFeatureConfig.GROVE_TREES),
                ConditionFilter.whenFalse(Conditions.ALT_GILDED),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(12), 9)
                        .add(ConstantInt.of(7), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())
        );

        register(context, GROVE_TREES_ALT, configs.getOrThrow(ReduxFeatureConfig.GROVE_TREES_ALT),
                ConditionFilter.whenTrue(Conditions.ALT_GILDED),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(12), 9)
                        .add(ConstantInt.of(7), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())
        );

        register(context, GRASSLAND_TREES, configs.getOrThrow(ReduxFeatureConfig.GRASSLAND_TREES),
                ConditionFilter.whenFalse(Conditions.ALT_GILDED),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(2), 9)
                        .add(ConstantInt.of(3), 1)
                        .add(ConstantInt.of(0), 4)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())
        );
        register(context, GRASSLAND_TREES_ALT, configs.getOrThrow(ReduxFeatureConfig.GRASSLAND_TREES_ALT),
                ConditionFilter.whenTrue(Conditions.ALT_GILDED),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(2), 9)
                        .add(ConstantInt.of(3), 1)
                        .add(ConstantInt.of(0), 4)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST,
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())
        );

        register(context, GOLDEN_HEIGHTS_GILDED_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE),
                CountPlacement.of(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                ConditionFilter.whenTrue(Conditions.MOSSY_ORE),
                BiomeFilter.biome()
        );


        register(context, GILDED_ROCK, configs.getOrThrow(ReduxFeatureConfig.GILDED_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );



        register(context, GLOWSPROUTS_PATCH, configs.getOrThrow(ReduxFeatureConfig.GLOWSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 2), 4),
                RarityFilter.onAverageOnceEvery(4),
                BiomeFilter.biome()
        );

        register(context, GOLDEN_CLOVER_PATCH, configs.getOrThrow(ReduxFeatureConfig.GOLDEN_CLOVER_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(4),
                BiomeFilter.biome()
        );


        register(context, SKYFIELDS_ROCK, configs.getOrThrow(ReduxFeatureConfig.SKYFIELDS_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 2)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );

        register(context, SHRUBLANDS_ROCK, configs.getOrThrow(ReduxFeatureConfig.SHRUBLANDS_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 6)
                                .add(UniformInt.of(1, 2), 3)
                                .add(UniformInt.of(1, 5), 1)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(12),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );



        register(context, SKYFIELDS_TREES, configs.getOrThrow(ReduxFeatureConfig.SKYFIELDS_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(1), 9)
                        .add(ConstantInt.of(2), 3)
                        .add(ConstantInt.of(0), 5)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, ConstantInt.of(2), 4),
                BiomeFilter.biome(),
                RarityFilter.onAverageOnceEvery(2),
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.FIELDSPROOT_SAPLING.get()),
                DUNGEON_BLACKLIST
        );

        register(context, SHRUBLANDS_TREES, configs.getOrThrow(ReduxFeatureConfig.SHRUBLANDS_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(5), 9)
                        .add(ConstantInt.of(9), 3)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, ConstantInt.of(2), 4),
                BiomeFilter.biome(),
                RarityFilter.onAverageOnceEvery(2),
                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()),
                DUNGEON_BLACKLIST
        );

        register(context, IRIDIA_PATCH, configs.getOrThrow(ReduxFeatureConfig.IRIDIA_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(15),
                BiomeFilter.biome());

        register(context, XAELIA_PATCH, configs.getOrThrow(ReduxFeatureConfig.XAELIA_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 3), 4),
                RarityFilter.onAverageOnceEvery(7),
                BiomeFilter.biome());

        register(context, LUMINA_PATCH, configs.getOrThrow(ReduxFeatureConfig.LUMINA_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 2), 4),
                RarityFilter.onAverageOnceEvery(7),
                BiomeFilter.biome());

        register(context, LIGHTROOTS, configs.getOrThrow(ReduxFeatureConfig.LIGHTROOTS),
                CountPlacement.of(UniformInt.of(104, 157)),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, new VerticalAnchor.Absolute(256))),
                InSquarePlacement.spread(),
                BiomeFilter.biome());

        register(context, MOSSY_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.MOSSY_HOLYSTONE_ORE),
                CountPlacement.of(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                ConditionFilter.whenTrue(Conditions.MOSSY_ORE),
                BiomeFilter.biome()
        );

        register(context, MOSSY_ROCK, configs.getOrThrow(ReduxFeatureConfig.MOSSY_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );
        register(context, ICESTONE_ROCK, configs.getOrThrow(ReduxFeatureConfig.ICESTONE_ROCK),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 7)
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 3), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );
        register(context, HOLYSILT_DISK, configs.getOrThrow(ReduxFeatureConfig.HOLYSILT_DISK),
                RarityFilter.onAverageOnceEvery(5),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST
        );
        register(context, AEROGEL_DISK, configs.getOrThrow(ReduxFeatureConfig.AEROGEL_DISK),
                RarityFilter.onAverageOnceEvery(5),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome(),
                DUNGEON_BLACKLIST
        );

        register(context, SURFACE_RULE_WATER_LAKE, configs.getOrThrow(ReduxFeatureConfig.SURFACE_RULE_WATER_LAKE),
                RarityFilter.onAverageOnceEvery(15),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome()
        );

        register(context, BLIGHT_LAKE, configs.getOrThrow(ReduxFeatureConfig.BLIGHT_LAKE),
                RarityFilter.onAverageOnceEvery(15),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome()
        );

        register(context, BLIGHT_SPRING, configs.getOrThrow(ReduxFeatureConfig.BLIGHT_SPRING),
                CountPlacement.of(30),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(8), VerticalAnchor.aboveBottom(128)),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());

        register(context, OASIS_LAKE, configs.getOrThrow(ReduxFeatureConfig.SURFACE_RULE_WATER_LAKE),
                RarityFilter.onAverageOnceEvery(6),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome()
        );

        register(context, WYNDSPROUTS_PATCH, configs.getOrThrow(ReduxFeatureConfig.WYNDSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(4),
                BiomeFilter.biome()
        );
        register(context, GENESIS_WYNDSPROUTS_PATCH, configs.getOrThrow(ReduxFeatureConfig.GENESIS_WYNDSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(8),
                BiomeFilter.biome()
        );

        register(context, GENESIS_SKYSPROUTS_PATCH, configs.getOrThrow(ReduxFeatureConfig.GENESIS_SKYSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(12),
                BiomeFilter.biome()
        );

        register(context, SKYSPROUTS_PATCH, configs.getOrThrow(ReduxFeatureConfig.SKYSPROUTS_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, BiasedToBottomInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(6),
                BiomeFilter.biome()
        );

        register(context, SPIROLYCTIL_PATCH, configs.getOrThrow(ReduxFeatureConfig.SPIROLYCTIL_PATCH),
                NOISE_THRESHOLD,
                RarityFilter.onAverageOnceEvery(12),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 3), 4),
                BiomeFilter.biome());

        register(context, JELLYSHROOM_PATCH, configs.getOrThrow(ReduxFeatureConfig.JELLYSHROOM_PATCH),
                NOISE_THRESHOLD,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                RarityFilter.onAverageOnceEvery(5),
                BiomeFilter.biome());

        register(context, BLIGHT_TREES, configs.getOrThrow(ReduxFeatureConfig.BLIGHT_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(UniformInt.of(2, 6), 5)
                        .add(ConstantInt.of(4), 3)
                        .add(ConstantInt.of(6), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(ReduxBlocks.BLIGHTWILLOW_SAPLING.get()),
                DUNGEON_BLACKLIST
        );

        register(context, BONEMEAL_OVERRIDE, configs.getOrThrow(ReduxFeatureConfig.GRASS_PATCH_BONEMEAL), PlacementUtils.isEmpty());

        register(context, VERIDIUM_ORE, configs.getOrThrow(ReduxFeatureConfig.VERIDIUM_ORE),
                CountPlacement.of(11),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
                BiomeFilter.biome()
        );

        register(context, SENTRITE_ORE, configs.getOrThrow(ReduxFeatureConfig.SENTRITE_ORE),
                InSquarePlacement.spread(),
//                RarityFilter.onAverageOnceEvery(1),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(128))),
                BiomeFilter.biome()
        );
        register(context, DIVINITE_ORE, configs.getOrThrow(ReduxFeatureConfig.DIVINITE_ORE),
                InSquarePlacement.spread(),
                RarityFilter.onAverageOnceEvery(6),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(192))),
                BiomeFilter.biome()
        );
        register(context, DIVINITE_ORE_INCREASED, configs.getOrThrow(ReduxFeatureConfig.DIVINITE_ORE),
                InSquarePlacement.spread(),
                RarityFilter.onAverageOnceEvery(2),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(192))),
                BiomeFilter.biome()
        );




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
