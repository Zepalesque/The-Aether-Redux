package net.zepalesque.redux.block.natural.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class StormfirLeavesBlock extends SnowableLeavesBlock {
	private final int lightBlock;

	public StormfirLeavesBlock(int lightBlock, Properties properties) {
		super(properties);
		this.lightBlock = lightBlock;
	}

	@Override
	protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return this.lightBlock;
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return this.lightBlock <= level.getMaxLightLevel()
			&& super.propagatesSkylightDown(state, level, pos);
	}
}
