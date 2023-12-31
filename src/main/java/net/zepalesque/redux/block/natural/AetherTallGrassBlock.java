package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.natural.cloudcap.AeveliumSproutsGrowthBlock;

import java.util.Map;

public class AetherTallGrassBlock extends AetherBushBlock {

    public static final Map<TagKey<Block>, Block> BLOCK_MAP = Maps.newHashMap();


    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    protected static VoxelShape COLLISION_SHAPE = Shapes.empty();
    public AetherTallGrassBlock(BlockBehaviour.Properties properties, TagKey<Block> grassBlockTag) {
        super(properties);
        BLOCK_MAP.put(grassBlockTag, this);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {

        this.checkPlacement(pState, pLevel, pPos);
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);

    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        this.checkPlacement(pState, pLevel, pPos);
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

    public void checkPlacement(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        BlockState below = pLevel.getBlockState(pPos.below());
        Block b = null;
        for (Map.Entry<TagKey<Block>, Block> entry : BLOCK_MAP.entrySet())
        {
            if (below.is(entry.getKey()))
            {
                b = entry.getValue();
            }

        }
        if (b != null && b != this)
        {
            pLevel.setBlock(pPos, b.defaultBlockState(), 3);
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        this.checkPlacement(pState, pLevel, pCurrentPos);
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return super.canBeReplaced(pState, pUseContext) && (pUseContext.getItemInHand().getItem() instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof AetherTallGrassBlock || blockItem.getBlock() instanceof AeveliumSproutsGrowthBlock));
    }



    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }
    
}
