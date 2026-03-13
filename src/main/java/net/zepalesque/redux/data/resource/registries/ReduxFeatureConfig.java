package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.world.foliageplacer.GoldenOakFoliagePlacer;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.resource.builders.ReduxDensityBuilders;
import net.zepalesque.redux.data.resource.builders.ReduxFeatureBuilders;
import net.zepalesque.redux.extstate.ReduxStateLists;
import net.zepalesque.redux.world.feature.gen.CloudbedFeature;
import net.zepalesque.redux.world.feature.gen.DebugNoiseFeature;
import net.zepalesque.redux.world.feature.gen.LakesFeature;
import net.zepalesque.redux.world.feature.gen.ReduxFeatures;
import net.zepalesque.redux.world.feature.gen.WaterPlantFeature;
import net.zepalesque.redux.world.tree.decorator.GoldenVineDecorator;
import net.zepalesque.redux.world.tree.foliage.BlightwillowFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.CrystalFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.HookedFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.MoonfirFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.SkyrootFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.SmallGoldenOakFoliagePlacer;
import net.zepalesque.redux.world.tree.roots.BlightwillowRootsPlacer;
import net.zepalesque.redux.world.tree.trunk.BlightwillowTrunkPlacer;
import net.zepalesque.redux.world.tree.trunk.HookedTrunkPlacer;
import net.zepalesque.unity.block.UnityBlocks;
import net.zepalesque.unity.data.UnityTags;
import net.zepalesque.unity.extstate.UnityStateLists;
import net.zepalesque.zenith.api.block.predicate.NoisePredicate;
import net.zepalesque.zenith.api.world.feature.gen.ExtendableStateListBlockFeature;
import net.zepalesque.zenith.api.world.feature.gen.LargeRockFeature;
import net.zepalesque.zenith.api.world.tree.trunk.IntProviderTrunkPlacer;
import net.zepalesque.zenith.core.registry.ZenithFeatures;

public class ReduxFeatureConfig extends ReduxFeatureBuilders {
	public static final ResourceKey<ConfiguredFeature<?, ?>> AURUM_PATCH = createKey(
		name(ReduxFlowerSets.AURUM.flower()) + "_patch"
	);
	public static final ResourceKey<ConfiguredFeature<?, ?>> LUCKY_CLOVER_PATCH = createKey(
		name(ReduxFlowerSets.LUCKY_CLOVER.flower()) + "_patch"
	);
	public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_CLOVERS_PATCH = createKey(
		name(ReduxBlocks.GOLDEN_CLOVERS) + "_patch"
	);
	public static final ResourceKey<ConfiguredFeature<?, ?>> TURBO_VERBENA_PATCH = createKey(
		name(ReduxBlocks.TURBO_VERBENA) + "_patch"
	);
	
	public static final ResourceKey<ConfiguredFeature<?, ?>> CAELGAE_PATCH = createKey(
		name(ReduxBlocks.CAELGAE_PATCH)
	);
	
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLOOMTAIL = createKey(
		name(ReduxBlocks.BLOOMTAIL) + "_piece"
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDBED = createKey("cloudbed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAKES = createKey("lakes");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEBUG_NOISE = createKey("debug_noise");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GILDENROOT_TREE = createKey("small_gildenroot");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GILDENROOT_TREE = createKey("large_gildenroot");
	public static final ResourceKey<ConfiguredFeature<?, ?>> VARIED_GILDENROOT_TREE = createKey("varied_gildenroot");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GOLDEN_OAK_TREE = createKey("small_golden_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GOLDEN_OAK_TREE = createKey("large_golden_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_SKYROOT_TREE = createKey("small_skyroot");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SKYROOT_TREE = createKey("large_skyroot");

	public static final ResourceKey<ConfiguredFeature<?, ?>> MOONFIR_TREE = createKey("moonfir");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SENTRITE_ORE = createKey(
		name(ReduxStoneSets.SENTRITE.block()) + "_ore"
	);
	public static final ResourceKey<ConfiguredFeature<?, ?>> ANGILITE_ORE = createKey(
		name(ReduxStoneSets.ANGILITE.block()) + "_ore"
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_HOLYSTONE_ORE = createKey(
		name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore"
	);
	public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_HOLYSTONE_ORE = createKey(
		name(ReduxStoneSets.GILDED_HOLYSTONE.block()) + "_ore"
	);
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLEAKMOSS_HOLYSTONE_ORE = createKey(
		name(ReduxStoneSets.BLEAKMOSS_HOLYSTONE.block()) + "_ore"
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES = createKey("gilded_groves_trees");

	public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_TREES = createKey("the_blight_trees");

	public static final ResourceKey<ConfiguredFeature<?, ?>> AMBROSIUM_ROCK = createKey("ambrosium_rock");

	public static final ResourceKey<ConfiguredFeature<?, ?>> WYNDSPROUTS_PATCH = createKey(
		name(ReduxBlocks.WYNDSPROUTS) + "_patch"
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> BLEAKMOSS_VEGETATION = createKey("bleakmoss_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLEAKMOSS_BONEMEAL = createKey("bleakmoss_bonemeal");

	public static final ResourceKey<ConfiguredFeature<?, ?>> GILDENMOSS_VEGETATION = createKey("gildenmoss_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GILDENMOSS_BONEMEAL = createKey("gildenmoss_bonemeal");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_STORMROOT_TREE = createKey("small_stormroot");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_STORMROOT_TREE = createKey("large_stormroot");

	public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTWILLOW_TREE = createKey("blightwillow");

	// Overrides
	public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TREE =
		AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION;
	public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE =
		AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION;
	public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE =
		AetherConfiguredFeatures.CRYSTAL_TREE_CONFIGURATION;
	public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_ISLAND =
		AetherConfiguredFeatures.CRYSTAL_ISLAND_CONFIGURATION;

	// rip bootstap :pensive:
	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configs = context.lookup(Registries.CONFIGURED_FEATURE);
		HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
		HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
		//        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

		FeatureUtils.register(
			context,
			CLOUDBED,
			ReduxFeatures.CLOUDBED.get(),
			new CloudbedFeature.Config(
				prov(AetherFeatureStates.COLD_AERCLOUD),
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				8,
				ReduxDensityBuilders.get(functions, ReduxDensityFunctions.CLOUDBED_NOISE),
				10,
				ReduxDensityBuilders.get(functions, ReduxDensityFunctions.CLOUDBED_Y_OFFSET),
				10
			)
		);
        
        var noises = context.lookup(Registries.NOISE);
		double threshold;
		var lakeFloor = new RuleBasedBlockStateProvider(
			BlockStateProvider.simple(AetherFeatureStates.AETHER_DIRT), Stream.of(
			new RuleBasedBlockStateProvider.Rule(
				BlockPredicate.allOf(
					BlockPredicate.matchesTag(UnityTags.Blocks.AETHER_LAKE_SKIP_REPLACEMENT),
					BlockPredicate.not(BlockPredicate.solid(OFFSET_ABOVE)),
					BlockPredicate.not(BlockPredicate.matchesBlocks(OFFSET_ABOVE, Blocks.WATER))
				), BlockStateProvider.simple(Blocks.AIR)
			),
			
			new RuleBasedBlockStateProvider.Rule(
				BlockPredicate.anyOf(
					BlockPredicate.matchesTag(OFFSET_ABOVE, AetherTags.Blocks.AETHER_DIRT),
					BlockPredicate.matchesBlocks(OFFSET_ABOVE, AetherBlocks.AETHER_DIRT.get())
				), prov(AetherBlocks.AETHER_DIRT)
			),
			
			new RuleBasedBlockStateProvider.Rule(
				BlockPredicate.allOf(
					new NoisePredicate(noises.getOrThrow(Noises.SWAMP), 2743L, -0.3, threshold = 0.1),
					BlockPredicate.matchesBlocks(OFFSET_ABOVE, Blocks.WATER)
				),
				prov(UnityBlocks.AETHER_MUD)
			),
			
			new RuleBasedBlockStateProvider.Rule(
				BlockPredicate.allOf(
					// Use same seed, mud will surround clay
					BlockPredicate.matchesBlocks(OFFSET_ABOVE, Blocks.WATER),
					new NoisePredicate(noises.getOrThrow(Noises.SWAMP), 2743L, threshold, Double.MAX_VALUE)
				),
				prov(UnityBlocks.VALKYRIE_CLAY)
			),
			
			new RuleBasedBlockStateProvider.Rule(
				BlockPredicate.allOf(
					BlockPredicate.matchesTag(UnityTags.Blocks.AETHER_LAKE_SKIP_REPLACEMENT),
					BlockPredicate.matchesTag(OFFSET_ABOVE, BlockTags.AIR)
				), BlockStateProvider.simple(Blocks.AIR)
			)
		).toList());
		
		FeatureUtils.register(
			context,
			LAKES,
			ReduxFeatures.LAKES.get(),
			new LakesFeature.Config(
				prov(Blocks.WATER.defaultBlockState()),
				prov(AetherFeatureStates.QUICKSOIL),
				lakeFloor,
				BlockPredicate.replaceable(),
				LakesFeature.Y_LEVEL_DEFAULT,
				ReduxDensityBuilders.get(functions, ReduxDensityFunctions.LAKES_NOISE),
				10,
				ReduxDensityBuilders.get(functions, ReduxDensityFunctions.LAKES_Y_OFFSET),
				10,
				ReduxDensityBuilders.get(functions, ReduxDensityFunctions.LAKES_THICKNESS),
				2.5d
			)
		);
		FeatureUtils.register(
			context,
			DEBUG_NOISE,
			ReduxFeatures.DEBUG_NOISE.get(),
			new DebugNoiseFeature.Config(
				List.of(
					prov(Blocks.BLACK_CONCRETE.defaultBlockState()),
					prov(Blocks.COAL_BLOCK.defaultBlockState()),
					prov(Blocks.BLACK_WOOL.defaultBlockState()),
					prov(Blocks.BLACK_CONCRETE_POWDER.defaultBlockState()),
					prov(Blocks.BLACK_TERRACOTTA.defaultBlockState()),
					prov(Blocks.BLACKSTONE.defaultBlockState()),
					prov(Blocks.GRAY_CONCRETE.defaultBlockState()),
					prov(Blocks.NETHERITE_BLOCK.defaultBlockState()),
					prov(Blocks.GRAY_WOOL.defaultBlockState()),
					prov(Blocks.SMOOTH_BASALT.defaultBlockState()),
					prov(Blocks.GRAY_CONCRETE_POWDER.defaultBlockState()),
					prov(Blocks.DEEPSLATE.defaultBlockState()),
					prov(Blocks.CYAN_TERRACOTTA.defaultBlockState()),
					prov(Blocks.TUFF.defaultBlockState()),
					prov(Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState()),
					prov(Blocks.STONE.defaultBlockState()),
					prov(Blocks.LIGHT_GRAY_WOOL.defaultBlockState()),
					prov(Blocks.LIGHT_GRAY_CONCRETE_POWDER.defaultBlockState()),
					prov(Blocks.LIGHT_GRAY_CONCRETE_POWDER.defaultBlockState()),
					prov(AetherBlocks.HOLYSTONE),
					prov(Blocks.DIORITE.defaultBlockState()),
					prov(Blocks.WHITE_CONCRETE.defaultBlockState()),
					prov(Blocks.CALCITE.defaultBlockState()),
					prov(UnityBlocks.VALKYRIE_CLAY),
					prov(Blocks.SMOOTH_QUARTZ.defaultBlockState()),
					prov(Blocks.WHITE_CONCRETE_POWDER.defaultBlockState()),
					prov(Blocks.WHITE_WOOL.defaultBlockState()),
					prov(Blocks.SNOW_BLOCK.defaultBlockState())
				),
				24,
				36,
				ReduxDensityBuilders.get(functions, ReduxDensityFunctions.LAKES_THICKNESS)
			)
		);

		FeatureUtils.register(
			context,
			CRYSTAL_ISLAND,
			ReduxFeatures.CRYSTAL_ISLAND.get(),
			NoneFeatureConfiguration.INSTANCE
		);

		FeatureUtils.register(
			context,
			SMALL_GILDENROOT_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
				new StraightTrunkPlacer(4, 2, 0),
				prov(ReduxBlocks.GILDENROOT_LEAVES),
				new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1)
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			SMALL_STORMROOT_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
				new StraightTrunkPlacer(4, 2, 0),
				prov(ReduxBlocks.STORMROOT_LEAVES),
				new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1)
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			LARGE_STORMROOT_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
				// TODO
				new DarkOakTrunkPlacer(7, 2, 1),
				prov(ReduxBlocks.STORMROOT_LEAVES),
				// TODO
				new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
				new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			BLIGHTWILLOW_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				prov(ReduxWoodSets.BLIGHTWILLOW.log()),
				new BlightwillowTrunkPlacer(ConstantInt.of(6)),
				new WeightedStateProvider(
					SimpleWeightedRandomList.<BlockState>builder()
						.add(drops(ReduxBlocks.BLIGHTWILLOW_LEAVES), 15)
						.add(drops(ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES), 1)
						.build()
				),
				new BlightwillowFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				Optional.of(new BlightwillowRootsPlacer(UniformInt.of(6, 8), 2, prov(ReduxWoodSets.BLIGHTWILLOW.wood()))),
				new TwoLayersFeatureSize(7, 0, 3)
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			LARGE_GILDENROOT_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
				new HookedTrunkPlacer(10, 12, 12),
				prov(ReduxBlocks.GILDENROOT_LEAVES),
				new HookedFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
				new TwoLayersFeatureSize(4, 2, 6)
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			MOONFIR_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				prov(ReduxWoodSets.MOONFIR.log()),
				new StraightTrunkPlacer(8, 1, 1),
				prov(ReduxBlocks.MOONFIR_LEAVES),
				new MoonfirFoliagePlacer(
					ConstantInt.of(4),
					ConstantInt.of(1),
					UniformInt.of(8, 10),
					UniformInt.of(4, 6),
					UniformFloat.of(0.6f, 1.4f),
					UniformFloat.of(0.0f, Mth.TWO_PI),
					UniformFloat.of(0.8f, 1.4f)
				),
				new TwoLayersFeatureSize(1, 0, 1)
			).ignoreVines().build()
		);

		FeatureUtils.register(
			context,
			SMALL_GOLDEN_OAK_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				new WeightedStateProvider(
					new SimpleWeightedRandomList.Builder<BlockState>()
						.add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
						.add(AetherFeatureStates.SKYROOT_LOG, 7)
				),
				new IntProviderTrunkPlacer(UniformInt.of(7, 9)),
				BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
				new SmallGoldenOakFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1)
			)
				.ignoreVines()
				.decorators(
					List.of(
						new GoldenVineDecorator(
							0.25F,
							prov(ReduxBlocks.GOLDEN_VINES_PLANT),
							prov(ReduxBlocks.GOLDEN_VINES),
							UniformInt.of(1, 3),
							Optional.empty()
						)
					)
				)
				.build()
		);

		FeatureUtils.register(
			context,
			LARGE_GOLDEN_OAK_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				new WeightedStateProvider(
					new SimpleWeightedRandomList.Builder<BlockState>()
						.add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
						.add(AetherFeatureStates.SKYROOT_LOG, 3)
				),
				new IntProviderTrunkPlacer(UniformInt.of(11, 14)),
				BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
				new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(10)),
				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(13))
			)
				.ignoreVines()
				.decorators(
					List.of(
						new GoldenVineDecorator(
							0.25F,
							prov(ReduxBlocks.GOLDEN_VINES_PLANT),
							prov(ReduxBlocks.GOLDEN_VINES),
							UniformInt.of(1, 5),
							Optional.empty()
						)
					)
				)
				.build()
		);

		FeatureUtils.register(
			context,
			SMALL_SKYROOT_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
				new StraightTrunkPlacer(4, 2, 0),
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
				new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1)
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			LARGE_SKYROOT_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
				new CherryTrunkPlacer(
					8,
					1,
					1,
					UniformInt.of(2, 3),
					UniformInt.of(2, 3),
					UniformInt.of(-4, -3),
					UniformInt.of(-1, 0)
				),
				BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
				new GoldenOakFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(8)),
				new TwoLayersFeatureSize(1, 0, 1)
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			VARIED_GILDENROOT_TREE,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							configs.getOrThrow(SMALL_GILDENROOT_TREE),
							PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get())
						),
						0.60F
					)
				),
				PlacementUtils.inlinePlaced(
					configs.getOrThrow(LARGE_GILDENROOT_TREE),
					PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get()),
					HAS_TRUNK_SUPPORT_2X2
				)
			)
		);

		FeatureUtils.register(
			context,
			GROVE_TREES,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							configs.getOrThrow(VARIED_GILDENROOT_TREE),
							PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get())
						),
						0.375F
					)
				),
				PlacementUtils.inlinePlaced(
					configs.getOrThrow(GOLDEN_OAK_TREE),
					PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())
				)
			)
		);

		FeatureUtils.register(
			context,
			BLIGHT_TREES,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							configs.getOrThrow(SMALL_STORMROOT_TREE),
							PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.STORMROOT_SAPLING.flower().get())
						),
						0.35F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							configs.getOrThrow(LARGE_STORMROOT_TREE),
							PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.STORMROOT_SAPLING.flower().get()),
							// TODO: similar to blightwillow? allow up to ONE block of leeway, up or down
							HAS_TRUNK_SUPPORT_2X2
						),
						0.4F
					)
				),
				PlacementUtils.inlinePlaced(
					configs.getOrThrow(BLIGHTWILLOW_TREE),
					PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.BLIGHTWILLOW_SAPLING.flower().get())
				)
			)
		);

		FeatureUtils.register(
			context,
			SENTRITE_ORE,
			Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
				drops(ReduxStoneSets.SENTRITE.block()),
				48,
				0.0F
			)
		);

		FeatureUtils.register(
			context,
			ANGILITE_ORE,
			Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
				drops(ReduxStoneSets.ANGILITE.block()),
				32,
				0.0F
			)
		);

		FeatureUtils.register(
			context,
			MOSSY_HOLYSTONE_ORE,
			Feature.ORE,
			new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE), drops(AetherBlocks.MOSSY_HOLYSTONE), 24, 0.3F)
		);

		FeatureUtils.register(
			context,
			GILDED_HOLYSTONE_ORE,
			Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
				drops(ReduxStoneSets.GILDED_HOLYSTONE.block()),
				24,
				0.3F
			)
		);

		FeatureUtils.register(
			context,
			BLEAKMOSS_HOLYSTONE_ORE,
			Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
				drops(ReduxStoneSets.BLEAKMOSS_HOLYSTONE.block()),
				24,
				0.3F
			)
		);

		FeatureUtils.register(context, AURUM_PATCH, Feature.FLOWER, patch(12, 7, 3, prov(ReduxFlowerSets.AURUM.flower())));

		FeatureUtils.register(
			context,
			LUCKY_CLOVER_PATCH,
			Feature.FLOWER,
			patch(14, 7, 3, prov(ReduxFlowerSets.LUCKY_CLOVER.flower()))
		);

		FeatureUtils.register(
			context,
			GOLDEN_CLOVERS_PATCH,
			Feature.FLOWER,
			patch(24, 7, 3, petals(drops(ReduxBlocks.GOLDEN_CLOVERS)))
		);
		FeatureUtils.register(
			context,
			TURBO_VERBENA_PATCH,
			Feature.FLOWER,
			patch(24, 5, 3, prov(ReduxBlocks.TURBO_VERBENA))
		);
		FeatureUtils.register(
			context,
			CAELGAE_PATCH,
			Feature.RANDOM_PATCH,
			patch(24, 6, 3, prov(ReduxBlocks.CAELGAE_PATCH))
		);
		
		FeatureUtils.register(
			context,
			BLOOMTAIL,
			ReduxFeatures.WATER_PLANT.get(),
			new WaterPlantFeature.Config(prov(ReduxBlocks.BLOOMTAIL))
		);

		FeatureUtils.register(
			context,
			AMBROSIUM_ROCK,
			ZenithFeatures.LARGE_ROCK.get(),
			new LargeRockFeature.Config(
				new WeightedStateProvider(
					new SimpleWeightedRandomList.Builder<BlockState>()
						.add(AetherFeatureStates.HOLYSTONE, 5)
						.add(drops(ReduxStoneSets.GILDED_HOLYSTONE.block()), 3)
						.add(AetherFeatureStates.AMBROSIUM_ORE, 1)
				),
				Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)),
				Optional.empty()
			)
		);

		// Overrides
		FeatureUtils.register(
			context,
			CRYSTAL_TREE,
			Feature.TREE,
			new TreeConfiguration.TreeConfigurationBuilder(
				prov(ReduxWoodSets.CRYSTAL.log()),
				new StraightTrunkPlacer(7, 0, 0),
				new WeightedStateProvider(
					new SimpleWeightedRandomList.Builder<BlockState>()
						.add(AetherFeatureStates.CRYSTAL_LEAVES, 4)
						.add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1)
						.build()
				),
				new CrystalFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 1)
			)
				.ignoreVines()
				.build()
		);

		FeatureUtils.register(
			context,
			SKYROOT_TREE,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							configs.getOrThrow(LARGE_SKYROOT_TREE),
							PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())
						),
						0.1F
					)
				),
				PlacementUtils.inlinePlaced(
					configs.getOrThrow(SMALL_SKYROOT_TREE),
					PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())
				)
			)
		);

		FeatureUtils.register(
			context,
			GOLDEN_OAK_TREE,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							configs.getOrThrow(SMALL_GOLDEN_OAK_TREE),
							PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())
						),
						0.35F
					)
				),
				PlacementUtils.inlinePlaced(
					configs.getOrThrow(LARGE_GOLDEN_OAK_TREE),
					PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())
				)
			)
		);

		FeatureUtils.register(context, WYNDSPROUTS_PATCH, Feature.FLOWER, patch(24, 5, 3, prov(ReduxBlocks.WYNDSPROUTS)));

		FeatureUtils.register(
			context,
			AetherConfiguredFeatures.SINGLE_GOLD_DUNGEON_FLOWER_CONFIGURATION,
			Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new WeightedStateProvider(
					new SimpleWeightedRandomList.Builder<BlockState>()
						.add(ReduxFlowerSets.AURUM.flower().get().defaultBlockState(), 2)
						.add(ReduxFlowerSets.FLAREBLOSSOM.flower().get().defaultBlockState(), 1)
				)
			)
		);

		// TODO
		FeatureUtils.register(
			context,
			BLEAKMOSS_VEGETATION,
			ZenithFeatures.EXTENDABLE_STATE_LIST_BLOCK.get(),
			new ExtendableStateListBlockFeature.Config(ReduxStateLists.BLEAKMOSS.get(), Optional.empty())
		);

		FeatureUtils.register(
			context,
			BLEAKMOSS_BONEMEAL,
			Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				UnityTags.Blocks.AETHER_CARVER_REPLACEABLES,
				prov(ReduxBlocks.BLEAKMOSS_BLOCK),
				Holder.direct(new PlacedFeature(configs.getOrThrow(BLEAKMOSS_VEGETATION), List.of())),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0.0F,
				2,
				0.8F,
				UniformInt.of(1, 2),
				0.75F
			)
		);

		// TODO
		FeatureUtils.register(
			context,
			GILDENMOSS_VEGETATION,
			ZenithFeatures.EXTENDABLE_STATE_LIST_BLOCK.get(),
			new ExtendableStateListBlockFeature.Config(UnityStateLists.FLUTEMOSS.get(), Optional.empty())
		);

		FeatureUtils.register(
			context,
			GILDENMOSS_BONEMEAL,
			Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				UnityTags.Blocks.AETHER_CARVER_REPLACEABLES,
				prov(ReduxBlocks.GILDENMOSS_BLOCK),
				Holder.direct(new PlacedFeature(configs.getOrThrow(GILDENMOSS_VEGETATION), List.of())),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0.0F,
				2,
				0.8F,
				UniformInt.of(1, 2),
				0.75F
			)
		);
	}
}
