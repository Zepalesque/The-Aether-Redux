package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AercloudBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// TODO: sounds, also on 1.20.1 with genesis
@Mixin(AercloudBlock.class)
public class AercloudBlockMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V", shift = At.Shift.AFTER), method = "entityInside", cancellable = true)
    private void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        AercloudBlock block = (AercloudBlock) (Object) this;
        if (block == AetherBlocks.GOLDEN_AERCLOUD.get() && ReduxConfig.COMMON.gold_aercloud_ability.get()) {
            if (!entity.isShiftKeyDown()) {
                entity.setDeltaMovement(entity.getDeltaMovement().x(), -2.0, entity.getDeltaMovement().z());
                level.playSound((entity instanceof Player player ? player : null), pos, ReduxSoundEvents.GOLD_AERCLOUD_WHOOSH.get(), SoundSource.BLOCKS, 0.8f,
                        0.9f + (level.random.nextFloat() * 0.2f));
            }
            ci.cancel();
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getDefaultCollisionShape", cancellable = true, remap = false)
    private void getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        AercloudBlock block = (AercloudBlock) (Object) this;
        if (block == AetherBlocks.GOLDEN_AERCLOUD.get() && ReduxConfig.COMMON.gold_aercloud_ability.get()) {
            cir.setReturnValue(Shapes.empty());
        }
    }
}
