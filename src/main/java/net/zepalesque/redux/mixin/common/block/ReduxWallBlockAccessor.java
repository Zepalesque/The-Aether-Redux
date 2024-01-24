package net.zepalesque.redux.mixin.common.block;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(WallBlock.class)
public interface ReduxWallBlockAccessor {
    @Accessor("shapeByIndex")
    Map<BlockState, VoxelShape> getShapeByIndex();

    @Mutable
    @Accessor("shapeByIndex")
    void setShapeByIndex(Map<BlockState, VoxelShape> var1);

    @Accessor("collisionShapeByIndex")
    Map<BlockState, VoxelShape> getCollisionShapeByIndex();

    @Mutable
    @Accessor("collisionShapeByIndex")
    void setCollisionShapeByIndex(Map<BlockState, VoxelShape> var1);
}
