package net.zepalesque.redux.world.tree.trunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.function.BiConsumer;

public class SmallBlightwillowTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<SmallBlightwillowTrunkPlacer>
        CODEC = RecordCodecBuilder.mapCodec(
        builder -> builder.group(
            IntProvider.codec(4, Integer.MAX_VALUE)
                .fieldOf("height")
                .forGetter(instance -> instance.height)
        ).apply(builder, SmallBlightwillowTrunkPlacer::new));
    
    
    protected final IntProvider height;
    public SmallBlightwillowTrunkPlacer(IntProvider height) {
        super(0, 0, 0);
        this.height = height;
    }
    
    @Override
    protected TrunkPlacerType<?> type() {
        return null; // todo
    }
    
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
        LevelSimulatedReader levelSimulatedReader,
        BiConsumer<BlockPos, BlockState> biConsumer,
        RandomSource randomSource,
        int i,
        BlockPos blockPos,
        TreeConfiguration treeConfiguration) {
        return List.of();
    }
}
