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
		return this.getState(context.getLevel(), context.getClickedPos());
	}

	private BlockState getState(LevelAccessor level, BlockPos pos) {
		var state = this.defaultBlockState();
		if (level.getBlockState(pos.above()).is(this)) state = state.setValue(UP, false);
		if (level.getBlockState(pos.below()).is(this)) state = state.setValue(DOWN, false);
		return state;
	}

	@Override
	public BlockState updateShape(
		BlockState state,
		Direction direction,
		BlockState neighborState,
		LevelAccessor level,
		BlockPos pos,
		BlockPos neighborPos
	) {
		return this.getState(level, pos);
	}
}
