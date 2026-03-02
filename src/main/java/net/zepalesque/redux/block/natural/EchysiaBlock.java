package net.zepalesque.redux.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.unity.block.natural.bush.CustomBoundsBushBlock;

public class EchysiaBlock extends CustomBoundsBushBlock {
	protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 12.0, 15.0);
	
	public EchysiaBlock(Properties properties) {
		super(SHAPE, properties);
	}
	
	@Override
	protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 1;
	}
}
