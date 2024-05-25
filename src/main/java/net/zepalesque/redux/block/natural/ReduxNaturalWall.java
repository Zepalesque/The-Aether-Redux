package net.zepalesque.redux.block.natural;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.mixin.mixins.common.accessor.WallBlockAccessor;

import java.util.Map;

public class ReduxNaturalWall extends ReduxDoubleDropsWall {
    public ReduxNaturalWall(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.NATURAL_GEN, false));
        this.fixShapeMaps2();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.NATURAL_GEN);
    }

    private void fixShapeMaps2() {
        WallBlockAccessor accessor = (WallBlockAccessor) this;
        Map<BlockState, VoxelShape> shapeByIndex = accessor.redux$getShapeByIndex();
        shapeByIndex = fixShapeMap2(shapeByIndex);
        accessor.redux$setShapeByIndex(shapeByIndex);

        Map<BlockState, VoxelShape> collisionShapeByIndex = accessor.redux$getCollisionShapeByIndex();
        collisionShapeByIndex = fixShapeMap2(collisionShapeByIndex);
        accessor.redux$setCollisionShapeByIndex(collisionShapeByIndex);
    }

    private static Map<BlockState, VoxelShape> fixShapeMap2(Map<BlockState, VoxelShape> map) {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
        builder.putAll(map);
        for (BlockState state : map.keySet()) {
            builder.put(state.cycle(ReduxStates.NATURAL_GEN), map.get(state));
        }
        return builder.build();
    }

}
