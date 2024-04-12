package net.zepalesque.redux.util.player;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.entity.projectile.VolatileFireCrystal;
import net.zepalesque.redux.item.ReduxItems;

public class AbilityUtil {

    public static void doDoubleJumpMovement(LivingEntity entity) {
        double upJump = 0.35D + ((EquipmentUtil.hasCurio(entity, ReduxItems.AIRBOUND_CAPE.get()) && EquipmentUtil.getCurios(entity, ReduxItems.AIRBOUND_CAPE.get()).size() == 2) ? 0.15D : 0.1D);

        double xDelta = entity.getDeltaMovement().x() * 1.4D;
        double yDelta = upJump;
        double zDelta = entity.getDeltaMovement().z() * 1.4D;
        entity.setDeltaMovement(xDelta, yDelta, zDelta);
        spawnDoubleJumpParticles(entity.level(), entity.position().x, entity.position().y, entity.position().z, 1.5D, 12);
        entity.level().playSound(null, entity.position().x, entity.position().y, entity.position().z, ReduxSoundEvents.BOOST_JUMP.get(), SoundSource.PLAYERS, 0.4f,
                0.9F + entity.level().random.nextFloat() * 0.2F);
    }

    public static void spawnDoubleJumpParticles(Level level, double x, double y, double z, double radius, int quantity) {
        RandomSource random = level.random;

        for (int i = 0; i < quantity; i++)
        {
            double x2 = x + (random.nextDouble() * radius) - (radius * 0.5D);
            double y2 = y + (random.nextDouble() * 0.4D);
            double z2 = z + (random.nextDouble() * radius) - (radius * 0.5D);

            level.addParticle(ReduxParticleTypes.SHINY_CLOUD, x2, y2, z2, 0.0D, random.nextDouble() * 0.03D, 0.0D);
        }
    }

    public static void shootFireballs(LivingEntity entity) {
        if (EquipmentUtil.hasCurio(entity, ReduxItems.SOLAR_EMBLEM.get())) {
            if (EquipmentUtil.getCurios(entity, ReduxItems.SOLAR_EMBLEM.get()).size() == 1) {
                float offset = 0;
                float rotation = Mth.wrapDegrees(entity.getYRot() + offset);
                VolatileFireCrystal crystal = new VolatileFireCrystal(entity.level(), entity);
                crystal.setPos(entity.getX(), entity.getY() + 1, entity.getZ());
                crystal.shootFromRotation(entity, entity.getXRot(), rotation, 0.0F, 1.0F, 1.0F);
                crystal.setOwner(entity);
                if (!entity.level().isClientSide) {
                    entity.level().addFreshEntity(crystal);
                    entity.level().playSound(null, entity.position().x, entity.position().y, entity.position().z, ReduxSoundEvents.FIREBALL_SHOOT.get(), SoundSource.PLAYERS, 0.4f,
                            0.9F + entity.level().random.nextFloat() * 0.2F);

                }
            } else {
                float offset = 2.0F;
                float rotation = Mth.wrapDegrees(entity.getYRot() + offset);
                VolatileFireCrystal crystal = new VolatileFireCrystal(entity.level(), entity);
                crystal.setPos(getSidedPoition(entity, HumanoidArm.RIGHT));
                crystal.shootFromRotation(entity, entity.getXRot(), rotation, 0.0F, 1.0F, 1.0F);
                crystal.setOwner(entity);
                if (!entity.level().isClientSide) {
                    entity.level().addFreshEntity(crystal);
                    entity.level().playSound(null, entity.position().x, entity.position().y, entity.position().z, ReduxSoundEvents.FIREBALL_SHOOT.get(), SoundSource.PLAYERS, 0.4f,
                            0.9F + entity.level().random.nextFloat() * 0.2F);
                }

                float offset1 = -2.0F;
                float rotation1 = Mth.wrapDegrees(entity.getYRot() + offset1);
                VolatileFireCrystal crystal1 = new VolatileFireCrystal(entity.level(), entity);
                crystal1.setPos(getSidedPoition(entity, HumanoidArm.LEFT));
                crystal1.shootFromRotation(entity, entity.getXRot(), rotation1, 0.0F, 1.0F, 1.0F);
                crystal1.setOwner(entity);
                if (!entity.level().isClientSide) {
                    entity.level().addFreshEntity(crystal1);
                    entity.level().playSound(null, entity.position().x, entity.position().y, entity.position().z, ReduxSoundEvents.FIREBALL_SHOOT.get(), SoundSource.PLAYERS, 0.4f,
                            0.9F + entity.level().random.nextFloat() * 0.2F);


                }
            }
            entity.swing(InteractionHand.MAIN_HAND, true);
        }
    }

    public  static Vec3 getSidedPoition(LivingEntity owner, HumanoidArm side) {
        double yaw = owner.getYRot();
        if (side == HumanoidArm.RIGHT) {
            yaw -= 90.0;
        } else {
            yaw += 90.0;
        }

        yaw /= -57.29577951308232;
        double targetX = owner.getX() + Math.sin(yaw) * 1.05;
        double targetY = owner.getY() + 1.0;
        double targetZ = owner.getZ() + Math.cos(yaw) * 1.05;
        return new Vec3(targetX, targetY, targetZ);
    }
}
