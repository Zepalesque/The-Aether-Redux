package net.zepalesque.redux.mixin.mixins.client.block;

import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.api.WeightedParticleEntry;
import net.zepalesque.redux.block.natural.leaves.LeafParticleUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesWithParticlesBlock.class)
public class ParticleLeavesMixin {

    // Invoke as we are already on the client, don't need an extra isClient call
    @Inject(method = "animateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z"), cancellable = true)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        @Nullable WeightedParticleEntry entry = LeafParticleUtil.findEntry((LeavesWithParticlesBlock) (Object) this);
        if (entry != null) {
            LeafParticleUtil.createParticle(state, level, pos, random, entry);
            ci.cancel();
        }
    }
}
