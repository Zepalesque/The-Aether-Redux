package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherGrassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.block.state.enums.BlightGrassColor;

public class BlightedGrassBlock extends AetherGrassBlock {
	public BlightedGrassBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.defaultBlockState().setValue(ReduxStates.BLIGHT_GRASS_COLOR, BlightGrassColor.CONSTANT)
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ReduxStates.BLIGHT_GRASS_COLOR);
	}
}
