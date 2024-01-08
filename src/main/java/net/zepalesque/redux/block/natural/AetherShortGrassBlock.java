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
import net.minecraft.world.level.block.Blocks;
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
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.GRASS_TYPE, ShortGrassType.AETHER).setValue(ReduxStates.SNOW_LAYER, false));
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
        builder.add(ReduxStates.SNOW_LAYER);
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
            return modifySnow(this.defaultBlockState().setValue(ReduxStates.GRASS_TYPE, type), context.getLevel(), context.getClickedPos());
        }
        return modifySnow(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos());
    }

    public BlockState modifySnow(BlockState state, LevelAccessor level, BlockPos pos) {
        int snowSides = 0;
        for (Direction d : Direction.Plane.HORIZONTAL) {
            BlockState b = level.getBlockState(pos.relative(d));
            if (b.getBlock() == Blocks.SNOW || (b.hasProperty(ReduxStates.SNOWY_TEXTURE) && b.getValue(ReduxStates.SNOWY_TEXTURE)) || (b.hasProperty(ReduxStates.GRASS_TYPE) && b.getValue(ReduxStates.GRASS_TYPE) == ShortGrassType.FROSTED_SNOWY)) { snowSides++; }

        }
        if (state.getValue(ReduxStates.GRASS_TYPE) == ShortGrassType.FROSTED) {
            if (snowSides >= 2 && level.getBlockState(pos.below()).is(ReduxTags.Blocks.FROSTED_GRASSES)) {
                state = state.setValue(ReduxStates.SNOW_LAYER, true);
            }
            if (snowSides >= 2 && level.getBlockState(pos.below()).is(ReduxTags.Blocks.FROSTED_GRASSES)) {
                state = state.setValue(ReduxStates.GRASS_TYPE, ShortGrassType.FROSTED_SNOWY);
            }
        }
        return state;
    }
    @Override
    @NotNull
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        @Nullable ShortGrassType type = check(state, level.getBlockState(currentPos.below()));
        BlockState b = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        return modifySnow(type != null && b.hasProperty(ReduxStates.GRASS_TYPE) ? b.setValue(ReduxStates.GRASS_TYPE, type) : b, level, currentPos);
    }
}
