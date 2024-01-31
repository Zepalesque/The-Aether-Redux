package net.zepalesque.redux.world.feature;

import com.aetherteam.aether.world.BlockPlacementUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.zepalesque.redux.util.level.WorldgenUtil;
import net.zepalesque.redux.world.feature.config.RootedShelfConfiguration;

public class RootedShelfFeature extends Feature<RootedShelfConfiguration> {
    public RootedShelfFeature(Codec<RootedShelfConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RootedShelfConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        RootedShelfConfiguration config = context.config();

        for (int x1 = pos.getX(); x1 < pos.getX() + 16; ++x1) {
            for (int z1 = pos.getZ(); z1 < pos.getZ() + 16; ++z1) {
                for (int y1 = config.yRange().getMinValue(); y1 < config.yRange().getMaxValue(); ++y1) {
                    BlockPos placementPos = new BlockPos(x1, y1, z1);
                    if (level.getBlockState(placementPos).isAir() && level.getBlockState(placementPos.above()).is(config.validBlocks()) && level.getBlockState(placementPos.above(2)).isAir()) {
                        BlockPlacementUtil.placeDisk(level, config.shelfBlock(), placementPos, config.radius().sample(random), random);
                        WorldgenUtil.placeRootsDisk(level, config.rootBlock(), placementPos.below(), config.radius().sample(random), random, 0.125F, config.shelfBlock());
                        break;
                    }
                }
            }
        }
        return true;
    }
}
