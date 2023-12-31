package net.zepalesque.redux.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class ReduxWallHangingSignBlock extends WallHangingSignBlock {
    public ReduxWallHangingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
}
