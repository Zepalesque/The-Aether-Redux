package net.zepalesque.redux.blockentity;

import com.aetherteam.aether_genesis.block.GenesisBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootChestBlockEntity extends ChestBlockEntity {
    public SkyrootChestBlockEntity() {
        this(GenesisBlockEntityTypes.SKYROOT_CHEST.get(), BlockPos.ZERO, GenesisBlocks.SKYROOT_CHEST.get().defaultBlockState());
    }

    public SkyrootChestBlockEntity(BlockPos pos, BlockState state) {
        this(GenesisBlockEntityTypes.SKYROOT_CHEST.get(), pos, state);
    }

    protected SkyrootChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
