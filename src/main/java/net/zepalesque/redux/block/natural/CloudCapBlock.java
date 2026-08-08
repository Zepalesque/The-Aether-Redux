package net.zepalesque.redux.block.natural;

import static net.zepalesque.redux.block.state.ReduxStates.CLOUDCAP_VARIANT;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.util.world.PureRand;

public class CloudCapBlock extends HugeAetherMushroomBlock {
	public CloudCapBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CLOUDCAP_VARIANT, 0));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(CLOUDCAP_VARIANT);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.transform(super.getStateForPlacement(context), context.getClickedPos());
	}
	
	@Override
	protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		return this.transform(super.updateShape(state, facing, facingState, level, currentPos, facingPos), currentPos);
	}
	
	public BlockState transform(BlockState original, BlockPos pos) {
		var seed = this.getSeed(original, pos);
		var f = PureRand.getInt(seed, 30);
		var val = f < 10
			? 0 : f < 17
			? 1 : f < 22
			? 2 : f < 27
			? 3 : 4;
		return original.setValue(CLOUDCAP_VARIANT, val);
	}
}
