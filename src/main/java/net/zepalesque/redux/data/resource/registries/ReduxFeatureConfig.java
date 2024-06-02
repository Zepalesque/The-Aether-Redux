package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.world.foliageplacer.CrystalFoliagePlacer;
import com.aetherteam.aether.world.trunkplacer.CrystalTreeTrunkPlacer;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenConfiguredFeatureBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
import net.zepalesque.redux.data.resource.builders.ReduxFeatureBuilders;
import net.zepalesque.redux.world.feature.gen.CloudbedFeature;
import net.zepalesque.redux.world.feature.gen.ReduxFeatures;
import net.zepalesque.redux.world.tree.foliage.SkyrootFoliagePlacer;

import java.util.function.Supplier;

public class ReduxFeatureConfig extends ReduxFeatureBuilders {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDBED = createKey("cloudbed");

    // Overrides
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_BONEMEAL = createKey("aether_grass_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH = AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH = AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION;

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE = AetherConfiguredFeatures.CRYSTAL_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TREE = AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION;




    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configs = context.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        register(context, CLOUDBED, ReduxFeatures.CLOUDBED.get(),
                new CloudbedFeature.Config(
                        prov(AetherFeatureStates.COLD_AERCLOUD),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        8,
                        ReduxDensityFunctions.get(functions, ReduxDensityFunctions.CLOUDBED_NOISE),
                        10,
                        ReduxDensityFunctions.get(functions, ReduxDensityFunctions.CLOUDBED_Y_OFFSET),
                        10));

        // Overrides
        register(context, GRASS_PATCH, Feature.RANDOM_PATCH, patch(32, 7, 3, prov(ReduxBlocks.SHORT_AETHER_GRASS), NOT_ON_COARSE_DIRT));
        register(context, TALL_GRASS_PATCH, Feature.NO_OP, new NoneFeatureConfiguration());

        register(context, GRASS_BONEMEAL, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(prov(ReduxBlocks.SHORT_AETHER_GRASS)));

        register(context, CRYSTAL_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(ReduxWoodSets.CRYSTAL.log()),
                        new CrystalTreeTrunkPlacer(7, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.CRYSTAL_LEAVES, 4).add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1).build()),
                        new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        register(context, SKYROOT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
                        new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());


    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Redux.MODID, name));
    }

    private static String name(DeferredHolder<?, ?> reg) {
        return reg.getId().getPath();
    }

    private static BlockStateProvider prov(BlockState state) {
        return BlockStateProvider.simple(drops(state));
    }

    private static BlockStateProvider prov(Supplier<? extends Block> block) {
        return prov(block.get().defaultBlockState());
    }

    private static BlockState drops(BlockState state) {
        return state.hasProperty(AetherBlockStateProperties.DOUBLE_DROPS) ? state.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true) : state;
    }

    private static BlockState drops(Supplier<? extends Block> block) {
        return drops(block.get().defaultBlockState());
    }

    private static BlockState naturalDrops(Supplier<? extends Block> block) {
        BlockState b = block.get().defaultBlockState();
        return b.hasProperty(ReduxStates.NATURAL_GEN) ? drops(b.setValue(ReduxStates.NATURAL_GEN, true)) : drops(b);
    }
}
