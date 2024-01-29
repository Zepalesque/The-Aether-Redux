package net.zepalesque.redux.block.construction;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.util.ReduxStates;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class FlowerGarlandBlock extends Block {

    private static final VoxelShape TOP_WEST = Block.box(0.0D, 8.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_EAST = Block.box(15.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape TOP_NORTH = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape TOP_SOUTH = Block.box(0.0D, 8.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape BOTTOM_WEST = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 8.0D, 16.0D);
    private static final VoxelShape BOTTOM_EAST = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    private static final VoxelShape BOTTOM_NORTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 1.0D);
    private static final VoxelShape BOTTOM_SOUTH = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 8.0D, 16.0D);
    private static final Map<Direction, VoxelShape> TOP_SHAPES = Util.make(Maps.newEnumMap(Direction.class), (p_153923_) -> {
        p_153923_.put(Direction.NORTH, TOP_NORTH);
        p_153923_.put(Direction.EAST, TOP_EAST);
        p_153923_.put(Direction.SOUTH, TOP_SOUTH);
        p_153923_.put(Direction.WEST, TOP_WEST);
        p_153923_.put(Direction.UP, Shapes.empty());
        p_153923_.put(Direction.DOWN, Shapes.empty());
    });
    private static final Map<Direction, VoxelShape> BOTTOM_SHAPES = Util.make(Maps.newEnumMap(Direction.class), (p_153923_) -> {
        p_153923_.put(Direction.NORTH, BOTTOM_NORTH);
        p_153923_.put(Direction.EAST, BOTTOM_EAST);
        p_153923_.put(Direction.SOUTH, BOTTOM_SOUTH);
        p_153923_.put(Direction.WEST, BOTTOM_WEST);
        p_153923_.put(Direction.UP, Shapes.empty());
        p_153923_.put(Direction.DOWN, Shapes.empty());
    });

    private static final BooleanProperty[] PROPERTIES = new BooleanProperty[] {
            ReduxStates.TOP_NORTH, ReduxStates.BOTTOM_NORTH,
            ReduxStates.TOP_SOUTH, ReduxStates.BOTTOM_SOUTH,
            ReduxStates.TOP_EAST, ReduxStates.BOTTOM_EAST,
            ReduxStates.TOP_WEST, ReduxStates.BOTTOM_WEST
    };
    private static final BooleanProperty[] TOPS = new BooleanProperty[] {
            ReduxStates.TOP_NORTH,
            ReduxStates.TOP_SOUTH,
            ReduxStates.TOP_EAST,
            ReduxStates.TOP_WEST
    };
    private static final BooleanProperty[] BOTTOMS = new BooleanProperty[] {
            ReduxStates.BOTTOM_NORTH,
            ReduxStates.BOTTOM_SOUTH,
            ReduxStates.BOTTOM_EAST,
            ReduxStates.BOTTOM_WEST
    };

    public static @Nullable BooleanProperty getProperty(Direction d, boolean isTop) {
        return isTop ? getTopProperty(d) : getBottomProperty(d);
    }
    public static @Nullable BooleanProperty getTopProperty(Direction d) {
        return d == Direction.NORTH ? ReduxStates.TOP_NORTH :
               d == Direction.SOUTH ? ReduxStates.TOP_SOUTH :
               d == Direction.EAST ? ReduxStates.TOP_EAST :
               d == Direction.WEST ? ReduxStates.TOP_WEST : null;
    }
    public static @Nullable BooleanProperty getBottomProperty(Direction d) {
        return d == Direction.NORTH ? ReduxStates.BOTTOM_NORTH :
               d == Direction.SOUTH ? ReduxStates.BOTTOM_SOUTH :
               d == Direction.EAST ? ReduxStates.BOTTOM_EAST :
               d == Direction.WEST ? ReduxStates.BOTTOM_WEST : null;
    }
    private final ImmutableMap<BlockState, VoxelShape> shapesCache;
    public FlowerGarlandBlock(Properties properties) {
        super(properties);
        this.shapesCache = this.getShapeForEachState(FlowerGarlandBlock::calculateShapes);
        this.registerDefaultState(createDefaultState(this.stateDefinition));

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        for(BooleanProperty booleanproperty : PROPERTIES) {
            builder.add(booleanproperty);
        }

    }

    public static boolean hasFace(BlockState state, Direction direction) {
        BooleanProperty top = getTopProperty(direction);
        BooleanProperty bottom = getBottomProperty(direction);
        return top != null && bottom != null && ((state.hasProperty(top) && state.getValue(top)) || (state.hasProperty(bottom) && state.getValue(bottom)));
    }
    public static boolean hasFace(BlockState state, Direction direction, boolean isTop) {
        BooleanProperty prop = isTop ? getTopProperty(direction) : getBottomProperty(direction);
        return prop != null && state.hasProperty(prop) && state.getValue(prop);
    }


    public static boolean canAttachTo(BlockGetter level, Direction direction, BlockPos pos, BlockState state) {
        return direction.getAxis() != Direction.Axis.Y && (Block.isFaceFull(state.getBlockSupportShape(level, pos), direction.getOpposite()) || Block.isFaceFull(state.getCollisionShape(level, pos), direction.getOpposite()));
    }

    private static BlockState removeFace(BlockState state, BooleanProperty faceProp) {
        BlockState blockstate = state.setValue(faceProp, Boolean.valueOf(false));
        return hasAnyFace(blockstate) ? blockstate : Blocks.AIR.defaultBlockState();
    }

    protected static boolean hasAnyFace(BlockState state) {
        return Arrays.stream(PROPERTIES).anyMatch(booleanProperty -> state.hasProperty(booleanProperty) && state.getValue(booleanProperty));
    }

    private static boolean hasAnyVacantFace(BlockState state) {
        return Arrays.stream(PROPERTIES).anyMatch(booleanProperty -> !state.hasProperty(booleanProperty) || !state.getValue(booleanProperty));

    }
    private static BlockState createDefaultState(StateDefinition<Block, BlockState> stateDefinition) {
        BlockState blockstate = stateDefinition.any();

        for(BooleanProperty booleanproperty : PROPERTIES) {
            if (blockstate.hasProperty(booleanproperty)) {
                blockstate = blockstate.setValue(booleanproperty, Boolean.FALSE);
            }
        }
        return blockstate;
    }

    private static VoxelShape calculateShapes(BlockState state) {
        VoxelShape voxelshape = Shapes.empty();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (hasFace(state, direction, true)) {
                voxelshape = Shapes.or(voxelshape, TOP_SHAPES.get(direction));
            }
            if (hasFace(state, direction, false)) {
                voxelshape = Shapes.or(voxelshape, BOTTOM_SHAPES.get(direction));
            }
        }

        return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!hasAnyFace(state)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return hasFace(state, direction) && !canAttachTo(level, direction, neighborPos, neighborState) ? removeFace(removeFace(state, getTopProperty(direction)), getBottomProperty(direction)) : state;
        }
    }


    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean flag = false;

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (hasFace(state, direction)) {
                BlockPos blockpos = pos.relative(direction);
                if (!canAttachTo(level, direction, blockpos, level.getBlockState(blockpos))) {
                    return false;
                }

                flag = true;
            }
        }

        return flag;
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return hasAnyVacantFace(state);
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        return Arrays.stream(context.getNearestLookingDirections()).map((p_153865_) ->
                this.getStateForPlacement(blockstate, level, blockpos, p_153865_, context.getClickLocation().y() % 1 > 0.5F))
                .filter(Objects::nonNull).findFirst().orElse(null);
    }

    public boolean isValidStateForPlacement(BlockGetter level, BlockState state, BlockPos pos, Direction direction) {
        if (this.isFaceSupported(direction) && (!state.is(this) || !hasFace(state, direction))) {
            BlockPos blockpos = pos.relative(direction);
            return canAttachTo(level, direction, blockpos, level.getBlockState(blockpos));
        } else {
            return false;
        }
    }


    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapesCache.get(state);
    }

    protected boolean isFaceSupported(Direction face) {
        return face.getAxis() != Direction.Axis.Y;
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockState currentState, BlockGetter level, BlockPos pos, Direction lookingDirection, boolean top) {
        if (!this.isValidStateForPlacement(level, currentState, pos, lookingDirection)) {
            return null;
        } else {
            BlockState blockstate;
            if (currentState.is(this)) {
                blockstate = currentState;
            } else {
                blockstate = this.defaultBlockState();
            }
            BooleanProperty b = getProperty(lookingDirection, top);
            return b != null ? blockstate.setValue(b, Boolean.TRUE) : blockstate;
        }
    }

}
