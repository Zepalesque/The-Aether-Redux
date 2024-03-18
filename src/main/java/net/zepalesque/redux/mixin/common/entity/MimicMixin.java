package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.entity.ReduxEntityTypes;
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

    @WrapWithCondition(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
    private boolean hurt(ServerLevel level) {
        return ((Mimic) (Object) this).getType() != ReduxEntityTypes.SKYROOT_MIMIC.get();
    }

    @Unique
    private boolean openMimic() {
        MimicAnimation.get((Mimic) (Object) this).ifPresent(MimicAnimation::open);
        return !ReduxConfig.COMMON.smaller_mimic_hitbox.get();
    }
}
