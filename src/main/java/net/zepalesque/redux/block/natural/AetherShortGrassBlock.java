package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.util.ReduxStates;
import net.zepalesque.redux.block.util.ShortGrassType;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

public class AetherShortGrassBlock extends AetherBushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    protected static VoxelShape COLLISION_SHAPE = Shapes.empty();

    public AetherShortGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.GRASS_TYPE, ShortGrassType.AETHER));
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
        builder.add(ReduxStates.GRASS_TYPE);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ReduxTags.Blocks.FROSTED_PLANTS_PLACEMENT) || super.mayPlaceOn(state, level, pos);
    }
    public @Nullable ShortGrassType check(BlockState pState, BlockState below)
    {
        ShortGrassType b = null;
        for (Map.Entry<TagKey<Block>, ShortGrassType> entry : ShortGrassType.TAGS.entrySet())
        {
            if (below.is(entry.getKey()))
            {
                b = entry.getValue();
            }

        }
        return b != pState.getValue(ReduxStates.GRASS_TYPE) ? b : null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        @Nullable ShortGrassType type = check(this.defaultBlockState(), context.getLevel().getBlockState(context.getClickedPos().below()));
        if (type != null) {
            return this.defaultBlockState().setValue(ReduxStates.GRASS_TYPE, type);
        }
        return super.getStateForPlacement(context);
    }

    @Override
    @NotNull
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        @Nullable ShortGrassType type = check(state, level.getBlockState(currentPos.below()));
        BlockState b = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        return type != null && b.hasProperty(ReduxStates.GRASS_TYPE) ? b.setValue(ReduxStates.GRASS_TYPE, type) : b;
    }
}
