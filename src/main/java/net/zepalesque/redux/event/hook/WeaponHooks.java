package net.zepalesque.redux.event.hook;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.SubzeroArrowHitGroundPacket;

public class WeaponHooks {


    public static void subzeroArrowHit(HitResult result, Projectile projectile) {
        if (projectile instanceof AbstractArrow abstractArrow) {
            if (result instanceof EntityHitResult entityHitResult) {
                Entity impactedEntity = entityHitResult.getEntity();
                if (impactedEntity.getType() == EntityType.ENDERMAN) {
                    return;
                }
                SubzeroArrow.get(abstractArrow).ifPresent(subzeroArrow -> {
                    if (subzeroArrow.isSubzeroArrow() && subzeroArrow.getSlownessTime() > 0) {
                        if (impactedEntity instanceof LivingEntity living && living != abstractArrow.getOwner()) {
                            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, subzeroArrow.getSlownessTime(), 2));
                        }

                    }
                });
            }

            SubzeroArrow.get(abstractArrow).ifPresent(subzeroArrow -> {
                if (subzeroArrow.isSubzeroArrow()) {
                    for (int i = 0; i < 10; i++) {
                        if (!subzeroArrow.hitGround()) {
                            abstractArrow.level.addParticle(ReduxParticleTypes.ICE_SHARD.get(), abstractArrow.getX(), abstractArrow.getY(), abstractArrow.getZ(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            });
            if (!abstractArrow.level.isClientSide) {
                ReduxPacketHandler.sendToAll(new SubzeroArrowHitGroundPacket(abstractArrow.getId()));
            }
        }
    }
}
