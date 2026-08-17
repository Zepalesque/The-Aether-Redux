package net.zepalesque.redux.block.natural;


import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.zepalesque.redux.util.world.PureRand;

public class CloudcapNettingBody extends HangingAetherVinesBody {
	public CloudcapNettingBody(Properties properties, Holder<Block> head) {
		super(properties, Optional.empty(), head);
		this.registerDefaultState(this.defaultBlockState().setValue(
			BlockStateProperties.HALF, Half.BOTTOM
		));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.transform(super.getStateForPlacement(context), context.getClickedPos());
	}
	
	@Override
	protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		return this.transform(super.updateShape(state, facing, facingState, level, currentPos, facingPos), currentPos);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.HALF);
	}
	
	public BlockState transform(BlockState original, BlockPos pos) {
		var seed = this.getSeed(original, pos.atY(0));
		var i = PureRand.getInt(seed);
		if ((pos.getY() & 1) == 0) i ^= 1;
		return original.setValue(BlockStateProperties.HALF, Half.values()[i & 1]);
	}
}
