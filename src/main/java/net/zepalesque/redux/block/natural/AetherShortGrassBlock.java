package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.util.ReduxStates;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AetherShortGrassBlock extends AetherBushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    protected static VoxelShape COLLISION_SHAPE = Shapes.empty();

    public AetherShortGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.ENCHANTED, false));
    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.ENCHANTED);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ReduxTags.Blocks.FROSTED_PLANTS_PLACEMENT) || super.mayPlaceOn(state, level, pos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return setValues(context.getLevel(), context.getClickedPos(), super.getStateForPlacement(context));
    }


    public BlockState setValues(Level level, BlockPos pos, BlockState state) {
        BlockPos below = pos.below();
        if (level.getBlockState(below).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
            return state.setValue(ReduxStates.ENCHANTED, true);
        }

        return state;
    }


    @Override
    @NotNull
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState b = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (b.hasProperty(ReduxStates.ENCHANTED)) {
            if (facing == Direction.DOWN && level.getBlockState(facingPos).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
                return b.setValue(ReduxStates.ENCHANTED, true);
            }
            return b.setValue(ReduxStates.ENCHANTED, false);
        }
        return b;
    }
}
