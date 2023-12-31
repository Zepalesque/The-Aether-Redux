package net.zepalesque.redux.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReduxHangingSignBlockEntity extends HangingSignBlockEntity {
    private final BlockEntityType<ReduxHangingSignBlockEntity> beType;
    public ReduxHangingSignBlockEntity(BlockPos pos, BlockState state, BlockEntityType<ReduxHangingSignBlockEntity> beType) {
        super(pos, state);
        this.beType = beType;
    }

    public BlockEntityType<ReduxHangingSignBlockEntity> getType() {
        return this.beType;
    }
}
