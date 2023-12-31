package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OffsetBushBlock extends AetherBushBlock {
    protected static final VoxelShape SHAPE_BUSH = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public OffsetBushBlock(Properties properties) {
        super(properties);
    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE_BUSH.move(vec3.x, vec3.y, vec3.z);
    }
}
