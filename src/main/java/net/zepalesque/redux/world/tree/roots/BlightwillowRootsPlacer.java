package net.zepalesque.redux.world.tree.roots;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.zenith.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

public class BlightwillowRootsPlacer extends RootPlacer {

    public static final MapCodec<BlightwillowRootsPlacer>
        CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                IntProvider.CODEC.fieldOf("trunk_offset_y")
                    .forGetter(instance -> instance.trunkOffsetY),
                Codec.INT.optionalFieldOf("max_root_depth", 2)
                    .forGetter(instance -> instance.maxRootDepth),
                BlockStateProvider.CODEC.fieldOf("wood")
                    .forGetter(instance -> instance.wood)
            ).apply(
                builder,
                BlightwillowRootsPlacer::new
            ));

    private final int maxRootDepth;
    private final BlockStateProvider wood;

    public BlightwillowRootsPlacer(IntProvider trunkOffset, int maxRootDepth, BlockStateProvider wood) {
        super(trunkOffset, BlockStateProvider.simple(Blocks.AIR), Optional.empty());
        this.maxRootDepth = maxRootDepth;
        this.wood = wood;
    }

    @Override
    protected RootPlacerType<?> type() {
        return ReduxRootPlacers.BLIGHTWILLOW_ROOTS.get();
    }

    private static final Direction[] HORIZONTAL_PLANE = Direction.Plane.HORIZONTAL.stream()
        .toArray(Direction[]::new);
    private static final Direction[] HORIZONTAL_PLANE_SHUFFLE = HORIZONTAL_PLANE.clone();


    // Reuse this instance as to avoid unneeded object creation
    //  The boolean determines if it should use the wood block instead of the log
    private final Map<BlockPos, Boolean> placements = new HashMap<>();

    @Override
    public boolean placeRoots(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, BlockPos origin, BlockPos trunkOrigin, TreeConfiguration treeConfig) {

        if (level.isStateAtPosition(origin.below(), state -> !isDirt(state)))
            return false;

        this.placements.clear();

        // Method to ensure there will be one of all 4 possible root heights for the tree
        ArrayUtil.shuffle(HORIZONTAL_PLANE_SHUFFLE, random);
        
        var height = trunkOrigin.getY() - origin.getY();

        for(var i = 0; i < height; i++) this.placements.put(origin.above(i), false);

        // from 2 to 3 --
        var baseRootHeight = Math.max(height - 5, 2);

        for (var dir : Direction.Plane.HORIZONTAL) {

            // Place side roots
            var rootSize = baseRootHeight + ArrayUtils.indexOf(HORIZONTAL_PLANE_SHUFFLE, dir);
            
            var rootStartPos = origin.relative(dir, 1);
            
            var min = 0;

            for (var i = -1; i > -2 - this.maxRootDepth; i--) {
                var testPos = rootStartPos.above(i);
                if (this.validRootPos(level, testPos))
                    if (i < -this.maxRootDepth) {
	                    this.unshuffle();
                        return false;
                    } else continue;
                min = i + 1;
                break;
            }

            for (var i = min; i < rootSize; i++) {
                var pos = rootStartPos.above(i);
                this.placements.put(pos, !(i < rootSize - 1 && this.validRootPos(level, pos.above())));
            }
        }
	    
	    this.unshuffle();

        if (this.validateAll(level, this.placements)) {
            this.placements.forEach((pos, useWood) -> setter.accept(pos, !useWood || !ReduxConfig.SERVER.use_wood_blocks.get() ? treeConfig.trunkProvider.getState(random, pos) : this.wood.getState(random, pos)));
            TrunkPlacer.setDirtAt(level, setter, random, origin.below(), treeConfig);

            return true;
        } else return false;
    }

    private boolean validateAll(LevelSimulatedReader level, Map<BlockPos, Boolean> placements) {
        for (var key : placements.keySet())
            if (!this.validRootPos(level, key)) return false;
        return true;
    }

    private void unshuffle() {
        // Reset shuffling array to ensure consistency
        System.arraycopy(HORIZONTAL_PLANE, 0, HORIZONTAL_PLANE_SHUFFLE, 0, HORIZONTAL_PLANE.length);
    }

    protected boolean validRootPos(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, state -> state.isAir() || state.is(BlockTags.REPLACEABLE) || state.is(BlockTags.REPLACEABLE_BY_TREES));
    }

    public static boolean isDirt(BlockState state) {
        return state.is(BlockTags.DIRT);
    }
}
