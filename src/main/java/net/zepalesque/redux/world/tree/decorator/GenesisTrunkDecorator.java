package net.zepalesque.redux.world.tree.decorator;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class GenesisTrunkDecorator extends TreeDecorator {
    public static final Codec<GenesisTrunkDecorator> CODEC = BlockStateProvider.CODEC.fieldOf("provider").xmap(GenesisTrunkDecorator::new, (p_69327_) -> {
        return p_69327_.provider;
    }).codec();
    private final BlockStateProvider provider;

    public GenesisTrunkDecorator(BlockStateProvider p_69306_) {
        this.provider = p_69306_;
    }

    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.GENESIS_TRUNK.get();
    }

    public void place(TreeDecorator.Context context) {
        BlockPos basePos = context.logs().get(1);

        float f = 0;
        for (int i = 0; i < 4; i++) {
            int j = (int) Mth.cos(f);
            int k = (int) Mth.sin(f);
            Direction direction = Direction.fromDelta(j, 0, k);
            if (direction != null) {
                if (direction == Direction.NORTH) {
                    BlockPos pos = basePos.offset(j, 0, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.SOUTH_WALL, WallSide.TALL).setValue(WallBlock.NORTH_WALL, WallSide.LOW));
                    pos = basePos.offset(j * 2, 0, k * 2);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.SOUTH_WALL, WallSide.LOW));
                    pos = basePos.offset(j, 1, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.SOUTH_WALL, WallSide.TALL));
                } else if (direction == Direction.SOUTH) {
                    BlockPos pos = basePos.offset(j, 0, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.NORTH_WALL, WallSide.TALL).setValue(WallBlock.SOUTH_WALL, WallSide.LOW));
                    pos = basePos.offset(j * 2, 0, k * 2);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.NORTH_WALL, WallSide.LOW));
                    pos = basePos.offset(j, 1, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.NORTH_WALL, WallSide.TALL));
                } else if (direction == Direction.EAST) {
                    BlockPos pos = basePos.offset(j, 0, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.WEST_WALL, WallSide.TALL).setValue(WallBlock.EAST_WALL, WallSide.LOW));
                    pos = basePos.offset(j * 2, 0, k * 2);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.WEST_WALL, WallSide.LOW));
                    pos = basePos.offset(j, 1, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.WEST_WALL, WallSide.TALL));
                } else if (direction == Direction.WEST) {
                    BlockPos pos = basePos.offset(j, 0, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.EAST_WALL, WallSide.TALL).setValue(WallBlock.WEST_WALL, WallSide.LOW));
                    pos = basePos.offset(j * 2, 0, k * 2);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.EAST_WALL, WallSide.LOW));
                    pos = basePos.offset(j, 1, k);
                    this.placeBlockAt(context, pos, this.provider.getState(context.random(), pos).setValue(WallBlock.EAST_WALL, WallSide.TALL));
                }
            }
            f += 0.25F * ((float) Math.PI * 2.0F);
        }
    }

    private void placeBlockAt(TreeDecorator.Context context, BlockPos pos, BlockState state) {
        if (TreeFeature.validTreePos(context.level(), pos) && !TreeFeature.validTreePos(context.level(), pos.below())) {
            context.setBlock(pos, state);
        }
    }
}