package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.mixin.mixins.common.accessor.WallBlockAccessor;

import java.util.Map;

// TODO: Replace with Nitrogen version
public class ReduxDoubleDropsWall extends WallBlock {
    public ReduxDoubleDropsWall(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
        this.fixShapeMaps();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
    }

    /**
     * Based on the Framed Blocks mod's shape map fix for implementing a wall with additional block properties.
     */
    protected void fixShapeMaps() {
        WallBlockAccessor wallBlockAccessor = (WallBlockAccessor) this;
        Map<BlockState, VoxelShape> shapeByIndex = wallBlockAccessor.redux$getShapeByIndex();
        shapeByIndex = fixShapeMap(shapeByIndex).build();
        wallBlockAccessor.redux$setShapeByIndex(shapeByIndex);

        Map<BlockState, VoxelShape> collisionShapeByIndex = wallBlockAccessor.redux$getCollisionShapeByIndex();
        collisionShapeByIndex = fixShapeMap(collisionShapeByIndex).build();
        wallBlockAccessor.redux$setCollisionShapeByIndex(collisionShapeByIndex);
    }

    protected ImmutableMap.Builder<BlockState, VoxelShape> fixShapeMap(Map<BlockState, VoxelShape> map) {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
        builder.putAll(map);
        for (BlockState state : map.keySet()) {
            builder.put(state.cycle(AetherBlockStateProperties.DOUBLE_DROPS), map.get(state));
        }
        return builder;
    }
}