package net.zepalesque.redux.world.tree.roots;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class BlightwillowRootsPlacer extends RootPlacer {

    public static final MapCodec<BlightwillowRootsPlacer> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    IntProvider.CODEC.fieldOf("trunk_offset_y").forGetter(roots -> roots.trunkOffsetY),
                    BlockStateProvider.CODEC.fieldOf("wood").forGetter(roots -> roots.wood)
            ).apply(builder, BlightwillowRootsPlacer::new));

    private final BlockStateProvider wood;

    public BlightwillowRootsPlacer(IntProvider trunkOffset, BlockStateProvider wood) {
        super(trunkOffset, BlockStateProvider.simple(Blocks.AIR), Optional.empty());
        this.wood = wood;
    }

    @Override
    protected RootPlacerType<?> type() {
        return ReduxRootPlacers.BLIGHTWILLOW_ROOTS.get();
    }

    private static final Direction[] HORIZONTAL_PLANE = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new);
    private static final Direction[] HORIZONTAL_PLANE_SHUFFLE = HORIZONTAL_PLANE.clone();


    // Reuse this instance as to avoid unneeded object creation -- boolean determines if it should use the wood block instead of the log
    private final Map<BlockPos, Boolean> placements = new HashMap<>();

    @Override
    public boolean placeRoots(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos origin, BlockPos trunkOrigin, TreeConfiguration treeConfig) {

        if (level.isStateAtPosition(origin.below(), state -> !isDirt(state))) {
            return false;
        }

        this.placements.clear();

        // Method to ensure there will be one of all 4 possible root heights for the tree
        ArrayUtil.shuffle(HORIZONTAL_PLANE_SHUFFLE, random);

        int height = trunkOrigin.getY() - origin.getY();

        for(int i = 0; i < height; i++) {
            this.placements.put(origin.above(i), false);
        }

        // from 2 to 3 --
        int baseRootHeight = Math.max(height - 5, 2);

        for (Direction d : Direction.Plane.HORIZONTAL) {

            // Place side roots
            int rootSize = baseRootHeight + ArrayUtils.indexOf(HORIZONTAL_PLANE_SHUFFLE, d);

            BlockPos rootStart = origin.relative(d, 1);

            int min = 0;

            for (int i = -1; i > -4; i--) {
                BlockPos test = rootStart.above(i);
                if (level.isStateAtPosition(test, BlockBehaviour.BlockStateBase::isAir))
                    if (i == -3) {
                        unshuffle();
                        return false;
                    } else continue;
                min = i + 1;
                break;
            }

            for (int i = min; i < rootSize; i++) {
                BlockPos pos = rootStart.above(i);
                if (i < rootSize - 1 && validTreePos(level, pos.above())) {
                    this.placements.put(pos, false);
                } else if (validTreePos(level, pos)) {
                    this.placements.put(pos, true);
                }
            }
        }

        unshuffle();

        if (validateAll(level, this.placements)) {
            this.placements.forEach((pos, useWood) -> blockSetter.accept(pos, !useWood ? treeConfig.trunkProvider.getState(random, pos) : this.wood.getState(random, pos)));

            return true;
        } else return false;
    }

    private boolean validateAll(LevelSimulatedReader level, Map<BlockPos, Boolean> placements) {
        for (var key : placements.keySet())
            if (!this.validTreePos(level, key)) return false;
        return true;
    }

    private void unshuffle() {
        // Reset shuffling array to ensure consistency
        System.arraycopy(HORIZONTAL_PLANE, 0, HORIZONTAL_PLANE_SHUFFLE, 0, HORIZONTAL_PLANE.length);
    }

    protected boolean validTreePos(LevelSimulatedReader level, BlockPos pos) {
        return TreeFeature.validTreePos(level, pos);
    }

    public static boolean isDirt(BlockState state) {
        return state.is(BlockTags.DIRT);
    }
}
