package net.zepalesque.redux.block.natural;

import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.unity.block.natural.bush.CustomBoundsBushBlock;

public class BloomtailBlock extends CustomBoundsBushBlock implements LiquidBlockContainer {
	public static final MapCodec<BloomtailBlock> CODEC = simpleCodec(BloomtailBlock::new);
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
	
	public BloomtailBlock(BlockBehaviour.Properties properties) {
		super(SHAPE, properties);
	}
	
	@Override
	protected MapCodec<? extends BloomtailBlock> codec() {
		return CODEC;
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter lvl, BlockPos pos) {
		return state.isFaceSturdy(lvl, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK);
	}
	
	// require wabter
	@Nullable @Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		var fluid = ctx.getLevel().getFluidState(ctx.getClickedPos());
		return fluid.is(FluidTags.WATER) && fluid.getAmount() == 8 ? super.getStateForPlacement(ctx) : null;
	}
	
	@Override
	protected BlockState updateShape(
		BlockState state,
		Direction dir,
		BlockState relState,
		LevelAccessor lvl,
		BlockPos pos,
		BlockPos relPos
	) {
		var blockstate = super.updateShape(state, dir, relState, lvl, pos, relPos);
		if (!blockstate.isAir()) {
			lvl.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(lvl));
		}
		
		return blockstate;
	}
	
	@Override
	protected FluidState getFluidState(BlockState state) {
		return Fluids.WATER.getSource(false);
	}
	
	@Override
	public boolean canPlaceLiquid(@Nullable Player plr, BlockGetter lvl, BlockPos pos, BlockState state, Fluid fluid) {
		return false;
	}
	
	@Override
	public boolean placeLiquid(LevelAccessor lvl, BlockPos pos, BlockState state, FluidState fluid) {
		return false;
	}
}
