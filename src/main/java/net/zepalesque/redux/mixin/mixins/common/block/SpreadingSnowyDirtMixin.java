package net.zepalesque.redux.mixin.mixins.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtMixin {
    
    @Inject(method = "canPropagate", at = @At(value = "HEAD"))
    private static void canPropogate(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
    
    }
    
}
