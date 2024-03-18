package net.zepalesque.redux.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.block.ReduxBlocks;

public class SkyrootChestMimicBlockEntity extends BlockEntity {
	public SkyrootChestMimicBlockEntity() {
		super(ReduxBlockEntityTypes.SKYROOT_CHEST_MIMIC.get(), BlockPos.ZERO, ReduxBlocks.SKYROOT_CHEST_MIMIC.get().defaultBlockState());
	}

	public SkyrootChestMimicBlockEntity(BlockPos pos, BlockState state) {
		super(ReduxBlockEntityTypes.SKYROOT_CHEST_MIMIC.get(), pos, state);
	}
}