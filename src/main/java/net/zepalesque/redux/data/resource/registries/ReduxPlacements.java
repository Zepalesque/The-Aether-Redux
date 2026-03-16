package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.builders.AetherPlacedFeatureBuilders;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.aetherteam.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.data.resource.builders.ReduxPlacementBuilders;
import net.zepalesque.zenith.api.world.feature.placement.ConditionPlacementModule;
import net.zepalesque.zenith.core.Zenith;

public class ReduxPlacements extends ReduxPlacementBuilders {
    
    public static final ResourceKey<PlacedFeature> CLOUDBED = copyKey(ReduxFeatureConfig.CLOUDBED);
    public static final ResourceKey<PlacedFeature> LAKES = copyKey(ReduxFeatureConfig.LAKES);
    public static final ResourceKey<PlacedFeature> SENTRITE_ORE = copyKey(ReduxFeatureConfig.SENTRITE_ORE);
    public static final ResourceKey<PlacedFeature> SPARSE_SENTRITE_ORE = copyKey(SENTRITE_ORE, "sparse_%s");
    public static final ResourceKey<PlacedFeature> ANGILITE_ORE = copyKey(ReduxFeatureConfig.ANGILITE_ORE);
    public static final ResourceKey<PlacedFeature> LARGE_ICESTONE_ORE = copyKey(ReduxFeatureConfig.LARGE_ICESTONE_ORE);
    public static final ResourceKey<PlacedFeature> DENSE_ANGILITE_ORE = copyKey(ANGILITE_ORE, "dense_%s");
    public static final ResourceKey<PlacedFeature> GROVE_TREES = copyKey(ReduxFeatureConfig.GROVE_TREES);
    public static final ResourceKey<PlacedFeature> AURUM_PATCH = copyKey(ReduxFeatureConfig.AURUM_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_CLOVERS_PATCH = copyKey(ReduxFeatureConfig.GOLDEN_CLOVERS_PATCH);
    public static final ResourceKey<PlacedFeature> TURBO_VERBENA_PATCH = copyKey(ReduxFeatureConfig.TURBO_VERBENA_PATCH);
    public static final ResourceKey<PlacedFeature> CAELGAE_PATCH = copyKey(ReduxFeatureConfig.CAELGAE_PATCH);
    public static final ResourceKey<PlacedFeature> BLOOMTAIL = copyKey(ReduxFeatureConfig.BLOOMTAIL);
    
    public static final ResourceKey<PlacedFeature> AMBROSIUM_ROCK = copyKey(ReduxFeatureConfig.AMBROSIUM_ROCK);
    public static final ResourceKey<PlacedFeature> LUCKY_CLOVER_PATCH = copyKey(ReduxFeatureConfig.LUCKY_CLOVER_PATCH);
    
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.MOSSY_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> GILDED_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> BLEAKMOSS_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.BLEAKMOSS_HOLYSTONE_ORE);
    
    public static final ResourceKey<PlacedFeature> SPARSE_BLUE_AERCLOUD = createKey("sparse_blue_aercloud");
    public static final ResourceKey<PlacedFeature> DENSE_BLUE_AERCLOUD = createKey("dense_blue_aercloud");
    public static final ResourceKey<PlacedFeature> SPARSE_ZANITE_ORE = createKey("sparse_zanite_ore");
    public static final ResourceKey<PlacedFeature> SPARSE_AMBROSIUM_ORE = createKey("sparse_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> DENSE_ZANITE_ORE = createKey("dense_zanite_ore");
    public static final ResourceKey<PlacedFeature> DENSE_AMBROSIUM_ORE = createKey("dense_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> WYNDSPROUTS_PATCH = copyKey(ReduxFeatureConfig.WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> LUXWEED_PATCH = copyKey(ReduxFeatureConfig.LUXWEED_PATCH);
    public static final ResourceKey<PlacedFeature> SPIROLYCTIL_PATCH = copyKey(ReduxFeatureConfig.SPIROLYCTIL_PATCH);
    public static final ResourceKey<PlacedFeature> GLOOMSHADE_PATCH = copyKey(ReduxFeatureConfig.GLOOMSHADE_PATCH);
    
    public static final ResourceKey<PlacedFeature> BLIGHT_TREES = copyKey(ReduxFeatureConfig.BLIGHT_TREES);
    
    
    public static final ResourceKey<PlacedFeature> SPARSE_WYNDSPROUTS_PATCH = copyKey(ReduxFeatureConfig.WYNDSPROUTS_PATCH, "sparse_%s");
    
    public static final ResourceKey<PlacedFeature> ICESTONE_ROCK  = copyKey(ReduxFeatureConfig.ICESTONE_ROCK);
    public static final ResourceKey<PlacedFeature> AEROGEL_DISK = copyKey(ReduxFeatureConfig.AEROGEL_DISK);
    
    public static final ResourceKey<PlacedFeature> FROSTED_TREES = copyKey(ReduxFeatureConfig.FROSTED_TREES);
    public static final ResourceKey<PlacedFeature> GLACIAL_TREES = copyKey(ReduxFeatureConfig.GLACIAL_TREES);
    
    public static final ResourceKey<PlacedFeature> LUMINA_PATCH = copyKey(ReduxFeatureConfig.LUMINA_PATCH);
    public static final ResourceKey<PlacedFeature> DAGGERBLOOM_PATCH = copyKey(ReduxFeatureConfig.DAGGERBLOOM_PATCH);
    public static final ResourceKey<PlacedFeature> SPARSE_PURPLE_FLOWER_PATCH = copyKey(ReduxFeatureConfig.SPARSE_PURPLE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> SKYFERN_PATCH = copyKey(ReduxFeatureConfig.SKYFERN_PATCH);
    
    public static final ResourceKey<PlacedFeature> AETHER_SNOW_LAYER = copyKey(ReduxFeatureConfig.AETHER_SNOW_LAYER);
    
    
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configs = context.lookup(Registries.CONFIGURED_FEATURE);
        var conditions = context.lookup(Zenith.Keys.CONDITION);
        var blacklist = new DungeonBlacklistFilter();
        var threshold = NoiseThresholdCountPlacement.of(-0.8D, 5, 10);
        
        PlacementUtils.register(context, CLOUDBED, configs.getOrThrow(ReduxFeatureConfig.CLOUDBED));
        PlacementUtils.register(context, LAKES, configs.getOrThrow(ReduxFeatureConfig.LAKES));
        
        PlacementUtils.register(context, SENTRITE_ORE, configs.getOrThrow(ReduxFeatureConfig.SENTRITE_ORE),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(128))),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(context, SPARSE_SENTRITE_ORE, configs.getOrThrow(ReduxFeatureConfig.SENTRITE_ORE),
            InSquarePlacement.spread(),
            RarityFilter.onAverageOnceEvery(3),
            HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(128))),
            BiomeFilter.biome()
        );
        
        register(context, ANGILITE_ORE, configs.getOrThrow(ReduxFeatureConfig.ANGILITE_ORE),
            InSquarePlacement.spread(),
            RarityFilter.onAverageOnceEvery(6),
            HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(192))),
            BiomeFilter.biome()
        );
        
        register(context, DENSE_ANGILITE_ORE, configs.getOrThrow(ReduxFeatureConfig.ANGILITE_ORE),
            InSquarePlacement.spread(),
            RarityFilter.onAverageOnceEvery(2),
            HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(192))),
            BiomeFilter.biome()
        );
        
        register(context, LARGE_ICESTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.LARGE_ICESTONE_ORE),
            CountPlacement.of(12),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(
            context,
            WYNDSPROUTS_PATCH,
            configs.getOrThrow(ReduxFeatureConfig.WYNDSPROUTS_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
            RarityFilter.onAverageOnceEvery(4),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(
            context,
            LUXWEED_PATCH,
            configs.getOrThrow(ReduxFeatureConfig.LUXWEED_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
            RarityFilter.onAverageOnceEvery(4),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(
            context,
            SPIROLYCTIL_PATCH,
            configs.getOrThrow(ReduxFeatureConfig.SPIROLYCTIL_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 3), 4),
            RarityFilter.onAverageOnceEvery(12),
            BiomeFilter.biome()
        );
        
        
        PlacementUtils.register(
            context,
            GLOOMSHADE_PATCH,
            configs.getOrThrow(ReduxFeatureConfig.GLOOMSHADE_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 3), 4),
            RarityFilter.onAverageOnceEvery(16),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(context, SPARSE_WYNDSPROUTS_PATCH, configs.getOrThrow(ReduxFeatureConfig.WYNDSPROUTS_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
            RarityFilter.onAverageOnceEvery(8),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(context, MOSSY_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.MOSSY_HOLYSTONE_ORE),
            CountPlacement.of(24),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
            ConditionPlacementModule.of(conditions.getOrThrow(ReduxConditions.MOSSY_ORE)),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(context, GILDED_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE),
            CountPlacement.of(24),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
            ConditionPlacementModule.of(conditions.getOrThrow(ReduxConditions.MOSSY_ORE)),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(context, BLEAKMOSS_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.BLEAKMOSS_HOLYSTONE_ORE),
            CountPlacement.of(24),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
            ConditionPlacementModule.of(conditions.getOrThrow(ReduxConditions.MOSSY_ORE)),
            BiomeFilter.biome()
        );
        
        PlacementUtils.register(context, GROVE_TREES, configs.getOrThrow(ReduxFeatureConfig.GROVE_TREES),
            CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                .add(ConstantInt.of(6), 9)
                .add(ConstantInt.of(4), 3)
                .add(ConstantInt.of(2), 5)
                .add(ConstantInt.of(10), 1)
                .build())),
            ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, ConstantInt.of(2), 4),
            BiomeFilter.biome(),
            PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()),
            blacklist
        );
        
        PlacementUtils.register(context, BLIGHT_TREES, configs.getOrThrow(ReduxFeatureConfig.BLIGHT_TREES),
            CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                .add(ConstantInt.of(18), 9)
                .add(ConstantInt.of(14), 3)
                .add(ConstantInt.of(12), 5)
                .add(ConstantInt.of(22), 1)
                .build())),
            ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, ConstantInt.of(2), 4),
            BiomeFilter.biome(),
            PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.STORMFIR_SAPLING.flower().get()),
            blacklist
        );
        
        PlacementUtils.register(context, AURUM_PATCH, configs.getOrThrow(ReduxFeatureConfig.AURUM_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
            RarityFilter.onAverageOnceEvery(8),
            BiomeFilter.biome());
        
        PlacementUtils.register(context, LUCKY_CLOVER_PATCH, configs.getOrThrow(ReduxFeatureConfig.LUCKY_CLOVER_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
            RarityFilter.onAverageOnceEvery(16),
            BiomeFilter.biome());
        
        
        PlacementUtils.register(context, GOLDEN_CLOVERS_PATCH, configs.getOrThrow(ReduxFeatureConfig.GOLDEN_CLOVERS_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
            BiomeFilter.biome());
        
        PlacementUtils.register(context, TURBO_VERBENA_PATCH, configs.getOrThrow(ReduxFeatureConfig.TURBO_VERBENA_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
            BiomeFilter.biome());
        
        PlacementUtils.register(context, CAELGAE_PATCH, configs.getOrThrow(ReduxFeatureConfig.CAELGAE_PATCH),
            threshold,
            CountPlacement.of(11),
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
            BiomeFilter.biome());
        
        PlacementUtils.register(context, BLOOMTAIL, configs.getOrThrow(ReduxFeatureConfig.BLOOMTAIL),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4),
            CountPlacement.of(24),
            BiomeFilter.biome(),
            InSquarePlacement.spread());
        
        PlacementUtils.register(context, SPARSE_BLUE_AERCLOUD,
            configs.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION),
            AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 48));
        
        PlacementUtils.register(context, DENSE_BLUE_AERCLOUD,
            configs.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION),
            AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 14));
        
        PlacementUtils.register(context, SPARSE_AMBROSIUM_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
            NitrogenPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));
        
        PlacementUtils.register(context, SPARSE_ZANITE_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
            NitrogenPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));
        
        PlacementUtils.register(context, DENSE_AMBROSIUM_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
            NitrogenPlacedFeatureBuilders.commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));
        
        PlacementUtils.register(context, DENSE_ZANITE_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
            NitrogenPlacedFeatureBuilders.commonOrePlacement(21, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));
        
        PlacementUtils.register(context, AMBROSIUM_ROCK, configs.getOrThrow(ReduxFeatureConfig.AMBROSIUM_ROCK),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                    .add(UniformInt.of(1, 2), 5)
                    .add(UniformInt.of(1, 4), 3)
                    .build()), 4),
            RarityFilter.onAverageOnceEvery(24),
            InSquarePlacement.spread(),
            BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
            BiomeFilter.biome()
        );
        
        
        
        register(context, ICESTONE_ROCK, configs.getOrThrow(ReduxFeatureConfig.ICESTONE_ROCK),
            threshold,
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
        
        register(context, AEROGEL_DISK, configs.getOrThrow(ReduxFeatureConfig.AEROGEL_DISK),
            RarityFilter.onAverageOnceEvery(5),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome(),
            blacklist
        );
        
        register(context,
            FROSTED_TREES,
            configs.getOrThrow(ReduxFeatureConfig.FROSTED_TREES),
            CountPlacement.of(
                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                    .add(ConstantInt.of(24), 9)
                    .add(ConstantInt.of(16), 1)
                    .build()
                )
            ), ImprovedLayerPlacementModifier.of(
                Heightmap.Types.OCEAN_FLOOR,
                UniformInt.of(0, 1),
                4
            ), BiomeFilter.biome(),
            blacklist,
            PlacementUtils.filteredByBlockSurvival(
                ReduxFlowerSets.MOONFIR_SAPLING.flower().get()
            )
        );
        
        register(context,
            GLACIAL_TREES,
            configs.getOrThrow(ReduxFeatureConfig.GLACIAL_TREES),
            CountPlacement.of(
                new WeightedListInt(
                    SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(8), 9)
                        .add(ConstantInt.of(6), 1)
                        .build()
                )
            ), ImprovedLayerPlacementModifier.of(
                Heightmap.Types.OCEAN_FLOOR,
                UniformInt.of(0, 1), 4
            ), BiomeFilter.biome(),
            blacklist,
            PlacementUtils.filteredByBlockSurvival(
                ReduxFlowerSets.STORMFIR_SAPLING.flower().get()
            )
        );
        
        register(context, LUMINA_PATCH, configs.getOrThrow(ReduxFeatureConfig.LUMINA_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(1, 2), 4),
            RarityFilter.onAverageOnceEvery(7),
            BiomeFilter.biome());
        
        register(context, DAGGERBLOOM_PATCH, configs.getOrThrow(ReduxFeatureConfig.DAGGERBLOOM_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
            RarityFilter.onAverageOnceEvery(10),
            BiomeFilter.biome()
        );
        
        register(context, SPARSE_PURPLE_FLOWER_PATCH, configs.getOrThrow(ReduxFeatureConfig.SPARSE_PURPLE_FLOWER_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
            RarityFilter.onAverageOnceEvery(13),
            BiomeFilter.biome()
        );
        
        register(context, SKYFERN_PATCH, configs.getOrThrow(ReduxFeatureConfig.SKYFERN_PATCH),
            threshold,
            ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 3), 4),
            RarityFilter.onAverageOnceEvery(7),
            BiomeFilter.biome()
        );
        
        register(
            context,
            AETHER_SNOW_LAYER,
            configs.getOrThrow(ReduxFeatureConfig.AETHER_SNOW_LAYER)
        );
    }
    
}
