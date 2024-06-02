package net.zepalesque.redux.data.resource.builders;

import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.zenith.world.feature.gen.BlockWithPredicateFeature;
import net.zepalesque.zenith.world.feature.gen.ZenithFeatures;

public class ReduxFeatureBuilders {

    public static final BlockPredicate NOT_ON_COARSE_DIRT = BlockPredicate.not(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), ReduxTags.Blocks.COARSE_AETHER_DIRT));

    public static RandomPatchConfiguration patch(int tries, int xz, int y, BlockStateProvider state) {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(state)));
    }

    public static RandomPatchConfiguration patch(int tries, int xz, int y, BlockStateProvider state, BlockPredicate predicate) {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ZenithFeatures.BLOCK_WITH_PREDICATE.get(), new BlockWithPredicateFeature.Config(state, predicate)));
    }
}
