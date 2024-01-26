package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.mixin.mixins.common.accessor.EntityAccessor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mimic.class)
public class MimicMixin {

    @Redirect(method = "spawnAnim", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/EntityUtil;spawnSummoningExplosionParticles(Lnet/minecraft/world/entity/Entity;)V"))
    private void open(Entity entity) {
        if ((Object) this instanceof Mimic mimic) {
            MimicAnimation.get(mimic).ifPresent(MimicAnimation::open);
        }
        if (!ReduxConfig.COMMON.smaller_mimic_hitbox.get())
        {
            RandomSource random = ((EntityAccessor) entity).aether$getRandom();
            for (int i = 0; i < 20; ++i) {
                double d0 = random.nextGaussian() * 0.02;
                double d1 = random.nextGaussian() * 0.02;
                double d2 = random.nextGaussian() * 0.02;
                double d3 = 10.0;
                double x = entity.getX(0.0) - d0 * d3;
                double y = entity.getRandomY() - d1 * d3;
                double z = entity.getRandomZ(1.0) - d2 * d3;
                entity.level().addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
            }
        }
    }
    @Redirect(method = "handleEntityEvent", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/EntityUtil;spawnSummoningExplosionParticles(Lnet/minecraft/world/entity/Entity;)V"))
    private void openEvent(Entity entity) {
        if ((Object) this instanceof Mimic mimic) {
            MimicAnimation.get(mimic).ifPresent(MimicAnimation::open);
        }
        if (!ReduxConfig.COMMON.smaller_mimic_hitbox.get())
        {
            RandomSource random = ((EntityAccessor) entity).aether$getRandom();
            for (int i = 0; i < 20; ++i) {
                double d0 = random.nextGaussian() * 0.02;
                double d1 = random.nextGaussian() * 0.02;
                double d2 = random.nextGaussian() * 0.02;
                double d3 = 10.0;
                double x = entity.getX(0.0) - d0 * d3;
                double y = entity.getRandomY() - d1 * d3;
                double z = entity.getRandomZ(1.0) - d2 * d3;
                entity.level().addParticle(ParticleTypes.POOF, x, y, z, d0, d1, d2);
            }
        }
    }
}
