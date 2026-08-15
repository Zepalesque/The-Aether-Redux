package net.zepalesque.redux.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CloudcapNettingBlock extends Block {
	protected static final VoxelShape SHAPE_HEAD = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape SHAPE_BODY = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public CloudcapNettingBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.defaultBlockState().setValue(BlockStateProperties.BOTTOM, true)
		);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.BOTTOM);
	}

	@Override
	protected boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
		return !ctx.getItemInHand().is(this.asItem());
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		var attachment = level.getBlockState(pos.above());
		return attachment.is(this) || attachment.isSolidRender(level, pos);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(BlockStateProperties.BOTTOM) ? SHAPE_HEAD : SHAPE_BODY;
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		Direction direction,
		BlockState neighborState,
		LevelAccessor level,
		BlockPos pos,
		BlockPos neighborPos
	) {
		if (!this.canSurvive(state, level, pos)) return Blocks.AIR.defaultBlockState();

		if (level.getBlockState(pos.below()).is(this)) {
			state = state.setValue(BlockStateProperties.BOTTOM, false);
		} else {
			state = state.setValue(BlockStateProperties.BOTTOM, true);
		}

		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}
}
