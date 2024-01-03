package net.zepalesque.redux.entity.projectile;

import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
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
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import org.jetbrains.annotations.Nullable;

public class Ember extends Projectile {
   public Ember(EntityType<? extends Ember> entityType, Level level) {
      super(entityType, level);
   }

   private @Nullable LivingEntity source;
   public Ember(Level level, Player owner) {
      this(ReduxEntityTypes.EMBER.get(), level);
      this.setOwner(owner);
   }

   public Ember(Level level, Player owner, LivingEntity source) {
      this(level, owner);
      this.source = source;

   }

   public @Nullable LivingEntity getEmberSource() {
      return this.source;
   }

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
      float f = 0.99F;
      float f1 = 0.06F;

      this.setDeltaMovement(vec3.scale((double)0.99F));
      if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult))
         this.onHit(hitresult);
      if (!this.isNoGravity()) {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
      }
      if (hitresult.getType() == HitResult.Type.MISS) {
         this.setPos(d0, d1, d2);
      }
   }

   public Vec3 bounceVector(Vec3 velocity, Direction face) {
      Vec3 normal = new Vec3(face.getStepX(), face.getStepY(), face.getStepZ());
      double mult = velocity.x * normal.x + velocity.y * normal.y + velocity.z * normal.z;
      return new Vec3(
              velocity.x - 2 * mult * normal.x,
              velocity.y - 2 * mult * normal.y,
              velocity.z - 2 * mult * normal.z
      );
   }

   public Vec3 bounceAxis(Vec3 velocity, Direction direction) {
      Direction.Axis axis = direction.getAxis();
      double x = velocity.x;
      double y = velocity.y;
      double z = velocity.z;

      if (axis == Direction.Axis.X) {
         x = -x;
      } else if (axis == Direction.Axis.Y) {
         y = -y;
      } else if (axis == Direction.Axis.Z) {
         z = -z;
      }

      return new Vec3(x, y, z);
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      Entity entity = this.getOwner();
      if (entity instanceof LivingEntity livingentity && livingentity != this.getEmberSource()) {
         result.getEntity().hurt(ReduxDamageTypes.entitySource(this.level(), ReduxDamageTypes.EMBER, this.getOwner()), 1.0F);
      }

   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      Vec3 velocity = this.getDeltaMovement();
      velocity = velocity.multiply(Math.abs(velocity.x)>1e-4 ? 1 : 0, Math.abs(velocity.y)>1e-4 ? 1 : 0, Math.abs(velocity.z)>1e-4 ? 1 : 0);
      Vec3 bounce = this.bounceAxis(velocity, result.getDirection());
      this.setDeltaMovement(bounce.scale(0.33D));
      this.setPos(result.getLocation());
   }



   protected void defineSynchedData() {
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      double d0 = packet.getXa();
      double d1 = packet.getYa();
      double d2 = packet.getZa();

      this.setDeltaMovement(d0, d1, d2);
   }
}
