package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.block.natural.ExtendedDistanceLeavesBlock;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(LeavesWithParticlesBlock.class)
public class ParticleLeavesMixin {
    @Shadow @Final private Supplier<? extends ParticleOptions> particle;

    @Inject(method = "animateTick", at = @At("RETURN"))
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci, @Share("replace") LocalBooleanRef shouldReplaceParticle) {
        if (level.isClientSide()) {
            if (shouldReplaceParticle.get()) {

                ParticleOptions newParticle = getReplacementParticle(this.particle.get());
                if (newParticle == null) {
                    throw new NullPointerException("ParticleOptions cannot be null!");
                }

                if (random.nextInt(15) == 0) {
                    BlockPos blockpos = pos.below();
                    BlockState blockstate = level.getBlockState(blockpos);
                    if ((!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) && !(blockstate.getBlock() instanceof LeavesBlock) && !(blockstate.getBlock() instanceof ExtendedDistanceLeavesBlock)) {
                        ParticleUtils.spawnParticleBelow(level, pos, random, newParticle);
                    }
                }


            }
        }
    }

    /**
     * This injector wraps the {@code level.addParticle()} method. It only executes it if this method returns
     * {@code true}.
     */
    @WrapWithCondition(method = "animateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public boolean redirectParticleOverride(Level instance, ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, @Share("replace") LocalBooleanRef shouldReplaceParticle) {
        return !shouldReplaceParticle.get();
    }

    /**
     * Injects into the {@code level.isClientSide()} check. If it is true, we continue with setting the shared variable.
     */
    @ModifyExpressionValue(method = "animateTick", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.isClientSide()Z"))
    public boolean onAnimateTick(boolean isClientSide, BlockState state, Level level, BlockPos pos, RandomSource random, @Share("replace") LocalBooleanRef shouldReplaceParticle) {
        if (isClientSide) {
            // this is a shared variable that can be used across injectors of the same method
            shouldReplaceParticle.set(ReduxConfig.CLIENT.better_leaf_particles.get() && (this.particle == AetherParticleTypes.GOLDEN_OAK_LEAVES.get() || this.particle == AetherParticleTypes.CRYSTAL_LEAVES.get() || this.particle == ReduxParticleTypes.GILDED_SKYROOT_LEAVES.get()));
        }

        return isClientSide;
    }

    @Unique
    private static <T extends ParticleOptions>  ParticleOptions getReplacementParticle(T particle)
    {
        return !ReduxConfig.CLIENT.better_leaf_particles.get() ? null : particle == AetherParticleTypes.GOLDEN_OAK_LEAVES.get() ? ReduxParticleTypes.FALLING_GOLDEN_LEAVES.get()
                : particle == AetherParticleTypes.CRYSTAL_LEAVES.get() ? ReduxParticleTypes.FALLING_CRYSTAL_LEAVES.get()
                : particle == ReduxParticleTypes.GILDED_SKYROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_GILDED_LEAVES.get() : null;
    }



}
