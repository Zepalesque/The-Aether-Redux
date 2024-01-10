package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.AetherTags;
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
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.ShortGrassTint;
import net.zepalesque.redux.block.util.ReduxStates;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TintableAetherShortGrassBlock extends AetherBushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
    protected static VoxelShape COLLISION_SHAPE = Shapes.empty();

    public TintableAetherShortGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.TINTABLE));
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
        builder.add(ReduxStates.SHORT_GRASS_TINT);
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
        if (level.getBlockState(below).is(ReduxTags.Blocks.ENCHANTED_GRASSES)) {
            return state.setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.ENCHANTED);
        }
        if (level.getBlockState(below).is(ReduxBlocks.REDUX_GRASS_BLOCK.get())) {
            return state.setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.BLOCK_DEPENDENT);
        }
        if (level.getBlockState(below).is(ReduxTags.Blocks.HIGHLANDS_GRASSES) || !level.getBiome(pos).is(AetherTags.Biomes.IS_AETHER)) {
            return state.setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.AETHER_COLOR);
        }
        return state;
    }


    @Override
    @NotNull
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState spr = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (state.hasProperty(ReduxStates.SHORT_GRASS_TINT) && spr.hasProperty(ReduxStates.SHORT_GRASS_TINT)) {
            if (state.getValue(ReduxStates.SHORT_GRASS_TINT) != ShortGrassTint.ENCHANTED && level.getBlockState(currentPos.below()).is(ReduxTags.Blocks.ENCHANTED_GRASSES)) {
                return spr.setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.ENCHANTED);
            }
            if (state.getValue(ReduxStates.SHORT_GRASS_TINT) != ShortGrassTint.BLOCK_DEPENDENT && level.getBlockState(currentPos.below()).is(ReduxBlocks.REDUX_GRASS_BLOCK.get())) {
                return spr.setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.BLOCK_DEPENDENT);
            }
            if (state.getValue(ReduxStates.SHORT_GRASS_TINT) != ShortGrassTint.AETHER_COLOR && (level.getBlockState(currentPos.below()).is(ReduxTags.Blocks.HIGHLANDS_GRASSES) || !level.getBiome(currentPos).is(AetherTags.Biomes.IS_AETHER))) {
                return spr.setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.AETHER_COLOR);
            }
            return spr.setValue(ReduxStates.SHORT_GRASS_TINT, ShortGrassTint.TINTABLE);
        }
        return spr;
    }
}
