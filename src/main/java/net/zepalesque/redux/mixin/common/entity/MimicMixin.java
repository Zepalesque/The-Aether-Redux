package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.mixin.mixins.common.accessor.EntityAccessor;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mimic.class)
public class MimicMixin {

    @WrapWithCondition(method = "spawnAnim", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/EntityUtil;spawnSummoningExplosionParticles(Lnet/minecraft/world/entity/Entity;)V"))
    private boolean open(Entity entity) {
        return this.openMimic();
    }

    @WrapWithCondition(method = "handleEntityEvent", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/EntityUtil;spawnSummoningExplosionParticles(Lnet/minecraft/world/entity/Entity;)V"))
    private boolean openEvent(Entity entity) {
        return this.openMimic();
    }

    @Unique
    private boolean openMimic() {
        MimicAnimation.get((Mimic) (Object) this).ifPresent(MimicAnimation::open);
        return !ReduxConfig.COMMON.smaller_mimic_hitbox.get();
    }
}
