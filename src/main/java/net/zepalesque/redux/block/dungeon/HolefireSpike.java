//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.block.dungeon;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;

import java.util.Map;

public class HolefireSpike extends DirectionalBlock implements SimpleWaterloggedBlock{

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape DOWN_AABB = Block.box(4.0, 5.0, 4.0, 12.0, 16.0, 12.0);
    protected static final VoxelShape UP_AABB = Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0);
    protected static final VoxelShape NORTH_AABB = Block.box(4.0, 4.0, 5.0, 12.0, 12.0, 16.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 11.0);
    protected static final VoxelShape WEST_AABB = Block.box(5.0, 4.0, 4.0, 16.0, 12.0, 12.0);
    protected static final VoxelShape EAST_AABB = Block.box(0.0, 4.0, 4.0, 11.0, 12.0, 12.0);
    private static final Map<Direction, VoxelShape> SHAPES = new ImmutableMap.Builder<Direction, VoxelShape>()
            .put(Direction.DOWN, DOWN_AABB)
            .put(Direction.UP, UP_AABB)
            .put(Direction.NORTH, NORTH_AABB)
            .put(Direction.SOUTH, SOUTH_AABB)
            .put(Direction.WEST, WEST_AABB)
            .put(Direction.EAST, EAST_AABB).build();

    public HolefireSpike(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighbor, level, currentPos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (!entity.level().isClientSide() && entity.getBoundingBox().intersects(getShape(state, level, pos, CollisionContext.of(entity)).bounds().move(pos).inflate(0.125))) {
            entity.hurt(ReduxDamageTypes.source(level, ReduxDamageTypes.SPIKE), 3F);
        }
    }
}
