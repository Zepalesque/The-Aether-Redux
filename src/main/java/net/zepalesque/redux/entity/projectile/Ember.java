
package net.zepalesque.redux.entity.projectile;

import com.aetherteam.nitrogen.entity.BossMob;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.zenith.util.math.VectorUtil;
import org.jetbrains.annotations.Nullable;

public class Ember extends Projectile {
    public Ember(EntityType<? extends Ember> entityType, Level level) {
        super(entityType, level);
    }
    public static final Vec3 VELOCITY_THRESHOLD = new Vec3(0.05D, 0.05D, 0.05D);

    public static final Vec3 BOUNCE_FRICTION = new Vec3(0.75D, 0.7D, 0.75D);

    public static final double GRAVITY = -0.04D;

    private @Nullable UUID source;
    private final Set<UUID> hitEntities = new HashSet<>();
    public final int lifetime = 80;
    public Ember(Level level, Player owner) {
        this(ReduxEntities.EMBER.get(), level);
        this.setOwner(owner);
    }

    public Ember(Level level, Player owner, Entity source) {
        this(level, owner);
        this.setEmberSource(source);

    }

    protected void hit(Entity entity) {
        this.hitEntities.add(entity.getUUID());
    }

    protected void setEmberSource(Entity entity) {
        this.source = entity.getUUID();
    }

    protected boolean originatedFrom(Entity entity) {
        return entity.getUUID().equals(this.source);
    }

    protected boolean hasHit(Entity entity) {
        return !this.hitEntities.isEmpty() && this.hitEntities.contains(entity.getUUID());
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    /**
     * Called to update the entity's position/logic.
     */

    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        this.setDeltaMovement(vec3.multiply(0.999D, 0.99D, 0.999D));
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult))
            this.onHit(hitresult);

        if (hitresult.getType() == HitResult.Type.MISS) {
            if (!this.isNoGravity()) this.setDeltaMovement(this.getDeltaMovement().add(0.0D, GRAVITY, 0.0D));
            this.setPos(d0, d1, d2);
        }

        if (this.tickCount >= this.lifetime && !this.isRemoved()) this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void remove(RemovalReason reason) {
        this.hitEntities.clear();
        super.remove(reason);
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        if (this.source != null) compound.putUUID("Source", this.source);

        if (!this.hitEntities.isEmpty()) {
            ListTag hits = new ListTag();

            List<IntArrayTag> list = this.hitEntities.stream().map(NbtUtils::createUUID).toList();
            for (int i = 0; i < list.size(); i++) {
                IntArrayTag arrayTag = list.get(i);
                hits.addTag(i, arrayTag);
            }

            compound.put("Hits", hits);
        }
    }
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.hasUUID("Source")) this.source = compound.getUUID("Source");

        if (compound.contains("Hits") && compound.get("Hits") instanceof ListTag hits)
            for (Tag tag : hits)
                if (tag.getType() == IntArrayTag.TYPE)
                    this.hitEntities.add(NbtUtils.loadUUID(tag));
    }

     public static Vec3 bounceVector(Vec3 velocity, Vec3 normal) {
        double multiplier = velocity.x * normal.x + velocity.y * normal.y + velocity.z * normal.z;
        return new Vec3(
                velocity.x - 2 * multiplier * normal.x,
                velocity.y - 2 * multiplier * normal.y,
                velocity.z - 2 * multiplier * normal.z
        );
    }

    public static Vec3 bounceAxis(Vec3 velocity, Direction direction) {
        Direction.Axis axis = direction.getAxis();
        double x = velocity.x;
        double y = velocity.y;
        double z = velocity.z;

        switch (axis) {
            case X -> x = -x;
            case Y -> y = -y;
            case Z -> z = -z;
        }

        return new Vec3(x, y, z);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity livingentity && !this.ownedBy(livingentity) && !this.originatedFrom(livingentity) && !this.hasHit(livingentity) && !(livingentity instanceof BossMob<?>)) {
            // TODO
            // livingentity.hurt(ReduxDamageTypes.entitySource(this.level(), ReduxDamageTypes.EMBER, this.getOwner()), 1.0F);
            this.hit(livingentity);
        }

    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Direction dir = result.getDirection();
        Direction.Axis axis = dir.getAxis();
        Vec3 loc = result.getLocation();
        Vec3 velocity = this.getDeltaMovement();

        velocity = VectorUtil.threshold(velocity, VELOCITY_THRESHOLD);
        Vec3 bounce = bounceAxis(velocity, dir);
        // How much the particles should be spread
        double spread = velocity.length() * 2.5;
        // Spawn spark particles
        for (int i = 0; i < Mth.floor(velocity.length() * 15); i++) {
            // Random radian angle
            float theta = this.level().getRandom().nextFloat() * 2 * Mth.PI;

            // trigonometry, how fun !! trigonometry dash 2.2 when

            double sin = Mth.sin(theta) * spread;
            double cos = Mth.cos(theta) * spread;

            switch (axis) {
                case X -> this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), velocity.length(), sin, cos);
                case Y -> this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), sin, velocity.length(), cos);
                case Z -> this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), sin, cos, velocity.length());
            }
        }

        SoundEvent sound = velocity.length() <= 0.75 ? ReduxSounds.EMBER_BOUNCE_SMALL.get()
                : velocity.length() <= 1.5 ? ReduxSounds.EMBER_BOUNCE_MEDIUM.get()
                : ReduxSounds.EMBER_BOUNCE_BIG.get();

        this.level().playSound(null, loc.x(), loc.y(), loc.z(), sound, SoundSource.NEUTRAL, (float) (velocity.length() * 10D), 0.8F + this.level().random.nextFloat() * 0.4F);

        Vec3 modified = bounce.multiply(Ember.BOUNCE_FRICTION);
        this.setDeltaMovement(modified);
        this.setPos(loc);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);

        double dx = packet.getXa();
        double dy = packet.getYa();
        double dz = packet.getZa();

        this.setDeltaMovement(dx, dy, dz);
    }
}
