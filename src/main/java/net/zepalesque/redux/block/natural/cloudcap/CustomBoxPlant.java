package net.zepalesque.redux.block.natural.cloudcap;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomBoxPlant extends AetherBushBlock /*implements BonemealableBlock TODO: spread patch when bonemealed?*/ {

    protected final VoxelShape box;


    public CustomBoxPlant(Properties pProperties, VoxelShape shape) {
        super(pProperties);
        this.box = shape;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return this.box.move(vec3.x, vec3.y, vec3.z);
    }
}
