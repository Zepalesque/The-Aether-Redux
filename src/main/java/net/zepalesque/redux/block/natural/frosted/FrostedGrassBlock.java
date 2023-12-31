package net.zepalesque.redux.block.natural.frosted;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.block.natural.ReduxGrassBlock;
import net.zepalesque.redux.block.util.ReduxStateProperties;
import org.apache.commons.lang3.function.TriFunction;

public class FrostedGrassBlock extends ReduxGrassBlock {

    public FrostedGrassBlock(Properties properties, TriFunction<LevelAccessor, BlockPos, RandomSource, BlockState> bonemealGrassFunction) {
        super(properties, bonemealGrassFunction);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStateProperties.SNOWED, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStateProperties.SNOWED);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        BlockState fState = level.getBlockState(pos.above());
        if (fState.hasProperty(ReduxStateProperties.SNOW_LAYER) && fState.getValue(ReduxStateProperties.SNOW_LAYER)) {
                level.setBlockAndUpdate(pos, state.setValue(ReduxStateProperties.SNOWED, true));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(ReduxStateProperties.SNOWED, false));
        }
    }
}
