package net.zepalesque.redux.block.construction;

import com.aetherteam.aether.block.construction.BookshelfBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class LayeredBookshelfBlock extends BookshelfBlock {

    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public LayeredBookshelfBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(UP, true).setValue(DOWN, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP);
        builder.add(DOWN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    private BlockState getState(BlockState b, LevelAccessor level, BlockPos pos) {
        if (level.getBlockState(pos.above()).is(this)) {
            b = b.setValue(UP, false);
        }
        if (level.getBlockState(pos.below()).is(this)) {
            b = b.setValue(DOWN, false);
        }
        return b;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return getState(super.updateShape(state, direction, neighborState, level, pos, neighborPos), level, pos);
    }
}
