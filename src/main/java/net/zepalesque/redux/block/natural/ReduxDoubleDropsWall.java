package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.mixin.common.block.ReduxWallBlockAccessor;

import java.util.Map;

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

    private void fixShapeMaps() {
        ReduxWallBlockAccessor reduxWallBlockAccessor = (ReduxWallBlockAccessor) this;
        Map<BlockState, VoxelShape> shapeByIndex = reduxWallBlockAccessor.getShapeByIndex();
        shapeByIndex = fixShapeMap(shapeByIndex);
        reduxWallBlockAccessor.setShapeByIndex(shapeByIndex);

        Map<BlockState, VoxelShape> collisionShapeByIndex = reduxWallBlockAccessor.getCollisionShapeByIndex();
        collisionShapeByIndex = fixShapeMap(collisionShapeByIndex);
        reduxWallBlockAccessor.setCollisionShapeByIndex(collisionShapeByIndex);
    }

    private static Map<BlockState, VoxelShape> fixShapeMap(Map<BlockState, VoxelShape> map) {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
        builder.putAll(map);
        for (BlockState state : map.keySet()) {
            builder.put(state.cycle(AetherBlockStateProperties.DOUBLE_DROPS), map.get(state));
        }
        return builder.build();
    }
}
