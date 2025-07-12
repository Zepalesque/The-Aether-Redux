package net.zepalesque.redux.blockentity;

import com.aetherteam.aether_genesis.block.GenesisBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootChestMimicBlockEntity extends BlockEntity {
	public SkyrootChestMimicBlockEntity() {
		super(GenesisBlockEntityTypes.SKYROOT_CHEST_MIMIC.get(), BlockPos.ZERO, GenesisBlocks.SKYROOT_CHEST_MIMIC.get().defaultBlockState());
	}

	public SkyrootChestMimicBlockEntity(BlockPos pos, BlockState state) {
		super(GenesisBlockEntityTypes.SKYROOT_CHEST_MIMIC.get(), pos, state);
	}
}