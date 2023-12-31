package net.zepalesque.redux.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class ReduxSignBlock extends StandingSignBlock {
    public ReduxSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
}