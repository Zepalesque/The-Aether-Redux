package net.zepalesque.redux.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.unity.block.natural.bush.CustomBoundsBushBlock;
import net.zepalesque.unity.block.natural.bush.CustomBoundsFlowerBlock;

public class VerbenaClusterBlock extends CustomBoundsBushBlock {
	private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 9.0, 15.0);

	public VerbenaClusterBlock(BlockBehaviour.Properties properties) {
		super(SHAPE, properties);
	}
	
	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(ReduxTags.Blocks.QUICKSOIL_PLANTS_SURVIVABLE);
	}
}
