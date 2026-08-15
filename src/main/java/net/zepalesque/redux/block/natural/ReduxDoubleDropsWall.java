package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.mixin.mixins.common.accessor.WallBlockAccessor;

// TODO: Replace with Nitrogen version
public class ReduxDoubleDropsWall extends WallBlock {
	public ReduxDoubleDropsWall(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
			.setValue(AetherBlockStateProperties.DOUBLE_DROPS, false)
		);
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
		var acc = (WallBlockAccessor) this;
		var shapeByIndex = acc
			.redux$getShapeByIndex();
		shapeByIndex = this.fixShapeMap(shapeByIndex);
		acc.redux$setShapeByIndex(shapeByIndex);

		var collisionShapeByIndex = acc.redux$getCollisionShapeByIndex();
		collisionShapeByIndex = this.fixShapeMap(collisionShapeByIndex);
		acc.redux$setCollisionShapeByIndex(collisionShapeByIndex);
	}

	protected ImmutableMap<BlockState, VoxelShape> fixShapeMap(Map<BlockState, VoxelShape> map) {
		ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
		builder.putAll(map);
		for (var state : map.keySet())
			builder.put(state.cycle(AetherBlockStateProperties.DOUBLE_DROPS), map.get(state));
		return builder.build();
	}
}
