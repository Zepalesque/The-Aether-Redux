package net.zepalesque.redux.mixin.mixins.client.block;

import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.block.natural.leaves.LeavesParticleUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesWithParticlesBlock.class)
public class ParticleLeavesMixin {

    @Inject(method = "animateTick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/block/LeavesBlock;animateTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V"), cancellable = true)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        @Nullable Pair<ParticleOptions, Integer> pair = LeavesParticleUtil.particleFor((LeavesWithParticlesBlock) (Object) this);
        if (pair != null) {
            LeavesParticleUtil.createParticle(state, level, pos, random, pair.getFirst(), pair.getSecond());
            ci.cancel();
        }
    }
}
