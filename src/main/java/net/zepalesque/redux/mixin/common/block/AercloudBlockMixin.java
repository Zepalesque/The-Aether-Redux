package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.natural.AercloudBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AercloudBlock.class)
public class AercloudBlockMixin {
    @Inject(at = @At(value = "HEAD"), method = "entityInside")
    private void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
    }
}
