package net.zepalesque.redux.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class VeridiumLanternBlock extends LanternBlock {

    public static final Property<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    protected static final VoxelShape BOX = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D), Block.box(6.0D, 10.0D, 6.0D, 10.0D, 12.0D, 10.0D));
    protected static final VoxelShape HANGING_BOX = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 11.0D, 11.0D), Block.box(6.0D, 11.0D, 6.0D, 10.0D, 13.0D, 10.0D));

    public VeridiumLanternBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HANGING) ? HANGING_BOX : BOX;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        @Nullable BlockState superState = super.getStateForPlacement(context);
        return superState == null ? null : superState.setValue(AXIS, context.getHorizontalDirection().getAxis());
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS);
    }
}
