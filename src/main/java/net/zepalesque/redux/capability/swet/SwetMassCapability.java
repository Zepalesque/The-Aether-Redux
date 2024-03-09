package net.zepalesque.redux.capability.swet;

import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.event.hook.SwetHooks;
import net.zepalesque.redux.misc.ReduxTags;

// TODO
public class SwetMassCapability implements SwetMass {
    protected static final AttributeModifier knockbackResistanceModifier = new AttributeModifier(
            "Temporary swet knockback resistance",
            1,
            AttributeModifier.Operation.ADDITION);
    private final Swet swet;
    protected float massStuck = 0;

    
    public SwetMassCapability(Swet swet) {
        this.swet = swet;
    }


    @Override
    public Swet getSwet() {
        return this.swet;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    }

    public void tick() {
        if (ReduxConfig.COMMON.improved_swet_behavior.get() && !this.getSwet().isDeadOrDying()) {
            massStuck = 0;
            this.getSwet().level().getEntities(this.getSwet(), this.getSwet().getBoundingBox().inflate(0.9, 0.9, 0.9)).forEach((entity) -> {
                AABB box = entity.getBoundingBox();
                massStuck += box.getXsize() * box.getYsize() * box.getZsize();
            });
            this.getSwet().level().getEntities(this.getSwet(), this.getSwet().getBoundingBox()).forEach(this::onEntityCollision);
        }
    }

    protected void onEntityCollision(Entity entity) {
        // special absorption rules
        if (entity instanceof Swet || entity.getType().is(ReduxTags.EntityTypes.SWET_PASSTHROUGH)) {
            return;
        }
        // Make items ride the swet. They often shake free with the jiggle physics
        if (entity instanceof ItemEntity item) {
            if (SwetHooks.shouldGrow(item.getItem(), this.getSwet().getType())) {
                this.getSwet().setSize(this.getSwet().getSize() + 1, false);
                item.remove(Entity.RemovalReason.KILLED);
                return;
            }
            item.startRiding(this.getSwet(), true);
            return;
        }
        if (entity instanceof LivingEntity livingEntity && EquipmentUtil.hasSwetCape(livingEntity)) {
            return;
        }
        boolean absorbable = isAbsorbable(entity, this.getSwet().level());
        if (SwetHooks.canAbsorbEntities(this.getSwet()) && absorbable) {
            // The higher this number, the stiffer the wobble is
            if (massStuck < 1) {
                massStuck = 1;
            }
            // dampened oscillator (nonlinear restoring force): x'' = -μx' - kx
            Vec3 center = this.getSwet().getBoundingBox().getCenter().add(0,0.45F * this.getSwet().getBoundingBox().getYsize() - (this.getSwet().getSize() == 0 ? -0.25F : 1),0);
            Vec3 suckVelocity = // acceleration (x'')
                    center.subtract(entity.position()) // entity displacement (-x)
                            .scale(Mth.clamp(0.25 + massStuck / 100, 0, 1)) // coefficient (k)
                            .add(
                                    this.getSwet().getDeltaMovement().subtract(entity.getDeltaMovement()) // delta velocity (-x')
                                            .scale(0.45 / massStuck / SwetHooks.getAbsorbVectorScale(this.getSwet())) // coefficient (μ)
                            );

            double maxSpeed = SwetHooks.getAbsorbVectorScale(this.getSwet()) * 0.1 + 0.25;
            if (suckVelocity.length() != 0) {
                // clamp the suck velocity
                suckVelocity = suckVelocity.scale(Math.min(1, maxSpeed / suckVelocity.length()));
            }

            entity.setDeltaMovement(entity.getDeltaMovement().add(suckVelocity));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }

        if (entity instanceof LivingEntity livingEntity && SwetHooks.canAbsorbEntities(this.getSwet())) {
            // Hack to prevent knockback; TODO: find a better way to prevent knockback
            AttributeInstance knockbackResistance = livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (absorbable && knockbackResistance != null) {
                knockbackResistance.addTransientModifier(knockbackResistanceModifier);
                this.damage(livingEntity);
                knockbackResistance.removeModifier(knockbackResistanceModifier);
            } else {
                this.damage(livingEntity);
            }

        }
    }

    protected void damage(LivingEntity livingEntity) {
        if (livingEntity.hurt(ReduxDamageTypes.entitySource(this.getSwet().level(), ReduxDamageTypes.SWET, this.getSwet()), SwetHooks.getDamage(this.getSwet()))) {
            this.getSwet().playSound(ReduxSoundEvents.SWET_ATTACK.get(), 1.0F, (this.getSwet().getRandom().nextFloat() - this.getSwet().getRandom().nextFloat()) * 0.2F + 1.0F);
        }
    }


    protected static boolean isAbsorbable(Entity entity, Level world) {
        if (entity.canBeCollidedWith()) {
            return false;
        }

        if (!((entity instanceof LivingEntity)
                || (entity instanceof PrimedTnt)
                || (entity instanceof MinecartTNT)
                || (entity instanceof FloatingBlockEntity)
                /* ArmorStands are LivingEntities */
        )) {
            return false;
        }

        boolean canPickupNonPlayers = world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
        boolean isPet = (entity instanceof TamableAnimal pet && pet.isTame());
        boolean isEligiblePlayer = (entity instanceof Player player && !player.getAbilities().flying);
        boolean isEligiblePet = isPet && world.getDifficulty() != Difficulty.EASY;
        boolean isEligibleNonPlayer = !(entity instanceof Player || isPet) && canPickupNonPlayers;

        return !entity.isShiftKeyDown() && (isEligiblePlayer || isEligiblePet || isEligibleNonPlayer);
    }


}
