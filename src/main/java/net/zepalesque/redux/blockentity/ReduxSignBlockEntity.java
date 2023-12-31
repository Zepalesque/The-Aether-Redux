package net.zepalesque.redux.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReduxSignBlockEntity extends SignBlockEntity {
    private final BlockEntityType<ReduxSignBlockEntity> signBlockEntity;
    public ReduxSignBlockEntity(BlockPos pos, BlockState state, BlockEntityType<ReduxSignBlockEntity> pSignBlockEntity) {
        super(pos, state);
        this.signBlockEntity = pSignBlockEntity;
    }

    public BlockEntityType<ReduxSignBlockEntity> getType() {
        return this.signBlockEntity;
    }
}
