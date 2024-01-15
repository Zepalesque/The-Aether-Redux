package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SkyfernBlock extends AetherBushBlock {
    protected static final VoxelShape SHAPE_FERN = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 15.0D, 13.0D);

    public SkyfernBlock(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE_FERN.move(vec3.x, vec3.y, vec3.z);
    }
}
