package net.zepalesque.redux.block.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CommonPlantBounds {

    public static final VoxelShape BUSH = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
    public static final VoxelShape FLOWER_CLUSTER = Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);
    public static final VoxelShape FLOWER = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    public static final VoxelShape FERN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    public static final VoxelShape SHORT_FERN = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);

}
