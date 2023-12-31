package net.zepalesque.redux.block.natural.frosted;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.block.util.ReduxStateProperties;

public class FrostedSaplingBlock extends SaplingBlock {
    public FrostedSaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties) {
        super(pTreeGrower, pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStateProperties.SNOWY_TEXTURE, false).setValue(ReduxStateProperties.SNOW_LAYER, false));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStateProperties.SNOWY_TEXTURE);
        builder.add(ReduxStateProperties.SNOW_LAYER);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = super.getStateForPlacement(pContext);
        return checkSnow(state, pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        BlockState newState = this.checkSnow(pState, pLevel, pPos);
        if (newState != pState)
        {
            pLevel.setBlock(pPos, newState, 3);
        }

    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        BlockState state = this.checkSnow(this.defaultBlockState(), pLevel, pPos);
        if (state != pState)
        {
            pLevel.setBlockAndUpdate(pPos, state);
        }
    }

    public BlockState checkSnow(BlockState state, LevelAccessor level, BlockPos pos) {
        int snowSides = 0;
        if (level.getBlockState(pos.east()).getBlock() == Blocks.SNOW) { snowSides++; }
        if (level.getBlockState(pos.west()).getBlock() == Blocks.SNOW) { snowSides++; }
        if (level.getBlockState(pos.north()).getBlock() == Blocks.SNOW) { snowSides++; }
        if (level.getBlockState(pos.south()).getBlock() == Blocks.SNOW) { snowSides++; }
        if (snowSides >= 2 && level.getBlockState(pos.below()).is(ReduxTags.Blocks.FROSTED_GRASSES)) { state = state.setValue(ReduxStateProperties.SNOW_LAYER, true); }
        if (level.getBlockState(pos.below()).is(ReduxTags.Blocks.FROSTED_PLANTS_PLACEMENT) || (snowSides >= 2 && level.getBlockState(pos.below()).is(ReduxTags.Blocks.FROSTED_GRASSES)))
        { state = state.setValue(ReduxStateProperties.SNOWY_TEXTURE, true); }
        return state;
    }
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        BlockState newState = this.checkSnow(pState, pLevel, pCurrentPos);
        if (newState != pState)
        {
            pLevel.setBlock(pCurrentPos, newState, 3);
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(ReduxTags.Blocks.FROSTED_PLANTS_PLACEMENT);

    }
}
