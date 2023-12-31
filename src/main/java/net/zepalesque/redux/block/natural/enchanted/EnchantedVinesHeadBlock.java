package net.zepalesque.redux.block.natural.enchanted;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Function;
import java.util.function.Supplier;

public class EnchantedVinesHeadBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private final Function<BlockState, Boolean> leafBlock;

    private final Supplier<? extends GrowingPlantBodyBlock> bodyBlock;

    public EnchantedVinesHeadBlock(Properties properties, Function<BlockState, Boolean> leaf, Supplier<? extends GrowingPlantBodyBlock> body) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
        this.leafBlock = leaf;
        this.bodyBlock = body;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource p_222680_) {
        return NetherVines.getBlocksToGrowWhenBonemealed(p_222680_);
    }

    @Override
    protected Block getBodyBlock() {
        return this.bodyBlock.get();
    }

    @Override
    protected boolean canGrowInto(BlockState p_154971_) {
        return NetherVines.isValidGrowthState(p_154971_);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock()) || leafBlock.apply(blockstate);
        }
    }

}
