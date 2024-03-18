package net.zepalesque.redux.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.block.ReduxBlocks;

public class SkyrootChestBlockEntity extends ChestBlockEntity {
    protected SkyrootChestBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public SkyrootChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(ReduxBlockEntityTypes.SKYROOT_CHEST.get(), pPos, pBlockState);
    }

    public SkyrootChestBlockEntity() {
        this(ReduxBlockEntityTypes.SKYROOT_CHEST.get(), BlockPos.ZERO, ReduxBlocks.SKYROOT_CHEST.get().defaultBlockState());
    }
}
