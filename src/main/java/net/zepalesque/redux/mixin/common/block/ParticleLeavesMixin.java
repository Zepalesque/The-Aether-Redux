package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(LeavesWithParticlesBlock.class)
public class ParticleLeavesMixin {


    @Inject(method = "animateTick", at = @At("RETURN"), cancellable = true)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        Supplier<? extends ParticleOptions> particle = ((ParticleLeavesAccessor) (Object) this).getParticle();
        if (level.isClientSide()) {
            if (shouldReplaceParticle(particle.get())) {

                ParticleOptions newParticle = getReplacementParticle(particle.get());
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

    @Redirect(method = "animateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public void redirectParticleOverride(Level instance, ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {

        if (instance.isClientSide() && !shouldReplaceParticle(pParticleData))
        {
            instance.addParticle(pParticleData, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        }


    }

    private static <T extends ParticleOptions>  boolean shouldReplaceParticle(T particle)
    {
        return ReduxConfig.CLIENT.better_leaf_particles.get() && (particle == AetherParticleTypes.GOLDEN_OAK_LEAVES.get() || particle == AetherParticleTypes.CRYSTAL_LEAVES.get() || particle == ReduxParticleTypes.GILDED_SKYROOT_LEAVES.get());
    }

    private static <T extends ParticleOptions>  ParticleOptions getReplacementParticle(T particle)
    {
        return !ReduxConfig.CLIENT.better_leaf_particles.get() ? null : particle == AetherParticleTypes.GOLDEN_OAK_LEAVES.get() ? ReduxParticleTypes.FALLING_GOLDEN_LEAVES.get()
                : particle == AetherParticleTypes.CRYSTAL_LEAVES.get() ? ReduxParticleTypes.FALLING_CRYSTAL_LEAVES.get()
                : particle == ReduxParticleTypes.GILDED_SKYROOT_LEAVES.get() ? ReduxParticleTypes.FALLING_GILDED_LEAVES.get() : null;
    }



}
