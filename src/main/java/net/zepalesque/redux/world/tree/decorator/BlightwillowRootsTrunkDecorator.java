package net.zepalesque.redux.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.util.level.WorldgenUtil;

public class BlightwillowRootsTrunkDecorator extends TreeDecorator {

    public static final Codec<BlightwillowRootsTrunkDecorator> CODEC = RecordCodecBuilder.create((vines) ->
            vines.group(
                            BlockStateProvider.CODEC.fieldOf("root_block").forGetter((config) -> config.rootBlock),
                            BlockStateProvider.CODEC.fieldOf("wall_block").forGetter((config) -> config.wallBlock),
                            IntProvider.CODEC.fieldOf("root_height").forGetter((config) -> config.rootHeight))
                    .apply(vines, BlightwillowRootsTrunkDecorator::new));
    private final BlockStateProvider rootBlock;
    private final BlockStateProvider wallBlock;
    private final IntProvider rootHeight;

    public BlightwillowRootsTrunkDecorator(BlockStateProvider rootBlock, BlockStateProvider wallBlock, IntProvider rootHeight) {
        this.rootBlock = rootBlock;
        this.wallBlock = wallBlock;
        this.rootHeight = rootHeight;
    }

    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.BLIGHTWILLOW_ROOTS.get();
    }

    public void place(Context context) {
        if (ReduxConfig.COMMON.wall_roots.get()) {
            BlockPos basePos = context.logs().get(1);
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            int heightBelowLeaves = context.logs().size() - 6;
            for (Direction d : Direction.Plane.HORIZONTAL) {
                BlockPos startPos = basePos.relative(d);
                int height = this.rootHeight.sample(context.random());
                for (int i = 0; i < height; i++) {
                    mutable.setWithOffset(startPos, 0, i, 0);
                    BlockPos immutable = mutable.immutable();
                    this.placeBlockAt(context, immutable, this.rootBlock.getState(context.random(), immutable));
                }
                for (int i = height; i < heightBelowLeaves; i++) {
                    mutable.setWithOffset(startPos, 0, i, 0);
                    BlockPos immutable = mutable.immutable();
                    this.placeBlockAt(context, immutable, this.wallBlock.getState(context.random(), immutable).setValue(WorldgenUtil.getWallSide(d.getOpposite()), WallSide.TALL));
                }
            }
        }
    }

    private void placeBlockAt(Context context, BlockPos pos, BlockState state) {
        if (TreeFeature.validTreePos(context.level(), pos) && !TreeFeature.validTreePos(context.level(), pos.below())) {
            context.setBlock(pos, state);
        }
    }
}