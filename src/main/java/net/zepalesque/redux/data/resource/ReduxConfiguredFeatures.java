package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.world.configuration.ShelfConfiguration;
import com.aetherteam.aether.world.feature.AetherFeatures;
import com.aetherteam.aether.world.foliageplacer.CrystalFoliagePlacer;
import com.aetherteam.aether.world.foliageplacer.GoldenOakFoliagePlacer;
import com.aetherteam.aether.world.trunkplacer.CrystalTreeTrunkPlacer;
import com.aetherteam.aether.world.trunkplacer.GoldenOakTrunkPlacer;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.ReduxStates;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.feature.CloudcapFeature;
import net.zepalesque.redux.world.feature.ReduxFeatureRegistry;
import net.zepalesque.redux.world.feature.config.*;
import net.zepalesque.redux.world.stateprov.SimpleConditionAlternativeStateProvider;
import net.zepalesque.redux.world.tree.decorator.EnchantedVineDecorator;
import net.zepalesque.redux.world.tree.decorator.PatchTreeDecorator;
import net.zepalesque.redux.world.tree.foliage.BlightwillowFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.GlaciaFoliagePlacer;
import net.zepalesque.redux.world.tree.root.BlightwillowRootConfig;
import net.zepalesque.redux.world.tree.root.BlightwillowRootPlacer;
import net.zepalesque.redux.world.tree.trunk.BlightwillowTrunkPlacer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class    ReduxConfiguredFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> AA_QUICKSOIL_SHELF = createKey(Folders.COMPAT + "aa_quicksoil_shelf");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEVELIUM_GRASSES_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.AEVELIUM) + "_grasses_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURUM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.AURUM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZANBERRY_BUSH_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.ZANBERRY_BUSH) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTED_SKYROOT_TREE = createKey(Folders.TREE + "blighted_skyroot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_PATCH = createKey(Folders.CAVE + "blightmoss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_PATCH_BONEMEAL = createKey(Folders.CAVE + "blightmoss_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_VEGETATION = createKey(Folders.CAVE + "blightmoss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTSHADE_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.BLIGHTSHADE) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTWILLOW_TREE = createKey(Folders.TREE + "blightwillow_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_ROCK = createKey(Folders.SURFACE + "blight_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_TREES = createKey(Folders.TREE + "blight_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_BUSH = createKey(Folders.PATCH + "skyroot_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDCAP_MUSHLING_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.CLOUDCAP_MUSHLING) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GILDED_OAK_TREE = createKey(Folders.TREE + "fancy_gilded_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GOLDEN_OAK_TREE = createKey(Folders.TREE + "fancy_golden_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAGGERBLOOM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.DAGGERBLOOM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPLITFERN_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SPLITFERN) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEROGEL_ORE = createKey(Folders.ORE + name(AetherBlocks.AEROGEL) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_PURPLE_FLOWER_PATCH = createKey(Folders.PATCH + "frosted_purple_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIAL_TREES = createKey(Folders.TREE + "glacial_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_TREES = createKey(Folders.TREE + "frosted_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_HOLYSTONE_ORE = createKey(Folders.ORE + name(ReduxBlocks.GILDED_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_LEAF_PATCH  = createKey(Folders.PATCH + "gilded_leaf_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_OAK_TREE = createKey(Folders.TREE + "gilded_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_ROCK  = createKey(Folders.SURFACE + "gilded_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_WHITE_FLOWER_PATCH = createKey(Folders.PATCH + "gilded_white_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.LUXWEED) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_CLOVER_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.GOLDEN_CLOVER) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_LEAF_PATCH = createKey(Folders.PATCH + "golden_leaf_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES = createKey(Folders.TREE + "grove_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSLAND_TREES = createKey(Folders.TREE + "grassland_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHFIELDS_ROCK  = createKey(Folders.SURFACE + "highfields_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUBLANDS_ROCK  = createKey(Folders.SURFACE + "shrublands_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLYSILT_DISK  = createKey(Folders.SURFACE + "holysilt_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SKYSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHFIELDS_TREES = createKey(Folders.TREE + "highfields_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUBLANDS_TREES = createKey(Folders.TREE + "shrublands_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRIDIA_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.IRIDIA) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_CLOUDCAP  = createKey(Folders.TREE + "large_cloudcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHTROOTS  = createKey(Folders.MISC + "lightroots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIA_TREE = createKey(Folders.TREE + "glacia_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MUSHROOMS = createKey(Folders.TREE + "large_mushrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_JELLYSHROOM = createKey(Folders.TREE + "large_jellyshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINA_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.LUMINA) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_CLOUDCAP  = createKey(Folders.TREE + "mega_cloudcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_HOLYSTONE_ORE  = createKey(Folders.ORE + name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_ROCK  = createKey(Folders.SURFACE + "mossy_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERING_FIELDSPROUT_TREE = createKey(Folders.TREE + "flowering_fieldsprout_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROOTED_QUICKSOIL_SHELF = createKey(Folders.SURFACE + "rooted_quicksoil_shelf");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WYNDSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.WYNDSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GENESIS_WYNDSPROUTS_PATCH = createKey(Folders.PATCH + "genesis_" +  name(ReduxBlocks.WYNDSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GENESIS_SKYSPROUTS_PATCH = createKey(Folders.PATCH + "genesis_" + name(ReduxBlocks.SKYSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_GLACIA_TREE = createKey(Folders.TREE + "purple_glacia_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPIROLYCTIL_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.SPIROLYCTIL) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYSHROOM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.JELLYSHROOM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SURFACE_RULE_WATER_LAKE = createKey(Folders.SURFACE + "surface_rule_water_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VERIDIUM_ORE = createKey(Folders.ORE + name(ReduxBlocks.VERIDIUM_ORE));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIVINITE_ORE = createKey(Folders.ORE + name(ReduxBlocks.DIVINITE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE_OVERRIDE = aetherKey("crystal_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE_OVERRIDE = aetherKey("golden_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH_OVERRIDE = aetherKey("grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH_OVERRIDE = aetherKey("tall_grass_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_SNOW_LAYER = createKey(Folders.SURFACE + "snow_layer");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_LAYER = createKey(Folders.MISC + "cloud_layer");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Redux.MODID, name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> aetherKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name));
    }


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, AEVELIUM_GRASSES_PATCH, Feature.RANDOM_PATCH,
                blockBelowPlacementPatch(32, 7, 3, (new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(drops(ReduxBlocks.AEVELIUM_SPROUTS), 5)
                                        .add(drops(ReduxBlocks.AEVELIUM_GROWTH), 3))),
                        BlockPredicate.matchesBlocks(ReduxBlocks.AEVELIUM.get())));

        register(context, AA_QUICKSOIL_SHELF, ReduxFeatureRegistry.ROOTED_SHELF.get(),
                new RootedShelfConfiguration(BlockStateProvider.simple(drops(AetherBlocks.QUICKSOIL)),
                        BlockStateProvider.simple(drops(ReduxBlocks.QUICKROOTS).setValue(ReduxStates.HARVESTED, false)),
                        ConstantFloat.of(7.5F), UniformInt.of(78, 80), blocks.getOrThrow(AetherTags.Blocks.AETHER_ISLAND_BLOCKS)));
        register(context, AURUM_PATCH, Feature.FLOWER,
                randomPatch(12, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.AURUM))));


        register(context, ZANBERRY_BUSH_PATCH, Feature.FLOWER,
                randomPatch(24, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.AURUM))));


        register(context, AETHER_SNOW_LAYER, ReduxFeatureRegistry.TREE_AWARE_SNOW.get(), FeatureConfiguration.NONE);
        register(context, GILDED_WHITE_FLOWER_PATCH, Feature.FLOWER,
                randomPatch(16, 7, 3, new SimpleConditionAlternativeStateProvider(drops(ReduxBlocks.ENCHANTED_WHITE_FLOWER), Conditions.ENCHGRASS, AetherFeatureStates.WHITE_FLOWER)));
        register(context, HOLYSILT_DISK, AetherFeatures.SHELF.get(),
                new ShelfConfiguration(BlockStateProvider.simple(drops(ReduxBlocks.HOLYSILT)),
                        ConstantFloat.of(3.5F), UniformInt.of(0, 48), HolderSet.direct(AetherBlocks.AETHER_GRASS_BLOCK.getHolder().get(), ReduxBlocks.COARSE_AETHER_DIRT.getHolder().get())));
        register(context, BLIGHTMOSS_PATCH, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(ReduxTags.Blocks.BLIGHT_REPLACEABLES,
                        prov(ReduxBlocks.BLIGHTMOSS_BLOCK),
                        Holder.direct(new PlacedFeature(configuredFeatures.getOrThrow(BLIGHTMOSS_VEGETATION),
                                List.of())),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        2,
                        0.8F,
                        UniformInt.of(4, 7),
                        0.3F));
        register(context, BLIGHTMOSS_PATCH_BONEMEAL, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(ReduxTags.Blocks.AETHER_CARVER_REPLACEABLES,
                        prov(ReduxBlocks.BLIGHTMOSS_BLOCK),
                        Holder.direct(new PlacedFeature(configuredFeatures.getOrThrow(BLIGHTMOSS_VEGETATION),
                                List.of())),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        2,
                        0.8F,
                        UniformInt.of(1, 2),
                        0.75F));

        register(context, CLOUD_LAYER, ReduxFeatureRegistry.CLOUD_LAYER.get(),
                new CloudLayerConfig(prov(AetherBlocks.COLD_AERCLOUD), BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR), 8, 1D));

        register(context, BLIGHT_ROCK, Feature.FOREST_ROCK,
                new BlockStateConfiguration(drops(ReduxBlocks.BLIGHTMOSS_HOLYSTONE)));
        register(context, BLIGHTMOSS_VEGETATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(drops(ReduxBlocks.BLIGHTSHADE), 11)
                                .add(drops(ReduxBlocks.SPIROLYCTIL), 7)
                                .add(drops(ReduxBlocks.BLIGHTMOSS_CARPET), 75)
                                .add(drops(ReduxBlocks.AETHER_SHORT_GRASS), 150)
                                .add(drops(ReduxBlocks.LUXWEED), 120)
                )));

        register(context, BLIGHTED_SKYROOT_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(drops(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES)),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT)).build());
        register(context, BLIGHTSHADE_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(24, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.BLIGHTSHADE)),
                        BlockPredicate.matchesBlocks(AetherBlocks.AETHER_GRASS_BLOCK.get())));
        register(context, BLIGHTWILLOW_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(drops(Redux.Handlers.Wood.BLIGHTWILLOW.log), 7)
                                .add(drops(Redux.Handlers.Wood.BLIGHTWILLOW.sporingLog.orElseThrow()), 1)
                ),
                new BlightwillowTrunkPlacer(5, 2, 2, new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(drops(Redux.Handlers.Wood.BLIGHTWILLOW.wood), 7)
                                .add(drops(Redux.Handlers.Wood.BLIGHTWILLOW.sporingWood.orElseThrow()), 1)
                ), UniformInt.of(4, 6), UniformInt.of(3, 4)),
                BlockStateProvider.simple(drops(ReduxBlocks.BLIGHTWILLOW_LEAVES)),
                new BlightwillowFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                Optional.of(
                        new BlightwillowRootPlacer(ConstantInt.of(1), BlockStateProvider.simple(drops(ReduxBlocks.BLIGHTWILLOW_ROOTS)),
                                Optional.of(new AboveRootPlacement(BlockStateProvider.simple(drops(ReduxBlocks.BLIGHTMOSS_CARPET)), 0.8F)),
                                new BlightwillowRootConfig(blocks.getOrThrow(ReduxTags.Blocks.BLIGHTWILLOW_ROOTS_CAN_GROW_THROUGH), 3))
                ),
                new TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines().dirt(BlockStateProvider.simple(Blocks.AIR)).build());
        register(context, CLOUDCAP_MUSHLING_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(16, 5, 3, BlockStateProvider.simple(drops(ReduxBlocks.CLOUDCAP_MUSHLING)),
                        BlockPredicate.matchesBlocks(ReduxBlocks.AEVELIUM.get())));
        register(context, DAGGERBLOOM_PATCH, Feature.FLOWER,
                randomPatch(12, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.DAGGERBLOOM))));
        register(context, SPLITFERN_PATCH, Feature.FLOWER,
                randomPatch(24, 9, 3, BlockStateProvider.simple(drops(ReduxBlocks.SPLITFERN))));

        register(context, AEROGEL_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(AetherBlocks.AEROGEL), 64, 0.3F));
        register(context, FROSTED_PURPLE_FLOWER_PATCH, Feature.FLOWER,
                randomPatch(16, 7, 3, BlockStateProvider.simple(drops(AetherBlocks.PURPLE_FLOWER))));
        register(context, GILDED_HOLYSTONE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.GILDED_HOLYSTONE), 32, 0.3F));
        register(context, GILDED_LEAF_PATCH, Feature.RANDOM_PATCH,
                blockTestPatch(8, 3, 3, createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE),
                        BlockPredicate.wouldSurvive(ReduxBlocks.AURUM.get().defaultBlockState(), BlockPos.ZERO)));
        register(context, GILDED_ROCK, Feature.FOREST_ROCK,
                new BlockStateConfiguration(drops(ReduxBlocks.GILDED_HOLYSTONE)));

        register(context, GILDED_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(AetherFeatureStates.SKYROOT_LOG, 3)
                                        .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                        ),
                        new GoldenOakTrunkPlacer(10, 0, 0),
                        BlockStateProvider.simple(drops(ReduxBlocks.GILDED_OAK_LEAVES)),
                        new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(7)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.125F, 0.35F,
                                        BlockStateProvider.simple(drops(ReduxBlocks.GILDED_VINES_PLANT)),
                                        BlockStateProvider.simple(drops(ReduxBlocks.GILDED_VINES)),
                                        UniformInt.of(1, 2),
                                        UniformInt.of(2, 3), Conditions.VINES),
                                new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).build());

        register(context, ReduxConfiguredFeatures.FANCY_GILDED_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(AetherFeatureStates.SKYROOT_LOG, 3)
                                        .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                        ),
                        new FancyTrunkPlacer(10, 0, 0),
                        BlockStateProvider.simple(drops(ReduxBlocks.GILDED_OAK_LEAVES)),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.5F, 0.75F,
                                        BlockStateProvider.simple(drops(ReduxBlocks.GILDED_VINES_PLANT)),
                                        BlockStateProvider.simple(drops(ReduxBlocks.GILDED_VINES)),
                                        UniformInt.of(1, 2),
                                        UniformInt.of(3, 5), Conditions.VINES
                                ), new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).build());

        register(context, GOLDEN_OAK_TREE_OVERRIDE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LOG),
                        new GoldenOakTrunkPlacer(10, 0, 0),
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                        new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(7)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.125F, 0.35F,
                                        BlockStateProvider.simple(drops(ReduxBlocks.GOLDEN_VINES_PLANT)),
                                        BlockStateProvider.simple(drops(ReduxBlocks.GOLDEN_VINES)),
                                        UniformInt.of(1, 2),
                                        UniformInt.of(2, 3), Conditions.VINES),
                                new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32))).build());

        register(context, ReduxConfiguredFeatures.FANCY_GOLDEN_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LOG),
                        new FancyTrunkPlacer(10, 0, 0),
                        BlockStateProvider.simple(drops(AetherBlocks.GOLDEN_OAK_LEAVES)),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.5F, 0.75F,
                                        BlockStateProvider.simple(drops(ReduxBlocks.GOLDEN_VINES_PLANT)),
                                        BlockStateProvider.simple(drops(ReduxBlocks.GOLDEN_VINES)),
                                        UniformInt.of(1, 2),
                                        UniformInt.of(3, 5), Conditions.VINES
                                ), new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32))).build());

        register(context, CRYSTAL_TREE_OVERRIDE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(drops(Redux.Handlers.Wood.CRYSTAL.log)),
                        new CrystalTreeTrunkPlacer(7, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.CRYSTAL_LEAVES, 4).add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1).build()),
                        new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        register(context, GLACIA_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(drops(Redux.Handlers.Wood.GLACIA.log)),
                        new StraightTrunkPlacer(9, 10, 0),
                        prov(ReduxBlocks.GLACIA_LEAVES),
                        new GlaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT)).build());

        register(context, LIGHTROOTS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration(ReduxBlocks.LIGHTROOTS.get(),
                        20, false, false, true, 0.5F,
                        HolderSet.direct(
                                AetherBlocks.AETHER_DIRT.getHolder().orElseThrow()
                        )));

        register(context, PURPLE_GLACIA_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(drops(Redux.Handlers.Wood.GLACIA.log)),
                        new StraightTrunkPlacer(5, 2, 0),
                        prov(ReduxBlocks.PURPLE_GLACIA_LEAVES),
                        new GlaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT)).build());


        register(context, GLOWSPROUTS_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(24, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.LUXWEED)),
                        BlockPredicate.matchesBlocks(AetherBlocks.AETHER_GRASS_BLOCK.get())));

        register(context, GOLDEN_CLOVER_PATCH, Feature.FLOWER,
                randomPatch(12, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.GOLDEN_CLOVER))));

        register(context, GOLDEN_LEAF_PATCH, Feature.RANDOM_PATCH,
                blockTestPatch(8, 3, 3, createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE),
                        BlockPredicate.wouldSurvive(ReduxBlocks.AURUM.get().defaultBlockState(), BlockPos.ZERO)));
        register(context, HIGHFIELDS_ROCK, Feature.FOREST_ROCK,
                new BlockStateConfiguration(AetherFeatureStates.HOLYSTONE));

        register(context, SHRUBLANDS_ROCK, Feature.FOREST_ROCK,
                new BlockStateConfiguration(AetherFeatureStates.HOLYSTONE));

        register(context, IRIDIA_PATCH, Feature.FLOWER,
                randomPatch(4, 3, 3, BlockStateProvider.simple(drops(ReduxBlocks.IRIDIA))));
        register(context, LARGE_CLOUDCAP, ReduxFeatureRegistry.CLOUDCAP.get(),
                new CloudcapFeature.CloudcapConfig(
                        prov(ReduxBlocks.CLOUD_CAP_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.DOWN, false)),
                        prov(ReduxBlocks.CLOUDCAP_SPORES),
                        prov(Redux.Handlers.Wood.CLOUDCAP.log),
                        prov(Redux.Handlers.Wood.CLOUDCAP.logWall),
                        UniformInt.of(11, 17),
                        UniformInt.of(1, 4),
                        UniformInt.of(1, 3),
                        UniformInt.of(6, 9))
        );
        register(context, LARGE_MUSHROOMS, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_JELLYSHROOM), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.JELLYSHROOM.get())), 0.3F),
                        new WeightedPlacedFeature(
                                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(MEGA_CLOUDCAP), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.CLOUDCAP_MUSHLING.get())), 0.35F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_CLOUDCAP), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.CLOUDCAP_MUSHLING.get()))));
        register(context, LARGE_JELLYSHROOM, ReduxFeatureRegistry.JELLYSHROOM.get(),
                new JellyshroomConfig(
                        prov(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK),
                        prov(Redux.Handlers.Wood.JELLYSHROOM.log),
                        UniformInt.of(7, 9)
                        ));

        register(context, LUMINA_PATCH, Feature.FLOWER,
                randomPatch(12, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.LUMINA))));
        register(context, MEGA_CLOUDCAP, ReduxFeatureRegistry.MEGA_CLOUDCAP.get(),
                new MegaCloudcapFeatureConfiguration(
                        prov(ReduxBlocks.CLOUD_CAP_BLOCK),
                        prov(ReduxBlocks.CLOUDCAP_SPORES),
                        prov(Redux.Handlers.Wood.CLOUDCAP.log),
                        prov(Redux.Handlers.Wood.CLOUDCAP.wood)
                ));
        register(context, MOSSY_HOLYSTONE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(AetherBlocks.MOSSY_HOLYSTONE), 32, 0.3F));
        register(context, MOSSY_ROCK, Feature.FOREST_ROCK,
                new BlockStateConfiguration(drops(AetherBlocks.MOSSY_HOLYSTONE)));

        register(context, FLOWERING_FIELDSPROUT_TREE, ReduxFeatureRegistry.FIELDSPROUT_TREE.get(),
                new FieldsproutTreeConfig(
                        prov(ReduxBlocks.FLOWERING_FIELDSPROUT_LEAVES),
                        prov(Redux.Handlers.Wood.FIELDSPROUT.log),
                        prov(Redux.Handlers.Wood.FIELDSPROUT.wood),
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 5)
                                .add(ConstantInt.of(1), 4)
                                .add(ConstantInt.of(2), 3)
                                .add(ConstantInt.of(3), 2)
                                .add(ConstantInt.of(4), 1)
                                .build()), 7, 3, 32));

        register(context, SKYROOT_BUSH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                prov(AetherFeatureStates.SKYROOT_LOG),
                new StraightTrunkPlacer(1, 0, 0),
                prov(AetherFeatureStates.SKYROOT_LEAVES),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0)
        ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT)).build());


        register(context, SURFACE_RULE_WATER_LAKE, ReduxFeatureRegistry.SURFACE_RULE_LAKE.get(),
                new SurfaceRuleLakeConfig(BlockStateProvider.simple(Blocks.WATER)));

        register(context, ROOTED_QUICKSOIL_SHELF, ReduxFeatureRegistry.ROOTED_SHELF.get(),
                new RootedShelfConfiguration(BlockStateProvider.simple(drops(AetherBlocks.QUICKSOIL)),
                        BlockStateProvider.simple(drops(ReduxBlocks.QUICKROOTS).setValue(ReduxStates.HARVESTED, false)),
                        ConstantFloat.of(3.5F), UniformInt.of(0, 48), blocks.getOrThrow(AetherTags.Blocks.AETHER_ISLAND_BLOCKS)));
        register(context, WYNDSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, BlockStateProvider.simple(drops(ReduxBlocks.WYNDSPROUTS))));
        register(context, GENESIS_WYNDSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, BlockStateProvider.simple(drops(ReduxBlocks.WYNDSPROUTS))));
        register(context, GENESIS_SKYSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, BlockStateProvider.simple(drops(ReduxBlocks.SKYSPROUTS))));
        register(context, SKYSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, BlockStateProvider.simple(drops(ReduxBlocks.SKYSPROUTS    ))));
        register(context, SPIROLYCTIL_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(16, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.SPIROLYCTIL)),
                        BlockPredicate.matchesBlocks(AetherBlocks.AETHER_GRASS_BLOCK.get())));
        register(context, JELLYSHROOM_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(8, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.JELLYSHROOM)),
                        BlockPredicate.matchesBlocks(ReduxBlocks.AEVELIUM.get())));


        register(context, BLIGHT_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BLIGHTED_SKYROOT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get())), 0.25F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BLIGHTWILLOW_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.BLIGHTWILLOW_SAPLING.get()))));

        register(context, GROVE_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GOLDEN_OAK_TREE_OVERRIDE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.67F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GILDED_OAK_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get()))));

        register(context, GRASSLAND_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GOLDEN_OAK_TREE_OVERRIDE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.33F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GILDED_OAK_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get()))));
        register(context, GLACIAL_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(PURPLE_GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.PURPLE_GLACIA_SAPLING.get())), 0.25F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GLACIA_SAPLING.get()))));

        register(context, FROSTED_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GLACIA_SAPLING.get())), 0.05F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(PURPLE_GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.PURPLE_GLACIA_SAPLING.get()))));


        register(context, HIGHFIELDS_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION),
                                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())), 0.35F)
                ),
                        PlacementUtils.inlinePlaced(
                                configuredFeatures.getOrThrow(FLOWERING_FIELDSPROUT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.FLOWERING_FIELDSPROUT_SAPLING.get()))
                ));

        register(context, SHRUBLANDS_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION),
                                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())), 0.05F)
                ),
                        PlacementUtils.inlinePlaced(
                                configuredFeatures.getOrThrow(SKYROOT_BUSH), PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()))
                ));

        register(context, VERIDIUM_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.VERIDIUM_ORE), 9, 0.1F));

        register(context, DIVINITE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.DIVINITE), 64, 0.0F));

        register(context, GRASS_PATCH_OVERRIDE, Feature.RANDOM_PATCH,
                randomPatch(32, 7, 3, BlockStateProvider.simple(drops(ReduxBlocks.AETHER_SHORT_GRASS))));
        register(context, TALL_GRASS_PATCH_OVERRIDE, Feature.NO_OP, FeatureConfiguration.NONE);

    }

    private static RandomPatchConfiguration randomPatch(int tries, int xz, int y, BlockStateProvider state)
    {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(state)));
    }
    private static RandomPatchConfiguration blockBelowPlacementPatch(int tries, int xz, int y, BlockStateProvider state, BlockPredicate predicate)
    {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ReduxFeatureRegistry.TEST_BELOW_BLOCK.get(), new PredicateStateConfig(state, predicate)));
    }
    private static RandomPatchConfiguration blockTestPatch(int tries, int xz, int y, BlockStateProvider state, BlockPredicate predicate)
    {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ReduxFeatureRegistry.TEST_AT_BLOCK.get(), new PredicateStateConfig(state, predicate)));
    }
    private static BlockStateProvider createLeafPileLayers(BlockState state)
    {
        if (state.hasProperty(ReduxStates.LEAF_LAYERS)) {
            return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 1), 6)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 2), 5)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 3), 4)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 4), 3)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 5), 2)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 6), 1)
            ); } else {
            return BlockStateProvider.simple(state);
        }
    }

    private static BlockStateProvider createFlowerbedProvider(BlockState state) {
        if (state.hasProperty(PinkPetalsBlock.FACING) || state.hasProperty(PinkPetalsBlock.AMOUNT)) {
            SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
            if (state.hasProperty(PinkPetalsBlock.FACING))
            {
                for (Direction d : PinkPetalsBlock.FACING.getPossibleValues())
                {
                    if (state.hasProperty(PinkPetalsBlock.AMOUNT))
                    {
                        BlockState temp = state.setValue(PinkPetalsBlock.FACING, d);
                        for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                            builder.add(temp.setValue(PinkPetalsBlock.AMOUNT, i), i);
                        }
                    } else {
                        builder.add(state.setValue(PinkPetalsBlock.FACING, d), 1);
                    }
                }
            } else if (state.hasProperty(PinkPetalsBlock.AMOUNT))
            {
                for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                    builder.add(state.setValue(PinkPetalsBlock.AMOUNT, i), i);
                }
            }
            return new WeightedStateProvider(builder);
        }
        return BlockStateProvider.simple(state);
    }



    private static BlockStateProvider createFlowerbedProvider(BlockState state, BlockState uncommon) {
        if (state.hasProperty(PinkPetalsBlock.FACING) || state.hasProperty(PinkPetalsBlock.AMOUNT) || uncommon.hasProperty(PinkPetalsBlock.FACING) || uncommon.hasProperty(PinkPetalsBlock.AMOUNT)) {
            SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
            if (state.hasProperty(PinkPetalsBlock.FACING))
            {
                for (Direction d : PinkPetalsBlock.FACING.getPossibleValues())
                {
                    if (state.hasProperty(PinkPetalsBlock.AMOUNT))
                    {
                        BlockState temp = state.setValue(PinkPetalsBlock.FACING, d);
                        for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                            builder.add(temp.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                        }
                    } else {
                        builder.add(state.setValue(PinkPetalsBlock.FACING, d), 6);
                    }
                }
            } else if (state.hasProperty(PinkPetalsBlock.AMOUNT))
            {
                for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                    builder.add(state.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                }
            }
            else {
                builder.add(state, 6);
            }



            if (uncommon.hasProperty(PinkPetalsBlock.FACING))
            {
                for (Direction d : PinkPetalsBlock.FACING.getPossibleValues())
                {
                    if (uncommon.hasProperty(PinkPetalsBlock.AMOUNT))
                    {
                        BlockState temp = uncommon.setValue(PinkPetalsBlock.FACING, d);
                        for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                            builder.add(temp.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                        }
                    } else {
                        builder.add(uncommon.setValue(PinkPetalsBlock.FACING, d), 3);
                    }
                }
            } else if (uncommon.hasProperty(PinkPetalsBlock.AMOUNT))
            {
                for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                    builder.add(uncommon.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                }
            }
            else {
                builder.add(uncommon, 2);
            }
            return new WeightedStateProvider(builder);
        }

        return BlockStateProvider.simple(state);
    }
    private static BlockStateProvider createLeafPileLayers(RegistryObject<? extends Block> block)
    {
        return createLeafPileLayers(drops(block));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature(feature, configuration));
    }

    private static String name(RegistryObject<?> reg)
    {
        return reg.getId().getPath();
    }
    private static BlockStateProvider prov(BlockState state)
    {
        return BlockStateProvider.simple(drops(state));
    }
    private static BlockStateProvider prov(RegistryObject<? extends Block> block)
    {
        return prov(block.get().defaultBlockState());
    }


    private static BlockState drops(BlockState state)
    {
        return state.hasProperty(AetherBlockStateProperties.DOUBLE_DROPS) ? state.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true) : state;
    }
    private static BlockState drops(RegistryObject<? extends Block> block)
    {
        return drops(block.get().defaultBlockState());
    }


}
