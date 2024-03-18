package net.zepalesque.redux.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.block.ReduxBlocks;

public class TrappedSkyrootChestBlockEntity extends ChestBlockEntity {
    protected TrappedSkyrootChestBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public TrappedSkyrootChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(ReduxBlockEntityTypes.TRAPPED_SKYROOT_CHEST.get(), pPos, pBlockState);
    }

    public TrappedSkyrootChestBlockEntity() {
        this(ReduxBlockEntityTypes.TRAPPED_SKYROOT_CHEST.get(), BlockPos.ZERO, ReduxBlocks.TRAPPED_SKYROOT_CHEST.get().defaultBlockState());
    }

    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int eventId, int eventParam) {
        super.signalOpenCount(level, pos, state, eventId, eventParam);
        if (eventId != eventParam) {
            Block block = state.getBlock();
            level.updateNeighborsAt(pos, block);
            level.updateNeighborsAt(pos.below(), block);
        }

    }
}
