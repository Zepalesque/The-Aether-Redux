package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.unity.block.UnityBlocks;

public class AveliumRootsBlock extends AetherBushBlock {
	//protected static final VoxelShape SHAPE_SPROUTS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
	protected static final VoxelShape SHAPE_ROOTS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
	//private final boolean isSprouts;

	public AveliumRootsBlock(Properties properties) {
		super(properties);
		//this.isSprouts = sprouts;
	}

	public VoxelShape getShape(
		BlockState pState,
		BlockGetter pLevel,
		BlockPos pPos,
		CollisionContext pContext
	) {
		var offset = pState.getOffset(pLevel, pPos);
		return SHAPE_ROOTS.move(offset.x, offset.y, offset.z);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		var below = level.getBlockState(pos.below());

		// TODO: make it not hacky
		return below.is(UnityBlocks.COARSE_AETHER_DIRT) || below.is(ReduxBlocks.AVELIUM);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
		return super.canBeReplaced(state, ctx);
		// return (
		// 	super.canBeReplaced(pState, pUseContext) &&
		// 	pUseContext.getItemInHand().getItem() instanceof BlockItem blockItem &&
		// 		!blockItem
		// 			.getBlock()
		// 			.builtInRegistryHolder()
		// 			.is(ReduxTags.Blocks.DO_NOT_REPLACE_AETHER_GRASS)
		// );
	}
}
