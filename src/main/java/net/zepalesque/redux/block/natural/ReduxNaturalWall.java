package net.zepalesque.redux.block.natural;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.state.ReduxStates;

import java.util.Map;

public class ReduxNaturalWall extends ReduxDoubleDropsWall {
    public ReduxNaturalWall(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.NATURAL_GEN, false));
        this.fixShapeMaps();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.NATURAL_GEN);
    }

    @Override
    protected ImmutableMap.Builder<BlockState, VoxelShape> fixShapeMap(Map<BlockState, VoxelShape> map) {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = super.fixShapeMap(map);
        for (BlockState state : map.keySet()) {
            builder.put(state.cycle(ReduxStates.NATURAL_GEN), map.get(state));
        }
        return builder;
    }
}
