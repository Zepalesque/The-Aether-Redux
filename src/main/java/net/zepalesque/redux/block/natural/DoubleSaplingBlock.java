package net.zepalesque.redux.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoubleSaplingBlock extends DoublePlantBlock implements BonemealableBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final float AABB_OFFSET = 6.0F;
    private static final VoxelShape SHAPE_SAPLING = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 32.0D, 14.0D);
    private static final VoxelShape SHAPE_TOP = Block.box(2.0D, -16.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private final AbstractTreeGrower treeGrower;

    public DoubleSaplingBlock(AbstractTreeGrower pTreeGrower, BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.treeGrower = pTreeGrower;
//        this.registerDefaultState(this.stateDefinition.any());
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(STAGE, Integer.valueOf(0)));
    }


    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return (double)pLevel.random.nextFloat() < 0.45D;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPE_SAPLING : SHAPE_TOP;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;
        if (pState.getValue(HALF) == DoubleBlockHalf.LOWER && pLevel.getMaxLocalRawBrightness(pPos.above(2)) >= 9 && pRandom.nextInt(7) == 0) {
            this.advanceTree(pLevel, pPos, pState, pRandom);
        }

    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (pFacing == Direction.UP) || pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : pFacingState.hasProperty(STAGE) ? pState.setValue(STAGE, pFacingState.getValue(STAGE)) : pState;
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public void advanceTree(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        if (pState.is(this)) {
            if (pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                if (pState.getValue(STAGE) == 0) {
                    BlockState modif = pState.cycle(STAGE);
                    pLevel.setBlock(pPos, modif, 4);
                } else {
                    pLevel.setBlockAndUpdate(pPos.above(), Blocks.AIR.defaultBlockState());
                    this.treeGrower.growTree(pLevel, pLevel.getChunkSource().getGenerator(), pState.getValue(HALF) == DoubleBlockHalf.LOWER ? pPos : pPos.below(), pState, pRandom);
                }
            } else {
                advanceTree(pLevel, pPos.below(), pLevel.getBlockState(pPos.below()), pRandom);
            }
        }
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        this.advanceTree(pLevel, pPos, pState, pRandom);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(STAGE);
    }
}
