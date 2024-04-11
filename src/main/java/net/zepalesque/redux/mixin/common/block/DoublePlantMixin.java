package net.zepalesque.redux.mixin.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoublePlantBlock.class)
public class DoublePlantMixin extends BlockBehaviourMixin {

    @Unique
    private static final VoxelShape redux$shape = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 32.0D, 14.0D);

    @Override
    protected void redux$getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext, CallbackInfoReturnable<VoxelShape> cir) {
        Vec3 vector = pState.getOffset(pLevel, pPos);
        cir.setReturnValue((pState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? redux$shape : redux$shape.move(0, -1, 0)).move(vector.x, vector.y, vector.z));
    }

}
