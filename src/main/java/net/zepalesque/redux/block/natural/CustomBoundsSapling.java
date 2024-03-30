package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBoundsSapling extends SaplingBlock {

    protected final VoxelShape shape;

    public CustomBoundsSapling(VoxelShape shape, AbstractTreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
        this.shape = shape;
    }


    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shape;
    }
}
