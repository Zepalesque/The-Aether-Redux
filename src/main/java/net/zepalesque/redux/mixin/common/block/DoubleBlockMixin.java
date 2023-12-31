package net.zepalesque.redux.mixin.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class DoubleBlockMixin {

    private static final VoxelShape SHAPE_BASE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 32.0D, 14.0D);
    private static final VoxelShape SHAPE_TOP = Block.box(2.0D, -16.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    @Inject(method = "getShape", at = @At("RETURN"), cancellable = true)
    private void changeShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext, CallbackInfoReturnable<VoxelShape> cir)
    {
        if ((Object) this instanceof DoublePlantBlock && ReduxConfig.COMMON.change_double_plant_hitbox.get())
        {
            Vec3 vector = pState.getOffset(pLevel, pPos);
            cir.setReturnValue((pState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? SHAPE_BASE : SHAPE_TOP).move(vector.x, vector.y, vector.z));
        }
    }
}
