package net.zepalesque.redux.block.backport;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MossyCarpetBlock extends Block implements BonemealableBlock {
    public static final MapCodec<MossyCarpetBlock> CODEC = simpleCodec(MossyCarpetBlock::new);
    public static final BooleanProperty BASE = BlockStateProperties.BOTTOM;
    public static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;
    public static final Map<Direction, EnumProperty<WallSide>> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(
        Util.make(Maps.newEnumMap(Direction.class), map -> {
            map.put(Direction.NORTH, NORTH);
            map.put(Direction.EAST, EAST);
            map.put(Direction.SOUTH, SOUTH);
            map.put(Direction.WEST, WEST);
        })
    );
    private static final int AABB_OFFSET = 1;
    private static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, AABB_OFFSET, 16.0);
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, AABB_OFFSET, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.box(16.0 - AABB_OFFSET, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, AABB_OFFSET);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 16.0 - AABB_OFFSET, 16.0, 16.0, 16.0);
    private static final int SHORT_HEIGHT = 10;
    private static final VoxelShape WEST_SHORT_AABB = Block.box(0.0, 0.0, 0.0, AABB_OFFSET, SHORT_HEIGHT, 16.0);
    private static final VoxelShape EAST_SHORT_AABB = Block.box(16.0 - AABB_OFFSET, 0.0, 0.0, 16.0, SHORT_HEIGHT, 16.0);
    private static final VoxelShape NORTH_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 16.0, SHORT_HEIGHT, AABB_OFFSET);
    private static final VoxelShape SOUTH_SHORT_AABB = Block.box(0.0, 0.0, 16.0 - AABB_OFFSET, 16.0, SHORT_HEIGHT, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    @Override
    public MapCodec<MossyCarpetBlock> codec() {
        return CODEC;
    }

    public MossyCarpetBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.stateDefinition
                .any()
                .setValue(BASE, true)
                .setValue(NORTH, WallSide.NONE)
                .setValue(EAST, WallSide.NONE)
                .setValue(SOUTH, WallSide.NONE)
                .setValue(WEST, WallSide.NONE)
        );
        this.shapesCache = ImmutableMap.copyOf(
            this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), MossyCarpetBlock::calculateShape))
        );
    }


    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    private static VoxelShape calculateShape(BlockState state) {
        var shape = Shapes.empty();
        if (state.getValue(BASE)) shape = DOWN_AABB;
        shape = switch (state.getValue(NORTH)) {
            case NONE -> shape;
            case LOW -> Shapes.or(shape, NORTH_SHORT_AABB);
            case TALL -> Shapes.or(shape, NORTH_AABB);
        };

        shape = switch (state.getValue(SOUTH)) {
            case NONE -> shape;
            case LOW -> Shapes.or(shape, SOUTH_SHORT_AABB);
            case TALL -> Shapes.or(shape, SOUTH_AABB);
        };

        shape = switch (state.getValue(EAST)) {
            case NONE -> shape;
            case LOW -> Shapes.or(shape, EAST_SHORT_AABB);
            case TALL -> Shapes.or(shape, EAST_AABB);
        };

        shape = switch (state.getValue(WEST)) {
            case NONE -> shape;
            case LOW -> Shapes.or(shape, WEST_SHORT_AABB);
            case TALL -> Shapes.or(shape, WEST_AABB);
        };
        return shape.isEmpty() ? Shapes.block() : shape;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapesCache.get(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(BASE) ? DOWN_AABB : Shapes.empty();
    }


    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.below());
        return state.getValue(BASE) ? !blockstate.isAir() : blockstate.is(this) && blockstate.getValue(BASE);
    }

    private static boolean hasFaces(BlockState state) {
        if (state.getValue(BASE)) return true;
        else {
            for (EnumProperty<WallSide> enumproperty : PROPERTY_BY_DIRECTION.values())
                if (state.getValue(enumproperty) != WallSide.NONE) return true;

            return false;
        }
    }

    private static boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        return direction != Direction.UP && canAttachTo(level, pos, direction);
    }

    public static boolean canAttachTo(BlockGetter level, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.relative(direction);
        BlockState blockstate = level.getBlockState(blockpos);
        return MultifaceBlock.canAttachTo(level, direction, blockpos, blockstate);
    }

    private BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos, boolean tip) {
        BlockState blockstate = null;
        BlockState blockstate1 = null;
        tip |= state.getValue(BASE);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            EnumProperty<WallSide> enumproperty = getPropertyForFace(direction);
            Objects.requireNonNull(enumproperty);
            WallSide wallside = canSupportAtFace(level, pos, direction)
                ? tip ? WallSide.LOW : state.getValue(enumproperty)
                : WallSide.NONE;
            if (wallside == WallSide.LOW) {
                if (blockstate == null) blockstate = level.getBlockState(pos.above());

                if (blockstate.is(this) && blockstate.getValue(enumproperty) != WallSide.NONE && !blockstate.getValue(BASE))
                    wallside = WallSide.TALL;

                if (!state.getValue(BASE)) {
                    if (blockstate1 == null) blockstate1 = level.getBlockState(pos.below());

                    if (blockstate1.is(this) && blockstate1.getValue(enumproperty) == WallSide.NONE)
                        wallside = WallSide.NONE;
                }
            }

            state = state.setValue(enumproperty, wallside);
        }

        return state;
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getUpdatedState(this.defaultBlockState(), context.getLevel(), context.getClickedPos(), true);
    }
    

    /**
     * TODO: Mixin into {@link net.minecraft.world.level.levelgen.feature.SimpleBlockFeature}
     */
    @SuppressWarnings("unused") // TODO: remove annotation
    public void placeAt(LevelAccessor level, BlockPos pos, RandomSource random, int flags) {
        BlockState blockstate = this.defaultBlockState();
        BlockState blockstate1 = getUpdatedState(blockstate, level, pos, true);
        level.setBlock(pos, blockstate1, 3);
        BlockState blockstate2 = createTopperWithSideChance(level, pos, random::nextBoolean);
        if (!blockstate2.isAir()) level.setBlock(pos.above(), blockstate2, flags);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (!level.isClientSide) {
            RandomSource randomsource = level.getRandom();
            BlockState blockstate = createTopperWithSideChance(level, pos, randomsource::nextBoolean);
            if (!blockstate.isAir()) level.setBlock(pos.above(), blockstate, 3);
        }
    }

    private BlockState createTopperWithSideChance(BlockGetter level, BlockPos pos, BooleanSupplier placeSide) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = level.getBlockState(blockpos);
        boolean flag = blockstate.is(this);
        if ((!flag || !blockstate.getValue(BASE)) && (flag || blockstate.canBeReplaced())) {
            BlockState blockstate1 = this.defaultBlockState().setValue(BASE, true);
            BlockState blockstate2 = getUpdatedState(blockstate1, level, pos.above(), true);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                EnumProperty<WallSide> enumproperty = getPropertyForFace(direction);
                Objects.requireNonNull(enumproperty);
                if (blockstate2.getValue(enumproperty) != WallSide.NONE && !placeSide.getAsBoolean())
                    blockstate2 = blockstate2.setValue(enumproperty, WallSide.NONE);
            }

            return hasFaces(blockstate2) && blockstate2 != blockstate ? blockstate2 : Blocks.AIR.defaultBlockState();
        } else return Blocks.AIR.defaultBlockState();
    }


    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) return Blocks.AIR.defaultBlockState();
        else {
            BlockState blockstate = getUpdatedState(state, level, pos, false);
            return !hasFaces(blockstate) ? Blocks.AIR.defaultBlockState() : blockstate;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BASE, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    // Ahh yes
    @Deprecated
    @SuppressWarnings("deprecation")
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> state.setValue(NORTH, state.getValue(SOUTH))
            .setValue(EAST, state.getValue(WEST))
            .setValue(SOUTH, state.getValue(NORTH))
            .setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, state.getValue(EAST))
            .setValue(EAST, state.getValue(SOUTH))
            .setValue(SOUTH, state.getValue(WEST))
            .setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90 -> state.setValue(NORTH, state.getValue(WEST))
            .setValue(EAST, state.getValue(NORTH))
            .setValue(SOUTH, state.getValue(EAST))
            .setValue(WEST, state.getValue(SOUTH));
            default -> state;
        };
    }

    @Override
    // Ahh yes
    @Deprecated
    @SuppressWarnings("deprecation")
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    @Nullable public static EnumProperty<WallSide> getPropertyForFace(Direction direction) {
        return PROPERTY_BY_DIRECTION.get(direction);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(BASE) && !createTopperWithSideChance(level, pos, () -> true).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState blockstate = createTopperWithSideChance(level, pos, () -> true);
        if (!blockstate.isAir()) level.setBlock(pos.above(), blockstate, 3);
    }
}
