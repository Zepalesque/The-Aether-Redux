package net.zepalesque.redux.block.natural.candy;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.zepalesque.redux.fluid.ReduxFluidInteractions;

import java.util.function.Supplier;

public class SyrupLiquidBlock extends LiquidBlock {

    public SyrupLiquidBlock(Supplier<? extends FlowingFluid> supplier, BlockBehaviour.Properties properties) {
        super(supplier, properties);
    }


    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter level, BlockPos pos, FluidState fluidState) {
        return true;
    }

    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState state, boolean b) {
        if (!ReduxFluidInteractions.canInteract(level, blockPos)) {
            level.scheduleTick(blockPos, blockState.getFluidState().getType(), this.getFluid().getTickDelay(level));
        }

    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos1, boolean b) {
        if (!ReduxFluidInteractions.canInteract(level, blockPos)) {
            level.scheduleTick(blockPos, blockState.getFluidState().getType(), this.getFluid().getTickDelay(level));
        }

    }
}