package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.material.Fluids;
import net.zepalesque.redux.util.level.WorldgenUtil;


public class BlightwillowFoliagePlacer extends FoliagePlacer {
    public static final Codec<BlightwillowFoliagePlacer> CODEC = RecordCodecBuilder.create((p2) -> {
        return foliagePlacerParts(p2).apply(p2, BlightwillowFoliagePlacer::new);
    });

    public BlightwillowFoliagePlacer(IntProvider rad, IntProvider offs) {
        super(rad, offs);
    }

    protected FoliagePlacerType<?> type() {
        return ReduxFoliagePlacers.BLIGHTWILLOW.get();
    }



    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int i1, FoliageAttachment foliageAttachment, int foliageMaxHeight, int i2, int i3) {
        BlockPos blockpos = foliageAttachment.pos();
        BlockPos.MutableBlockPos mbp = new BlockPos.MutableBlockPos();
        generateLeaves(mbp, levelSimulatedReader, blockpos, foliageSetter, randomSource, treeConfiguration);
    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return 2;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }

    private void generateLeaves(BlockPos.MutableBlockPos mutable, LevelSimulatedReader level, BlockPos origin, FoliageSetter setter, RandomSource rand, TreeConfiguration config)
    {
        mutable.set(origin);
        BlockPos center = origin.below(3);
        // Extra logs
        for (Direction d : Direction.Plane.HORIZONTAL) {
            mutable.setWithOffset(center, d);
            tryPlaceLog(level, setter, rand, config, mutable.immutable());
        }
        // Base round shape
        int extend = 3;
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 3; y++) {
                for (int z = -2; z <= 2; z++) {
                int add = Mth.abs(x) + Mth.abs(y) + Mth.abs(z);
                    if (add <= extend) {
                        mutable.setWithOffset(center, x, y, z);
                        tryPlaceLeaf(level, setter, rand, config, mutable.immutable());
                    }
                }
            }
        }
        // Spike-like things
        int layer1Height = 3;
        int layer2Height = 4;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            BlockPos start = WorldgenUtil.withOffset(center, d, extend);
            for (int i = 0; i < layer1Height; i++) {
                mutable.setWithOffset(start, 0, i, 0);
                tryPlaceLeaf(level, setter, rand, config, mutable.immutable());
            }
            for (int i = 1; i < layer2Height + 1; i++) {
                mutable.setWithOffset(start, d.getStepX(), i, d.getStepZ());
                tryPlaceLeaf(level, setter, rand, config, mutable.immutable());
            }
        }

    }
    protected static boolean tryPlaceLog(LevelSimulatedReader level, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfiguration, BlockPos pos) {
        if (!TreeFeature.validTreePos(level, pos)) {
            return false;
        } else {
            BlockState blockstate = treeConfiguration.trunkProvider.getState(random, pos);
            foliageSetter.set(pos, blockstate);
            return true;
        }
    }


}