package net.zepalesque.redux.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.block.state.enums.AetherMossType;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.unity.block.natural.bush.CustomBoundsBushBlock;
import net.zepalesque.unity.data.UnityTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EchysiaBlock extends CustomBoundsBushBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 11.0, 14.0);
	
	public EchysiaBlock(Properties properties) {
		super(SHAPE, properties);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(ReduxStates.AETHER_MOSS_TYPE, AetherMossType.FLUTEMOSS)
		);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ReduxStates.AETHER_MOSS_TYPE);
	}
	
	@Override
	protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 1;
	}
	
	public BlockState setValues(Level level, BlockPos pos, BlockState state) {
		var below = pos.below();
		if (level.getBlockState(below).is(UnityTags.Blocks.SHORT_AETHER_GRASS_STATE_ENCHANTING))
			return state.setValue(ReduxStates.AETHER_MOSS_TYPE, AetherMossType.GILDENMOSS);
		if (level.getBlockState(below).is(ReduxTags.Blocks.SHORT_AETHER_GRASS_BLEAKMOSS_COLORING) || level.getBlockState(below).is(ReduxTags.Blocks.SHORT_AETHER_GRASS_BLIGHT_COLORING))
			return state.setValue(ReduxStates.AETHER_MOSS_TYPE, AetherMossType.BLEAKMOSS);
		else return state;
	}
	
	@Nullable @Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.setValues(context.getLevel(), context.getClickedPos(), super.getStateForPlacement(context));
	}
	
	@Override
	@NotNull public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		var b = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
		if (b.hasProperty(ReduxStates.AETHER_MOSS_TYPE) && facing == Direction.DOWN) {
			if (level.getBlockState(facingPos).is(UnityTags.Blocks.SHORT_AETHER_GRASS_STATE_ENCHANTING))
				return b.setValue(ReduxStates.AETHER_MOSS_TYPE, AetherMossType.GILDENMOSS);
			if (level.getBlockState(facingPos).is(ReduxTags.Blocks.SHORT_AETHER_GRASS_BLEAKMOSS_COLORING) || level.getBlockState(facingPos).is(ReduxTags.Blocks.SHORT_AETHER_GRASS_BLIGHT_COLORING))
				return b.setValue(ReduxStates.AETHER_MOSS_TYPE, AetherMossType.BLEAKMOSS);
			return b.setValue(ReduxStates.AETHER_MOSS_TYPE, AetherMossType.FLUTEMOSS);
		}
		return b;
	}
}
