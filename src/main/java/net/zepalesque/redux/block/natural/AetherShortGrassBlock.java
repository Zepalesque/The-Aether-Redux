package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.natural.cloudcap.AveliumPlantBlock;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.block.util.state.enums.GrassSize;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AetherShortGrassBlock extends AetherBushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    protected static final VoxelShape SHAPE_TALL = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    protected static final VoxelShape SHAPE_SHORT = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);
    protected static VoxelShape COLLISION_SHAPE = Shapes.empty();

    public AetherShortGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.ENCHANTED, false).setValue(ReduxStates.GRASS_SIZE, GrassSize.med));
    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        GrassSize size = pState.getValue(ReduxStates.GRASS_SIZE);
        return size == GrassSize.shrt ? SHAPE_SHORT : size == GrassSize.tall ? SHAPE_TALL : SHAPE;
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return super.canBeReplaced(pState, pUseContext) && (pUseContext.getItemInHand().getItem() instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof AveliumPlantBlock || blockItem.getBlock() instanceof AetherShortGrassBlock));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.ENCHANTED);
        builder.add(ReduxStates.GRASS_SIZE);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ReduxTags.Blocks.FROSTED_PLANTS_PLACEMENT) || super.mayPlaceOn(state, level, pos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        long r = Mth.getSeed(context.getClickedPos());
        RandomSource rand = new XoroshiroRandomSource(r);
        int i = rand.nextInt(3);
        GrassSize size = GrassSize.values()[i];
        return setValues(context.getLevel(), context.getClickedPos(), super.getStateForPlacement(context));
    }


    public BlockState setValues(Level level, BlockPos pos, BlockState state) {
        if (state != null) {
            long r = Mth.getSeed(pos);
            RandomSource rand = new XoroshiroRandomSource(r);
            int i = rand.nextInt(3);
            GrassSize size = GrassSize.values()[i];
            BlockState b = state.setValue(ReduxStates.GRASS_SIZE, size);
            BlockPos below = pos.below();
            if (level.getBlockState(below).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
                return b.setValue(ReduxStates.ENCHANTED, true);
            }
            return b;
        }
        return state;
    }


    @Override
    @NotNull
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState b = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (b.hasProperty(ReduxStates.GRASS_SIZE)) {
            long r = Mth.getSeed(currentPos);
            RandomSource rand = new XoroshiroRandomSource(r);
            int i = rand.nextInt(3);
            GrassSize size = GrassSize.values()[i];
            b = b.setValue(ReduxStates.GRASS_SIZE, size);
        }
        if (b.hasProperty(ReduxStates.ENCHANTED) && facing == Direction.DOWN) {
            if (level.getBlockState(facingPos).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
                return b.setValue(ReduxStates.ENCHANTED, true);
            }
            return b.setValue(ReduxStates.ENCHANTED, false);
        }
        return b;
    }
}
