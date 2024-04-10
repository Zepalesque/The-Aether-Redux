package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether_genesis.block.natural.OrangeTreeBlock;
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
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OrangeTreeBlock.class)
public class OrangeTreeMixin extends BlockBehaviourMixin {

    private static final @Unique VoxelShape redux$shapeStage2 = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 26.0D, 14.0D);
    private static final @Unique VoxelShape redux$shape = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 30.0D, 14.0D);

    @Override
    protected void redux$getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext, CallbackInfoReturnable<VoxelShape> cir) {
        int age = state.getValue(OrangeTreeBlock.AGE);
        @Nullable VoxelShape shape = age >= 2 ? age == 2 ? redux$shapeStage2 : redux$shape : null;
        if (shape != null) {
            cir.setReturnValue((state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? shape : shape.move(0, -1, 0)));
        }
    }
    
}
