package net.zepalesque.redux.block.container;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.blockentity.TrappedSkyrootChestBlockEntity;

import java.util.function.Supplier;

public class TrappedSkyrootChestBlock extends SkyrootChestBlock {

    public TrappedSkyrootChestBlock(Properties pProperties, Supplier<BlockEntityType<? extends ChestBlockEntity>> pBlockEntityType) {
        super(pProperties, pBlockEntityType);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TrappedSkyrootChestBlockEntity(pos, state);
    }

    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#isSignalSource}
     * whenever possible. Implementing/overriding is fine.
     */
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    /**
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getSignal}
     * whenever possible. Implementing/overriding is fine.
     */
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(blockAccess, pos), 0, 15);
    }

    /**
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getDirectSignal}
     * whenever possible. Implementing/overriding is fine.
     */
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return side == Direction.UP ? blockState.getSignal(blockAccess, pos, side) : 0;
    }
}
