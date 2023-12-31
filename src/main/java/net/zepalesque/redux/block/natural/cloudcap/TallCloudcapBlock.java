package net.zepalesque.redux.block.natural.cloudcap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;

public class TallCloudcapBlock extends DoublePlantBlock implements BonemealableBlock {
    protected static final float AABB_OFFSET = 6.0F;
    private static final VoxelShape SHAPE_SAPLING = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 30.0D, 14.0D);
    private static final VoxelShape SHAPE_TOP = Block.box(2.0D, -16.0D, 2.0D, 14.0D, 14.0D, 14.0D);
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public TallCloudcapBlock(BlockBehaviour.Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> pFeature) {
        super(pProperties);
        this.feature = pFeature;
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
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

    @Nullable
    protected BlockPos getCorrectPosForPlacingMushroom(BlockGetter level, BlockPos pos)
    {
        boolean noOffset = level.getBlockState(pos.offset(1, 0, 0)).is(this) && level.getBlockState(pos.offset(0, 0, 1)).is(this) && level.getBlockState(pos.offset(1, 0, 1)).is(this);
        boolean offsetn100 = level.getBlockState(pos.offset(-1, 0, 0)).is(this) && level.getBlockState(pos.offset(0, 0, 1)).is(this)  && level.getBlockState(pos.offset(-1, 0, 1)).is(this);
        boolean offset00n1 = level.getBlockState(pos.offset(0, 0, -1)).is(this) && level.getBlockState(pos.offset(1, 0, -1)).is(this)  && level.getBlockState(pos.offset(1, 0, -1)).is(this);
        boolean offsetn10n1 = level.getBlockState(pos.offset(-1, 0, -1)).is(this) && level.getBlockState(pos.offset(0, 0, -1)).is(this)  && level.getBlockState(pos.offset(-1, 0, 0)).is(this);
        if (noOffset)
        {
            return pos;
        } else if (offsetn100) {
            return pos.offset(-1, 0, 0);
        } else if (offset00n1) {
            return pos.offset(0, 0, -1);
        } else if (offsetn10n1){
            return pos.offset(-1, 0, -1);
        } else {
            return null;
        }
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (pFacing == Direction.UP) || pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : pState;
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public void doShroomGrowing(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        if (pState.is(this)) {
            if (pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockPos correctPos = this.getCorrectPosForPlacingMushroom(pLevel, pPos);
                if (correctPos != null) {
                    BlockPos.MutableBlockPos mutableBlockPos = correctPos.mutable();
                    for (int x = 0; x <= 1; x++)
                    {
                        for (int y = 0; y <= 1; y++)
                        {
                            for (int z = 0; z <= 1; z++)
                            {
                                mutableBlockPos.setWithOffset(correctPos, x, y, z);
                                boolean isOrigin = x == 0 && y == 0 && z == 0;
                                if (!isOrigin)
                                {
                                    pLevel.setBlockAndUpdate(mutableBlockPos, Blocks.AIR.defaultBlockState());
                                }
                            }
                        }
                    }
                    this.growMushroom(pLevel, pState.getValue(HALF) == DoubleBlockHalf.LOWER ? correctPos : correctPos.below(), pState, pRandom);
                }
            } else {
                doShroomGrowing(pLevel, pPos.below(), pLevel.getBlockState(pPos.below()), pRandom);
            }
        }
    }

    public boolean growMushroom(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
        if (optional.isEmpty()) {
            return false;
        } else {
            net.minecraftforge.event.level.SaplingGrowTreeEvent event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(pLevel, pRandom, pPos, optional.get());
            if (event.getResult().equals(net.minecraftforge.eventbus.api.Event.Result.DENY)) return false;
            pLevel.removeBlock(pPos, false);
            if (event.getFeature().value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, pPos)) {
                return true;
            } else {
                for (int x = 0; x <= 1; x++) {
                    for (int z = 0; z <= 1; z++) {
                        pLevel.setBlock(pPos.offset(x, 0, z), pState, 2);
                        pLevel.setBlock(pPos.offset(x, 1, z), pState.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 2);
                    }
                }
                return false;
            }
        }
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        this.doShroomGrowing(pLevel, pPos, pState, pRandom);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
    }
}
