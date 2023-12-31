package net.zepalesque.redux.block.natural.enchanted;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Function;
import java.util.function.Supplier;

public class EnchantedVinesPlantBlock extends GrowingPlantBodyBlock {

    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    private final Function<BlockState, Boolean> leafBlock;
    private final Supplier<? extends GrowingPlantHeadBlock> headBlock;

    public EnchantedVinesPlantBlock(Properties properties, Function<BlockState, Boolean> leaf, Supplier<? extends GrowingPlantHeadBlock> head) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.leafBlock = leaf;
        this.headBlock = head;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return this.headBlock.get();
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock()) || this.leafBlock.apply(blockstate);
        }
    }

}
