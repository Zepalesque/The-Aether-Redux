package net.zepalesque.redux.world.tree.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class IntProviderTrunkPlacer extends TrunkPlacer {
    public static final Codec<IntProviderTrunkPlacer> CODEC = IntProvider.CODEC.fieldOf("height").xmap(IntProviderTrunkPlacer::new, placer -> placer.height).stable().codec();

    protected final IntProvider height;
    public IntProviderTrunkPlacer(IntProvider height) {
        super(0, 0, 0);
        this.height = height;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ReduxTrunkPlacers.INT_PROVIDER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, int height, BlockPos origin, TreeConfiguration config) {
        setDirtAt(level, setter, random, origin.below(), config);

        for(int i = 0; i < height; ++i) {
            this.placeLog(level, setter, random, origin.above(i), config);
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(height), 0, false));
    }

    @Override
    public int getTreeHeight(RandomSource random) {
        return this.height.sample(random);
    }
}
