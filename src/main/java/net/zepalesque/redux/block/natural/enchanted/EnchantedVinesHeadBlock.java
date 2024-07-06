package net.zepalesque.redux.block.natural.enchanted;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class EnchantedVinesHeadBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    private final TagKey<Block> leafTag;
    private final Supplier<? extends GrowingPlantBodyBlock> bodyBlock;

    public EnchantedVinesHeadBlock(Properties properties, Supplier<? extends GrowingPlantBodyBlock> bodyBlock, TagKey<Block> leafTag) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
        this.leafTag = leafTag;
        this.bodyBlock = bodyBlock;
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


    public int getLength(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
        int i = 0;
        while (!level.isOutsideBuildHeight(pos.getY() + i) && level.isStateAtPosition(pos.above(i), state -> state.is(this.getHeadBlock()) || state.is(this.getBodyBlock()))) {
            i++;
        }
        return i;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return !this.canAttachTo(blockstate) ? super.canSurvive(pState, pLevel, pPos) : super.canSurvive(pState, pLevel, pPos) || blockstate.is(this.leafTag);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.getLength(pLevel, pPos) < 10) {
            super.randomTick(pState, pLevel, pPos, pRandom);
        }
    }
}
