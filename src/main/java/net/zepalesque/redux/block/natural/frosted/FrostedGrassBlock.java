package net.zepalesque.redux.block.natural.frosted;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.block.natural.ReduxGrassBlock;
import net.zepalesque.redux.block.util.ReduxStates;

public class FrostedGrassBlock extends ReduxGrassBlock {

    public FrostedGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.SNOWED, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.SNOWED);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        BlockState fState = level.getBlockState(pos.above());
        if (fState.hasProperty(ReduxStates.SNOWLOGGED) && fState.getValue(ReduxStates.SNOWLOGGED)) {
                level.setBlockAndUpdate(pos, state.setValue(ReduxStates.SNOWED, true));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(ReduxStates.SNOWED, false));
        }
    }
}
